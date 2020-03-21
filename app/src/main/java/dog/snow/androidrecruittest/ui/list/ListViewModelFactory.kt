package dog.snow.androidrecruittest.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dog.snow.androidrecruittest.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(private val repository: AppRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}