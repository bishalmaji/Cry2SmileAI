package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;

import java.util.List;

@Dao
public interface MondayDao {
    @Insert
    long insert(MondayEntity entity);

    @Query("SELECT * FROM Monday")
    List<MondayEntity> getAllData();

    @Query("DELETE FROM Monday")
    void deleteAllData();


    // Add other queries as needed
}

