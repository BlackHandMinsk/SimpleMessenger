package com.example.simplemessenger.ui.screens

import androidx.fragment.app.Fragment
import com.example.simplemessenger.R
import com.example.simplemessenger.databinding.FragmentChatsBinding
import com.example.simplemessenger.utilits.APP_ACTIVITY
import com.example.simplemessenger.utilits.hideKeybord


class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Simple messenger"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeybord()
    }
}