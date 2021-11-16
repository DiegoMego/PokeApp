package com.example.pokeapp.model

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.os.HandlerCompat
import com.example.pokeapp.network.NetworkClient
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class PokemonManager(val context : Context) {
    val API_URL = "https://pokeapi.co/api/v2/pokemon/"

    private val dbFirebase = Firebase.firestore

    fun getPokemons(callbackOK : (List<Pokemon>) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("pokemon")
            .get()
            .addOnSuccessListener { res ->
                val result = res.filter { it.id.toInt() > 0 }.toMutableList()
                val pokemons = arrayListOf<Pokemon>()
                for (document in result) {
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
                callbackError(it.message!!)
            }
    }

    fun getPokemon(id : Int, callbackOK : (Pokemon) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("pokemon").document(id.toString()).get()
            .addOnSuccessListener { pokemon ->
                callbackOK(Pokemon(
                    pokemon.id.toInt(),
                    pokemon["name"]!! as String,
                    pokemon["hp"].toString().toInt(),
                    pokemon["attack"].toString().toInt(),
                    pokemon["defense"].toString().toInt(),
                    pokemon["special-attack"].toString().toInt(),
                    pokemon["special-defense"].toString().toInt(),
                    pokemon["url"].toString()
                ))
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun addToFavorites(pokemonId : Int, userId : Long, callbackOK : (Boolean) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("favorites").get().addOnSuccessListener { favorites ->
            val exists = favorites.any { (it["pokemon"]!! as DocumentReference).id.toInt() == pokemonId && (it["user"]!! as DocumentReference).id.toLong() == userId }
            if(!exists) {
                val pokemon = dbFirebase.collection("pokemon").document(pokemonId.toString())
                val user = dbFirebase.collection("users").document(userId.toString())

                pokemon.get()
                    .addOnSuccessListener {
                        val data = hashMapOf<String, Any>(
                            "pokemon" to pokemon,
                            "user" to user,
                            "name" to it["name"].toString()
                        )
                        val id = System.currentTimeMillis()

                        dbFirebase.collection("favorites").document(
                            id.toString()
                        ).set(data)
                            .addOnSuccessListener {
                                callbackOK(exists)
                            }
                            .addOnFailureListener {
                                callbackError(it.message!!)
                            }
                    }
            } else {
                callbackOK(exists)
            }
        }
    }

    fun getFavorites(userId : Long, callbackOK : (List<Favorite>) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("favorites")
            .get()
            .addOnSuccessListener { res ->
                val result = res.filter {
                    (it["pokemon"]!! as DocumentReference).id.toInt() > 0
                    (it["user"]!! as DocumentReference).id.toLong() == userId
                }.toMutableList()
                val favorites = arrayListOf<Favorite>()
                for (document in result) {
                    val id = (document.data["pokemon"]!! as DocumentReference).id
                    val pokemon = dbFirebase.collection("pokemon").document(id)
                            Log.i("favoritos", pokemon.id)
                            favorites.add(
                                Favorite(
                                document.id.toLong(),
                                document["name"].toString()
                            ))
                        }
                callbackOK(favorites)
            }
            .addOnFailureListener{
                callbackError(it.message!!)
            }
    }

    fun deleteFromFavorites(id : Long, callbackOk : (Boolean) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("favorites").document(id.toString()).delete()
            .addOnSuccessListener {
                callbackOk(true)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}