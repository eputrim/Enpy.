package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {
    androidx.appcompat.widget.AppCompatButton btn_regis;
    ImageView back;
    TextView skip;
    EditText address, phone_number, password, email, last_name, first_name, username;

    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);

        btn_regis = findViewById(R.id.btn_regis);
        back = findViewById(R.id.back);
        skip = findViewById(R.id.skip);
        address = findViewById(R.id.address);
        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        last_name = findViewById(R.id.last_name);
        first_name = findViewById(R.id.first_name);
        username = findViewById(R.id.username);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah button jadi loading ketika sudah di klik
                btn_regis.setEnabled(false);
                btn_regis.setText("Loading ...");


                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();
                final String xlastname = last_name.getText().toString();
                final String xfirstname = first_name.getText().toString();
                final String xaddress = address.getText().toString();
                final String xphonenumber = phone_number.getText().toString();
                final String xemail = email.getText().toString();

                if (xusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username empty !", Toast.LENGTH_SHORT).show();
                    //ubah button jadi loading ketika sudah di klik
                    btn_regis.setEnabled(true);
                    btn_regis.setText("Next");
                }
                else {
                    if (xpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password empty !", Toast.LENGTH_SHORT).show();
                        //ubah button jadi loading ketika sudah di klik
                        btn_regis.setEnabled(true);
                        btn_regis.setText("Next");
                    } else {
                        if (xlastname.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Last Name empty !", Toast.LENGTH_SHORT).show();
                            //ubah button jadi loading ketika sudah di klik
                            btn_regis.setEnabled(true);
                            btn_regis.setText("Next");
                        }else {
                            if (xfirstname.isEmpty()){
                                Toast.makeText(getApplicationContext(), "First Name empty !", Toast.LENGTH_SHORT).show();
                                //ubah button jadi loading ketika sudah di klik
                                btn_regis.setEnabled(true);
                                btn_regis.setText("Next");
                            }else {
                                if (xemail.isEmpty()){
                                    Toast.makeText(getApplicationContext(), "Email empty !", Toast.LENGTH_SHORT).show();
                                    //ubah button jadi loading ketika sudah di klik
                                    btn_regis.setEnabled(true);
                                    btn_regis.setText("Next");
                                }else {
                                    if (xaddress.isEmpty()){
                                        Toast.makeText(getApplicationContext(), "Address empty !", Toast.LENGTH_SHORT).show();
                                        //ubah button jadi loading ketika sudah di klik
                                        btn_regis.setEnabled(true);
                                        btn_regis.setText("Next");
                                    }else {
                                        if (xphonenumber.isEmpty()){
                                            Toast.makeText(getApplicationContext(), "Phone number empty !", Toast.LENGTH_SHORT).show();
                                            //ubah button jadi loading ketika sudah di klik
                                            btn_regis.setEnabled(true);
                                            btn_regis.setText("Next");
                                        }else {
                                            // mengambil username pada firebase database
                                            reference_username = FirebaseDatabase.getInstance().getReference().child("Users")
                                                    .child(username.getText().toString());
                                            reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //jika username tersedia
                                                    if (snapshot.exists()) {
                                                        Toast.makeText(getApplicationContext(), "Username is exist !", Toast.LENGTH_SHORT).show();
                                                        //ubah button jadi aktif
                                                        btn_regis.setEnabled(true);
                                                        btn_regis.setText("Next");
                                                    } else {
                                                        //menyimpan data ke local storage
                                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString(username_key, username.getText().toString());
                                                        editor.apply();


                                                        //simpan ke database
                                                        reference = FirebaseDatabase.getInstance().getReference()
                                                                .child("Users").child(username.getText().toString());
                                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                snapshot.getRef().child("first_name").setValue(first_name.getText().toString());
                                                                snapshot.getRef().child("last_name").setValue(last_name.getText().toString());
                                                                snapshot.getRef().child("password").setValue(password.getText().toString());
                                                                snapshot.getRef().child("email").setValue(email.getText().toString());
                                                                snapshot.getRef().child("phone_number").setValue(phone_number.getText().toString());
                                                                snapshot.getRef().child("address").setValue(address.getText().toString());
                                                                snapshot.getRef().child("username").setValue(username.getText().toString());
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                        //pindah activity
                                                        Intent register = new Intent(Register.this, AddPhotoAct.class);
                                                        startActivity(register);


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(Register.this, Login.class);
                startActivity(gohome);
            }
        });
    }

}
