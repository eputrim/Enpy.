package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button edit;
    ImageView url_photo_profile;
    TextView username, email, phone_number, address;

    DatabaseReference reference_user;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getUsernameLocal();

        drawerLayout = findViewById(R.id.menu_drawer);
        edit = findViewById(R.id.edit);
        url_photo_profile = findViewById(R.id.url_photo_profile);
        username = findViewById(R.id.username);
        phone_number = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);

        reference_user = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //final String xphone = snapshot.child("phone_number").getValue().toString();
                username.setText(snapshot.child("username").getValue().toString());
                phone_number.setText(snapshot.child("phone_number").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                address.setText(snapshot.child("address").getValue().toString());
                Picasso.with(Profile.this).load(snapshot.child("url_photo_profile").getValue()
                        .toString()).centerCrop().fit().into(url_photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(Profile.this, EditProfile.class);
                startActivity(edit);
            }
        });
    }

    public void ClickBack (View view){
        Home.openDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        Home.redirectActivity(this, Home.class);
    }

    public void ClickProfile(View view) {
        recreate();
    }

    public void ClickOrder(View view) {
        Home.redirectActivity(this, Order.class);
    }

    public void ClickAbout(View view) {
        Home.redirectActivity(this, About.class);
    }

    public void ClickLogout(View view) {
        Home.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}