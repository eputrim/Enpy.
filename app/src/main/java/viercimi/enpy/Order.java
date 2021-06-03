package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Order extends AppCompatActivity {

    ImageView back;
    DrawerLayout drawerLayout;
    RecyclerView order_place;

    DatabaseReference reference_order;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    ArrayList<MyOrder> list_order;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getUsernameLocal();

        back = findViewById(R.id.back);
        drawerLayout = findViewById(R.id.menu_drawer);
        order_place = findViewById(R.id.order_place);

        order_place.setLayoutManager(new LinearLayoutManager(this));
        list_order = new ArrayList<MyOrder>();

        reference_order = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("order_history");
        reference_order.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyOrder p = dataSnapshot1.getValue(MyOrder.class);
                    p.key = dataSnapshot1.getKey();
                    list_order.add(p);
                }
                orderAdapter = new OrderAdapter(Order.this, list_order);
                order_place.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(Order.this, Home.class);
                startActivity(gohome);
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
        Home.redirectActivity(this, Profile.class);
    }

    public void ClickOrder(View view) {
        recreate();
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