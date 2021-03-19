package com.example.simplemessenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R
import com.example.simplemessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment :BaseFragment(R.layout.fragment_change_name) {

  override fun onResume() {
    super.onResume()
    setHasOptionsMenu(true)
    val fullNameList = USER.fullname.split(" ")
    settings_input_name.setText(fullNameList[0])
    settings_input_surname.setText(fullNameList[1])
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      R.id.settings_confirm_change->changeName()
    }
    return true
  }

  private fun changeName() {
    val name = settings_input_name.text.toString()
    val surname = settings_input_surname.text.toString()
    if(name.isEmpty()){
      showToast("Имя не может быть пустым")
    }else{
      val fullname = "$name $surname"
      REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME).setValue(fullname).addOnCompleteListener{
        if(it.isSuccessful){
          showToast("Данные обновлены")
          USER.fullname = fullname
          fragmentManager?.popBackStack()
        }
      }
    }
  }
}