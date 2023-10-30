package com.rekha.cry2smileai.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rekha.cry2smileai.entities.FridayEntity;
import com.rekha.cry2smileai.entities.MondayEntity;

import java.util.List;
@Dao
public interface FridayDao {
    @Insert
    long insert(FridayEntity entity);

    @Query("SELECT * FROM Friday")
    List<FridayEntity> getAllData();

    @Query("DELETE FROM Friday")
    void deleteAllData();
}
