package com.hd.splashscreen.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.hd.splashscreen.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by hd on 2018/1/20 .
 * background video
 */
public class BackgroundVideoView extends FrameLayout implements View.OnClickListener, //
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener {

    private boolean controlVideo, loopPlay, mute;
    private AtomicBoolean touching = new AtomicBoolean(false);
    private Uri playUri;
    private BackgroundVideo backgroundVideo;
    private RelativeLayout screen;
    private ImageButton btPlayControl;
    private SeekBar seekBar;
    private TextView currentDuration, allDuration;
    private Timer positionTimer;
    private Handler handler;
    /**
     * play state ,
     * <p>
     * -1:waiting , 0 : playing, 1 : pausing, 2 : completed
     */
    private int playState = -1;

    public BackgroundVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    @SuppressLint({"CustomViewStyleable"})
    public BackgroundVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.background_video_style);
        setControlVideo(typedArray.getBoolean(R.styleable.background_video_style_controlPlay, false));
        setLoopPlay(typedArray.getBoolean(R.styleable.background_video_style_loopPlay, true));
        mute = typedArray.getBoolean(R.styleable.background_video_style_mute, true);
        typedArray.recycle();
        init();
    }

    public BackgroundVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_background_video, this, true);
        backgroundVideo = view.findViewById(R.id.video);
        btPlayControl = view.findViewById(R.id.btPlayControl);
        currentDuration = view.findViewById(R.id.currentDuration);
        allDuration = view.findViewById(R.id.allDuration);
        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(this);
        btPlayControl.setOnClickListener(this);
        screen = view.findViewById(R.id.screen);
        screen.setVisibility(controlVideo ? View.VISIBLE : View.GONE);
        screen.setOnClickListener(this);
    }

    public void setControlVideo(boolean controlVideo) {
        this.controlVideo = controlVideo;
        if (controlVideo) {
            alphaHandler();
        }
    }

    private Runnable alphaRunnable;

    private void alphaHandler() {
        if (controlVideo)
            if (handler == null)
                handler = new Handler();
        handler.postDelayed(getAlphaHandler(), 3000);
    }

    @NonNull
    private Runnable getAlphaHandler() {
        alphaRunnable = new Runnable() {
            @Override
            public void run() {
                if (touching.get()) {
                    touching.set(false);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (screen.getVisibility() == View.VISIBLE) {
                                screen.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                alphaHandler();
            }
        };
        return alphaRunnable;
    }

    public void addMiddleLayer(View view) {
        addView(view, 1);
    }

    public void setLoopPlay(boolean loopPlay) {
        this.loopPlay = loopPlay;
    }

    public void setPlayRawId(@RawRes int id) {
        setPlayPath("android.resource://" + getContext().getPackageName() + "/" + id);
    }

    public void setPlayPath(String path) {
        setPlayUri(Uri.parse(path));
    }

    public void setPlayUri(Uri playUri) {
        this.playUri = playUri;
        Log.d("tag", "video play path：" + playUri.toString());
    }

    public void start() {
        initVideo();
    }

    public void stop() {
        if (handler != null) {
            handler.removeCallbacks(alphaRunnable);
            handler = null;
        }
        stopPlayVideo();
        clearPositionTimer();
    }

    public VideoView getVideoView() {
        return backgroundVideo;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private boolean prepareVideo = false;

    private void initVideo() {
        if (controlVideo) {
            prepareVideo = true;
            playVideo();
            playState = -1;
            screen.setVisibility(View.VISIBLE);
            btPlayControl.setImageResource(R.drawable.play);
        } else {
            playVideo();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (controlVideo) {
            touching.set(true);
            createAlphaHandler();
            screen.setVisibility(View.VISIBLE);
            return true;
        } else {
            return false;
        }
    }

    private void createAlphaHandler() {
        if (handler != null) {
            handler.removeCallbacks(alphaRunnable);
            alphaHandler();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.screen) {
            screen.setVisibility(screen.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            touching.set(screen.getVisibility() != View.VISIBLE);
            if (touching.get())
                createAlphaHandler();
        } else {
            switch (playState) {
                case -1://waiting
                    playVideo();
                    break;
                case 0://playing
                    pauseVideo();
                    break;
                case 1://pausing
                    resumePlayVideo();
                    break;
                case 2://completed
                    playVideo();
                    break;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void clearPositionTimer() {
        if (positionTimer != null) {
            positionTimeTask.cancel();
            positionTimer.cancel();
            positionTimer = null;
            positionTimeTask = null;
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.INVISIBLE) {
            stop();
        }
    }

    private void playVideo() {
        if (controlVideo) {
            btPlayControl.setImageResource(R.drawable.pause);
            screen.setVisibility(View.GONE);
        }
        playState = 0;
        backgroundVideo.setVideoURI(playUri);
        backgroundVideo.setLayoutParams(new LayoutParams(-1, -1));
        backgroundVideo.setOnPreparedListener(this);
        backgroundVideo.setOnCompletionListener(this);
    }

    private void pauseVideo() {
        if (controlVideo) {
            btPlayControl.setImageResource(R.drawable.play);
        }
        playState = 1;
        backgroundVideo.pause();
    }

    private void resumePlayVideo() {
        if (controlVideo) {
            btPlayControl.setImageResource(R.drawable.pause);
            screen.setVisibility(View.GONE);
        }
        playState = 0;
        backgroundVideo.start();
    }

    private void stopPlayVideo() {
        if (backgroundVideo != null && backgroundVideo.isPlaying())
            backgroundVideo.stopPlayback();
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer = mp;
        mp.setLooping(loopPlay);
        if (prepareVideo) {
            mp.seekTo(1);
            prepareVideo = false;
        } else {
            if (mute) {
                mp.setVolume(0, 0);
            }
            mp.start();
            initPositionTimer();
        }
        playDuration = mp.getDuration();
        seekBar.setMax(playDuration);
        allDuration.setText(ConvertTime.secToTime(playDuration));
    }

    private int playDuration;

    private void initPositionTimer() {
        positionTimer = new Timer();
        positionTimer.schedule(getPositionTimeTask(), 1000, 1000);
    }

    private TimerTask positionTimeTask;

    private int playProgress = 0;

    private TimerTask getPositionTimeTask() {
        positionTimeTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        try {
                            playProgress = mediaPlayer.getCurrentPosition();
                            if (playProgress >= progress) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    seekBar.setProgress(playProgress, true);
                                } else {
                                    seekBar.setProgress(playProgress);
                                }
                            }
                        } finally {
                            if (playProgress >= playDuration) {
                                cancel();
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                    playVideo();
                }
            }
        };
        return positionTimeTask;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!loopPlay && controlVideo) {
            screen.setVisibility(View.VISIBLE);
            playState = 2;
            clearPositionTimer();
            progress = 0;
            currentDuration.setText(ConvertTime.secToTime(playDuration));
            btPlayControl.setImageResource(R.drawable.loop);
        }
    }

    private int progress;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.progress = progress;
        currentDuration.setText(ConvertTime.secToTime(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        touching.set(false);
        clearPositionTimer();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        touching.set(true);
        createAlphaHandler();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(progress);
            initPositionTimer();
        }
    }
}
