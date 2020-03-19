package dog.snow.androidrecruittest.repository.network
import dog.snow.androidrecruittest.repository.model.Album
import dog.snow.androidrecruittest.repository.model.Photo
import dog.snow.androidrecruittest.repository.model.RawUser
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
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()
                .create(PlaceholderApi::class.java)

        }
    }
    @GET("/photos")
    suspend fun getPhotos(@Query("_limit") _limit : Int = 100) : Response<List<Photo>>

    @GET("/albums/{id}")
    suspend fun getAlbums(@Path(value = "id") id: Int): Response <Album>

    @GET("/users/{id}")
    suspend fun getUsers(@Path(value = "id") id:Int ):Response<RawUser>

}

