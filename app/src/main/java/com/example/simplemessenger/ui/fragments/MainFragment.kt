package com.example.simplemessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplemessenger.R
import com.example.simplemessenger.databinding.FragmentChatsBinding
import com.example.simplemessenger.utilits.APP_ACTIVITY


class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Simple messenger"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}