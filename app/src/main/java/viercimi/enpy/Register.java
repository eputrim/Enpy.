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
    ImageView back, image_add;
    TextView skip;
    EditText address, phone_number, password, email, last_name, first_name, username;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

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

        image_add = findViewById(R.id.image_add);

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
                Intent firstpage = new Intent(Register.this,LandingPage.class);
                startActivity(firstpage);
            }
        });
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah button jadi loading ketika sudah di klik
                btn_regis.setEnabled(false);
                btn_regis.setText("Loading ...");
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
                Intent register = new Intent(Register.this,AddPhotoAct.class);
                startActivity(register);
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
