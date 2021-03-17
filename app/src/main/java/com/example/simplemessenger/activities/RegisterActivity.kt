package com.example.simplemessenger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.simplemessenger.R
import com.example.simplemessenger.databinding.ActivityRegisterBinding
import com.example.simplemessenger.ui.fragments.EnterPhoneNumberFragment
import com.example.simplemessenger.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolBar
//       setSupportActionBar(mToolbar)
        title = "Ваш телефон"
        replaceFragment(EnterPhoneNumberFragment())
    }
}