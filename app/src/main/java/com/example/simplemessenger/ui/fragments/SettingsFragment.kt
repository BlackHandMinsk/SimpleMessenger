package com.example.simplemessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.*
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.databinding.FragmentSettingsBinding
import com.example.simplemessenger.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
        settings_btn_change_user_name.setOnClickListener{replaceFragment(ChangeUserNameFragment())}
        settings_btn_change_bio.setOnClickListener{replaceFragment(ChangeBioFragment())}
        settings_change_photo.setOnClickListener{
            changePhotoUser()
        }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1,1)
            .setRequestedSize(600,600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      activity?.menuInflater?.inflate(R.menu.settings_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_exit->{
                AUTH.signOut()
              APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name->replaceFragment(ChangeNameFragment())
        }
        return true
    }
}