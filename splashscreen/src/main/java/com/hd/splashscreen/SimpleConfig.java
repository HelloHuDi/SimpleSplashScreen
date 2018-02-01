package com.hd.splashscreen;

import android.graphics.Color;
import android.support.annotation.DrawableRes;

/**
 * Created by hd on 2018/1/30 .
 *
 */
public class SimpleConfig {
    private String text;
    private @DrawableRes int iconId;
    private int textColor= Color.RED;
    private float textSize=20f;
    private int iconDelayTime;
    private float iconSize = 0.8f;
    private int animationDuration=1800;
    private SimpleSplashFinishCallback callback;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
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
