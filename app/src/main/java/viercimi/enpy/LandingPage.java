package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {
    Animation ttb, btt;
    ImageView logo;
    TextView newaccount;
    androidx.appcompat.widget.AppCompatButton Signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Signin = findViewById(R.id.Signin);
        newaccount = findViewById(R.id.newaccount);
        logo = findViewById(R.id.logo);

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
        logo.startAnimation(ttb);
        Signin.startAnimation(btt);
        newaccount.startAnimation(btt);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(LandingPage.this,Login.class);
                startActivity(login);
            }
        });

        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newaccount = new Intent(LandingPage.this,Register.class);
                startActivity(newaccount);
            }
        });
    }
}