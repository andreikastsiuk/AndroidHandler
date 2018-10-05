package com.toplin.handlersample.thread_communication.domain;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.WeakHashMap;

public class HandlerHolder {

    public enum HandlerTag {
        CONSUMER, MAIN, SLIDER
    }

    private WeakHashMap<HandlerTag, Handler> handlers = new WeakHashMap<>();

    private static volatile HandlerHolder instance;

    public static HandlerHolder getInstance() {
        if (instance == null) {
            synchronized (HandlerHolder.class) {
                if (instance == null) {
                    instance = new HandlerHolder();
                }
            }
        }
        return instance;
    }

    public void addHandler(HandlerTag handlerTag,
                           @NonNull Handler handler) {
        handlers.put(handlerTag, handler);
    }

    @Nullable
    public Handler getHandler(HandlerTag handlerTag) {
        return handlers.get(handlerTag);
    }
}
