package com.example.simplemessenger.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.simplemessenger.R
import com.example.simplemessenger.databinding.FragmentChatsBinding
import com.example.simplemessenger.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var mBinding: FragmentSettingsBinding

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      activity?.menuInflater?.inflate(R.menu.settings_action_menu,menu)
    }
}