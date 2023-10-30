package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.SundayEntity;

import java.util.List;

@Dao
public interface SundayDao {
    @Insert
    long insert(SundayEntity entity);

    @Query("SELECT * FROM Sunday")
    List<SundayEntity> getAllData();

    @Query("DELETE FROM Sunday")
    void deleteAllData();
}
