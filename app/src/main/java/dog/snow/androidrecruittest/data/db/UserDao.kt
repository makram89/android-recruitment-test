package dog.snow.androidrecruittest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.data.model.User

@Dao
interface UserDao{

    @Query("SELECT * FROM users")
    suspend fun getUsers():List<User>

    @Query("SELECT * FROM users WHERE id= :id")
    suspend fun getUserById(id : Int) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

}