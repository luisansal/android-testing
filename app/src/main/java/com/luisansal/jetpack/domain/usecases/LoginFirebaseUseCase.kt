package com.luisansal.jetpack.domain.usecases

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFirebaseUseCase {

    private var auth: FirebaseAuth = Firebase.auth

    fun login(activity: Activity, email: String, password: String,success:(FirebaseUser?)->Unit,error: (Exception?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("login", "signInWithEmail:success")
                    val user = auth.currentUser
                    success(user)
                } else {
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    error(task.exception)
                }
            }
    }
}