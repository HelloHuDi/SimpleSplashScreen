package com.hd.splashscreen.text;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hd.splashscreen.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by hd on 2018/1/30 .
 * simple splash screen animation
 */
public class SimpleSplashScreen extends LinearLayout {

    private LinearLayout linContent;

    private int width, height;

    private SimpleConfig config;

    private List<View> viewList;

    private float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());

    private final String tag = "lastViewTag";

    public SimpleSplashScreen(Context context) {
        super(context);
        init();
    }

    public SimpleSplashScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleSplashScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.simple_splash_screen_layout, this);
        linContent = rootView.findViewById(R.id.linContent);
        linContent.setOrientation(getOrientation());
        linContent.setGravity(Gravity.CENTER);
        addConfig(new SimpleConfig(getContext()));
    }

    public void addConfig(@NonNull SimpleConfig config) {
        this.config = config;
        viewList = new ArrayList<>();
    }

    public void start() {
        if (config == null)
            return;
        linContent.removeAllViews();
        String text = config.getText();
        if (text != null && text.length() > 0) {
            for (int index = 0, len = text.length(); index < len; index++) {
                String str = ((Character) text.charAt(index)).toString();
                addContentView(createTextView(str));
            }
            if (config.getIcon()!=null) {
                addContentView(createIconView(viewList.get(viewList.size() - 1)));
            }
            viewList.get(viewList.size() - 1).setTag(tag);
            viewList.get(0).post(new Runnable() {
                @Override
                public void run() {
                    for (final View view : viewList) {
                        if (!(view instanceof TextView)) {
                            startLastView(view);
                        } else {
                            startViewInAnim(view);
                        }
                    }
                }
            });
        }
    }

    private void startLastView(final View view) {
        if (config.getIconDelayTime() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startViewInAnim(view);
                }
            }, config.getIconDelayTime());
        }else{
            startViewInAnim(view);
        }
    }

    private void addContentView(View view) {
        view.setVisibility(View.INVISIBLE);
        viewList.add(view);
        linContent.addView(view);
        view.setTag("tag");
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @NonNull
    private TextView createTextView(String str) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(config.getTextColor());
        textView.setTextSize(config.getTextSize() > 0 ? config.getTextSize() : defaultTextSize);
        textView.setText(str);
        return textView;
    }

    @SuppressLint("RtlHardcoded")
    @NonNull
    private View createIconView(final View view) {
        final float textSize = config.getTextSize() > 0 ? config.getTextSize() : defaultTextSize;
        final float iconSize = textSize*config.getIconSize();
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(config.getIcon());
        final LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams((int) iconSize, (int) iconSize);
        imageView.setLayoutParams(imageParams);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect1 = new Rect();
                Rect rect2 = new Rect();
                view.getGlobalVisibleRect(rect1);
                imageView.getGlobalVisibleRect(rect2);
                imageParams.topMargin = (rect1.bottom-rect2.bottom)/2;
                imageParams.width = (int) iconSize;
                imageParams.height = (int) iconSize;
                imageView.setLayoutParams(imageParams);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        return imageView;
    }

    private void startViewInAnim(View v) {
        v.setVisibility(View.VISIBLE);
        Random r = new Random();
        int x = r.nextInt(width * 4 / 3);
        int y = r.nextInt(height * 4 / 3);
        float s = r.nextFloat() + 4.0f;
        ValueAnimator tranY = ObjectAnimator.ofFloat(v, "translationY", y - v.getY(), 0);
        ValueAnimator tranX = ObjectAnimator.ofFloat(v, "translationX", x - v.getX(), 0);
        ValueAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", s, 1.0f);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", s, 1.0f);
        ValueAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(config.getAnimationDuration());
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(tranX, tranY, scaleX, scaleY, alpha);
        if (tag.equals(v.getTag()))
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (config.getCallback() != null)
                        config.getCallback().loadFinish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        set.start();
    }

}
