package uz.pdp.smssherlar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.pdp.smssherlar.models.PoemModel

@Dao
interface PoemDao {

    @Insert
    fun insertPoem(poemEntity: PoemEntity)

    @Query("select * from poementity")
    fun getAllPoem(): LiveData<List<PoemEntity>>

    @Delete
    fun deletePoem(poemEntity: PoemEntity)

    @Query("select * from poementity where id=:id")
    fun getPoemById(id: Int): PoemEntity

}