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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDetail extends AppCompatActivity {

    ImageView back, cart, btnminus, btnplus;
    TextView jumlah;
    Integer valuejumlah = 1;

    ImageView product_image;
    TextView product_name, product_price, product_description;

    RecyclerView size_place, color_place;

    DatabaseReference reference_user, reference_product, reference_size, reference_color;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    ArrayList<MySizeColor> list_size, list_color;
    SizeAdapter sizeAdapter;
    ColorAdapter colorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getUsernameLocal();

        final String product_id = getIntent().getExtras().getString("product_id");
        final String category_id = getIntent().getExtras().getString("category_id");

        btnminus = findViewById(R.id.btnminus);
        btnplus = findViewById(R.id.btnplus);
        jumlah = findViewById(R.id.jumlah);
        back = findViewById(R.id.back);
        cart = findViewById(R.id.cart);

        product_image = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        size_place = findViewById(R.id.size_place);
        color_place = findViewById(R.id.color_place);

        size_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_size = new ArrayList<MySizeColor>();

        color_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_color = new ArrayList<MySizeColor>();

        reference_product = FirebaseDatabase.getInstance().getReference().child("Products").child(category_id).child(product_id);
        reference_product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(ProductDetail.this).load(snapshot.child("url_photo_product").getValue().toString()).into(product_image);
                product_name.setText(snapshot.child("product_name").getValue().toString());
                product_price.setText(getResources().getString(R.string.rp, snapshot.child("price").getValue().toString()));
                product_description.setText(snapshot.child("description").getValue().toString());

                reference_size = reference_product.child("size");
                reference_size.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                            MySizeColor p = dataSnapshot1.getValue(MySizeColor.class);
                            p.key = dataSnapshot1.getKey();
                            list_size.add(p);
                        }
                        sizeAdapter = new SizeAdapter(ProductDetail.this, list_size);
                        size_place.setAdapter(sizeAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference_color = reference_product.child("color");
                reference_color.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                            MySizeColor p = dataSnapshot1.getValue(MySizeColor.class);
                            p.key = dataSnapshot1.getKey();
                            list_color.add(p);
                        }
                        colorAdapter = new ColorAdapter(ProductDetail.this, list_color);
                        color_place.setAdapter(colorAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //menyembunyikan button minus di awal
        btnminus.animate().alpha(0).setDuration(300).start();
        btnminus.setEnabled(false);
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlah+=1;
                jumlah.setText(valuejumlah.toString());
                if (valuejumlah > 1){
                    btnminus.animate().alpha(1).setDuration(300).start();
                    btnminus.setEnabled(true);
                }
            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlah-=1;
                jumlah.setText(valuejumlah.toString());
                if (valuejumlah < 2){
                    btnminus.animate().alpha(0).setDuration(300).start();
                    btnminus.setEnabled(false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ProductDetail.this,Home.class);
                startActivity(back);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocart = new Intent(ProductDetail.this,Cart.class);
                startActivity(gocart);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}