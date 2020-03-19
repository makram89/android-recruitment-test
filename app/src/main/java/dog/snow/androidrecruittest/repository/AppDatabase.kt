package dog.snow.androidrecruittest.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.repository.model.Album
import dog.snow.androidrecruittest.repository.model.Photo
import dog.snow.androidrecruittest.repository.model.User
import dog.snow.androidrecruittest.repository.db.*



@Database(
    entities = [Photo::class, Album::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "cool.db")
                .build()
        }
    }
}