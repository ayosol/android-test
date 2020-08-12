package ng.riby.androidtest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The LocationData class is a jetpack room database model class
 *
 * @author Solomon Ayodele Ogunbowale
 * @version 1.0
 * @created 12th of August 2020
 */


@Database(entities = {Locations.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "location_database";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile LocationDatabase INSTANCE;

    public static LocationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocationDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract LocationDao locationDao();

// --Commented out by Inspection START (12/08/2020 4:18 PM):
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                LocationDao dao = INSTANCE.locationDao();
//                dao.deleteAllLocation();
//            });
//        }
//    };
// --Commented out by Inspection STOP (12/08/2020 4:18 PM)

}
