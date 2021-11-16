package com.example.pokeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pokeapp.R
import com.example.pokeapp.model.LoginManager

class LoginFragment : Fragment() {
    interface OnButtonClickedListener {
        fun Continue(userId : Long)
        fun Favorites(userId : Long)
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
        val eteUsername = view.findViewById<EditText>(R.id.eteUsername)

        butContinue.setOnClickListener{ _ : View ->
            val username = eteUsername.text.toString()
            if (username.isNotEmpty()) {
                LoginManager.instance.userExists(
                        username,
                        { exists ->
                            if (!exists) {
                                LoginManager.instance.saveUser(
                                    username,
                                    { id ->
                                        Toast.makeText(context, "Usuario Guardado", Toast.LENGTH_SHORT)
                                        listener?.Continue(id)
                                    },
                                    {
                                        Toast.makeText(context, "Error al intentar guardar el usuario", Toast.LENGTH_LONG)
                                    }
                                )
                            }else{
                                LoginManager.instance.getUser(
                                    username,
                                    { id ->
                                        listener?.Continue(id)
                                    },
                                    {
                                        Toast.makeText(context, "Error al intentar obtener el usuario", Toast.LENGTH_LONG)
                                    }
                                )
                            }
                        },
                        {
                            Toast.makeText(context, "Error al intentar guardar su usuario", Toast.LENGTH_LONG)
                        })

            }else{
                Toast.makeText(context, "Ingrese su nombre de usuario", Toast.LENGTH_LONG)
            }
        }

        butFavorites.setOnClickListener{ _ : View ->
            val username = eteUsername.text.toString()
            if (username.isNotEmpty()) {
                LoginManager.instance.userExists(
                    username,
                    { exists ->
                        if (!exists) {
                            LoginManager.instance.saveUser(
                                username,
                                { id ->
                                    Toast.makeText(context, "Usuario Guardado", Toast.LENGTH_SHORT)
                                    listener?.Favorites(id)
                                },
                                {
                                    Toast.makeText(context, "Error al intentar guardar el usuario", Toast.LENGTH_LONG)
                                }
                            )
                        }else{
                            LoginManager.instance.getUser(
                                username,
                                { id ->
                                    listener?.Favorites(id)
                                },
                                {
                                    Toast.makeText(context, "Error al intentar obtener el usuario", Toast.LENGTH_LONG)
                                }
                            )
                        }
                    },
                    {
                        Toast.makeText(context, "Error al intentar guardar su usuario", Toast.LENGTH_LONG)
                    })

            }else{
                Toast.makeText(context, "Ingrese su nombre de usuario", Toast.LENGTH_LONG)
            }
        }
    }
}