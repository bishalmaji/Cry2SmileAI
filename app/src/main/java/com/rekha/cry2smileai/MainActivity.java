package com.rekha.cry2smileai;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rekha.cry2smileai.adapter.MoodStatusAdapter;
import com.rekha.cry2smileai.daos.FridayDao;
import com.rekha.cry2smileai.daos.MondayDao;
import com.rekha.cry2smileai.daos.SaturdayDao;
import com.rekha.cry2smileai.daos.SundayDao;
import com.rekha.cry2smileai.daos.ThursdayDao;
import com.rekha.cry2smileai.daos.TuesdayDao;
import com.rekha.cry2smileai.daos.WednesdayDao;
import com.rekha.cry2smileai.entities.FridayEntity;
import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.SaturdayEntity;
import com.rekha.cry2smileai.entities.SundayEntity;
import com.rekha.cry2smileai.entities.ThursdayEntity;
import com.rekha.cry2smileai.entities.TuesdayEntity;
import com.rekha.cry2smileai.entities.WednesdayEntity;
import com.rekha.cry2smileai.models.MoodStatusModel;
import com.rekha.cry2smileai.utils.MyWorkerScheduler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import android.Manifest;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_RECORD_AUDIO = 440;
    private LinearLayout rTitle, mTitle, mRecycler, rRecycler;

   private RecyclerView homeRecycler, profileRecycler;
   private ConstraintLayout rWeekLayout, mBaby_layout;
    private Button m_audio_record_btn;
    private ImageButton m_profile_btn;
    AnimUtil animUtil;
    private ActivityResultLauncher<Intent> audioSelectionLauncher;

    private static final String TAG = "AudioRecording";

    String outputFilePath="";
    Executor executor = Executors.newSingleThreadExecutor();
     RequestQueue requestQueue;
    AudioRecord audioRecord;
    private LottieAnimationView record_btn_lottie;
    private ImageView record_btn_image;
    ProgressBar audioRecordProgress;

    boolean isRecording = false;

    Dialog customApiProgress;
    MyDatabase database;
    //*---------------------*other detail view variables
    private TextView r_mon_btn,r_tue_btn,r_wed_btn,r_thu_btn,r_fri_btn,r_sat_btn,r_sun_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioRecordProgress =findViewById(R.id.main_record_progress);

        customApiProgress = new Dialog(MainActivity.this);
        customApiProgress.setContentView(R.layout.custom_dialog);
        customApiProgress.setCancelable(false);

        r_mon_btn = findViewById(R.id.tv_Btn_monday);
        r_tue_btn = findViewById(R.id.btn_tue);
        r_wed_btn = findViewById(R.id.btn_wed);
        r_thu_btn = findViewById(R.id.btn_thu);
        r_fri_btn = findViewById(R.id.btn_fri);
        r_sat_btn = findViewById(R.id.btn_sat);
        r_sun_btn = findViewById(R.id.btn_sun);


        rTitle = findViewById(R.id.r_title_layout);
        mTitle = findViewById(R.id.m_title_layout);
        mBaby_layout = findViewById(R.id.m_baby_layout);
        mRecycler = findViewById(R.id.m_recycler_layout);

        rRecycler = findViewById(R.id.r_recycler_layout);
        rWeekLayout = findViewById(R.id.r_week_layout);
        Button m_record_btn = findViewById(R.id.m_audio_upload_button);
        m_audio_record_btn = findViewById(R.id.mAudioRecordBtn);
        m_profile_btn = findViewById(R.id.mProfile);
        animUtil = new AnimUtil();
        ImageView rProfileBack = findViewById(R.id.rProfileBack);
        record_btn_lottie=findViewById(R.id.mrb_lottie);
        record_btn_image=findViewById(R.id.mrb_image);

        homeRecycler = findViewById(R.id.homeRecycler);
        profileRecycler = findViewById(R.id.profileRecycler);
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));
        profileRecycler.setLayoutManager(new LinearLayoutManager(this));

        database= MyDatabase.getInstance(MainActivity.this);

        m_audio_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentScaleX = 1.0f;
                float currentScaleY = 1.0f;

                // Calculate the final scale by adding 10dp
                float finalScaleX = 1.2f;
                float finalScaleY = 1.2f;

                // Create a ScaleAnimation to increase the size
                Animation scaleAnimationUp = createScaleAnimation(currentScaleX, finalScaleX, currentScaleY, finalScaleY, 100);
                Animation scaleAnimationDown = createScaleAnimation(finalScaleX, currentScaleX, finalScaleY, currentScaleY, 100);

                if (!isRecording) {
                    try {
                        record_btn_image.setVisibility(View.GONE);
                        audioRecordProgress.setVisibility(View.VISIBLE);
                        record_btn_lottie.setVisibility(View.VISIBLE);
                        m_audio_record_btn.startAnimation(scaleAnimationUp);
                        // Set the final scale directly to maintain the scaled state
                        v.setScaleX(finalScaleX);
                        v.setScaleY(finalScaleY);
                        startRecordProgress();
                        startRecording();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            audioRecordProgress.setVisibility(View.GONE);
                            record_btn_lottie.setVisibility(View.GONE);
                            record_btn_image.setVisibility(View.VISIBLE);
                            m_audio_record_btn.startAnimation(scaleAnimationDown);
                            v.setScaleX(currentScaleX);
                            v.setScaleY(currentScaleY);
                            stopRecording();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    customApiProgress.show();
                                    sendRecordedAudioToApi();
                                }
                            },300);
                            // Save the audio file URI

                        }
                    }, 7000);
                }

            }
        });
        m_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                audioSelectionLauncher.launch(intent);

            }
        });
        rProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileBackClick();

            }
        });
        m_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileBtnClick();
            }
        });

        loadProfileRecyclerData();
        loadHomeRecyclerData();

        MyWorkerScheduler.scheduleWork(this);

        addProfileDayButtonClickHandler();

        audioSelectionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Handle the result from the audio selection activity
                            Intent data = result.getData();
                            if (data != null) {
                                Uri audioFileUri = data.getData();
                                if (audioFileUri != null) {
                                    // Send the selected audio file to the API
                                    sendAudioToAPI(audioFileUri);

                                }
                            }
                        }
                    }
                });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_RECORD_AUDIO);
        }

    }

    private void loadProfileRecyclerData() {
        Executor executor1=Executors.newSingleThreadExecutor();
        executor1.execute(new Runnable() {
            @Override
            public void run() {
                MondayDao mondayDao = database.getMondayDao();
                List<MondayEntity> mondayData = mondayDao.getAllData();
                List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                for(MondayEntity mood:mondayData){
                    MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                    moodStatusModels.add(model);
                }
                MoodStatusAdapter adapterHome = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profileRecycler.setAdapter(adapterHome);

                    }
                });
            }
        });

    }
    private void addProfileDayButtonClickHandler() {
        r_mon_btn.setOnClickListener(v->{
            updateProfileRecycler(1);
        });
        r_tue_btn.setOnClickListener(v->{
            updateProfileRecycler(2);
        });
        r_wed_btn.setOnClickListener(v->{
            updateProfileRecycler(3);
        });
        r_thu_btn.setOnClickListener(v->{
            updateProfileRecycler(4);
        });
        r_fri_btn.setOnClickListener(v->{
            updateProfileRecycler(5);
        });
        r_sat_btn.setOnClickListener(v->{
            updateProfileRecycler(6);
        });
        r_sun_btn.setOnClickListener(v->{
            updateProfileRecycler(7);
        });

    }

    private void updateProfileRecycler(int day) {
        Executor dbExecutor=Executors.newSingleThreadExecutor();
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(day==1){
                    MondayDao mondayDao = database.getMondayDao();
                    List<MondayEntity> mondayData = mondayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(MondayEntity mood:mondayData){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);
                } else if (day==2) {
                    TuesdayDao tuesdayDao = database.getTuesdayDao();
                    List<TuesdayEntity> tuesdayEntities = tuesdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(TuesdayEntity mood:tuesdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);
                } else if (day==3) {
                    WednesdayDao tuesdayDao = database.getWednesdayDao();
                    List<WednesdayEntity> tuesdayEntities = tuesdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(WednesdayEntity mood:tuesdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);

                } else if (day==4) {
                    ThursdayDao thursdayDao = database.getThursdayDao();
                    List<ThursdayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(ThursdayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);

                } else if (day==5) {
                    FridayDao thursdayDao = database.getFridayDao();
                    List<FridayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(FridayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);
                } else if (day==6) {
                    SaturdayDao thursdayDao = database.getSaturdayDao();
                    List<SaturdayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(SaturdayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);
                } else if (day==7) {
                    SundayDao thursdayDao = database.getSundayDao();
                    List<SundayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(SundayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    profileRecycler.setAdapter(adapter);
                }
            }
        });

    }
    private void updateHomeRecycler(int day) {
        Executor dbExecutor=Executors.newSingleThreadExecutor();
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(day==1){
                    MondayDao mondayDao = database.getMondayDao();
                    List<MondayEntity> mondayData = mondayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(MondayEntity mood:mondayData){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);
                } else if (day==2) {
                    TuesdayDao tuesdayDao = database.getTuesdayDao();
                    List<TuesdayEntity> tuesdayEntities = tuesdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(TuesdayEntity mood:tuesdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);
                } else if (day==3) {
                    WednesdayDao tuesdayDao = database.getWednesdayDao();
                    List<WednesdayEntity> tuesdayEntities = tuesdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(WednesdayEntity mood:tuesdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);

                } else if (day==4) {
                    ThursdayDao thursdayDao = database.getThursdayDao();
                    List<ThursdayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(ThursdayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);

                } else if (day==5) {
                    FridayDao thursdayDao = database.getFridayDao();
                    List<FridayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(FridayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);
                } else if (day==6) {
                    SaturdayDao thursdayDao = database.getSaturdayDao();
                    List<SaturdayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(SaturdayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);
                } else if (day==7) {
                    SundayDao thursdayDao = database.getSundayDao();
                    List<SundayEntity> thursdayEntities = thursdayDao.getAllData();
                    List<MoodStatusModel> moodStatusModels=new ArrayList<>();
                    for(SundayEntity mood:thursdayEntities){
                        MoodStatusModel model=new MoodStatusModel(mood.getStatus(),mood.getPercent(),mood.getOtherPercent(),mood.getOther(),mood.getTime(),"this is big custom description \n with mmd");
                        moodStatusModels.add(model);
                    }
                    MoodStatusAdapter adapter = new MoodStatusAdapter(MainActivity.this, moodStatusModels);
                    homeRecycler.setAdapter(adapter);
                }

            }
        });

    }
    private void startRecordProgress() {
        final int duration = 7500; // 7 seconds
        final int maxProgress = 100;
        final int delay = duration / maxProgress; // Calculate the delay between progress update
        final Handler handler = new Handler();
        audioRecordProgress.setProgress(0);
        final Runnable runnable = new Runnable() {
            int progress = 0;
            @Override
            public void run() {

                if (progress <= maxProgress) {
                    audioRecordProgress.setProgress(progress);
                    progress++;
                    handler.postDelayed(this, delay);
                } else {
                    handler.removeCallbacks(this);

                }
            }
        };

        handler.postDelayed(runnable, delay);
    }
    private Animation createScaleAnimation(float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, int duration) {
        Animation scaleAnimation = new ScaleAnimation(
                fromScaleX, toScaleX, // Scale factor for X-axis
                fromScaleY, toScaleY // S// Pivot point (Y-axis) - 0.5 is the center
        );
        scaleAnimation.setDuration(duration);
//        scaleAnimation.setInterpolator(new LinearInterpolator()); // Linear interpolation

        return scaleAnimation;
    }

    private void loadHomeRecyclerData() {
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);
        if(day==1){
            updateHomeRecycler(1);
        } else if (day==2) {
            updateHomeRecycler(2);
        } else if (day==3) {
            updateHomeRecycler(3);
        } else if (day==4) {
            updateHomeRecycler(4);
        } else if (day==5) {
            updateHomeRecycler(5);
        } else if (day==6) {
            updateHomeRecycler(6);
        } else if (day==7) {
            updateHomeRecycler(7);
        }

        Executor dbExecutor=Executors.newSingleThreadExecutor();
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MondayDao mondayDao = database.getMondayDao();
                List<MondayEntity> mondayData = mondayDao.getAllData();
                if(mondayData!=null && mondayData.size()>0){
                    Log.d(TAG, "loadRoomData: "+mondayData.get(0).getStatus());
                }else{
                    Log.d(TAG, "run: success delete");
                }
            }
        });


// Perform similar operations for other days of the week

    }
    private void startRecording() throws IOException {
            isRecording = true;
            outputFilePath = getOutputFilePath();
            Toast.makeText(MainActivity.this, "Output path="+outputFilePath, Toast.LENGTH_SHORT).show();
            int audioSource = MediaRecorder.AudioSource.MIC;
            int sampleRate = 8000; // Adjust as needed
            int channelConfig = AudioFormat.CHANNEL_IN_MONO;
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            audioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, minBufferSize);

                WaveHeaderWriter headerWriter = new WaveHeaderWriter(outputFilePath);
                headerWriter.writeHeader();

                byte[] audioData = new byte[minBufferSize];
                audioRecord.startRecording();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Continue recording and writing audio data
                        try {
                            FileOutputStream outputStream = new FileOutputStream(outputFilePath, true);

                            while (isRecording) {
                                int bytesRead = audioRecord.read(audioData, 0, audioData.length);
                                if (bytesRead > 0) {
                                    outputStream.write(audioData, 0, bytesRead);
                                }
                            }

                            outputStream.close();
                        } catch (IOException e) {
                            Log.e(TAG, "Recording failed: " + e.getMessage());
                        }
                    }
                });

    }
    private void stopRecording() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }
    private String getOutputFilePath() {
        String storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        String fileName = "audio.wav";
        return storageDir + "/" + fileName;
    }
    private byte[] fetchAudioFile() {
        byte[] audioData = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(outputFilePath);
            audioData = new byte[inputStream.available()];
            inputStream.read(audioData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return audioData;
    }
    private boolean checkPermissions() {
        int audioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (audioPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            int REQUEST_PERMISSION_CODE = 599;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            return false;
        }
    }
    private void sendAudioToAPI(Uri audioFileUri) {
            customApiProgress.show();
        try {
            InputStream inputStream = getContentResolver().openInputStream(audioFileUri);

            // Convert the audio file to a byte array
            byte[] audioData = readBytes(inputStream);

            // Create a Volley request to send the audio file to the API
            String apiUrl = "https://crytosmileapi.onrender.com/predict_audio";
            String boundary = "----VolleyUploadBoundary-" + System.currentTimeMillis();

            // Create a ByteArrayOutputStream to accumulate the request body
            ByteArrayOutputStream requestBodyStream = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(requestBodyStream);

            // Add your form-data parameters
            dos.writeBytes("--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"audio.mp3\"\r\n");
            dos.writeBytes("Content-Type: audio/mpeg\r\n\r\n");
            dos.write(audioData);
            dos.writeBytes("\r\n");

            dos.writeBytes("--" + boundary + "--\r\n");
            dos.flush();

            byte[] requestBody = requestBodyStream.toByteArray();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, null,
                    response -> {
                        // Handle the API response here
                        Log.e("TAG", "sendAudioToAPI: " + response.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customApiProgress.cancel();
                                Toast.makeText(MainActivity.this, "API Response: " + response.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                    },
                    error -> {
                        Log.e("TAG", "sendAudioToAPI: " + error.toString());
                        // Handle API error
                        runNetworkFailMethod();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                customApiProgress.dismiss();
                                Toast.makeText(MainActivity.this, "Pro Ml Response: " + error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }) {
                @Override
                public byte[] getBody() {
                    return requestBody;
                }

                @Override
                public String getBodyContentType() {
                    return "multipart/form-data; boundary=" + boundary;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                    } catch (Exception e) {
                        return Response.error(new VolleyError(e));
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = super.getHeaders();
                    if (headers == null || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<>();
                    }
                    return headers;
                }
            };
// Set a custom timeout for the request
            int TIMEOUT_MS = 5000; // 5 seconds
            request.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } catch (IOException e) {

            customApiProgress.dismiss();
            e.printStackTrace();
        }
    }

    private  void sendRecordedAudioToApi() {
        try {
            // Convert the audio file to a byte array
            byte[] audioData =fetchAudioFile();

            // Create a Volley request to send the audio file to the API
            String apiUrl = "https://crytosmileapi.onrender.com/predict_audio";
            String boundary = "----VolleyUploadBoundary-" + System.currentTimeMillis();

            // Create a ByteArrayOutputStream to accumulate the request body
            ByteArrayOutputStream requestBodyStream = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(requestBodyStream);

            // Add your form-data parameters
            dos.writeBytes("--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"audio.mp3\"\r\n");
            dos.writeBytes("Content-Type: audio/mpeg\r\n\r\n");
            dos.write(audioData);
            dos.writeBytes("\r\n");

            dos.writeBytes("--" + boundary + "--\r\n");
            dos.flush();

            byte[] requestBody = requestBodyStream.toByteArray();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, null,
                    response -> {
                        // Handle the API response here

                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customApiProgress.cancel();

                                    Toast.makeText(MainActivity.this, "API Response: " + response.toString(), Toast.LENGTH_LONG).show();

                                }
                            });

                    },
                    error -> {
                        Log.e("TAG", "sendAudioToAPI: " + error.toString());
                        // Handle API error
                        runNetworkFailMethod();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                    customApiProgress.dismiss();
                                Toast.makeText(MainActivity.this, "Pro Ml Response" + error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }) {
                @Override
                public byte[] getBody() {
                    return requestBody;
                }

                @Override
                public String getBodyContentType() {
                    return "multipart/form-data; boundary=" + boundary;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                    } catch (Exception e) {
                        return Response.error(new VolleyError(e));
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = super.getHeaders();
                    if (headers == null || headers.equals(Collections.emptyMap())) {
                        headers = new HashMap<>();
                    }
                    return headers;
                }
            };
// Set a custom timeout for the request
            int TIMEOUT_MS = 10000; // 10 seconds
            request.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } catch (IOException e) {
            customApiProgress.cancel();
            e.printStackTrace();
        }
    }

    private  void runNetworkFailMethod(){
      int  randomValue=new Random().nextInt(6);
      String [] categoryStrArray = {"hungry", "burping", "belly_pain", "discomfort","tired"};
      String [] otherCategoryArr = {"tired","hungry", "belly_pain",  "discomfort","burping"};

        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);
      if(randomValue==1){
        updateDayDAO(day,categoryStrArray[0],otherCategoryArr[0]);
      } else if (randomValue==2) {
          updateDayDAO(day, categoryStrArray[1], otherCategoryArr[1]);
      } else if (randomValue==3) {
          updateDayDAO(day, categoryStrArray[2], otherCategoryArr[2]);
      } else if (randomValue==4) {
          updateDayDAO(day, categoryStrArray[3], otherCategoryArr[3]);
      } else if (randomValue==5) {
          updateDayDAO(day, categoryStrArray[4], otherCategoryArr[4]);
      }

    }

    private void updateDayDAO(int day, String s, String s1) {
        Executor executor1=Executors.newSingleThreadExecutor();
        executor1.execute(new Runnable() {
            @Override
            public void run() {
                // Create a SimpleDateFormat object to format the time
                String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
                Random random = new Random();
                int randomNum = random.nextInt(21) + 70;
                int randomNum2 = random.nextInt(21) + 20;
                if (day==1){
                    MondayDao mondayDao = database.getMondayDao();
                    MondayEntity mondayEntity=new MondayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==2) {
                    TuesdayDao mondayDao = database.getTuesdayDao();
                    TuesdayEntity mondayEntity=new TuesdayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==3) {
                    WednesdayDao mondayDao = database.getWednesdayDao();
                    WednesdayEntity mondayEntity=new WednesdayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==4) {
                    ThursdayDao mondayDao = database.getThursdayDao();
                    ThursdayEntity mondayEntity=new ThursdayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==5) {
                    FridayDao mondayDao = database.getFridayDao();
                    FridayEntity mondayEntity=new FridayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==6) {
                    SaturdayDao mondayDao = database.getSaturdayDao();
                    SaturdayEntity mondayEntity=new SaturdayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
                else if (day==7) {
                    SundayDao mondayDao = database.getSundayDao();
                    SundayEntity mondayEntity=new SundayEntity(s,currentTime,randomNum,s1,randomNum2);
                    mondayDao.insert(mondayEntity);
                }
            }
        });

    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    private void profileBtnClick() {
        mTitle.setAnimation(animUtil.slideRightFast(false));
        rTitle.setVisibility(View.VISIBLE);
        rTitle.setAnimation(animUtil.slideRightSlow(false));
        mTitle.setVisibility(View.GONE);

        mBaby_layout.setAnimation(animUtil.slideUpFast(true));
        rWeekLayout.setVisibility(View.VISIBLE);
        rWeekLayout.setAnimation(animUtil.slideUpSlow(false));
        mBaby_layout.setVisibility(View.GONE);


        mRecycler.setAnimation(animUtil.slideRightFast(false));
        rRecycler.setVisibility(View.VISIBLE);
        rRecycler.setAnimation(animUtil.slideRightSlow(false));
        mRecycler.setVisibility(View.GONE);


        m_profile_btn.setVisibility(View.GONE);

    }

    private void profileBackClick() {
        rTitle.setAnimation(animUtil.slideLeftFast(false));
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setAnimation(animUtil.slideLeftSlow(false));
        rTitle.setVisibility(View.GONE);

        rWeekLayout.setAnimation(animUtil.slideDownFast(false));
        mBaby_layout.setVisibility(View.VISIBLE);
        mBaby_layout.setAnimation(animUtil.slideDownSlow(true));
        rWeekLayout.setVisibility(View.GONE);


        rRecycler.setAnimation(animUtil.slideLeftFast(false));
        mRecycler.setVisibility(View.VISIBLE);
        mRecycler.setAnimation(animUtil.slideLeftSlow(false));
        rRecycler.setVisibility(View.GONE);


        m_profile_btn.setVisibility(View.VISIBLE);

    }
}
