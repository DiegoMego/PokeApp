package com.example.pokeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.model.Pokemon

class PokemonListAdapter(
    private val pokemonList : List<Pokemon>,
    private val fragment : Fragment,
    private val listener : (Pokemon) ->  Unit) :
    RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    class ViewHolder(
        view: View,
        val listener : (Pokemon) -> Unit,
        val pokemonList: List<Pokemon>) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val pokemonName: TextView = view.findViewById(R.id.tviPokemonName)
        //val pokemonHp: TextView = view.findViewById(R.id.tviPokemonHp)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener(pokemonList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_pokemon_item, parent, false)
        return  ViewHolder(view, listener, pokemonList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokemonName.text = pokemonList[position].name
        /*
        Glide.with(fragment)
            .load(productsList[position].url)
            .fitCenter()
            .into(holder.iviProductImage)
         */
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
