package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView back;
    androidx.appcompat.widget.AppCompatButton login;
    TextView regis;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        back = findViewById(R.id.back);
        login = findViewById(R.id.login);
        regis = findViewById(R.id.regis);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gostart = new Intent(Login.this,LandingPage.class);
                startActivity(gostart);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            //ambil data dari firebase
                            String passwordFromFirebase = snapshot.child("password").getValue().toString();
                            //validasi password dgn password firebase
                            if (password.equals(passwordFromFirebase)){

                                //simpan ke local
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, xusername.getText().toString());
                                editor.apply();

                                //pindah activity
                                Intent gohome = new Intent(Login.this,Home.class);
                                startActivity(gohome);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Data Not Found !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });


            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregis = new Intent(Login.this,Register.class);
                startActivity(goregis);
            }
        });
    }
}