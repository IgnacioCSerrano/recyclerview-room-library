package dam.iesaugustobriga.cityproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dam.iesaugustobriga.cityproject.db.dao.CityDao;
import dam.iesaugustobriga.cityproject.db.entity.CityEntity;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class CityRoomDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
    private static volatile CityRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityRoomDatabase.class, "city_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
