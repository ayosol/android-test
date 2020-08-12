package ng.riby.androidtest.ui_views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ng.riby.androidtest.R;
import ng.riby.androidtest.database.Locations;
import ng.riby.androidtest.viewmodels.LocationViewModel;

/**
 * The MainActivity class is
 *
 * @author Solomon Ayodele Ogunbowale
 * @version 1.0
 * @created 12th of August 2020
 */

public class MainActivity extends AppCompatActivity {

    public static final int ACTIVITY_REQUEST_CODE = 100;
    LocationManager locationManager;
    LocationListener locationListener;
    boolean isTracking;
    Button btn_start;
    TextView txt_start_lat, txt_start_long, txt_end_lat, txt_end_long, textView, txt_distance;
    double start_lat, start_long, end_lat, end_long;
    List<Address> addresses;
    FusedLocationProviderClient client;
    private LocationViewModel mLocationViewModel;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_distance = findViewById(R.id.txt_distance);
        txt_start_lat = findViewById(R.id.txt_start_lat);
        txt_start_long = findViewById(R.id.txt_start_long);
        txt_end_lat = findViewById(R.id.txt_end_lat);
        txt_end_long = findViewById(R.id.txt_end_long);
        textView = findViewById(R.id.textView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        mLocationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        //Initialize FusedLocationProviderClient
        client = LocationServices.getFusedLocationProviderClient(this);

        getLocation();

        isTracking = false;
        btn_start = findViewById(R.id.btn_start_stop);
        btn_start.setOnClickListener(v -> {

            if (!isTracking) {
                isTracking = true;
                btn_start.setText("STOP");
                textView.setVisibility(View.GONE);
            } else {
                isTracking = false;
                btn_start.setText("START");
                updateUI();
                float[] result = new float[1];
                Location.distanceBetween(start_lat, start_long, end_lat, end_long, result);
                txt_distance.setText("Distance is:\n" + String.valueOf(result[0] + "meters"));
            }

            updateUI();
            Log.d("TAG", "onClick: " + isTracking);
        });

    }

    private void updateUI() {
        if (mLocationViewModel != null) {
            mLocationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
            mLocationViewModel.getAllLocations().observe(this, locations -> {
                Log.d("TAG", "onChanged: " + locations);

                if (isTracking) {
                    start_lat = locations.get(0).getLatitude();
                    start_long = locations.get(0).getLongitude();

                    txt_start_lat.setText(String.valueOf(start_lat));
                    txt_start_long.setText(String.valueOf(start_long));
                } else {
                    end_lat = locations.get(0).getLatitude();
                    end_long = locations.get(0).getLongitude();

                    txt_end_lat.setText(String.valueOf(end_lat));
                    txt_end_long.setText(String.valueOf(end_long));

                    Log.d("TAG", "onChanged: " + locations.get(0).getLatitude());

                }
            });
        } else {
            getLocation();
        }
    }


    private void getLocation() {
        //Check Permission
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Get Locations when permission granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            client.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //Initialize Address List
                        Log.d("TAG", "onChanged: " + addresses);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Locations locations = new Locations(location.getLatitude(), location.getLongitude());
                    Log.d("TAG", "onActivityResult: " + locations);
                    mLocationViewModel.insert(locations);
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACTIVITY_REQUEST_CODE);
        }

    }
}