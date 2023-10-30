package com.rekha.cry2smileai;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rekha.cry2smileai.daos.FridayDao;
import com.rekha.cry2smileai.daos.MondayDao;
import com.rekha.cry2smileai.daos.SaturdayDao;
import com.rekha.cry2smileai.daos.SundayDao;
import com.rekha.cry2smileai.daos.ThursdayDao;
import com.rekha.cry2smileai.daos.TuesdayDao;
import com.rekha.cry2smileai.daos.WednesdayDao;
import com.rekha.cry2smileai.entities.FridayEntity;
import com.rekha.cry2smileai.entities.MondayEntity;
import com.rekha.cry2smileai.entities.SaturdayEntity;
import com.rekha.cry2smileai.entities.SundayEntity;
import com.rekha.cry2smileai.entities.ThursdayEntity;
import com.rekha.cry2smileai.entities.TuesdayEntity;
import com.rekha.cry2smileai.entities.WednesdayEntity;

@Database(entities = {MondayEntity.class, TuesdayEntity.class, WednesdayEntity.class, ThursdayEntity.class, FridayEntity.class, SaturdayEntity.class, SundayEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MondayDao getMondayDao();

    public abstract TuesdayDao getTuesdayDao();
    public abstract WednesdayDao getWednesdayDao();
    public abstract ThursdayDao getThursdayDao();
    public abstract FridayDao getFridayDao();
    public abstract SaturdayDao getSaturdayDao();
    public abstract SundayDao getSundayDao();

    // Define DAOs for other days of the week

    // Singleton pattern to ensure only one instance of the database is created
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

