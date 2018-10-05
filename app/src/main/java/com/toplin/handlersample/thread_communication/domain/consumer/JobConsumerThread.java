package com.toplin.handlersample.thread_communication.domain.consumer;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.toplin.handlersample.thread_communication.domain.HandlerHolder;
import com.toplin.handlersample.thread_communication.domain.Job;

class JobConsumerThread extends HandlerThread {

    static final String TAG = JobConsumerThread.class.getSimpleName();

    private static volatile Handler resolverHandler;

    JobConsumerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        resolverHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj instanceof Job) {
                    consumeJob((Job) msg.obj);
                }
            }
        };
        HandlerHolder.getInstance().addHandler(HandlerHolder.HandlerTag.CONSUMER, resolverHandler);
    }

    private void consumeJob(Job job) {
        switch (job) {
            case ONE_SHOT:
                WorkManager.getInstance().executeOneShotJob();
                break;
            case LONG_SHOT:
                WorkManager.getInstance().executeLongRunningJob();
                break;
        }
    }

    @Override
    public boolean quit() {
        if (resolverHandler != null) {
            resolverHandler.removeCallbacksAndMessages(null);
        }
        return super.quit();
    }
}
