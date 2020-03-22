package dog.snow.androidrecruittest.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.model.Detail
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsFragment(val detailId: Int) : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var repository: AppRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val api = PlaceholderApi(this.resources.getString(R.string.base_url))
        appDatabase = AppDatabase.getInstance(inflater.context)
        repository = AppRepository(api, appDatabase)
        viewModel =
            ViewModelProvider(
                this,
                DetailViemModelFactory(repository)
            ).get(DetailViewModel::class.java)

//        viewModel.fetchDetail(detailId)
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GlobalScope.launch {
            val detail = viewModel.getDetail(detailId)
            launch(Main) {
                tv_photo_title.text = detail.photoTitle
                tv_album_title.text = detail.albumTitle
                tv_username.text = detail.username
                tv_email.text = detail.email
                tv_phone.text = detail.phone
                Picasso.get()
                    .load(detail.url)
                    .error(R.drawable.ic_logo_sd_symbol)
                    .into(iv_photo)
            }
        }


    }




}
