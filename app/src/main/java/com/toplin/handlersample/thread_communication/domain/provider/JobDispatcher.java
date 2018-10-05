package com.toplin.handlersample.thread_communication.domain.provider;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.toplin.handlersample.thread_communication.domain.HandlerHolder;
import com.toplin.handlersample.thread_communication.domain.Job;

public class JobDispatcher implements Dispatcher {

    private static final long LONG_RUNNING_JOB_CACHE_AGE = 5000L;

    private volatile long lastLongJobPosted = System.currentTimeMillis();

    @Override
    public void dispatch() {
        postOneShotJob();
        if (shouldPostLongRunningJob()) {
            updateLongRunningJobLastPost();
            postLongRunningJob();
        }
    }

    private boolean shouldPostLongRunningJob() {
        return System.currentTimeMillis() - lastLongJobPosted >= LONG_RUNNING_JOB_CACHE_AGE;
    }

    private void updateLongRunningJobLastPost() {
        lastLongJobPosted = System.currentTimeMillis();
    }

    private void postOneShotJob() {
        postJob(Job.ONE_SHOT);
    }

    private void postLongRunningJob() {
        postJob(Job.LONG_SHOT);
    }

    private void postJob(Job job) {
        Handler destinationHandler = getDestinationHandler();
        if (destinationHandler != null) {
            Message message = destinationHandler.obtainMessage();
            message.obj = job;
            destinationHandler.sendMessage(message);
        }
    }

    @Nullable
    private Handler getDestinationHandler() {
        return HandlerHolder.getInstance().getHandler(HandlerHolder.HandlerTag.CONSUMER);
    }
}
