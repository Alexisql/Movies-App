package com.alexis.moviesapp.data.core

import com.alexis.moviesapp.data.core.Constants.AUTHORIZATION
import com.alexis.moviesapp.data.core.Constants.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().header(AUTHORIZATION, TOKEN).build()
        return chain.proceed(request)
    }
}