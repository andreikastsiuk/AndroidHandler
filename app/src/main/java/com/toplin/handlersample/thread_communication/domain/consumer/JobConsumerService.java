package com.toplin.handlersample.thread_communication.domain.consumer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class JobConsumerService extends Service {

    private JobConsumerThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        prepareConsumer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prepareConsumer();
        startConsumer();
        return START_NOT_STICKY;
    }

    private void prepareConsumer() {
        if (thread == null) {
            setUpConsumer();
        }
    }

    private void setUpConsumer() {
        thread = new JobConsumerThread(JobConsumerThread.TAG);
    }

    private void startConsumer() {
        if (thread != null) {
            thread.start();
        }
    }

    @Override
    public void onDestroy() {
        quitConsumer();
        super.onDestroy();
    }

    private void quitConsumer() {
        if (thread != null) {
            thread.quitSafely();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
