package dog.snow.androidrecruittest.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.model.ListItem
import dog.snow.androidrecruittest.utls.Coroutines
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

class ListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private lateinit var job: Job

    val photosMutable = MutableLiveData<ArrayList<ListItem>>()


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

    fun getPhotos(): LiveData<ArrayList<ListItem>> {
        fetchPhotos()
        Log.d("did he?", photosMutable.value?.size.toString())
        return photosMutable
    }
}