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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_entry.*
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        arguments?.let {
            var gelenbilgi = NotesFragmentArgs.fromBundle(it).gelen

            if (gelenbilgi.equals("menudengeldim")){
                titleText.setText("")
                notesText.setText("")
                noteSaveButton.visibility = View.VISIBLE


            }else{

                noteSaveButton.visibility = View.INVISIBLE
                var secilenid = NotesFragmentArgs.fromBundle(it).sira
                context?.let {
                    try {

                        var db = it.openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null)
                        var cursor = db.rawQuery("SELECT * FROM note WHERE id = ?", arrayOf(secilenid.toString()))

                        val nottitle = cursor.getColumnIndex("title")
                        val noticeri = cursor.getColumnIndex("notes")

                        while (cursor.moveToNext()){
                            titleText.setText(cursor.getString(nottitle))
                            notesText.setText(cursor.getString(noticeri))
                        }
                        cursor.close()


                    }catch (e: java.lang.Exception){
                        e.printStackTrace()
                    }
                }


            }
        }

        noteSaveButton.setOnClickListener {
            noteSave(view)
        }



        super.onViewCreated(view, savedInstanceState)
    }

    fun noteSave(view: View){

        var title = titleText.text.toString()
        var notes = notesText.text.toString()

        DataBaseProcess().noteSave(title, notes, requireContext(), view, requireArguments())

    }

}