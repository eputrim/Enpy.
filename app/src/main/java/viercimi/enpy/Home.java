package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    TextView name_user;
    DrawerLayout drawerLayout;
    private static Context mlogout;

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

        mlogout = this;

        getUsernameLocal();

        name_user = findViewById(R.id.name_user);
        categories_place = findViewById(R.id.categories_place);
        products_place = findViewById(R.id.products_place);
        drawerLayout = findViewById(R.id.menu_drawer);

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

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public void ClickBack(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickCart(View view) {
        redirectActivity(this, Cart.class);
    }

    public void ClickProfile(View view) {
        redirectActivity(this, Profile.class);
    }

    public void ClickOrder(View view){
        redirectActivity(this, Order.class);
    }

    public void ClickAbout(View view){
        redirectActivity(this, About.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(mlogout, LandingPage.class);
                mlogout.startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}