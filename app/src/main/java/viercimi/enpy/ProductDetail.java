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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ProductDetail extends AppCompatActivity {

    ImageView back, cart, btnminus, btnplus;
    LinearLayout addtocart;
    TextView jumlah;
    Integer valuejumlah = 1;

    ImageView product_image;
    TextView product_name, product_price, product_description;

    RecyclerView size_place, color_place;

    DatabaseReference reference_user, reference_product, reference_product2, reference_size, reference_color;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    ArrayList<MyColor> list_color;
    ColorAdapter colorAdapter;

    ArrayList<MySize> list_size;
    SizeAdapter sizeAdapter;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    Integer cart_number = new Random().nextInt();
    private String xproduct_name, xproduct_price, xproduct_photo, xquantity;

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
        addtocart = findViewById(R.id.addtocart);

        product_image = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        size_place = findViewById(R.id.size_place);
        color_place = findViewById(R.id.color_place);

        size_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_size = new ArrayList<MySize>();

        color_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_color = new ArrayList<MyColor>();

        reference_product = FirebaseDatabase.getInstance().getReference().child("Products").child(category_id).child(product_id);
        reference_product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xproduct_name = snapshot.child("product_name").getValue().toString();
                xproduct_price = snapshot.child("price").getValue().toString();
                xproduct_photo = snapshot.child("url_photo_product").getValue().toString();
                xquantity = snapshot.child("quantity").getValue().toString();

                Glide.with(ProductDetail.this).load(snapshot.child("url_photo_product").getValue().toString()).into(product_image);
                product_name.setText(snapshot.child("product_name").getValue().toString());
                product_price.setText(formatRupiah.format(Integer.parseInt(snapshot.child("price").getValue().toString())));
                product_description.setText(snapshot.child("description").getValue().toString());

                reference_size = reference_product.child("size");
                reference_size.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                            MySize p = dataSnapshot1.getValue(MySize.class);
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
                            MyColor p = dataSnapshot1.getValue(MyColor.class);
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

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlah-=1;
                jumlah.setText(valuejumlah.toString());
                if (valuejumlah < 2){
                    btnminus.animate().alpha(0).setDuration(300).start();
                    btnminus.setEnabled(false);
                }
                if(valuejumlah < Integer.parseInt(xquantity)){
                    btnplus.animate().alpha(1).setDuration(300).start();
                    btnplus.setEnabled(true);
                }
            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlah+=1;
                jumlah.setText(valuejumlah.toString());
                if(valuejumlah < Integer.parseInt(xquantity)){
                    btnplus.animate().alpha(1).setDuration(300).start();
                    btnplus.setEnabled(true);
                } else {
                    btnplus.animate().alpha(0).setDuration(300).start();
                    btnplus.setEnabled(false);
                }
                if (valuejumlah > 1){
                    btnminus.animate().alpha(1).setDuration(300).start();
                    btnminus.setEnabled(true);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocart = new Intent(ProductDetail.this,Cart.class);
                startActivity(gocart);
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sizeAdapter.getSelected() == null || colorAdapter.getSelected() == null){
                    Toast.makeText(ProductDetail.this, "Please pick the size / color!", Toast.LENGTH_SHORT).show();
                } else {
                    int total = Integer.parseInt(jumlah.getText().toString()) * Integer.parseInt(xproduct_price);

                    reference_user = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("cart").child(product_id + cart_number);
                    reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reference_user.child("color").setValue(colorAdapter.getSelected().getChart().toString());
                            reference_user.child("price").setValue(xproduct_price);
                            reference_user.child("product_name").setValue(xproduct_name);
                            reference_user.child("quantity").setValue(valuejumlah.toString());
                            reference_user.child("size").setValue(sizeAdapter.getSelected().getChart().toString());
                            reference_user.child("total").setValue(String.valueOf(total));
                            reference_user.child("url_photo_product").setValue(xproduct_photo);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    reference_product2 = FirebaseDatabase.getInstance().getReference().child("Products").child(category_id).child(product_id);
                    reference_product2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int new_stock = Integer.parseInt(xquantity) - valuejumlah;
                            reference_product2.child("quantity").setValue(String.valueOf(new_stock));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Toast.makeText(ProductDetail.this, "Item has been added to your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}