package com.example.simplemessenger.ui.screens

import android.view.*
import androidx.fragment.app.Fragment
import com.example.simplemessenger.R
import com.example.simplemessenger.utilits.APP_ACTIVITY
import com.example.simplemessenger.utilits.hideKeybord

open class BaseChangeFragment(layout:Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        (APP_ACTIVITY).mAppDrawer.disableDrawer()
        hideKeybord()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change->change()
        }
        return true
    }

    open fun change() {

    }
}