package com.hd.simplesplashscreen.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hd.splashscreen.SimpleConfig
import com.hd.splashscreen.SimpleSplashFinishCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SimpleSplashFinishCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        simpleSplashScreen.addConfig(getSimpleConfig(25f))
        simpleSplashScreen.start()
    }

    private fun getSimpleConfig(size:Float=30f): SimpleConfig {
        val simpleConfig = SimpleConfig()
        simpleConfig.text = "SIMPLESPLASHSCREEN"
        simpleConfig.textColor = R.color.colorAccent
        simpleConfig.textSize = size
        simpleConfig.iconId = R.mipmap.ic_launcher
        simpleConfig.iconDelayTime=800
        simpleConfig.callback=this
        return simpleConfig
    }

    override fun loadFinish() {
        Toast.makeText(this,"load completed",Toast.LENGTH_SHORT).show()
    }

}
