package com.example.simplemessenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R
import com.example.simplemessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_user_name.*
import java.util.*


class ChangeUserNameFragment : BaseFragment(R.layout.fragment_change_user_name) {

    lateinit var mNewUserName:String

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        settings_input_username.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change->change()
        }
        return true
    }

    private fun change() {
        mNewUserName = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if(mNewUserName.isEmpty()){
            showToast("Поле пустое")
        }else{
            REF_DATABASE_ROOT.child(NODE_USERNAMES).addListenerForSingleValueEvent(AppValueEventListener{
                if(it.hasChild(mNewUserName)){
                    showToast("Такой пользователь уже существует")
                }else{
                    changeUserName()
                }
            })
        }
    }

    private fun changeUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUserName).setValue(UID).addOnCompleteListener() {
            if (it.isSuccessful){
                updateCurrentUserName()
            }
        }
    }

    private fun updateCurrentUserName() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME).setValue(mNewUserName).addOnCompleteListener{
            if(it.isSuccessful){
                showToast("Все ок")
                deleteOldUsername()
            }else{
                showToast(it.exception?.message.toString())
            }
        }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    showToast("Все ок")
                    fragmentManager?.popBackStack()
                    USER.username = mNewUserName
                }else{
                    showToast(it.exception?.message.toString())
                }
            }
    }
}