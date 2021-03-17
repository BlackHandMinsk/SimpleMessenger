package com.example.simplemessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.simplemessenger.MainActivity
import com.example.simplemessenger.R
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.utilits.AUTH
import com.example.simplemessenger.utilits.AppTextWatcher
import com.example.simplemessenger.utilits.replaceActivity
import com.example.simplemessenger.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter__code.*

class EnterCodeFragment(val phoneNumber: String,val id: String) : Fragment(R.layout.fragment_enter__code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
                val string = register_input_code.text.toString()
                if(string.length==6){
                    enterCode()
                }
        })
    }

    private fun  enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id,code)
        AUTH.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            }else showToast(it.exception?.message.toString())
        }
    }
}