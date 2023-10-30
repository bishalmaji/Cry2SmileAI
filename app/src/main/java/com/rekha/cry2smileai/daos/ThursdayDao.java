package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.ThursdayEntity;

import java.util.List;

@Dao
public interface ThursdayDao {
    @Insert
    long insert(ThursdayEntity entity);

    @Query("SELECT * FROM Thursday")
    List<ThursdayEntity> getAllData();

    @Query("DELETE FROM Thursday")
    void deleteAllData();
}
