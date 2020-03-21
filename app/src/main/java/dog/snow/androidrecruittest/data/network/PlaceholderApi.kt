package dog.snow.androidrecruittest.data.network

import dog.snow.androidrecruittest.data.model.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PlaceholderApi{

    companion object{
        operator fun invoke(): PlaceholderApi{
            val okClient = OkHttpClient.Builder()
                .addInterceptor(UserAgentInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .create(PlaceholderApi::class.java)

        }
    }

    @GET("/photos")
    suspend fun getPhotos(@Query("_limit") _limit : Int = 100) : Response<List<Photo>>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id") id: Int): Response <Album>

    @GET("/users/{id}")
    suspend fun getUser(@Path(value = "id") id:Int ):Response<User>

}

