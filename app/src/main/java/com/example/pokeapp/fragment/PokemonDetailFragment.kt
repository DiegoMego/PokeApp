package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.model.Pokemon
import com.example.pokeapp.model.PokemonManager
import org.w3c.dom.Text

class PokemonDetailFragment : Fragment() {

    private var poke : Pokemon? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = this.arguments?.get("PokemonId").toString().toInt()
        var userId = this.arguments?.get("UserId").toString().toLong()
        PokemonManager(requireContext()).getPokemon(id, { pokemon : Pokemon ->
            poke = pokemon
            val name = view.findViewById<TextView>(R.id.tviPokemonName)
            val hp = view.findViewById<TextView>(R.id.tviPokemonHpValue)
            val attack = view.findViewById<TextView>(R.id.tviPokemonAttackValue)
            val defense = view.findViewById<TextView>(R.id.tviPokemonDefenseValue)
            val sAttack = view.findViewById<TextView>(R.id.tviPokemonSAttackValue)
            val sDefense = view.findViewById<TextView>(R.id.tviPokemonSDefenseValue)
            val iviPokeImage = view.findViewById<ImageView>(R.id.iviPokemonImg)

            name.text = pokemon.name
            hp.text = pokemon.hp.toString()
            attack.text = pokemon.att.toString()
            defense.text = pokemon.def.toString()
            sAttack.text = pokemon.special_attack.toString()
            sDefense.text = pokemon.special_defense.toString()
            Glide.with(this).load(pokemon.url).fitCenter().into(iviPokeImage)
        }, {error : String ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        view.findViewById<Button>(R.id.butFav).setOnClickListener{ _ : View ->
            PokemonManager(requireContext()).addToFavorites(id, userId,
                { exists ->
                    if (exists) {
                        Toast.makeText(context, "El pokemon ya existe en favoritos", Toast.LENGTH_LONG)
                    } else {
                        Toast.makeText(context, "Pokemon registrado satisfactoriamente", Toast.LENGTH_LONG)
                    }
                },
                { error ->
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG)
                })
        }
    }
}