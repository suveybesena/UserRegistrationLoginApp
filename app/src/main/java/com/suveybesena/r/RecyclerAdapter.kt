package com.suveybesena.r

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerAdapter ( var noteidlist : ArrayList<Int>,var noteList : ArrayList<String>): RecyclerView.Adapter<RecyclerAdapter.NotesVH>() {


    class NotesVH (itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        var inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return NotesVH(view)
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        holder.itemView.recycler_text_id.text = noteList[position]
        holder.itemView.setOnClickListener {
            var action = EntryFragmentDirections.actionEntryFragmentToNotesFragment("",noteidlist.get(position))
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
    return  noteList.size
    }
}