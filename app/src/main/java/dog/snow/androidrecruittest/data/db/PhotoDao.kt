package dog.snow.androidrecruittest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.data.model.Photo
import dog.snow.androidrecruittest.data.model.PhotoWithDetails

@Dao
interface PhotoDao{

    @Query("SELECT * FROM photos")
    fun getPhotosDao() : List<Photo>

    @Query("SELECT * FROM photos where id=:id")
    fun getPhotoById(id : Int) : Photo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdatePhoto(photo: Photo) : Long

    @Query("SELECT photos.id AS photoId, photos.title AS photoTitle, " +
            "albums.title AS albumTitle, users.email AS email, users.phone, " +
            "photos.url AS url, photos.thumbnailUrl AS thumbnailUrl " +
            "FROM photos INNER JOIN albums ON photos.albumId=albums.id INNER JOIN users ON albums.userId = users.id " +
            "WHERE photos.id = :id; ")
    fun getDetailPhoto(id : Int): PhotoWithDetails

}