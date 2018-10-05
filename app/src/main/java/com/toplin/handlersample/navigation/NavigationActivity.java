package com.toplin.handlersample.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.toplin.handlersample.R;
import com.toplin.handlersample.simple_post_messages.SimplePostMessagesActivity;
import com.toplin.handlersample.thread_communication.ui.ThreadCommunicationActivity;

public class NavigationActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        setUpViews();
    }

    private void setUpViews() {
        View simpleMessagesBtn = findViewById(R.id.btn_simple_messages);
        simpleMessagesBtn.setOnClickListener(this);
        View threadCommunicationBtn = findViewById(R.id.btn_thread_communication);
        threadCommunicationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_simple_messages:
                moveTo(SimplePostMessagesActivity.class);
                break;
            case R.id.btn_thread_communication:
                moveTo(ThreadCommunicationActivity.class);
                break;
        }
    }

    private void moveTo(Class destination) {
        startActivity(new Intent(this, destination));
    }
}
