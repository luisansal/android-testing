package com.luisansal.jetpack.data.network

import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig(private val baseUrl: String,private val authSharedPreferences: AuthSharedPreferences) {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(object: Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()

                val request: Request = original.newBuilder()
                        .header("Authorization", "${authSharedPreferences.tokenType} ${authSharedPreferences.token}")
                        .method(original.method, original.body)
                        .build()

                return chain.proceed(request)
            }
        })
        return httpClient.build()
    }

    fun <T> creteService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}