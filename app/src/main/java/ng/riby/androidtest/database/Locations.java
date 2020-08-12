package ng.riby.androidtest.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * The Locations class is the Entity
 *
 * @author Solomon Ayodele Ogunbowale
 * @version 1.0
 * @created 12th of August 2020
 */


@Entity(tableName = "Locations")
public class Locations {

    @PrimaryKey
    @ColumnInfo(name = "start_lat")
    private double latitude;

    @ColumnInfo(name = "start_long")
    private double longitude;

    public Locations() {
    }

    public Locations(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
