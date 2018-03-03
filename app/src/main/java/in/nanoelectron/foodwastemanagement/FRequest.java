package in.nanoelectron.foodwastemanagement;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FRequest extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap googleMap;
    private DatabaseReference databaseReference,donarsdatabase;
    private RecyclerView recyclerViewDonations;
    private DonationsAdapter dAdapter;
    private ArrayList<Donations> donationsList = new ArrayList<>();
    private SimpleDateFormat sdf;
    private Date mtime;



    @SuppressLint({"WrongViewCast", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frequest);
        sdf = new SimpleDateFormat("HH:mm");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String email = pref.getString("name", null);
        databaseReference = FirebaseDatabase.getInstance().getReference("donations");
        donarsdatabase = FirebaseDatabase.getInstance().getReference("profile");




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        dAdapter = new DonationsAdapter(getApplicationContext(),donationsList);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dAdapter);



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

       /* recyclerViewDonations.setLayoutManager(mLayoutManager);
        recyclerViewDonations.setItemAnimator(new DefaultItemAnimator());
        dAdapter = new DonationsAdapter(getApplicationContext(),donationsList);
        recyclerViewDonations.setAdapter(dAdapter);*/

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot donars: dataSnapshot.getChildren()){
                    final String email = donars.getKey();
                    String type = (String) donars.child("type_food").getValue();

                    String waiting_time = (String) donars.child("expiry_time").getValue();
                    try {
                        mtime = sdf.parse(waiting_time);
                        Log.d("stringtest", String.valueOf(mtime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int number =  Integer.parseInt(String.valueOf(donars.child("num_people").getValue()));




                    Donations d = new Donations(email,type,number,mtime);
                    donationsList.add(d);
                    dAdapter.notifyDataSetChanged();

                    donarsdatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double lat = Double.valueOf(dataSnapshot.child(email).child("location").child("lat").getValue().toString());
                            Double lon = Double.valueOf(dataSnapshot.child(email).child("location").child("lon").getValue().toString());
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 5));


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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
