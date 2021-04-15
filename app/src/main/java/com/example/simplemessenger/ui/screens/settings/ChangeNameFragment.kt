package com.example.simplemessenger.ui.screens.settings

import com.example.simplemessenger.R
import com.example.simplemessenger.database.*
import com.example.simplemessenger.ui.screens.BaseChangeFragment
import com.example.simplemessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

  override fun onResume() {
    super.onResume()
    initFullnameList()
  }

  private fun initFullnameList() {
    val fullNameList = USER.fullname.split(" ")
    if (fullNameList.size > 1) {
      settings_input_name.setText(fullNameList[0])
      settings_input_surname.setText(fullNameList[1])
      } else settings_input_name.setText(fullNameList[0])
  }

  override fun change() {
    val name = settings_input_name.text.toString()
    val surname = settings_input_surname.text.toString()
    if(name.isEmpty()){
      showToast("Имя не может быть пустым")
    }else{
      val fullname = "$name $surname"
      setNameToDatabase(fullname)
    }
  }
}