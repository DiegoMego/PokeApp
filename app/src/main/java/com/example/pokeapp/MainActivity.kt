package com.example.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.pokeapp.fragment.PokemonListFragment
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.shared.PokemonDetailsView
import com.example.pokeapp.shared.PokemonListView

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
        ft.add(R.id.flaContent, fragments[PokemonListView])
        Log.i("IngredientsFragment", "Click")
        ft.commit()
    }

    fun ChangeToPokemonDetails(poke: Pokemon){
        val bundle = Bundle()
        bundle.putString("PokemonId", poke.name.toString())
        val fragment = fragments[PokemonDetailsView]
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)

        ft.commit()
    }

    override fun onSelect(pokemon: Pokemon) {
        ChangeToPokemonDetails(pokemon)
    }
}