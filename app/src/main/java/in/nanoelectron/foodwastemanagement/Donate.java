package in.nanoelectron.foodwastemanagement;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donate extends AppCompatActivity {

    private EditText e_time;
    private EditText type,num_people,expiry_time;
    private Button btn;
    private Date mtime;
    private SimpleDateFormat sdf;

    private DatabaseReference mDatabase,mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        sdf = new SimpleDateFormat("HH:mm");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        e_time = (EditText) findViewById(R.id.duration);
        type = (EditText) findViewById(R.id.type_of_food);
        num_people = (EditText) findViewById(R.id.num_people);
        expiry_time = (EditText) findViewById(R.id.duration);
        btn = (Button) findViewById(R.id.btn_donate);

        SetTime time = new SetTime(e_time,this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=pref.getString("name", null);
                try{
                    mtime = sdf.parse(expiry_time.getText().toString());
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                Donations d = new Donations(email,type.getText().toString(),Integer.parseInt(num_people.getText().toString()),mtime);
                mRef = mDatabase.child("donations").child(email);
                mRef.setValue(d);
            }
        });
    }
}
