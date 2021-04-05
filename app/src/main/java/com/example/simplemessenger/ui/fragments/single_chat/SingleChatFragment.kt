package com.example.simplemessenger.ui.fragments.single_chat

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.models.UserModel
import com.example.simplemessenger.ui.fragments.BaseFragment
import com.example.simplemessenger.utilits.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*
import kotlinx.coroutines.channels.consumesAll


class SingleChatFragment(private val contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var  mListenerInfoToolbar:AppValueEventListener
    private lateinit var  mReceivingUser:UserModel
    private lateinit var mToolBarInfo:View
    private lateinit var mRefUser:DatabaseReference
    private lateinit var mRefMessages:DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppChildEventListener
   private var mCountMessages = 15
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onResume() {
        super.onResume()
        initFields()
        initToolbar()
        initRecycleView()
    }

    private fun initFields() {
        mSwipeRefreshLayout = chat_swipe_refresh
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecycleView() {
        mRecyclerView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID).child(contact.id)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = mLayoutManager
        mMessagesListener = AppChildEventListener {
            val message = it.getCommonModel()
            if(mSmoothScrollToPosition){
                mAdapter.addItemToBottom(message){
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                }
            }else{
                mAdapter.addItemToTop(message){
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }


        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(mIsScrolling&&dy<0&&mLayoutManager.findFirstVisibleItemPosition()<=3){
                    updateData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true
                }
            }
        })
        mSwipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        mSmoothScrollToPosition = false
        mIsScrolling = false
        mCountMessages +=10
        mRefMessages.removeEventListener(mMessagesListener)
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

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
            mSmoothScrollToPosition = true
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