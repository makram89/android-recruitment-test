package dog.snow.androidrecruittest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.adapter.PhotoListAdapter
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(), PhotosListener {

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
        viewModel =
            ViewModelProvider(this, ListViewModelFactory(repository)).get(ListViewModel::class.java)

        viewModel.fetchPhotos()
        viewModel.livePhotos.observe(viewLifecycleOwner, Observer {
            rv_items.adapter = PhotoListAdapter(it, this)
            if (it.isNotEmpty()) {
                this.rv_items.visibility = View.VISIBLE
                this.empty_view.visibility = View.GONE
            }
        })

        et_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (rv_items.adapter != null) {
                    (rv_items.adapter as PhotoListAdapter).filter.filter(newText)
                }

                return false
            }
        })


    }

    override fun onClick(view: View, item: ListItem) {

//        item....


        (context as MainActivity).openDetailFragment(this, item.id )
    }

    override fun onSearch(members: Int) {
        if (members > 0) {
            this.rv_items.visibility = View.VISIBLE
            this.empty_view.visibility = View.GONE
        } else {
            this.rv_items.visibility = View.GONE
            this.empty_view.visibility = View.VISIBLE
        }
    }

}

