package dog.snow.androidrecruittest.repository.network
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
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
    fun getPhotos(@Query("_limit") _limit : Int = 100) : Response<List<RawPhoto>>

    @GET("/albums/{id}")
    fun getAlbums(@Path(value = "id") id: Int): Response <List<RawAlbum>>

    @GET("/users/{id}")
    fun getUsers(@Path(value = "id") id:Int ):Response<List<RawUser>>

}

