package com.hd.simplesplashscreen.demo

import android.os.Bundle
import com.hd.splashscreen.text.SimpleConfig
import com.hd.splashscreen.text.SimpleSplashFinishCallback
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), SimpleSplashFinishCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //default
        horizontalScreen.start()
        //custom
        verticalScreen.addConfig(getSimpleConfig(10f))
        verticalScreen.start()
    }

    private fun getSimpleConfig(size: Float = 30f, needCallback: Boolean = false): SimpleConfig {
        val simpleConfig = SimpleConfig(this)
        simpleConfig.text = "SIMPLESPLASHSCREEN"
        simpleConfig.setTextColorFromResources(R.color.colorAccent)
        simpleConfig.textSize = size
        simpleConfig.setIconId(R.mipmap.ic_launcher)
        simpleConfig.iconDelayTime = 800
        simpleConfig.iconSize = 0.7f
        if (needCallback)
            simpleConfig.callback = this
        return simpleConfig
    }

    override fun loadFinish() {
        startActivity<LoginActivity>()
        finish()
    }

}
