package com.example.simplemessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.simplemessenger.R
import com.example.simplemessenger.utilits.replaceFragment
import com.example.simplemessenger.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*


class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    override fun onStart() {
        super.onStart()
      register_btn_next.setOnClickListener{sendCode()}
    }

    private fun sendCode() {
if(register_input_phone_number.text.toString().isEmpty()){
    showToast("Введите телефон")
}else{
    replaceFragment(EnterCodeFragment())
}
    }
}