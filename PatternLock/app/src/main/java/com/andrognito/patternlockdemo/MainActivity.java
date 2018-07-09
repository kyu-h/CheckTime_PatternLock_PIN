package com.andrognito.patternlockdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    TextView textView = null;
    String pattern;
    String pattern01;
    int ex_num = 0;

    Handler handler = null;
    boolean flag = true;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progressssss: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
            pattern01 = PatternLockUtils.patternToString(mPatternLockView, progressPattern);
            ex_num++;
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.profile_name);

        mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        handler = new Handler();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    final double time = mPatternLockView.getTime();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(0.0 < time && time < 1.0){
                                textView.setText("short click");
                            }else if(1.0 < time && time < 2.0) {
                                textView.setText("middle click");
                            }else if(2.0 < time) {
                                textView.setText("long click");
                            }else if(time < 0.0){
                                textView.setText("");
                            }
                        }
                    });
                }
            }
        });

        thread.start();

        RxPatternLockView.patternComplete(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompleteEvent>() {
                    @Override
                    public void accept(PatternLockCompleteEvent patternLockCompleteEvent) throws Exception {
                        Log.d(getClass().getName(), "Complete: " + patternLockCompleteEvent.getPattern().toString());
                        ex_num = 0;
                    }
                });

        RxPatternLockView.patternChanges(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log.d(getClass().getName(), "Pattern drawing started"); //최초로 그림 그릴때
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {

                            Log.d("getTime", String.valueOf(mPatternLockView.getTime()));
                            mPatternLockView.resetTime();
                            Log.d(getClass().getName(), "Pattern progress: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));

                            pattern = PatternLockUtils.patternToString(mPatternLockView, event.getPattern());

                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            Log.d(getClass().getName(), "Pattern complete: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                            ex_num = 0;
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log.d(getClass().getName(), "Pattern has been cleared");
                        }
                    }
                });
    }
}