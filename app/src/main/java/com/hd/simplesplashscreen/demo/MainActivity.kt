package com.hd.simplesplashscreen.demo

import android.os.Bundle
import com.hd.splashscreen.SimpleConfig
import com.hd.splashscreen.SimpleSplashFinishCallback
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), SimpleSplashFinishCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        horizontalScreen.addConfig(getSimpleConfig(20f,true))
        horizontalScreen.start()
        verticalScreen.addConfig(getSimpleConfig(10f))
        verticalScreen.start()
    }

    private fun getSimpleConfig(size: Float = 30f, needCallback: Boolean = false): SimpleConfig {
        val simpleConfig = SimpleConfig()
        simpleConfig.text = "SIMPLESPLASHSCREEN"
        simpleConfig.textColor = R.color.colorAccent
        simpleConfig.textSize = size
        simpleConfig.iconId = R.mipmap.ic_launcher
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
