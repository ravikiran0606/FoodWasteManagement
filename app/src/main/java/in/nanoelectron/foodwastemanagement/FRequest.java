package in.nanoelectron.foodwastemanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FRequest extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap googleMap;
    private DatabaseReference databaseReference,donarsdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String email = pref.getString("name", null);
        databaseReference = FirebaseDatabase.getInstance().getReference("donations");
        donarsdatabase = FirebaseDatabase.getInstance().getReference("profile");



        setContentView(R.layout.activity_frequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot donars: dataSnapshot.getChildren()){
                    donarsdatabase.child(donars.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double lat = Double.valueOf(dataSnapshot.child("location").child("lat").getValue().toString());
                            Double lon = Double.valueOf(dataSnapshot.child("location").child("lon").getValue().toString());
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 5));


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Log.d("tesst1",donars.getKey());


                        }
                    });


                    //googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(lat),Double.valueOf(lon))));






                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
