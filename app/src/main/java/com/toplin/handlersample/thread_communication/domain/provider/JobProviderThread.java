package com.toplin.handlersample.thread_communication.domain.provider;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class JobProviderThread extends Thread {

    private static final long ONE_SECOND_DELAY = 1000L;

    private static volatile Handler workerHandler;
    private Dispatcher jobDispatcher;

    JobProviderThread(@NonNull Dispatcher jobDispatcher) {
        this.jobDispatcher = jobDispatcher;
    }

    public void run() {
        tryPrepareWorkerLooper();
        setUpWorkerHandler();
        provideJobsBySchedule();
        tryLoopWorkerLooper();
    }

    private void tryPrepareWorkerLooper() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    private void setUpWorkerHandler() {
        workerHandler = new Handler();
    }

    private void provideJobsBySchedule() {
        if (workerHandler != null) {
            workerHandler.post(timerTickRunnable);
        }
    }

    private void tryLoopWorkerLooper() {
        if (Looper.myLooper() != null) {
            Looper.loop();
        }
    }

    private Runnable timerTickRunnable = new Runnable() {
        @Override
        public void run() {
            jobDispatcher.dispatch();
            workerHandler.postDelayed(timerTickRunnable, ONE_SECOND_DELAY);
        }
    };

    void quit() {
        if (workerHandler != null) {
            workerHandler.removeCallbacks(timerTickRunnable);
            workerHandler.getLooper().quitSafely();
        }
    }
}
