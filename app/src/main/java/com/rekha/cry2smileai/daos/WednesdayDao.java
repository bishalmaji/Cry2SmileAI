package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.WednesdayEntity;

import java.util.List;

@Dao
public interface WednesdayDao {
    @Insert
    long insert(WednesdayEntity entity);

    @Query("SELECT * FROM WEDNESDAY")
    List<WednesdayEntity> getAllData();

    @Query("DELETE FROM WEDNESDAY")
    void deleteAllData();
}
