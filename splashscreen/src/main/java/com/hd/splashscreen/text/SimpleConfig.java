package com.hd.splashscreen.text;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by hd on 2018/1/30 .
 * config
 */
public class SimpleConfig {
    private Context context;
    private String text;
    private Drawable icon;
    private int textColor = Color.parseColor("#FF4081");
    private float textSize = 20f;
    private int iconDelayTime = 1000;
    private float iconSize = 0.8f;
    private int animationDuration = 1800;
    private SimpleSplashFinishCallback callback;

    public SimpleConfig(Context context) {
        try {
            this.context=context.getApplicationContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            setText(context.getResources().getString(labelRes).toUpperCase());
            ApplicationInfo info = packageManager.getApplicationInfo(context.getPackageName(), 0);
            setIcon(info.loadIcon(packageManager));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(@Nullable Drawable icon) {
        this.icon = icon;
    }

    public void setIconId(@DrawableRes int iconId) {
        setIcon(context.getResources().getDrawable(iconId));
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }

    public void setTextColorFromResources(@ColorRes int textColor) {
       setTextColor(context.getResources().getColor(textColor));
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getIconDelayTime() {
        return iconDelayTime;
    }

    public void setIconDelayTime(int iconDelayTime) {
        this.iconDelayTime = iconDelayTime;
    }

    public float getIconSize() {
        return iconSize;
    }

    public void setIconSize(float iconSize) {
        this.iconSize = iconSize > 1 || iconSize < 0 ? 1 : iconSize;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public SimpleSplashFinishCallback getCallback() {
        return callback;
    }

    public void setCallback(SimpleSplashFinishCallback callback) {
        this.callback = callback;
    }
}
