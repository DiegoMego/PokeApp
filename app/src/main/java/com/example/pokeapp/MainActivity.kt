package com.example.pokeapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.pokeapp.fragment.PokemonListFragment
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.shared.PokemonDetailsView
import com.example.pokeapp.shared.PokemonListView
import com.google.gson.Gson
import java.util.*

/*
Integrantes:
   IZAGUIRRE CASTRO ANDONI TOMAS (20142881)
   MEGO FERNANDEZ DIEGO ALONSO (20100696)

   REPOSITORIO: https://github.com/DiegoMego/PokeApp
* */

class MainActivity : AppCompatActivity(), PokemonListFragment.OnPokemonSelectedListener {
    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //fragments.add(LoginFragment())
        fragments.add(PokemonListFragment())
        //fragments.add(PokemonDetailFragment())
        //fragments.add(FavouritesListFragment())


        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaContent, fragments[0])
        Log.i("IngredientsFragment", "Click")
        ft.commit()
    }

    /*
    private fun almacenarPokemonAI(poke : List<Pokemon>) {
        val gson = Gson()
        val pokemon = Pokemon(name)
        val loginInfoSerializado = gson.toJson(loginInfo)
        openFileOutput("login_info.json", Context.MODE_PRIVATE).use {
            it.write(loginInfoSerializado.toByteArray(Charsets.UTF_8))
        }
    }
    */

    fun ChangeToPokemonDetails(poke: Pokemon){
        val bundle = Bundle()
        bundle.putString("PokemonId", poke.name.toString())
        val fragment = fragments[1]
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)

        ft.commit()
    }

    override fun onSelect(pokemon: Pokemon) {
        ChangeToPokemonDetails(pokemon)
    }
}