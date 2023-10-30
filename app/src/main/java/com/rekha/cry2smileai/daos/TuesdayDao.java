package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.TuesdayEntity;

import java.util.List;

@Dao
public interface TuesdayDao {
    @Insert
    long insert(TuesdayEntity entity);

    @Query("SELECT * FROM Tuesday")
    List<TuesdayEntity> getAllData();

    @Query("DELETE FROM Tuesday")
    void deleteAllData();
}
