package com.example.pokeapp.model

import com.google.gson.annotations.SerializedName

data class PokemonStat (
    @SerializedName("base_stat")
    val base_stat : Int,
    val stat : PokemonStatName
)