package dog.snow.androidrecruittest.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoWithDetails(
    val photoId : Int,
    val photoTitle : String,
    val albumTitle : String,
    val email : String,
    val phoneNumber : String,
    var url : String

): Parcelable