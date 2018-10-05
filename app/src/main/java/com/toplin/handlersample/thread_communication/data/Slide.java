package com.toplin.handlersample.thread_communication.data;

import android.support.annotation.DrawableRes;

import com.toplin.handlersample.R;

public final class Slide {

    private int slideNumber;

    public Slide(int slideNumber) {
        this.slideNumber = slideNumber;
    }

    @DrawableRes
    public int getPhotoId() {
        @DrawableRes int slideRes;
        switch (slideNumber) {
            case 0:
                slideRes = R.drawable.ic_slide_first;
                break;
            case 1:
                slideRes = R.drawable.ic_slide_second;
                break;
            case 2:
                slideRes = R.drawable.ic_slide_third;
                break;
            default:
                slideRes = R.drawable.ic_slide_first;
        }

        return slideRes;
    }
}
