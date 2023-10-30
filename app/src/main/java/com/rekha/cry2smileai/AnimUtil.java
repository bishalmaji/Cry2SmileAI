package com.rekha.cry2smileai;

import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimUtil {

    public TranslateAnimation slideLeftFast(boolean intStatus) {
        TranslateAnimation animation = new TranslateAnimation(0, -1000, 0, 0);
        if (intStatus){
            animation.setInterpolator(new BounceInterpolator());
        }

        animation.setDuration(300);
        return animation;
    }
    public ScaleAnimation scaleUpnDown(float fx,float tx,float fy,float ty,boolean toBounce) {
        ScaleAnimation animation = new ScaleAnimation(fx, tx, fy, ty);
        if (toBounce){
            animation.setInterpolator(new BounceInterpolator());
        }

        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideLeftSlow(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(1000, 0, 0, 0);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(500);
        return animation;
    }

    public TranslateAnimation slideRightFast(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(0, 1000, 0, 0);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(300);
        return animation;
    }

    public TranslateAnimation slideRightSlow(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(-1000, 0, 0, 0);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(500);
        return animation;
    }



    public TranslateAnimation slideUpFast(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(300);
        return animation;
    }
    public TranslateAnimation slideUpSlow(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 1000, 0);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(500);
        return animation;
    }

    public TranslateAnimation slideDownFast(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 1000);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(300);
        return animation;
    }


    public TranslateAnimation slideDownSlow(boolean b) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -1000, 0);
        if (b){
            animation.setInterpolator(new BounceInterpolator());
        }
        animation.setDuration(800);
        return animation;
    }

}
