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
        fun onSelect(pokemon : Pokemon, userId : Long)
    }

    private var listener : OnPokemonSelectedListener? = null
    var userId : Long? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPokemonSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = this.arguments?.get("userId").toString().toLong()
        return inflater.inflate(R.layout.fragment_list_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PokemonManager(requireContext()).getPokemons({pokeList : List<Pokemon> ->
            val rviPoke = view.findViewById<RecyclerView>(R.id.rviPokemon)
            rviPoke.adapter = PokemonListAdapter(
              pokeList,
                this
            ) { poke: Pokemon ->
                listener?.onSelect(poke, userId!!)
            }
        }, {error : String ->
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })
    }
}