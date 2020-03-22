package dog.snow.androidrecruittest.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.data.repository.AppRepository
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.utls.Coroutines
import kotlinx.coroutines.runBlocking

class DetailViewModel(
    private val repository: AppRepository
) : ViewModel() {


    fun getDetail(id: Int) = repository.getPhotoDetailDao(id)



}
