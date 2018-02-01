package com.hd.splashscreen.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;


/**
 * Created by hd on 2018/1/20 .
 * provide background video view
 */
public class BackgroundVideo extends VideoView {

    public BackgroundVideo(Context context) {
        super(context);
    }

    public BackgroundVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackgroundVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
