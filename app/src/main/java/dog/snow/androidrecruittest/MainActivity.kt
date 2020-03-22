package dog.snow.androidrecruittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dog.snow.androidrecruittest.ui.detail.DetailsFragment
import dog.snow.androidrecruittest.ui.model.Detail

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.d("MSG", "MainActivity")

    }


}