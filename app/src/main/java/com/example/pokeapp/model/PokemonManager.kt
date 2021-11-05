package com.example.pokeapp.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.pokeapp.network.NetworkClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PokemonManager {
    val API_URL = "https://pokeapi.co/api/v2/pokemon/"

    fun getPokemon(callbackOK : (List<Pokemon>) -> Unit, callbackError : (String) -> Unit) {

        val networkClient = NetworkClient(API_URL)

        val handler = HandlerCompat.createAsync(Looper.myLooper()!!) // main thread
        networkClient.download({data : String ->
            // ok
            val collectionType = (object : TypeToken<PokeMapper>() {}).type
            val gson = Gson()
            val d = gson.fromJson<PokeMapper>(data, collectionType)
            handler.post { callbackOK(d.results) } // se ejecuta en main thread
        }, { error : String ->
            //error
            handler.post { callbackError(error) } // se ejecuta en main thread
        })
    }

    fun getPokemonStats(callbackOK : (List<Pokemon>) -> Unit, callbackError : (String) -> Unit) {
        //Obtener stats?
    }
}