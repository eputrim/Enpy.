package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton btn_checkout;
    ImageView back;
    TextView cart_total;

    RecyclerView cart_place;

    DatabaseReference reference_cart;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    ArrayList<MyCart> list_cart;
    CartAdapter cartAdapter;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getUsernameLocal();

        btn_checkout = findViewById(R.id.btn_checkout);
        back = findViewById(R.id.back);
        cart_place = findViewById(R.id.cart_place);
        cart_total = findViewById(R.id.cart_total);

        cart_place.setLayoutManager(new LinearLayoutManager(this));
        list_cart = new ArrayList<MyCart>();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay = new Intent(Cart.this,Payment.class);
                startActivity(pay);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(Cart.this,Home.class);
                startActivity(gohome);
            }
        });

        reference_cart = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("cart");
        reference_cart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer total_harga = 0;
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyCart p = dataSnapshot1.getValue(MyCart.class);
                    Integer total = Integer.valueOf(p.getTotal());
                    total_harga = total_harga + total;
                    p.key = dataSnapshot1.getKey();
                    list_cart.add(p);
                }
                cartAdapter = new CartAdapter(Cart.this, list_cart, reference_cart);
                cart_place.setAdapter(cartAdapter);
                cart_total.setText(formatRupiah.format(total_harga));
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