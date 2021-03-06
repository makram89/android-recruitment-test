package dog.snow.androidrecruittest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.data.model.Album

@Dao
interface AlbumDao{

    @Query("SELECT * FROM albums")
    fun getAlbums() : List<Album>

    @Query("SELECT * FROM albums WHERE id=:id")
    fun getAlbumById(id: Int) : Album

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateAlbum(album: Album) : Long
}