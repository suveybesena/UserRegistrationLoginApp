package com.suveybesena.r

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_delete.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.lang.Exception

class DataBaseProcess {


    fun proportion(selectedbitmapfrom: Bitmap, maxSize: Int): Bitmap {
        var width = selectedbitmapfrom.width
        var height = selectedbitmapfrom.height
        var ratio: Double = width.toDouble() / height.toDouble()
        if (ratio > 1) {
            var shortenedHeight = width / ratio
            height = shortenedHeight.toInt()
        } else {

            var shortenedWidth = height * ratio
            width = shortenedWidth.toInt()

        }
        return Bitmap.createScaledBitmap(selectedbitmapfrom, width, height, true)
    }


    fun change(mail: String, password: String, context: Context) {

        context.let {

            try {
                var db = it.openOrCreateDatabase("Userdb", Context.MODE_PRIVATE, null)
                db.execSQL("UPDATE users SET  password = '${password}' WHERE mail = '${mail}'")
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
            Toast.makeText(
                context,
                "Your information has been updated.",
                Toast.LENGTH_LONG
            )
        }

    }


    fun delete(mail: String, context: Context, view: View) {
        context.let {


            try {
                var db = it.openOrCreateDatabase("Userdb", Context.MODE_PRIVATE, null)
                db.execSQL("DELETE FROM users WHERE  mail = '${mail}'")
                var action = DeleteFragmentDirections.actionDeleteFragmentToRegisterFragment()
                Navigation.findNavController(view).navigate(action)

            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }

    }

    fun noteSave(title: String, notes: String, context: Context, view: View, arguments: Bundle) {

            if (title.isNotEmpty() && notes.isNotEmpty()) {
                try {
                    context.let {
                        val database = it.openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
                        database.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY, title VARCHAR, notes VARCHAR, mail VARCHAR) ")
                        var sqlString = "INSERT INTO note (  title, notes ) VALUES ( ? , ?)"
                        val statement = database.compileStatement(sqlString)
                        statement.bindString(1, title)
                        statement.bindString(2, notes)
                        statement.execute()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val action2 = NotesFragmentDirections.actionNotesFragmentToEntryFragment("")
                Navigation.findNavController(view).navigate(action2)
            } else {
                Toast.makeText(
                    context,
                    "Please enter a note.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun sqlverialma(
        titleList: ArrayList<String>,
        idNoteList: ArrayList<Int>,
        context: Context,
        listadapter: RecyclerAdapter,
        mail: String,
    ) {

        try {

            context.let {
                val database = it.openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
                val cursor = database.rawQuery("SELECT * FROM note WHERE mail ='${mail}' ", null)
                val titleindex = cursor.getColumnIndex("title")
                val idIndex = cursor.getColumnIndex("id")

                while (cursor.moveToNext()) {
                    titleList.add(cursor.getString(titleindex))
                    idNoteList.add(cursor.getInt(idIndex))

                }
                listadapter.notifyDataSetChanged()

                cursor.close()
            }

        } catch (e: Exception) {
            e.printStackTrace()

            }


            }


    fun register(
        mail: String,
        passwordreg: String,
        imageselected: Uri?,
        bitmapselected: Bitmap?,
        maillist: ArrayList<String>,
        passwordlist: ArrayList<String>,
        idlist: ArrayList<Int>,
        context: Context,
        view: View
    ) {

        if (mail.isNotEmpty() && passwordreg.isNotEmpty()) {

            if (imageselected != null) {

                val smallBitmap = proportion(bitmapselected!!, 300)
                val outputstream = ByteArrayOutputStream()
                smallBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputstream)
                val byteDizisi = outputstream.toByteArray()

                if (maillist.contains(mail)) {

                    Toast.makeText(
                        context,
                        "This mail has already registered. Please enter a new email or login.",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    try {
                        context?.let {
                            val database =
                                it.openOrCreateDatabase("Userdb", Context.MODE_PRIVATE, null)
                            database.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, mail VARCHAR, password VARCHAR, image BLOB) ")
                            var sqlString =
                                "INSERT INTO users ( mail, password,image) VALUES ( ? , ? ,? )"
                            val statement = database.compileStatement(sqlString)
                            statement.bindString(1, mail)
                            statement.bindString(2, passwordreg)
                            statement.bindBlob(3, byteDizisi)
                            statement.execute()

                            val cursor = database.rawQuery("SELECT *FROM users", null)

                            val idIndex = cursor.getColumnIndex("id")


                            while (cursor.moveToNext()) {
                                maillist.add(cursor.getString(1))
                                passwordlist.add(cursor.getString(2))
                                idlist.add(cursor.getInt(idIndex))
                            }
                            cursor.close()

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    try {
                        context?.let {
                            val database = it.openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
                            database.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY, title VARCHAR, notes VARCHAR, mail VARCHAR) ")
                            var sqlString = "INSERT INTO note (mail) VALUES (?)"
                            val statement = database.compileStatement(sqlString)
                            statement.bindString(1, mail)
                            statement.execute()

                        }} catch (e: Exception){
                        e.printStackTrace()
                    }


                    val action2 =
                        RegisterFragmentDirections.actionRegisterFragmentToEntryFragment("${mail}")
                    Navigation.findNavController(view).navigate(action2)
                }
            }

        } else {

            Toast.makeText(context, "Related fields cannot be left blank.", Toast.LENGTH_LONG)
                .show()


        }


    }


    fun login(context: Context, mailLogin: String, passLog: String, view: View) {


        context?.let {
            val database = it.openOrCreateDatabase("Userdb", Context.MODE_PRIVATE, null)
            val cursor = database.rawQuery(
                "SELECT * FROM users WHERE  mail = '${mailLogin}' AND password = '${passLog}'",
                null
            )


            if (mailLogin.isNotEmpty() && passLog.isNotEmpty()) {
                if (cursor.count <= 0) {
                    Toast.makeText(
                        context,
                        "Username and password do not match.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    var action2 =
                        LoginFragmentDirections.actionLoginFragmentToEntryFragment("$mailLogin")
                    Navigation.findNavController(view).navigate(action2)
                }
            } else {
                Toast.makeText(context, "Related fields cannot be left blank.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }


}