package com.dot.lid.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.TestResult;

@Database(entities = {TestResult.class, CacheQuestion.class, Achievement.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "lid_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AppDao getAppDaoInstance();
}
