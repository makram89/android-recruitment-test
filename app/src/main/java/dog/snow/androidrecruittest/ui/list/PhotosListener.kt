package dog.snow.androidrecruittest.ui.list

import android.view.View
import dog.snow.androidrecruittest.ui.model.ListItem

interface  PhotosListener {
    fun onClick(view: View, item: ListItem)
    fun onSearch(members : Int)
}