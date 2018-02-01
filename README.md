# SimpleSplashScreen

## Provide a simple splash screen animation (use of demo)

<img src="art/text_screen.gif" width="250px" height="500px"/> <img src="art/video_screen.gif" width="250px" height="500px"/>

## dependencies

```
compile 'com.hd.splashscreen:splashscreen:1.3'
```

## in layout xml:

```
<com.hd.splashscreen.SimpleSplashScreen
    android:id="@+id/simpleSplashScreen"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

## in code:

```
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
        simpleConfig.iconSize=0.7f
        simpleConfig.callback=this
        return simpleConfig
    }

    override fun loadFinish() {
        Toast.makeText(this,"load completed",Toast.LENGTH_SHORT).show()
    }
```