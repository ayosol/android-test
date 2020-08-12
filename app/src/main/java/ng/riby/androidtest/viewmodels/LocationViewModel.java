package ng.riby.androidtest.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ng.riby.androidtest.database.Locations;
import ng.riby.androidtest.repository.LocationRepository;

public class LocationViewModel extends AndroidViewModel {

    private static final String TAG = "LocationViewModel";

    private final LocationRepository mLocationRepository;
    private final LiveData<List<Locations>> mAllLocations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        mLocationRepository = new LocationRepository(application);
        mAllLocations = mLocationRepository.getAllLocations();
        Log.d("TAG", "LocationViewModel: " + mAllLocations);
    }

    public LiveData<List<Locations>> getAllLocations() {
        return mAllLocations;
    }

    public void insert(Locations locations) {
        Log.d("TAG", "insert: " + locations);
        mLocationRepository.insert(locations);
    }
}
