package in.nanoelectron.foodwastemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final String MY_PREFS_NAME = "username";
    EditText username,password;
    Button login;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        final SharedPreferences.Editor editor = pref.edit();

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("profile");


        username = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot emails: dataSnapshot.getChildren()) {
                            Log.d("helllo",String.valueOf(emails.getKey()));
                            if(Objects.equals(username.getText().toString(), emails.getKey()));
                            {
                                editor.putString("name", username.getText().toString());
                                editor.apply();
                                editor.commit();

                                
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
