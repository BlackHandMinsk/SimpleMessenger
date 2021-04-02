package com.example.simplemessenger.ui.fragments.single_chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.models.UserModel
import com.example.simplemessenger.ui.fragments.BaseFragment
import com.example.simplemessenger.utilits.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var  mListenerInfoToolbar:AppValueEventListener
    private lateinit var  mReceivingUser:UserModel
    private lateinit var mToolBarInfo:View
    private lateinit var mRefUser:DatabaseReference
    private lateinit var mRefMessages:DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppValueEventListener
    var mListMessages = emptyList<CommonModel>()

    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID).child(contact.id)
        mRecyclerView.adapter = mAdapter
        mMessagesListener = AppValueEventListener { dataSnapshot->
            mListMessages = dataSnapshot.children.map { it.getCommonModel() }
            mAdapter.setList(mListMessages)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mRefMessages.addValueEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolBarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolBarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        chat_btn_sent_message.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else sendMessage(message, contact.id, TYPE_TEXT) {
                chat_input_message.setText("")
            }
        }
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
        mRefMessages.removeEventListener(mMessagesListener)
    }
}