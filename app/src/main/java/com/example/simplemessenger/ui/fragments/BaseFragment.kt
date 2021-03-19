package com.example.simplemessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R

open class BaseFragment(layout:Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
         (activity as MainActivity).mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).mAppDrawer.enableDrawer()
    }
}