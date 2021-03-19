package com.example.simplemessenger.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.databinding.FragmentChatsBinding
import com.example.simplemessenger.databinding.FragmentSettingsBinding
import com.example.simplemessenger.utilits.AUTH
import com.example.simplemessenger.utilits.USER
import com.example.simplemessenger.utilits.replaceActivity
import com.example.simplemessenger.utilits.replaceFragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var mBinding: FragmentSettingsBinding

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        id_settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        id_settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        id_settings_user_name.text = USER.username
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      activity?.menuInflater?.inflate(R.menu.settings_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_exit->{
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name->replaceFragment(ChangeNameFragment())
        }
        return true
    }
}