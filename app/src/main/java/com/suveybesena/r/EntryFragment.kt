package com.suveybesena.r

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_entry.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.lang.Exception

class EntryFragment : Fragment() {


    var titleList = ArrayList<String>()
    var idNoteList = ArrayList<Int>()



    lateinit var listadapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,


        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        listadapter = RecyclerAdapter(idNoteList,titleList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listadapter

     arguments?.let {

         var mail = EntryFragmentArgs.fromBundle(it).bilgi

             try {
                    context?.let {
                 var db = it.openOrCreateDatabase("Userdb", Context.MODE_PRIVATE,null)
                 val cursor = db.rawQuery("SELECT * FROM users WHERE  mail = '${mail}'", null)
                 val mailIndex = cursor.getColumnIndex("mail")
                 val gorselindex = cursor.getColumnIndex("image")

                 while (cursor.moveToNext()){

                     mailText.setText("${cursor.getString(mailIndex)} is here. ")
                     val bytedizisi = cursor.getBlob(gorselindex)
                     val bitmap = BitmapFactory.decodeByteArray(bytedizisi,0,bytedizisi.size)
                     profilePhoto.setImageBitmap(bitmap)

                 }

                 cursor.close()
                    }

             }catch (e: Exception){
                 e.printStackTrace()
             }


     }
        sqlverialma()

        super.onViewCreated(view, savedInstanceState)
    }

    fun sqlverialma(){

        arguments?.let {

            var mailgelen = EntryFragmentArgs.fromBundle(it).bilgi

        DataBaseProcess().sqlverialma(titleList, idNoteList, requireContext(), listadapter,mailgelen)
        }

    }

}