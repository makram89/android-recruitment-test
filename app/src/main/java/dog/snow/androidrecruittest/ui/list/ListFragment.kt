package dog.snow.androidrecruittest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.adapter.PhotoListAdapter
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.joinAll

class ListFragment : Fragment(), PhotoOnClickListener {

    private lateinit var viewModel: ListViewModel
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


        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, ListViewModelFactory(repository)).get(ListViewModel::class.java)
//
//        viewModel.fetchPhotos()


        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {  photosLive ->
            rv_items!!.also{
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = PhotoListAdapter(photosLive, this)
            }
        })


    }

    override fun onClick(view: View, item: ListItem) {
        Log.d("LISTENER", "Item Clicked")
    }
}

