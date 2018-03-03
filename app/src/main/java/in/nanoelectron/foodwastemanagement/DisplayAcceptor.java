package in.nanoelectron.foodwastemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayAcceptor extends AppCompatActivity {

    private String email;
    private DatabaseReference mRef;
    private TextView dname,dnum,daddr,dweb,dcredit;
    private ImageView dauth;
    private Button request;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_acceptor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final String user = pref.getString("name", null);

        databaseReference  = FirebaseDatabase.getInstance().getReference();

        dname = (TextView) findViewById(R.id.a_name);
        dnum = (TextView) findViewById(R.id.a_num);
        daddr = (TextView) findViewById(R.id.a_addr);
        dweb = (TextView) findViewById(R.id.a_web);
        dcredit = (TextView) findViewById(R.id.a_credit);
        dauth = (ImageView) findViewById(R.id.a_auth);
        request = (Button) findViewById(R.id.a_btn);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        mRef = FirebaseDatabase.getInstance().getReference("profile").child(email);
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dname.setText(email);
                dnum.setText(dataSnapshot.child("phone").getValue().toString());
                daddr.setText(dataSnapshot.child("address").getValue().toString());
                dweb.setText(dataSnapshot.child("website").getValue().toString());
                dcredit.setText(dataSnapshot.child("credit").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("donations").child(email).child("requests_by").child(user).setValue(dnum);
                databaseReference.child("requests").child(user).child("requests_to").child(email).setValue(dnum);






            }
        });


    }

}
