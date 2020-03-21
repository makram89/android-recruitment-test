package dog.snow.androidrecruittest.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.list.PhotoOnClickListener
import dog.snow.androidrecruittest.ui.model.ListItem

class PhotoListAdapter(var photosList: List<ListItem>, private val listener: PhotoOnClickListener) :
    androidx.recyclerview.widget.ListAdapter<ListItem, PhotoListAdapter.PhotoItemHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemHolder {
        Log.d("oncreateVH", photosList.size.toString())
        return PhotoItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoItemHolder, position: Int) {
        holder.bind(photosList[position], listener)
    }

    override fun getItemCount(): Int {
        Log.d("LetsCount", photosList.size.toString())
        return photosList.size
    }

    class PhotoItemHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem, listener: PhotoOnClickListener) {
            val ivThumb: ImageView = itemView.findViewById(R.id.iv_thumb)
            val tvTitle: TextView = itemView.findViewById(R.id.tv_photo_title)
            val tvAlbumTitle: TextView = itemView.findViewById(R.id.tv_album_title)
            tvTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            //TODO: display item.thumbnailUrl in ivThumb
            itemView.setOnClickListener { listener.onClick(itemView, item) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem
        }
    }
}