package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ImageView btn_menu, btn_cart;
    TextView name_user;

    DatabaseReference reference_user, reference_categories, reference_products;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView categories_place, products_place;

    ArrayList<MyCategories> list_categories;
    CategoriesAdapter categoriesAdapter;

    ArrayList<MyProducts> list_products;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_menu = findViewById(R.id.btn_menu);
        btn_cart = findViewById(R.id.btn_cart);
        name_user = findViewById(R.id.name_user);
        categories_place = findViewById(R.id.categories_place);
        products_place = findViewById(R.id.products_place);

        reference_user = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_user.setText(getResources().getString(R.string.hey, snapshot.child("first_name").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        categories_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_categories = new ArrayList<MyCategories>();

        products_place.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        products_place.addItemDecoration(itemDecoration);
        list_products = new ArrayList<MyProducts>();

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Home.this,Menu.class);
                startActivity(menu);
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocart = new Intent(Home.this,Cart.class);
                startActivity(gocart);
            }
        });

        reference_categories = FirebaseDatabase.getInstance().getReference().child("Products");
        reference_categories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyCategories p = dataSnapshot1.getValue(MyCategories.class);
                    p.key = dataSnapshot1.getKey();
                    list_categories.add(p);
                }
                categoriesAdapter = new CategoriesAdapter(Home.this, list_categories);
                categories_place.setAdapter(categoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference_products = FirebaseDatabase.getInstance().getReference().child("Popular");
        reference_products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyProducts p = dataSnapshot1.getValue(MyProducts.class);
                    p.key = dataSnapshot1.getKey();
                    list_products.add(p);
                }
                productsAdapter = new ProductsAdapter(Home.this, list_products);
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