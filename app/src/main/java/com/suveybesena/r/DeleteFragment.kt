package com.suveybesena.r

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_delete.*
import kotlinx.android.synthetic.main.fragment_entry.*
import java.lang.Exception


class DeleteFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        changeButton.setOnClickListener {
           change()
        }

        deleteButton.setOnClickListener {
            delete(view)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun change (){
        var mail = mailText2.text.toString()
        var password = passwordText2.text.toString()
        DataBaseProcess().change(mail,password,requireContext(), )
    }
    fun delete(view: View){
        var mail = mailText2.text.toString()
        var password = passwordText2.text.toString()
        DataBaseProcess().delete(mail,requireContext(), view)
    }


}