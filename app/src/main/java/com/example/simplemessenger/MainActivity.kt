package com.example.simplemessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.simplemessenger.databinding.ActivityMainBinding
import com.example.simplemessenger.ui.fragments.ChatsFragment
import com.example.simplemessenger.ui.objects.AppDrawer

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
      //  setSupportActionBar(mToolbar)
        mAppDrawer.create()
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                ChatsFragment()
            ).commit()
        mAppDrawer = AppDrawer(this,mToolbar)

    }

    private fun initFields() {
       mToolbar = mBinding.mainToolBar
        mAppDrawer = AppDrawer(this,mToolbar)

    }
}