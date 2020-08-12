package ng.riby.androidtest.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import ng.riby.androidtest.database.LocationDao;
import ng.riby.androidtest.database.LocationDatabase;
import ng.riby.androidtest.database.Locations;

public class LocationRepository {
    private final LocationDao mLocationDao;
    private final LiveData<List<Locations>> mAllLocations;

    public LocationRepository(Application application) {
        LocationDatabase location_db = LocationDatabase.getDatabase(application);
        mLocationDao = location_db.locationDao();
        mAllLocations = mLocationDao.getAllLocations();
    }

    public LiveData<List<Locations>> getAllLocations() {
        return mAllLocations;
    }

    public void insert(Locations locations) {
        LocationDatabase.databaseWriteExecutor.execute(() -> {
            Log.d("TAG", "insert: databaseWriteExecutor: " + locations);
            mLocationDao.insertLocation(locations);
        });
    }

}
