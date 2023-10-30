package com.rekha.cry2smileai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

public class IntroSliderAtivity extends AppCompatActivity {

    KenBurnsView i1,i2,i3;
    ImageView d1,d2,d3;
    private LinearLayout l1,l2,l3;
    int counter=1;
    AnimUtil animUtil;
    int dimensionInDp,dimensionInDpLow;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_intro_slider_ativity);
        ConstraintLayout introLayout=findViewById(R.id.intro_layout);
        i1=findViewById(R.id.is_i1);
        i2=findViewById(R.id.is_i2);
        i3=findViewById(R.id.is_i3);
        d1=findViewById(R.id.is_d1);
        d2=findViewById(R.id.is_d2);
        d3=findViewById(R.id.is_d3);
        l1=findViewById(R.id.is_l1);
        l2=findViewById(R.id.is_l2);
        l3=findViewById(R.id.is_l3);




        dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
         dimensionInDpLow = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        d1.getLayoutParams().height = dimensionInDp;
        d1.getLayoutParams().width = dimensionInDp;
        d1.requestLayout();

        RandomTransitionGenerator generator = new RandomTransitionGenerator();
        generator.setTransitionDuration(3500);
        i1.setTransitionGenerator(generator);
        i2.setTransitionGenerator(generator);
        i3.setTransitionGenerator(generator);



        Button intoNext=findViewById(R.id.intro_nxt_btn);
        intoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo();
            }
        });
        animUtil=new AnimUtil();
        introLayout.setOnTouchListener(new OnSwipeTouchListener(IntroSliderAtivity.this) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                changeInfoReverse();

            }
            public void onSwipeLeft() {
                changeInfo();
            }
            public void onSwipeBottom() {
            }

        });
    }

    private void changeInfo() {
        if(counter==1){
            i2.setVisibility(View.VISIBLE);
            i1.startAnimation(animUtil.slideLeftFast(false));
            i2.startAnimation(animUtil.slideLeftSlow(false));
            i1.setVisibility(View.GONE);

            d2.getLayoutParams().height = dimensionInDp;
            d2.getLayoutParams().width = dimensionInDp;
            d2.requestLayout();
            d1.getLayoutParams().height = dimensionInDpLow;
            d1.getLayoutParams().width = dimensionInDpLow;
            d1.requestLayout();

            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
            l2.startAnimation(animUtil.slideDownSlow(true));

            counter++;
        } else if (counter==2) {
            i3.setVisibility(View.VISIBLE);
            i2.startAnimation(animUtil.slideLeftFast(false));
            i3.startAnimation(animUtil.slideLeftSlow(false));
            i2.setVisibility(View.GONE);

            d3.getLayoutParams().height = dimensionInDp;
            d3.getLayoutParams().width = dimensionInDp;
            d3.requestLayout();

            d2.getLayoutParams().height = dimensionInDpLow;
            d2.getLayoutParams().width = dimensionInDpLow;
            d2.requestLayout();

            l3.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
            l3.startAnimation(animUtil.slideDownSlow(true));

            counter++;
        } else if (counter==3) {
            //final main activity
            l3.startAnimation(animUtil.slideDownSlow(true));
            l3.setVisibility(View.GONE);

            i3.startAnimation(animUtil.slideLeftFast(true));
            i3.setVisibility(View.GONE);
            d3.getLayoutParams().height = dimensionInDpLow;
            d3.getLayoutParams().width = dimensionInDpLow;
            d3.requestLayout();
            finish();
            startActivity(new Intent(IntroSliderAtivity.this,MainActivity.class));
        }
    }
    private void changeInfoReverse() {
        if(counter==3){

            i2.setVisibility(View.VISIBLE);
            i3.startAnimation(animUtil.slideRightFast(false));
            i2.startAnimation(animUtil.slideRightSlow(false));
            i3.setVisibility(View.GONE);
            d2.getLayoutParams().height = dimensionInDp;
            d2.getLayoutParams().width = dimensionInDp;
            d2.requestLayout();
            d3.getLayoutParams().height = dimensionInDpLow;
            d3.getLayoutParams().width = dimensionInDpLow;
            d3.requestLayout();

            l3.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
            l2.setAnimation(animUtil.slideDownSlow(true));

            counter--;
        } else if (counter==2) {
            i1.setVisibility(View.VISIBLE);
            i2.startAnimation(animUtil.slideRightFast(false));
            i1.startAnimation(animUtil.slideRightSlow(false));
            i2.setVisibility(View.GONE);

            d1.getLayoutParams().height = dimensionInDp;
            d1.getLayoutParams().width = dimensionInDp;
            d1.requestLayout();
            d2.getLayoutParams().height = dimensionInDpLow;
            d2.getLayoutParams().width = dimensionInDpLow;
            d2.requestLayout();

            l2.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
            l1.setAnimation(animUtil.slideDownSlow(true));

            counter--;
        }
    }
}