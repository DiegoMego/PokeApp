package com.example.pokeapp.model

data class PokeMapper (
    val count : Int,
    val next : String?,
    val previous : String?,
    val results : List<Pokemon>,
    val stats : List<Pokemon>
)