package dog.snow.androidrecruittest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.list.PhotosListener
import dog.snow.androidrecruittest.ui.model.ListItem
import java.util.*
import kotlin.collections.ArrayList

class PhotoListAdapter(var photosDataset: List<ListItem>, private val listener: PhotosListener) :
    androidx.recyclerview.widget.ListAdapter<ListItem, PhotoListAdapter.PhotoItemHolder>(
        DIFF_CALLBACK
    ), Filterable {
    private val fullDataset = photosDataset

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemHolder {
        return PhotoItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoItemHolder, position: Int) {
        holder.bind(photosDataset[position], listener)
    }

    override fun getItemCount(): Int {
        return photosDataset.size
    }

    class PhotoItemHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem, listener: PhotosListener) {
            val ivThumb: ImageView = itemView.findViewById(R.id.iv_thumb)
            val tvTitle: TextView = itemView.findViewById(R.id.tv_photo_title)
            val tvAlbumTitle: TextView = itemView.findViewById(R.id.tv_album_title)
            tvTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle

            Picasso.get()
                .load(item.thumbnailUrl)
                .error(R.drawable.ic_placeholder)
                .into(ivThumb)

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

    private var valueFilter: ValueFilter = ValueFilter()

    inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                val filterList: MutableList<ListItem> = ArrayList()
                for (i in fullDataset.indices) {
                    if (fullDataset[i].title.toUpperCase(Locale.ROOT).contains(
                            constraint.toString().toUpperCase(
                                Locale.ROOT
                            )
                        )
                        || fullDataset[i].albumTitle.toUpperCase(Locale.ROOT).contains(
                            constraint.toString().toUpperCase(
                                Locale.ROOT
                            )
                        )
                    ) {
                        filterList.add(fullDataset[i])
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = photosDataset.size
                results.values = photosDataset
            }
            return results
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults
        ) {
            photosDataset = results.values as List<ListItem>
            notifyDataSetChanged()
            listener.onSearch(itemCount)
        }
    }

    override fun getFilter(): Filter {
        return valueFilter
    }
}