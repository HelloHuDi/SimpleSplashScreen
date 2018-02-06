# SimpleSplashScreen

## Provide a simple splash screen animation (use of demo)

## dependencies

```
compile 'com.hd.splashscreen:splashscreen:1.4'
```

## screenshot:

<img src="art/text_screen.gif" width="250px" height="500px"/> <img src="art/video_screen.gif" width="250px" height="500px"/>

## use about text view:

#### in xml:
```
 <com.hd.splashscreen.text.SimpleSplashScreen
        android:id="@+id/horizontalScreen"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"/>
```

#### code (default usage):

```
  horizontalScreen.start();
```