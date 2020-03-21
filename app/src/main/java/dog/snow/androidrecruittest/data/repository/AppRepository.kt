package dog.snow.androidrecruittest.data.repository

import dog.snow.androidrecruittest.data.db.AppDatabase
import dog.snow.androidrecruittest.data.model.Album
import dog.snow.androidrecruittest.data.model.Photo
import dog.snow.androidrecruittest.data.model.User
import dog.snow.androidrecruittest.data.network.PlaceholderApi
import dog.snow.androidrecruittest.utls.SafeApiRequest
import java.lang.Exception

class AppRepository(val api: PlaceholderApi, var appDatabase: AppDatabase) : SafeApiRequest() {


    //geting data from jsonplaceholder
    suspend fun getPhotos() = apiRequest { api.getPhotos() }

    suspend fun getAlbum(id: Int) = apiRequest { api.getAlbum(id) }
    suspend fun getUser(id: Int) = apiRequest { api.getUser(id) }


    private val photoDao = appDatabase.photoDao()
    private val albumDao = appDatabase.albumDao()
    private val userDao = appDatabase.userDao()

    //DAO

    fun getPhotosDao() = photoDao.getPhotosDao()
    fun getPhotoDetailDao(id: Int) = photoDao.getDetailPhoto(id)
    fun insertPhotoDao(photo: Photo) = photoDao.insertOrUpdatePhoto(photo)
    fun getListPhotos() = photoDao.getListPhotos()

    fun getAlbumsDao() = albumDao.getAlbums()
    fun getAlbumByIdDao(id: Int) = albumDao.getAlbumById(id)
    fun insertAlbumDao(album: Album) = albumDao.insertOrUpdateAlbum(album)

    fun getUsersDao() = userDao.getUsers()
    fun getUserByIdDao(id: Int) = userDao.getUserById(id)
    fun insertUserDao(user: User) = userDao.insertOrUpdate(user)


}