package com.example.simplemessenger

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.example.simplemessenger.activities.RegisterActivity
import com.example.simplemessenger.databinding.ActivityMainBinding
import com.example.simplemessenger.models.User
import com.example.simplemessenger.ui.fragments.ChatsFragment
import com.example.simplemessenger.ui.objects.AppDrawer
import com.example.simplemessenger.utilits.*
import com.theartofdev.edmodo.cropper.CropImage

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
        APP_ACTIVITY =this
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
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).addListenerForSingleValueEvent(AppValueEventListener{
            USER = it.getValue(User::class.java)?:User()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode== Activity.RESULT_OK &&data!=null){
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURRENT_UID)
            path.putFile(uri).addOnCompleteListener{
                if(it.isSuccessful){
                    showToast("данные обновлены")
                }
            }
        }
    }

    fun hideKeybord(){
        val imn:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(window.decorView.windowToken,0)
    }
}