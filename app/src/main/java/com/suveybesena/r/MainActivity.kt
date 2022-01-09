package com.suveybesena.r

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInf = menuInflater
        menuInf.inflate(R.menu.add_notes, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.adding_note_item){
            var action = EntryFragmentDirections.actionEntryFragmentToNotesFragment("menudengeldim")
            Navigation.findNavController(this,R.id.fragmentContainerView).navigate(action)
        }

        if (item.itemId == R.id.update_profile_item){
            var action2 = EntryFragmentDirections.actionEntryFragmentToDeleteFragment()
            Navigation.findNavController(this,R.id.fragmentContainerView).navigate(action2)
        }

        return super.onContextItemSelected(item)

    }





}