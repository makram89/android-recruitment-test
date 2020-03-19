package dog.snow.androidrecruittest.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoWithDetails(
    val photoId : Int,
    val photoTitle : String,
    val albumTitle : String,
    val email : String,
    val phone : String,
    var url : String,
    val thumbnailUrl: String

): Parcelable