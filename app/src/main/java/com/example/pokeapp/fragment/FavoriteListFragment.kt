package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.adapter.FavoriteListAdapter
import com.example.pokeapp.adapter.PokemonListAdapter
import com.example.pokeapp.model.Favorite
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonManager

class FavoriteListFragment : Fragment() {
    interface OnPokemonDeletedListener {
        fun onSelect(favorite : Favorite, userId : Long)
    }

    private var listener : OnPokemonDeletedListener? = null
    var userId : Long? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = this.arguments?.get("userId").toString().toLong()
        return inflater.inflate(R.layout.fragment_list_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PokemonManager(requireContext()).getFavorites(userId!!, { favorites : List<Favorite> ->
            val rviFavorites = view.findViewById<RecyclerView>(R.id.rviFavourites)
            rviFavorites.adapter = FavoriteListAdapter(
                favorites
            ) { favorite ->
                listener?.onSelect(favorite, userId!!)
            }
        }, {error : String ->
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })
    }
}