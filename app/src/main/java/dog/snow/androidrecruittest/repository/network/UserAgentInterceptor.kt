package dog.snow.androidrecruittest.repository.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object UserAgentInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest : Request = chain.request()

        val requestWithUserAgent = originalRequest.newBuilder()
            .header("User-Agent", "Snow.Dog App")
            .build()
        return chain.proceed(requestWithUserAgent)

    }


}