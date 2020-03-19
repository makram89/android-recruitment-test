package dog.snow.androidrecruittest.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.Album

@Dao
interface AlbumsDao{

    @Query("SELECT * FROM albums")
    suspend fun getAlbums() : List<Album>

    @Query("SELECT * FROM albums WHERE id=:id")
    suspend fun getAlbumById(id: Int) : Album

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAlbum(album: Album) : Long
}