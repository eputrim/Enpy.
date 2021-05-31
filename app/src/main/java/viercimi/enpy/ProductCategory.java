package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.util.ArrayList;

public class ProductCategory extends AppCompatActivity {

    ImageView back, cart;
    RecyclerView products_place;
    TextView category_name;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference_categories;

    ArrayList<MyProducts> list_products;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        getUsernameLocal();

        final String category_id = getIntent().getExtras().getString("category_id");

        back = findViewById(R.id.back);
        cart = findViewById(R.id.cart);
        category_name = findViewById(R.id.category_name);
        products_place = findViewById(R.id.products_place);

        products_place.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        products_place.addItemDecoration(itemDecoration);
        list_products = new ArrayList<MyProducts>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ProductCategory.this,Home.class);
                startActivity(back);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocart = new Intent(ProductCategory.this,Cart.class);
                startActivity(gocart);
            }
        });

        reference_categories = FirebaseDatabase.getInstance().getReference().child("Products").child(category_id);
        reference_categories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               category_name.setText(category_id);
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyProducts p = dataSnapshot1.getValue(MyProducts.class);
                    p.key = dataSnapshot1.getKey();
                    list_products.add(p);
                }
                productsAdapter = new ProductsAdapter(ProductCategory.this, list_products);
                products_place.setAdapter(productsAdapter);
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