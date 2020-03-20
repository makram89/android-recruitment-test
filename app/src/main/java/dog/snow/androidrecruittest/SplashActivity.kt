package dog.snow.androidrecruittest

import android.accounts.NetworkErrorException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.utls.Coroutines
import dog.snow.androidrecruittest.utls.NetworkUtils
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    lateinit var repository: AppRepository
    lateinit var appDatabase : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MSG", "Splash")
        appDatabase = AppDatabase.getInstance(this)
//        val api = PlaceholderApi()
//        repository = AppRepository(api, appDatabase)
        cacheData()


    }

    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> cacheData()}
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun startMain(){

        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

    private fun cacheData(){
        progressbar.visibility = View.VISIBLE
        var networkUtils = NetworkUtils()
        try{
            if(networkUtils.isOnline(this))
            {
                Log.d("NETWORK", "CONNECTED")

                runBlocking {
                    Coroutines.ioThenMain(
                        {repository.getPhotos()},
                        {

                        }
                    )
                }

            }
            else{
                Log.d("NETWORK", "woops!")
                throw NetworkErrorException()
            }


        }
        catch(e: NetworkErrorException) {
            showError("Cannot connect to the Internet")
        }


    }

}