package in.nanoelectron.foodwastemanagement;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donate extends AppCompatActivity {

    private EditText e_time;
    private EditText type,num_people,expiry_time;
    private Button btn;
    private Date mtime;
    private SimpleDateFormat sdf;
    private ProgressDialog progressDialog;

    private DatabaseReference mDatabase;
    private ValueEventListener databaseReference;
    private DatabaseReference placesdatabaseReference;
    private PlaceAutocompleteFragment autocompleteFragment;
    private String TAG = "hel";
    private String placeName;
    private double Lattitude;
    private double Longitude;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("loading");
        progressDialog.show();


        sdf = new SimpleDateFormat("HH:mm");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email=pref.getString("name", null);

        type = (EditText) findViewById(R.id.type_of_food);
        num_people = (EditText) findViewById(R.id.num_people);
        expiry_time = (EditText) findViewById(R.id.duration);
        btn = (Button) findViewById(R.id.btn_donate);

        SetTime time = new SetTime(expiry_time,this);

        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                placeName = (String) place.getName();
                latLng = place.getLatLng();
                Lattitude = latLng.latitude;
                Longitude = latLng.longitude;




            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        placesdatabaseReference = FirebaseDatabase.getInstance().getReference("profile");
        placesdatabaseReference.child(email).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    autocompleteFragment.setText((CharSequence) dataSnapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference =  FirebaseDatabase.getInstance().getReference("donations").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("type", String.valueOf(dataSnapshot.child("type_food").getValue()));
                if(String.valueOf(dataSnapshot.child("type_food").getValue()) != "null" ) {

                    type.setText(dataSnapshot.child("type_food").getValue().toString());
                    num_people.setText(dataSnapshot.child("num_people").getValue().toString());
                    expiry_time.setText(dataSnapshot.child("expiry_time").getValue().toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    mtime = sdf.parse(expiry_time.getText().toString());
                }
                catch (ParseException e){
                    e.printStackTrace();
                }

                mDatabase.child("profile").child(email).child("location").child("name").setValue(placeName);
                mDatabase.child("profile").child(email).child("location").child("lat").setValue(Lattitude);

                mDatabase.child("profile").child(email).child("location").child("lon").setValue(Longitude);
                mDatabase.child("donations").child(email).child("email").setValue(email);

                mDatabase.child("donations").child(email).child("type_food").setValue(type.getText().toString());
                mDatabase.child("donations").child(email).child("num_people").setValue(Integer.parseInt(num_people.getText().toString()));
                mDatabase.child("donations").child(email).child("expiry_time").setValue(expiry_time.getText().toString());
            }
        });
    }
}
