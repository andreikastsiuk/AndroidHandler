package com.toplin.handlersample.thread_communication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.toplin.handlersample.R;
import com.toplin.handlersample.thread_communication.data.Time;
import com.toplin.handlersample.thread_communication.domain.HandlerHolder;
import com.toplin.handlersample.thread_communication.domain.Job;

public class ThreadCommunicationActivity extends AppCompatActivity
        implements Handler.Callback {

    private TextView timeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_communication);

        setUpViews();
        setUpHandlerHolder();
    }

    private void setUpViews() {
        timeView = findViewById(R.id.view_time);
    }

    private void setUpHandlerHolder() {
        HandlerHolder.getInstance()
                .addHandler(HandlerHolder.HandlerTag.MAIN, new Handler(this));
    }

    @Override
    public boolean handleMessage(Message msg) {
        Job job = Job.getJobByPosition(msg.what);
        if (job != null) {
            switch (job) {
                case ONE_SHOT:
                    Time time = (Time) msg.obj;
                    timeView.setText(time.convert());
                    break;
            }
        }
        return false;
    }
}
