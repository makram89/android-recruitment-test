package dog.snow.androidrecruittest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.data.model.User

@Dao
interface UserDao{

    @Query("SELECT * FROM users")
    fun getUsers():List<User>

    @Query("SELECT * FROM users WHERE id= :id")
    fun getUserById(id : Int) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(user: User): Long
}