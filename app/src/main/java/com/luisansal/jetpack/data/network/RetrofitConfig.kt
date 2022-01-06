package com.luisansal.jetpack.data.network

import com.luisansal.jetpack.core.data.preferences.AuthSharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfig(private val baseUrl: String, private val authSharedPreferences: AuthSharedPreferences) {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()

                    val request: Request = original.newBuilder()
                        .header("Authorization", "${authSharedPreferences.tokenType} ${authSharedPreferences.token}")
                        .header("X-Socket-ID", "${authSharedPreferences.socketId}")
                        .method(original.method, original.body)
                        .build()

                    return chain.proceed(request)
                }
            })
                //puede ser el intercepto de arriba como el de la clase TokenAuthenticator
//            .addInterceptor(TokenAuthenticator())
//            .authenticator(TokenAuthenticator())
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    fun <T> creteService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}