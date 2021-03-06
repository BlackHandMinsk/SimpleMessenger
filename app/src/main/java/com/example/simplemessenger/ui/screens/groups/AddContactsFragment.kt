package com.example.simplemessenger.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.ui.screens.base.BaseFragment
import com.example.simplemessenger.utilits.*

import kotlinx.android.synthetic.main.fragment_add_contacts.*


class AddContactsFragment : BaseFragment(R.layout.fragment_add_contacts) {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:AddContactsAdapter
    private val mRefContactsList = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        listContacts.clear()
        super.onResume()
        APP_ACTIVITY.title = "Добавить участников"
        hideKeybord()
        initRecycleView()
        add_contacts_btn_next.setOnClickListener{
            if(listContacts.isEmpty()) showToast("Добавьте участников")
            else replaceFragment(CreateGroupFragment(listContacts))
        }
    }

    private fun initRecycleView() {
       mRecyclerView = add_contacts_recycle_view
        mAdapter = AddContactsAdapter()
                /// 1 запрос
        mRefContactsList.addListenerForSingleValueEvent(AppValueEventListener{
            mListItems = it.children.map { it.getCommonModel() }
            mListItems.forEach { model->


                //2 запрос
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
        })
        mRecyclerView.adapter = mAdapter
    }

    companion object{
        val listContacts = mutableListOf<CommonModel>()
    }
}