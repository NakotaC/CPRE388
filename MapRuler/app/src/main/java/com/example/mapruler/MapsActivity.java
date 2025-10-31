package com.example.mapruler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mapruler.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private Geocoder geocoder;
    List<Marker> markers = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this);

        Button button = findViewById(R.id.button);
        EditText editTextAddress = findViewById(R.id.editTextAddress);

        button.setOnClickListener(view -> {
            String address = editTextAddress.getText().toString();
            try {
                placeMarkerandMeasure(address);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_mine));
        checkLocationPermissionAndEnableLocation();

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            //mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                            markers.add(mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location")));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        }
                    }
                });
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Enable the location layer.
                    enableMyLocation();
                } else {
                    // Permission was denied. Show a message to the user.
                    Toast.makeText(this, "Location permission is required to show your position on the map.", Toast.LENGTH_LONG).show();
                }
            });

    private void checkLocationPermissionAndEnableLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, enable the location layer.
            enableMyLocation();
        } else {
            // Permission is not granted, so request it.
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

private void enableMyLocation() {
    if (mMap != null) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}

private LatLng getLocationFromAddress(String address) throws IOException {
  List<Address> convertedAddress =  geocoder.getFromLocationName(address, 1);
    assert convertedAddress != null;
    return new LatLng(convertedAddress.get(0).getLatitude(), convertedAddress.get(0).getLongitude());
}

public void placeMarkerandMeasure(String address) throws IOException {
    LatLng location = getLocationFromAddress(address);
    if(markers.size() == 2){
        markers.get(1).remove();
        markers.remove(1);
    }

    Marker newMarker = mMap.addMarker(new MarkerOptions().position(location).title(address));
    if (newMarker != null) {
        // Add the new marker to our tracking list
        markers.add(newMarker);
    }

    float[] results = new float[1];

    if (markers.size() == 2) {
        Location.distanceBetween(
                markers.get(0).getPosition().latitude,
                markers.get(0).getPosition().longitude,
                markers.get(1).getPosition().latitude,
                markers.get(1).getPosition().longitude,
                results);
    }
    Toast.makeText(this, "Distance: " + results[0] / 1000 * 0.621 + " miles", Toast.LENGTH_LONG).show();
    }
}