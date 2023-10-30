package com.rekha.cry2smileai.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rekha.cry2smileai.R;
import com.rekha.cry2smileai.ResultActivity;
import com.rekha.cry2smileai.models.MoodStatusModel;

import java.util.List;
import java.util.Random;

public class MoodStatusAdapter extends RecyclerView.Adapter<MoodStatusAdapter.MoodStatusViewHolder> {
    private Context context;
    private List<MoodStatusModel> modelList;

    public MoodStatusAdapter(Context context, List<MoodStatusModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public MoodStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mood_status_item,parent,false);
        return new MoodStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodStatusViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String mood=modelList.get(position).getMood();
        Random rand=new Random();
        int randomNum = rand.nextInt(2) + 1;
       if (mood.equals("hungry")) {
          if (randomNum==1){
              holder.mood_image.setImageResource(R.drawable.b_h1);
          }else {
              holder.mood_image.setImageResource(R.drawable.b_h2);
          }

          holder.title.setText("Humgry");
          holder.time.setText(modelList.get(position).getTime());
        }
        else if (mood.equals("burping")) {
           if (randomNum==1){
               holder.mood_image.setImageResource(R.drawable.b_b1);
           }else {
               holder.mood_image.setImageResource(R.drawable.b_b2);
           }

           holder.title.setText("Burping");
           holder.time.setText(modelList.get(position).getTime());
        }
        else if (mood.equals("discomfort")) {
           if (randomNum==1){
               holder.mood_image.setImageResource(R.drawable.b_d1);
           }else {
               holder.mood_image.setImageResource(R.drawable.b_d2);
           }

           holder.title.setText("Discomfort");
           holder.time.setText(modelList.get(position).getTime());
        }
        else if (mood.equals("headPain")) {
           if (randomNum==1){
               holder.mood_image.setImageResource(R.drawable.b_hd1);
           }else {
               holder.mood_image.setImageResource(R.drawable.b_hd2);
           }

           holder.title.setText("Head Pain");
           holder.time.setText(modelList.get(position).getTime());
        }
        else if (mood.equals("bellyPain")) {
           if (randomNum==1){
               holder.mood_image.setImageResource(R.drawable.b_bly1);
           }else {
               holder.mood_image.setImageResource(R.drawable.b_bly2);
           }

           holder.title.setText("Belly Pain");
           holder.time.setText(modelList.get(position).getTime());
        }
        else if (mood.equals("tired")) {
           if (randomNum==1){
               holder.mood_image.setImageResource(R.drawable.b_r1);
           }else {
               holder.mood_image.setImageResource(R.drawable.b_r2);
           }

           holder.title.setText("Tired");
           holder.time.setText(modelList.get(position).getTime());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ResultActivity.class);
                intent.putExtra("rTitle",modelList.get(position).getMood());
                intent.putExtra("rDescription",modelList.get(position).getDescription());
                intent.putExtra("rProgress",modelList.get(position).getPercent());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MoodStatusViewHolder extends RecyclerView.ViewHolder{
        TextView title,time;
        ImageView mood_image;
        public MoodStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.mi_time);
            title=itemView.findViewById(R.id.mi_title);
            mood_image=itemView.findViewById(R.id.mi_image);

        }
    }
}
