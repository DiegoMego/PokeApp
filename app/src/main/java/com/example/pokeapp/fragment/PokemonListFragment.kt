package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.adapter.PokemonListAdapter
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonManager

class PokemonListFragment : Fragment() {

    interface OnPokemonSelectedListener {
        fun onSelect(pokemon : Pokemon)
    }

    private var listener : OnPokemonSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPokemonSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PokemonManager().getPokemon({pokeList : List<Pokemon> ->
            val rviPoke = view.findViewById<RecyclerView>(R.id.rviPokemon)
            rviPoke.adapter = PokemonListAdapter(
              pokeList,
                this
            ) { poke: Pokemon ->
                listener?.onSelect(poke)
            }
        }, {error : String ->
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })
    }
}