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
import com.example.pokeapp.model.PokemonManager

class FavoriteListAdapter (
    private val context : Context,
    private val favoriteList : List<Favorite>
) : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {
    class ViewHolder(
        view : View
    ) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvi_favourite_name)
        val delete = view.findViewById<ImageView>(R.id.CloseIcon)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_favourite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = favoriteList[position].pokemon
        holder.delete.setOnClickListener{ _ : View ->
            PokemonManager(context).deleteFromFavorites(favoriteList[position].id,
                { deleted ->
                    if (deleted) {
                        Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT)
                    } else {
                        Toast.makeText(context, "No se pudo eliminar el registro", Toast.LENGTH_SHORT)
                    }
                },
                {
                    Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT)
                })
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}