package com.example.pokeapp.model

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.example.pokeapp.network.NetworkClient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class PokemonManager(context : Context) {
    val API_URL = "https://pokeapi.co/api/v2/pokemon/"

    private val dbFirebase = Firebase.firestore

    fun getPokemons(callbackOK : (List<Pokemon>) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("pokemon")
            .get()
            .addOnSuccessListener { res ->
                val pokemons = arrayListOf<Pokemon>()
                for (document in res) {
                    pokemons.add(Pokemon(
                        document.id.toInt(),
                        document.data["name"]!! as String,
                        document.data["hp"].toString().toInt(),
                        document.data["attack"].toString().toInt(),
                        document.data["defense"].toString().toInt(),
                        document.data["special-attack"].toString().toInt(),
                        document.data["special-defense"].toString().toInt(),
                        document.data["url"].toString()
                    ))
                }
                callbackOK(pokemons)
            }
            .addOnFailureListener{

            }
    }

    fun getPokemon(url : String, callbackOK : (List<PokemonStat>) -> Unit, callbackError : (String) -> Unit) {

        val networkClient = NetworkClient(url)

        val handler = HandlerCompat.createAsync(Looper.myLooper()!!) // main thread
        networkClient.download({data : String ->
            // ok
            val collectionType = (object : TypeToken<PokemonStat>() {}).type
            val gson = Gson()
            val stats = gson.fromJson<PokemonStat>(data, collectionType)
            handler.post { callbackOK(listOf(stats)) } // se ejecuta en main thread
        }, { error : String ->
            //error
            handler.post { callbackError(error) } // se ejecuta en main thread
        })
    }
}