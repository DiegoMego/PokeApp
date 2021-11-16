package com.example.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pokeapp.fragment.FavoriteListFragment
import com.example.pokeapp.fragment.LoginFragment
import com.example.pokeapp.fragment.PokemonDetailFragment
import com.example.pokeapp.fragment.PokemonListFragment
import com.example.pokeapp.model.Favorite
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonManager
import com.example.pokeapp.shared.*
import java.util.*

/*
Integrantes:
   IZAGUIRRE CASTRO ANDONI TOMAS (20142881)
   MEGO FERNANDEZ DIEGO ALONSO (20100696)

   REPOSITORIO: https://github.com/DiegoMego/PokeApp
* */

class MainActivity : AppCompatActivity(), PokemonListFragment.OnPokemonSelectedListener, LoginFragment.OnButtonClickedListener, FavoriteListFragment.OnPokemonDeletedListener {
    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(LoginFragment())
        fragments.add(PokemonListFragment())
        fragments.add(PokemonDetailFragment())
        fragments.add(FavoriteListFragment())


        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flaContent, fragments[LoginView])
        ft.addToBackStack(null)
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

    fun ChangeToPokemonDetails(poke: Pokemon, userId : Long){
        val bundle = Bundle()
        bundle.putString("PokemonId", poke.id.toString())
        bundle.putString("UserId", userId.toString())
        val fragment = fragments[PokemonDetailsView]
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onSelect(pokemon: Pokemon, userId : Long) {
        ChangeToPokemonDetails(pokemon, userId)
    }

    override fun Continue(userId : Long) {
        val bundle = Bundle()
        bundle.putString("userId", userId.toString())
        val fragment = fragments[PokemonListView]
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun Favorites(userId : Long) {
        val bundle = Bundle()
        bundle.putString("userId", userId.toString())
        val fragment = fragments[FavouritesView]
        fragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaContent, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onSelect(favorite: Favorite, userId: Long) {
        PokemonManager(this).deleteFromFavorites(favorite.id,
            { deleted ->
                if (deleted) {
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_SHORT)
                }
            },
            {
                Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT)
            })
    }
}