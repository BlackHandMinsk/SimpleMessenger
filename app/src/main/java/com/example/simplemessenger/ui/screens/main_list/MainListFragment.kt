package com.example.simplemessenger.ui.screens.main_list

import android.database.CursorIndexOutOfBoundsException
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.utilits.*

import kotlinx.android.synthetic.main.fragment_main_list.*


class MainListFragment : Fragment(R.layout.fragment_main_list) {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Simple messenger"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeybord()
        initRecycleView()
    }

    private fun initRecycleView() {
       mRecyclerView = main_list_recycle_view
        mAdapter = MainListAdapter()
                /// 1 запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener{
            mListItems = it.children.map { it.getCommonModel() }
            mListItems.forEach { model->

                when(model.type){
                    TYPE_CHAT->showChat(model)
                    TYPE_GROUP->showGroup(model)
                }
            }
        })
        mRecyclerView.adapter = mAdapter
    }

    private fun showGroup(model: CommonModel) {
        REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id).addListenerForSingleValueEvent(AppValueEventListener{
            val newModel = it.getCommonModel()
            // 3 хапрос
            REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id).child(NODE_MESSAGES).limitToLast(1).addListenerForSingleValueEvent(AppValueEventListener{
                val tempList = it.children.map{it.getCommonModel()}

                if(tempList.isEmpty()){
                    newModel.lastMessage = "Чат очищен"
                }else{
                    newModel.lastMessage = tempList[0].text
                }
                mAdapter.updateListItems(newModel)
            })
        })
    }

    private fun showChat(model: CommonModel) {
        mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{
            val newModel = it.getCommonModel()
            // 3 хапрос
            mRefMessages.child(model.id).limitToLast(1).addListenerForSingleValueEvent(AppValueEventListener{
                val tempList = it.children.map{it.getCommonModel()}

                if(tempList.isEmpty()){
                    newModel.lastMessage = "Чат очищен"
                }else{
                    newModel.lastMessage = tempList[0].text
                }
                if(newModel.fullname.isEmpty()){
                    newModel.fullname = newModel.phone
                }
                mAdapter.updateListItems(newModel)
            })
        })
    }
}