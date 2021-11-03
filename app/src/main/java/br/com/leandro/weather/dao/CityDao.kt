package br.com.leandro.weather.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.leandro.weather.data.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(city: City)

    @Delete
    suspend fun remove(city: City)

    @Update
    suspend fun update(city: City)

    @Query("SELECT * FROM table_city")
    fun getAll(): LiveData<List<City>>

    @Query("SELECT * FROM table_city WHERE id = :key")
    fun get(key: Long): City
}





