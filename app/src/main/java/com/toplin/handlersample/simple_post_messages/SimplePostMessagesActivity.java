package com.toplin.handlersample.simple_post_messages;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toplin.handlersample.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class SimplePostMessagesActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final long DELAY = 3000L;

    private static final int POST = 0;
    private static final int POST_DELAY = 1;
    private static final int POST_AT_TIME = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POST, POST_DELAY, POST_AT_TIME})
    private @interface Action {
    }

    private TextView resultView;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_post_messages);

        setUpViews();
    }

    private void setUpViews() {
        resultView = findViewById(R.id.view_result);

        Button postBtn = findViewById(R.id.btn_post);
        postBtn.setOnClickListener(this);
        Button postDelayBtn = findViewById(R.id.btn_post_delay);
        postDelayBtn.setOnClickListener(this);
        Button postAtTimeBtn = findViewById(R.id.btn_post_at_time);
        postAtTimeBtn.setOnClickListener(this);
    }

    private void doAction(@Action int action) {
        switch (action) {
            case POST:
                mainHandler.post(
                        new ActionRunnable(resultView,
                                R.string.simple_post_messages_post));
                break;
            case POST_DELAY:
                mainHandler.postDelayed(
                        new ActionRunnable(resultView,
                                R.string.simple_post_messages_post_delay), DELAY);
                break;
            case POST_AT_TIME:
                mainHandler.postAtTime(
                        new ActionRunnable(resultView,
                                R.string.simple_post_messages_post_at_time), getTimeAt());
                break;
        }
    }

    private long getTimeAt() {
        return SystemClock.uptimeMillis() + DELAY * 2;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_post:
                doAction(POST);
                break;
            case R.id.btn_post_delay:
                doAction(POST_DELAY);
                break;
            case R.id.btn_post_at_time:
                doAction(POST_AT_TIME);
                break;
        }
    }

    private static class ActionRunnable implements Runnable {

        @StringRes
        private int messageId;
        private WeakReference<TextView> viewWeakRef;

        ActionRunnable(@Nullable TextView view,
                              @StringRes int messageId) {
            this.messageId = messageId;
            this.viewWeakRef = new WeakReference<>(view);
        }

        @Override
        public void run() {
            if (viewWeakRef != null) {
                TextView view = viewWeakRef.get();
                if (view != null) {
                    view.setText(view.getContext().getString(messageId));
                }
            }
        }
    }
}
