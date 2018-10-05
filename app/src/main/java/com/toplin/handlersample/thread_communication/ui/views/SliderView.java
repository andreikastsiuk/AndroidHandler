package com.toplin.handlersample.thread_communication.ui.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.toplin.handlersample.thread_communication.data.Slide;
import com.toplin.handlersample.thread_communication.domain.HandlerHolder;

public class SliderView extends AppCompatImageView
        implements Handler.Callback {

    private Handler sliderHandler;

    public SliderView(Context context) {
        super(context);
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        sliderHandler = new Handler(this);
        HandlerHolder.getInstance().addHandler(HandlerHolder.HandlerTag.SLIDER, sliderHandler);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Slide slide = (Slide) msg.obj;
        setBackgroundResource(slide.getPhotoId());
        return false;
    }
}
