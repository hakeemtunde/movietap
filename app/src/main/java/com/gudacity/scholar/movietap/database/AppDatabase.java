package com.gudacity.scholar.movietap.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.gudacity.scholar.movietap.utils.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DBNAME = "moviedb";
    private static AppDatabase sInstance;

    public static AppDatabase getsIntance(Context context) {
        if (sInstance == null) {
            synchronized (new Object()) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DBNAME).build();
            }
        }
        Log.i(TAG, "DATABASE IS UP");
        return sInstance;
    }

    public abstract MovieDAO movieDAO();
}
