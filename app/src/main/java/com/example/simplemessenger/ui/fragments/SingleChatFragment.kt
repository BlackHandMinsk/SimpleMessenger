package com.example.simplemessenger.ui.fragments

import android.view.View
import com.example.simplemessenger.R
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.models.UserModel
import com.example.simplemessenger.utilits.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var  mListenerInfoToolbar:AppValueEventListener
    private lateinit var  mReceivingUser:UserModel
    private lateinit var mToolBarInfo:View
    private lateinit var mRefUser:DatabaseReference

    override fun onResume() {
        super.onResume()
        mToolBarInfo =   APP_ACTIVITY.mToolbar.toolbar_info
        mToolBarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }

    private fun initInfoToolbar() {
        if(mReceivingUser.fullname.isEmpty()) {
            mToolBarInfo.toolbar_chat_fullname.text = contact.fullname
        }else   mToolBarInfo.toolbar_chat_fullname.text = mReceivingUser.fullname
        mToolBarInfo.toolbar_chat_image.downloadAndSetImage(mReceivingUser.photoUrl)

        mToolBarInfo.toolbar_chat_status.text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        mToolBarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }
}