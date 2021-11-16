package com.example.pokeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.Favorite
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonManager

class FavoriteListAdapter (
    private val favoriteList : List<Favorite>,
    private val listener : (Favorite) ->  Unit
) : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {
    class ViewHolder(
        view : View,
        val listener : (Favorite) ->  Unit,
        val favoriteList : List<Favorite>
    ) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvi_favourite_name)
        val delete = view.findViewById<ImageView>(R.id.CloseIcon)

        init {
            delete.setOnClickListener{ _ : View ->
                listener(favoriteList[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_favourite_item, parent, false)
        return ViewHolder(view, listener, favoriteList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = favoriteList[position].pokemon
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}