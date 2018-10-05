package com.toplin.handlersample;

import android.app.Application;
import android.content.Intent;

import com.toplin.handlersample.thread_communication.domain.provider.JobProviderService;
import com.toplin.handlersample.thread_communication.domain.consumer.JobConsumerService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setUpEnvironment();
    }

    private void setUpEnvironment() {
        startService(new Intent(this, JobConsumerService.class));
        startService(new Intent(this, JobProviderService.class));
    }
}
