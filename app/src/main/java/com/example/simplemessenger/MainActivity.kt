package com.example.simplemessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.databinding.ActivityMainBinding
import com.example.simplemessenger.ui.fragments.ChatsFragment
import com.example.simplemessenger.ui.objects.AppDrawer
import com.example.simplemessenger.utilits.AUTH
import com.example.simplemessenger.utilits.initFirebase
import com.example.simplemessenger.utilits.replaceActivity
import com.example.simplemessenger.utilits.replaceFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var mToolbar: Toolbar

    private lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunctions()
    }

    private fun initFunctions() {
        setSupportActionBar(mToolbar)
     if(AUTH.currentUser!=null) {
         mAppDrawer.create()
         replaceFragment(ChatsFragment(),false)
         mAppDrawer = AppDrawer(this, mToolbar)
     }else{
         replaceActivity(RegisterActivity())
     }
    }

    private fun initFields() {
       mToolbar = mBinding.mainToolBar
        mAppDrawer = AppDrawer(this,mToolbar)
        initFirebase()

    }
}