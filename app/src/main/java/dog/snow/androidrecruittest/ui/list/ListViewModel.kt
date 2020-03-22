package dog.snow.androidrecruittest.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.model.ListItem
import dog.snow.androidrecruittest.utls.Coroutines
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private lateinit var job: Job

    private var photosMutable = MutableLiveData<List<ListItem>>()
    val livePhotos : LiveData<List<ListItem>>
        get() = photosMutable


    fun fetchPhotos() = runBlocking {
        job = Coroutines.ioThenMain(
            {
                repository.getListPhotos()
            },
            {
                photosMutable.value = it as ArrayList<ListItem>
            }
        )
    }

}