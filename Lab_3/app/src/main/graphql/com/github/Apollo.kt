package com.github

import android.content.Context
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

fun apolloClient(context: Context): ApolloClient {
    return  ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(context))
            .build()
        )
        .build()
}

private class AuthorizationInterceptor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "9d828039a19a904512a4519be63b5af0746b0f91")
            .build()

        return chain.proceed(request)
    }

}