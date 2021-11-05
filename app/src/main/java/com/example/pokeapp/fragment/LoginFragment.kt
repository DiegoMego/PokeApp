package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.pokeapp.R

class LoginFragment : Fragment() {
    interface OnButtonClickedListener {
        fun Continue()
        fun Favorites()
    }

    private var listener : OnButtonClickedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnButtonClickedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val butContinue = view.findViewById<Button>(R.id.butContinue)
        val butFavorites = view.findViewById<Button>(R.id.butFavorites)

        butContinue.setOnClickListener{ _ : View ->
            listener?.Continue()
        }

        butFavorites.setOnClickListener{ _ : View ->
            listener?.Favorites()
        }
    }
}