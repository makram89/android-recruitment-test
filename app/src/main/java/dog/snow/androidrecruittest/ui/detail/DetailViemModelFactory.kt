package dog.snow.androidrecruittest.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dog.snow.androidrecruittest.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class DetailViemModelFactory(private val repository: AppRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}