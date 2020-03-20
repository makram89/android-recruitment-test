package dog.snow.androidrecruittest.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity(
    tableName = "albums"
)
@Parcelize
data class Album(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable
