package com.suveybesena.r

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.processNextEventInCurrentThread

class LoginFragment () : Fragment() {

    var mailLogin = mailLoginText.text.toString()
    var passLog = passwordLoginText.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginButton.setOnClickListener {
            login(view)
        }
        registerTextView.setOnClickListener {
            var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)

    }
    fun login(view: View){
        DataBaseProcess().login(requireContext(), mailLogin, passLog,view)

    }

}