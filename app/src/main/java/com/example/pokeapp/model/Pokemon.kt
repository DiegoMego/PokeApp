package com.example.pokeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "hp")
    val hp : Int,
    @ColumnInfo(name = "attack")
    val att : Int,
    @ColumnInfo(name = "defense")
    val def : Int,
    @ColumnInfo(name = "special_attack")
    val special_attack : Int,
    @ColumnInfo(name = "special_defense")
    val special_defense : Int,
    val url : String
)