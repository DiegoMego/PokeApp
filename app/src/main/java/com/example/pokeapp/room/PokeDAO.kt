package com.example.pokeapp.room

import androidx.room.Dao
import androidx.room.Query
import com.example.pokeapp.model.Pokemon

@Dao
interface PokeDAO {
    @Query("Select * From Pokemon")
    fun findAll() : List<Pokemon>
}