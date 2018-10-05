package com.toplin.handlersample.thread_communication.domain.provider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class JobProviderService extends Service {

    private JobProviderThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        prepareProvider();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prepareProvider();
        startProvider();
        return START_NOT_STICKY;
    }

    private void prepareProvider() {
        if (thread == null) {
            setUpProvider();
        }
    }

    private void setUpProvider() {
        thread = new JobProviderThread(new JobDispatcher());
    }

    private void startProvider() {
        if (thread != null && !thread.isAlive()) {
            thread.start();
        }
    }

    @Override
    public void onDestroy() {
        finishProvider();
        super.onDestroy();
    }

    private void finishProvider() {
        if (thread != null) {
            thread.quit();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}