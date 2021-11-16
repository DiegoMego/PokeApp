package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pokeapp.R
import com.example.pokeapp.model.PokemonManager
import com.example.pokeapp.model.PokemonStat

class PokemonDetailFragment : Fragment() {

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
        var url = this.arguments?.get("PokemonUrl").toString()
        PokemonManager(requireContext()).getPokemon(url, { pokeStats : List<PokemonStat> ->
            val hp = view.findViewById<TextView>(R.id.tviPokemonHpValue)
            val attack = view.findViewById<TextView>(R.id.tviPokemonAttackValue)
            val defense = view.findViewById<TextView>(R.id.tviPokemonDefenseValue)
            val sAttack = view.findViewById<TextView>(R.id.tviPokemonSAttackValue)
            val sDefense = view.findViewById<TextView>(R.id.tviPokemonSDefenseValue)

//            for (poke in pokeStats) {
//                when (poke.stat.name) {
//                    "hp" -> hp.text = poke.base_stat.toString()
//                    "attack" -> attack.text = poke.base_stat.toString()
//                    "defense" -> defense.text = poke.base_stat.toString()
//                    "special-attack" -> sAttack.text = poke.base_stat.toString()
//                    "special-defense" -> sDefense.text = poke.base_stat.toString()
//                }
//            }
        }, {error : String ->
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })
    }
}