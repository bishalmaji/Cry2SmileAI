package com.rekha.cry2smileai;

import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimUtil {

    public TranslateAnimation slideLeftFast() {
        TranslateAnimation animation = new TranslateAnimation(0, -1000, 0, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideLeftSlow() {
        TranslateAnimation animation = new TranslateAnimation(1000, 0, 0, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(800);
        return animation;
    }

    public TranslateAnimation slideRightFast() {
        TranslateAnimation animation = new TranslateAnimation(0, 1000, 0, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideRightSlow() {
        TranslateAnimation animation = new TranslateAnimation(-1000, 0, 0, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(800);
        return animation;
    }

    public TranslateAnimation slideUpFast() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 1000);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideUpSlow() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -1000, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(800);
        return animation;
    }

    public TranslateAnimation slideDownFast() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideDownSlow() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -1000, 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(800);
        return animation;
    }
}
