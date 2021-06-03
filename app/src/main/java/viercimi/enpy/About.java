package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        drawerLayout = findViewById(R.id.menu_drawer);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(About.this, Home.class);
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
        Home.redirectActivity(this, Order.class);
    }

    public void ClickAbout(View view) {
        recreate();
    }

    public void ClickLogout(View view) {
        Home.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
}