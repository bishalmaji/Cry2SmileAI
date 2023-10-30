package com.rekha.cry2smileai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ResultActivity extends AppCompatActivity {
    TextView title, description;
    LottieAnimationView progress;
    ImageView resultImage,resultBack;
    String tStr,dStr;
    float pInt=0F;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        title=findViewById(R.id.result_title);
        description=findViewById(R.id.result_description);
        progress=findViewById(R.id.result_progress_anim);
        resultBack=findViewById(R.id.result_back);
        resultImage=findViewById(R.id.result_image);

        tStr=getIntent().getStringExtra("rTitle");
        dStr=getIntent().getStringExtra("rDescription");
        pInt=(float) getIntent().getIntExtra("rProgress",60);
        title.setText(tStr);
        description.setText(dStr);
        progress.setMaxProgress(pInt);
        if(tStr.equals("hungry")){
            resultImage.setImageResource(R.drawable.b_h2);
        }

        resultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}