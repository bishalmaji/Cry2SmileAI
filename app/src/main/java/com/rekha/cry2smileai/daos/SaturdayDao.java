package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.SaturdayEntity;

import java.util.List;

@Dao
public interface SaturdayDao {
    @Insert
    long insert(SaturdayEntity entity);

    @Query("SELECT * FROM Saturday")
    List<SaturdayEntity> getAllData();

    @Query("DELETE FROM Saturday")
    void deleteAllData();
}
