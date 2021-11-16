package com.example.pokeapp.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//Singleton
class LoginManager {
    companion object {
        var instance : LoginManager = LoginManager()
            private set
    }

    private val dbFirebase = Firebase.firestore

    fun userExists(name: String, callbackOk : (Boolean) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("users").get()
            .addOnSuccessListener { res ->
                val exists = res.any { it["name"].toString() == name}
                callbackOk(exists)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun saveUser(name : String, callbackOk : (Long) -> Unit, callbackError : (String) -> Unit) {
        val data = hashMapOf<String, Any>(
            "name" to name
        )
        val userId = System.currentTimeMillis()

        dbFirebase.collection("users").document(
            userId.toString()
        ).set(data)
            .addOnSuccessListener {
                callbackOk(userId)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun getUser(name : String, callbackOk : (Long) -> Unit, callbackError : (String) -> Unit) {
        dbFirebase.collection("users").get()
            .addOnSuccessListener { res ->
                val user = res.filter { it["name"].toString() == name}.first()
                callbackOk(user.id.toLong())
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}