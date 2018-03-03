package in.nanoelectron.foodwastemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Donations> donationsList = new ArrayList<>();
    private ArrayList<Requests> requestsList = new ArrayList<>();
    private static final String MY_PREFS_NAME = "username";
    private RecyclerView recyclerViewDonations,recyclerViewRequests;
    private DonationsAdapter dAdapter;
    private RequestsAdapter rAdapter;
    private DatabaseReference databaseReference,adatabaseReference,rdatabaseReference;
    private SimpleDateFormat sdf;
    private Date mtime;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        sdf = new SimpleDateFormat("HH:mm");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        adatabaseReference = FirebaseDatabase.getInstance().getReference("requests");
        rdatabaseReference  =FirebaseDatabase.getInstance().getReference("donations");

        String email=pref.getString("name", null);         // getting String

        recyclerViewDonations = (RecyclerView) findViewById(R.id.recycler_view_donations);
        recyclerViewRequests = (RecyclerView) findViewById(R.id.recycler_view_requests);


        rAdapter = new RequestsAdapter(requestsList);
        dAdapter = new DonationsAdapter(donationsList);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());


        recyclerViewDonations.setLayoutManager(mLayoutManager);
        recyclerViewRequests.setLayoutManager(layoutManager);

        recyclerViewDonations.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRequests.setItemAnimator(new DefaultItemAnimator());

        recyclerViewDonations.setAdapter(dAdapter);
        recyclerViewRequests.setAdapter(rAdapter);


        databaseReference.child("requests").child(email).child("requests_to").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestsList.clear();

                for (final DataSnapshot requests_pending: dataSnapshot.getChildren()){
                    rdatabaseReference.child(requests_pending.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String email = (String) dataSnapshot.child("email").getValue();
                            String donar = (String) dataSnapshot.child("donar").getValue();
                            if(donar == null)
                            {
                                donar = "pending";
                            }
                            Object people = dataSnapshot.child("num_people").getValue();
                            int peop = Integer.parseInt(String.valueOf(people));
                            Requests r = new Requests(email,donar,peop);
                            requestsList.add(r);
                            rAdapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        databaseReference.child("donations").child(email).child("requests_by").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                donationsList.clear();


                /* Date now = new Date();
                  Donations d = new Donations("ravi","Dinner",105,now);
                  donationsList.add(d);*/

                for (final DataSnapshot accept_pendings: dataSnapshot.getChildren()){
                    Log.d("something",accept_pendings.getKey());
                    adatabaseReference.child(accept_pendings.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d("something2", String.valueOf(dataSnapshot.child("email").getValue()));

                            String email = (String) dataSnapshot.child("email").getValue();
                            String type = (String) dataSnapshot.child("type").getValue();
                            String waiting_time = (String) dataSnapshot.child("waiting_time").getValue();
                            try {
                                 mtime = sdf.parse(waiting_time);
                                 Log.d("stringtest", String.valueOf(mtime));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int number =  Integer.parseInt(String.valueOf(dataSnapshot.child("number").getValue()));


                            Donations d = new Donations(email,type,number,mtime);
                            donationsList.add(d);
                            dAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_donate) {
            Intent intent = new Intent(this,Donate.class);
            startActivity(intent);

        } else if (id == R.id.nav_request) {

            Intent intent  =  new Intent(this,FRequest.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
