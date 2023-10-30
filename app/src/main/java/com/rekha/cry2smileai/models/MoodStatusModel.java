package com.rekha.cry2smileai.models;

public class MoodStatusModel {
    private String mood;
    private int percent;
    private int otherPercent;
    private String other;
    private String time;
    private String  description;

    public MoodStatusModel(String mood, int percent, int otherPercent, String other, String time, String description) {
        this.mood = mood;
        this.percent = percent;
        this.otherPercent = otherPercent;
        this.other = other;
        this.time = time;
        this.description = description;
    }

    public int getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(int otherPercent) {
        this.otherPercent = otherPercent;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
