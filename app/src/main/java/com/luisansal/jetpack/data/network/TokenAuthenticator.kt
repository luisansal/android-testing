package com.luisansal.jetpack.data.network
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.domain.network.ApiService
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val HEADER_AUTHORIZATION = "Authorization"

class TokenAuthenticator :
    Interceptor, Authenticator, KoinComponent {

    private val authPreferences: AuthSharedPreferences by inject ()
    private val refreshTokenService: ApiService by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "${authPreferences.token}")
            .build()
        return chain.proceed(newRequest)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == UNAUTHORIZED){
            if (updateToken() == null) return null
        }
        return response.request
            .newBuilder()
            .header(HEADER_AUTHORIZATION, "${authPreferences.token}")
            .build()
    }

    private fun updateToken(): String? {
        TODO()
//        val refreshTokenRequest = RefreshTokenRequest(
//            authPreferences.tokenId!!,
//            authPreferences.refreshToken!!,
//            authPreferences.userId!!
//        )
//        return try {
//            val authTokenResponse = refreshTokenService.getNewToken(refreshTokenRequest).execute().body()!!
//            authPreferences.token = authTokenResponse.data.accessToken
//            authPreferences.tokenId = authTokenResponse.data.idToken
//            authPreferences.refreshToken = authTokenResponse.data.refreshToken
//            authTokenResponse.data.accessToken
//        }catch (e : Exception){
//            authPreferences.isLogged = false
//            null
//        }
    }

    companion object{
        const val UNAUTHORIZED = 401
    }

}