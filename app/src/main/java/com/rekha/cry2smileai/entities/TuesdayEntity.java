package com.rekha.cry2smileai.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tuesday")
public class TuesdayEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String status;
    private String time;

    private int percent;
    private String other;
    private int otherPercent;


    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(int otherPercent) {
        this.otherPercent = otherPercent;
    }

    public TuesdayEntity( String status, String time, int percent, String other, int otherPercent) {
        this.status = status;
        this.time = time;
        this.percent = percent;
        this.other = other;
        this.otherPercent = otherPercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
