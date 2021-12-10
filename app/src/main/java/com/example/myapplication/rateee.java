package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.willy.ratingbar.RotationRatingBar;
import java.util.ArrayList;
import java.util.Random;

public class rateee extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient myclient;
    Location last_location;
    boolean flag = false;
    double latitude , longitude;
    GoogleMap gmap;

    double [] lat = {16.4919253 , 16.492105 , 16.4973307 , 16.502602 , 16.5026047};
    double [] log = {80.6669121 , 80.6661006 , 80.6646811 , 80.6489444 ,80.6489402};

    private TextView name;
    private RotationRatingBar ratebar;
    private Button sub;
    private foodDataBase fd;
    private int val = 0 , size = 0;


    private final Random rand = new Random();
    private final int randomNum = rand.nextInt(4) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateee);


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        myclient = LocationServices.getFusedLocationProviderClient(this);

        fd = foodDataBase.getDbInstance(getApplicationContext());
        ratebar = new RotationRatingBar(this);
        name = findViewById(R.id.rate_name);
        ratebar = findViewById(R.id.rate_ratebar);
        sub = findViewById(R.id.rate_submit);

        ArrayList<String> l = getIntent().getStringArrayListExtra("list_name");

        size = l.size();
        name.setText(l.get(0));



        sub.setOnClickListener(v -> {
            int tot_rating = (int) ratebar.getRating() + fd.itemDao().get_totalcounted(l.get(val));
            int tot_rev = fd.itemDao().get_totalratings(l.get(0)) + 1;
            fd.itemDao().update_totalcounted(tot_rating, l.get(0));
            fd.itemDao().update_totalratings(tot_rev, l.get(0));
            ++val;
            if (val >= size) {
                name.setText(R.string.thank_rate);
                ratebar.setVisibility(View.GONE);
                sub.setVisibility(View.GONE);
            } else {
                name.setText(l.get(val));
            }
        });
        checklocpermission();
    }

    public void checklocpermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            flag = true;
            myclient.getLastLocation().addOnSuccessListener(location -> {
                if (location!=null){
                    last_location = location;
                    latitude = last_location.getLatitude();
                    longitude = last_location.getLongitude();
                    inmap();
                }
            });
        }
    }


    void inmap(){
       supportMapFragment.getMapAsync(googleMap -> {
            gmap = googleMap;
            if (flag){
                LatLng mylating = new LatLng(lat[randomNum],log[randomNum]);
                gmap.moveCamera(CameraUpdateFactory.newLatLng(mylating));
                if (ActivityCompat.checkSelfPermission(rateee.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(rateee.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    return;
                }
                gmap.setMyLocationEnabled(true);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(mylating);
                markerOptions.title("here");
                gmap.addMarker(markerOptions);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checklocpermission();
            }
        }
    }


}