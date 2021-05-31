package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        drawerLayout = findViewById(R.id.menu_drawer);
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