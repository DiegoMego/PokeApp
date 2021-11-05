package com.example.pokeapp.room

import androidx.room.Database
import com.example.pokeapp.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokeAppDB {
    abstract fun pokeDao() :PokeDAO
}