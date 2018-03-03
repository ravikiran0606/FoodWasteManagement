package in.nanoelectron.foodwastemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayDonor extends AppCompatActivity {

    private String email;
    private DatabaseReference mRef;
    private TextView dname,dnum,daddr,dweb,dcredit;
    private ImageView dauth;
    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_donor);

        dname = (TextView) findViewById(R.id.d_name);
        dnum = (TextView) findViewById(R.id.d_num);
        daddr = (TextView) findViewById(R.id.d_addr);
        dweb = (TextView) findViewById(R.id.d_web);
        dcredit = (TextView) findViewById(R.id.d_credit);
        dauth = (ImageView) findViewById(R.id.d_auth);
        accept = (Button) findViewById(R.id.d_btn);

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

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
