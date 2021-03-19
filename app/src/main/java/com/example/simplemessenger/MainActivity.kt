package com.example.simplemessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.databinding.ActivityMainBinding
import com.example.simplemessenger.models.User
import com.example.simplemessenger.ui.fragments.ChatsFragment
import com.example.simplemessenger.ui.objects.AppDrawer
import com.example.simplemessenger.utilits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

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
     if(AUTH.currentUser!=null) {
         setSupportActionBar(mToolbar)
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
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).addListenerForSingleValueEvent(AppValueEventListener{
            USER = it.getValue(User::class.java)?:User()
        })
    }
}