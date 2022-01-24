package com.luisansal.jetpack.domain.usecases

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.data.repository.VisitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserUseCase(private val userRepository: UserRepository, private val visitRepository: VisitRepository) {
    private var auth: FirebaseAuth = Firebase.auth
    val currentUser by lazy {
        auth.currentUser
    }

    fun newAuthUser(activity: Activity, email: String, password: String, success: (FirebaseUser?) -> Unit, error: (Exception?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Newuser", "createUserWithEmail:success")
                    val user = auth.currentUser
                    success(user)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Newuser", "createUserWithEmail:failure", task.exception)
                    error(task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

    fun newUser(user: User): User {
        val userExist = userRepository.getUserByDni(user.dni)

        if (userExist !== null)
            return userExist

        val userId = userRepository.save(user)
        return userRepository.getUserById(userId)!!
    }

    suspend fun getUser(dni: String): User? = withContext(Dispatchers.IO){
        userRepository.getUserByDni(dni)
    }

    suspend fun getAllUser(): List<User> = withContext(Dispatchers.IO) {
        userRepository.allUsers
    }

    suspend fun getAllUserPaged(): LiveData<PagedList<User>> = withContext(Dispatchers.IO) {
        LivePagedListBuilder(userRepository.allUsersPaging, 50).build()
    }

    suspend fun getByNamesPaged(names:String): LiveData<PagedList<User>> = withContext(Dispatchers.IO) {
        LivePagedListBuilder(userRepository.getByNamePaging(names), 50).build()
    }

    fun getUserById(id: Long): User? {
        return userRepository.getUserById(id)
    }

    fun deleUser(dni: String): Boolean {
        return userRepository.deleteUser(dni)
    }

    suspend fun deleUsers(): Boolean = withContext(Dispatchers.IO) {
        visitRepository.deleteAll()
        userRepository.deleteUsers()
    }
}