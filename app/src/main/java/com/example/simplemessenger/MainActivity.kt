package com.example.simplemessenger

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.simplemessenger.database.AUTH
import com.example.simplemessenger.database.initFirebase
import com.example.simplemessenger.database.initUser

import com.example.simplemessenger.databinding.ActivityMainBinding
import com.example.simplemessenger.ui.screens.MainFragment
import com.example.simplemessenger.ui.screens.register.EnterPhoneNumberFragment
import com.example.simplemessenger.ui.objects.AppDrawer
import com.example.simplemessenger.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
      lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY =this
        initFirebase()
        initUser {
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }
            initFields()
            initFunctions()
        }
    }



    private fun initFunctions() {
        setSupportActionBar(mToolbar)
     if(AUTH.currentUser!=null) {
         mAppDrawer.create()
         replaceFragment(MainFragment(),false)
     }else{
        replaceFragment(EnterPhoneNumberFragment(),false)
     }
    }

    private fun initFields() {
       mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer()

            }


    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            initContacts()
        }
    }
}