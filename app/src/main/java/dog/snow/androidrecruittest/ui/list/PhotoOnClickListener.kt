package dog.snow.androidrecruittest.ui.list

import android.view.View
import dog.snow.androidrecruittest.ui.model.ListItem

interface  PhotoOnClickListener {
    fun onClick(view: View, item: ListItem)
}