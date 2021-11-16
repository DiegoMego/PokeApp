package com.example.pokeapp.model

data class Pokemon(
    val id : Int,
    val name : String,
    val hp : Int,
    val att : Int,
    val def : Int,
    val special_attack : Int,
    val special_defense : Int,
    val url : String
)