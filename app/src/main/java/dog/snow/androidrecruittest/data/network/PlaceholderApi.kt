package dog.snow.androidrecruittest.data.network
import dog.snow.androidrecruittest.data.model.Album
import dog.snow.androidrecruittest.data.model.Photo
import dog.snow.androidrecruittest.data.model.RawUser
import dog.snow.androidrecruittest.data.model.User
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

            return Retrofit.Builder()
                .client(okClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
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

