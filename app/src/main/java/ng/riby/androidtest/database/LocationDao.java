package ng.riby.androidtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The LocationDao class is used to create db queries
 *
 * @author Solomon Ayodele Ogunbowale
 * @version 1.0
 * @created 12th of August 2020
 */


@Dao
public interface LocationDao {

    @Insert(onConflict = REPLACE)
    void insertLocation(Locations locations);

    @Query("SELECT * FROM Locations")
    LiveData<List<Locations>> getAllLocations();

    @Query("DELETE FROM Locations")
    void deleteAllLocation();


}
