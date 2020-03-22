package dog.snow.androidrecruittest

import android.accounts.NetworkErrorException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.model.Album
import dog.snow.androidrecruittest.data.model.User
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.utls.Coroutines
import dog.snow.androidrecruittest.utls.NetworkUtils
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.Timeout
import java.net.SocketTimeoutException

class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    lateinit var repository: AppRepository
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MSG", "Splash")
        appDatabase = AppDatabase.getInstance(this)
        val api = PlaceholderApi(this.resources.getString(R.string.base_url))
        repository = AppRepository(api, appDatabase)

    }

    override fun onStart() {
        super.onStart()
        cacheData()
    }


    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> cacheData() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun cacheData() {
//        TODO: Timeout errors handle!!!!!!!!!!!!!!
        progressbar.visibility = View.VISIBLE
        var networkUtils = NetworkUtils()
        try {
            if (networkUtils.isOnline(this)) {
                Log.d("NETWORK", "CONNECTED")
                val job = runBlocking {
                    Coroutines.ioThenMain(
                        {
                            val photos = repository.getPhotos()
                            val albums: ArrayList<Album> = arrayListOf()
                            val users: ArrayList<User> = arrayListOf()
                            val albumsIndexes = arrayListOf<Int>()
                            val usersIndexes = arrayListOf<Int>()

                            for (photo in photos) {
                                repository.insertPhotoDao(photo)
                                if (albumsIndexes.contains(photo.albumId)) {
                                    continue
                                } else {
                                    albumsIndexes.add(photo.albumId)
                                    albums.add(repository.getAlbum(photo.albumId))
                                }

                            }
                            for (album in albums) {
                                repository.insertAlbumDao(album)
                                if (usersIndexes.contains(album.userId)) {
                                    continue
                                } else {
                                    usersIndexes.add(album.userId)
                                    users.add(repository.getUser(album.userId))
                                }
                            }
                            for (user in users) {
                                repository.insertUserDao(user)
                            }
                        },
                        {
                            progressbar.hide()
                            startMain()
                        }
                    )
                }
            } else {
                Log.d("NETWORK", "woops!")
                throw NetworkErrorException()
            }
        } catch (e: NetworkErrorException) {
            showError("Cannot connect to the Internet")
        }
        catch (e: SocketTimeoutException)
        {
            showError("Problem with connection")
        }
        catch (e: Throwable)
        {
            showError(e.message)
        }


    }

}