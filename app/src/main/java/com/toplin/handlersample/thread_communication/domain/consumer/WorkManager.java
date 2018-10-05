package com.toplin.handlersample.thread_communication.domain.consumer;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;

import com.toplin.handlersample.thread_communication.data.Slide;
import com.toplin.handlersample.thread_communication.data.Time;
import com.toplin.handlersample.thread_communication.domain.HandlerHolder;
import com.toplin.handlersample.thread_communication.domain.Job;

import java.util.Random;

final class WorkManager {

    private static final long LONG_JOB_DELAY = 3000L;

    private static volatile WorkManager instance;

    static WorkManager getInstance() {
        if (instance == null) {
            synchronized (WorkManager.class) {
                if (instance == null) {
                    instance = new WorkManager();
                }
            }
        }
        return instance;
    }

    @android.support.annotation.WorkerThread
    void executeOneShotJob() {
        Handler handler = HandlerHolder.getInstance().getHandler(HandlerHolder.HandlerTag.MAIN);
        if (handler != null) {
            Message message = handler.obtainMessage(Job.ONE_SHOT.ordinal());
            message.obj = new Time(System.currentTimeMillis());
            handler.sendMessage(message);
        }
    }

    @android.support.annotation.WorkerThread
    void executeLongRunningJob() {
        final WorkerThread workerThread = new WorkerThread();
        workerThread.start();
        workerThread.executeJob(new Runnable() {
            @Override
            public void run() {
                Handler handler = HandlerHolder.getInstance().getHandler(HandlerHolder.HandlerTag.SLIDER);
                if (handler != null) {
                    Random random = new Random();
                    Message message = handler.obtainMessage(Job.LONG_SHOT.ordinal());
                    message.obj = new Slide(random.nextInt(3));
                    try {
                        // TODO: imitation of a long job
                        Thread.sleep(LONG_JOB_DELAY);
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                workerThread.quitSafely();
            }
        });
    }

    private static final class WorkerThread extends HandlerThread {

        private static volatile Handler workerHandler;

        WorkerThread() {
            super(WorkerThread.class.getName());
        }

        @Override
        protected void onLooperPrepared() {
            workerHandler = new Handler(getLooper());
        }

        @android.support.annotation.WorkerThread
        void executeJob(@NonNull Runnable job) {
            if (workerHandler != null && workerHandler.getLooper().getThread().isAlive()) {
                workerHandler.post(job);
            }
        }

        @Override
        public boolean quit() {
            if (workerHandler != null) {
                workerHandler.removeCallbacksAndMessages(null);
            }
            return super.quit();
        }

        @Override
        public boolean quitSafely() {
            if (workerHandler != null) {
                workerHandler.removeCallbacksAndMessages(null);
            }
            return super.quitSafely();
        }
    }
}
