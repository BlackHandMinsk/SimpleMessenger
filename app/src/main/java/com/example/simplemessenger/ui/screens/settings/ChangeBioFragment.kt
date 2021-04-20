package com.example.simplemessenger.ui.screens.settings

import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.ui.screens.base.BaseChangeFragment
import kotlinx.android.synthetic.main.fragment_change_bio.*


class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDatabase(newBio)
    }
}