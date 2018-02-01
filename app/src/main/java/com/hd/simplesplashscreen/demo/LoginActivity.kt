package com.hd.simplesplashscreen.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by hd on 2018/1/21 .
 *
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        backgroundVideo.setPlayRawId(R.raw.video1)
        backgroundVideo.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundVideo.stop()
    }

    fun onLogin(view:View?){
        Toast.makeText(this,"login",Toast.LENGTH_SHORT).show()
    }
}