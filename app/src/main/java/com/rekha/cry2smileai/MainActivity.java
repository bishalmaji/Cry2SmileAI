package com.rekha.cry2smileai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // Find your TextViews by their IDs
//        TextView textView1 = findViewById(R.id.textView1);
//        TextView textView2 = findViewById(R.id.tv2);
//        TextView rv1 = findViewById(R.id.rtv1);
//        TextView rv2 = findViewById(R.id.rtv2);
//        Button makeamin=findViewById(R.id.makeanimation);
//
//        AnimUtil animUtil=new AnimUtil();
//        makeamin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                textView1.startAnimation(animUtil.slideLeftFast());
//                textView2.setVisibility(View.VISIBLE);
//                textView2.startAnimation(animUtil.slideLeftSlow());
//                textView1.setVisibility(View.GONE);
//
//                rv1.startAnimation(animUtil.slideDownFast());
//                rv2.setVisibility(View.VISIBLE);
//                rv2.startAnimation(animUtil.slideDownSlow());
//                rv1.setVisibility(View.GONE);
//
//            }
//        });
    }
}
