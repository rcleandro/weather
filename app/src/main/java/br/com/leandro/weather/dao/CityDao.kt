package br.com.leandro.weather.dao

import androidx.room.*
import br.com.leandro.weather.data.City

@Dao
interface CityDao {

    @Insert
    suspend fun save(city: City)

    @Delete
    suspend fun remove(city: City)

    @Update
    suspend fun update(city: City)

    @Query("SELECT * FROM City")
    fun getAll(): List<City>

    @Query("SELECT * FROM City WHERE id = :key")
    fun get(key: Long): City
}





