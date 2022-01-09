package com.suveybesena.r

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.io.ByteArrayOutputStream


class RegisterFragment : Fragment() {
    var maillist = ArrayList<String>()
    var passwordlist = ArrayList<String>()
    var idlist = ArrayList<Int>()
    var imageselected : Uri? = null
    var bitmapselected : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerButton.setOnClickListener {
            register(it)
        }

        logintextView.setOnClickListener {

            var action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }

        selectImageButton.setOnClickListener {
            selectImage(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun register(view: View) {

        var mail = mailRegisterText.text.toString()
        val passwordreg = passwordRegisterText.text.toString()
        DataBaseProcess().register(mail, passwordreg, imageselected, bitmapselected, maillist, passwordlist, idlist, requireContext(), view )



    }

    fun selectImage ( view: View){

        activity?.let {
            if (ContextCompat.checkSelfPermission(it.applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)


            } else{


                val gallerynt = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallerynt, 2)
            }


        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                val gallerynt = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallerynt, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            imageselected = data.data

            try {
                context?.let {

                    if (imageselected != null){

                        if (Build.VERSION.SDK_INT >= 28 ){
                            var source = ImageDecoder.createSource(it.contentResolver, imageselected!!)
                            bitmapselected = ImageDecoder.decodeBitmap(source)
                            selectImageButton.setImageBitmap(bitmapselected)

                        }else{
                            bitmapselected = MediaStore.Images.Media.getBitmap(it.contentResolver, imageselected)
                            selectImageButton.setImageBitmap(bitmapselected)
                        }
                    }
                }

            }catch (e : java.lang.Exception){
                e.printStackTrace()
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }








}