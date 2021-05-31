package viercimi.enpy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class Main_Nav_Drawer extends AppCompatActivity {

    TextView name_depan, nama_belakang;
    ImageView url_photo_profile;

    DatabaseReference reference_user;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_nav_drawer);

        url_photo_profile = findViewById(R.id.url_photo_profile);

        getUsernameLocal();

        name_depan = findViewById(R.id.nama_depan);
        nama_belakang = findViewById(R.id.nama_belakang);
        reference_user = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_depan.setText(getResources().getString(R.string.hey, snapshot.child("first_name").getValue().toString()));
                nama_belakang.setText(getResources().getString(R.string.hey, snapshot.child("last_name").getValue().toString()));
                Picasso.with(Main_Nav_Drawer.this).load(snapshot.child("url_photo_profile").getValue()
                        .toString()).centerCrop().fit().into(url_photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


        public void getUsernameLocal(){
            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
            username_key_new = sharedPreferences.getString(username_key, "");
        }
}

