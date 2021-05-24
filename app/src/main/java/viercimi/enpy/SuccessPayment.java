package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessPayment extends AppCompatActivity {
    ImageView success;
    androidx.appcompat.widget.AppCompatButton order;
    TextView btn_back, app_title, app_subtitle;
    Animation btt, ttb, app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_payment);

        btn_back = findViewById(R.id.btn_back);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        order = findViewById(R.id.order);
        success = findViewById(R.id.success);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //run animation
        success.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);
        order.startAnimation(btt);
        btn_back.startAnimation(btt);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(SuccessPayment.this,Home.class);
                startActivity(home);
            }
        });
    }
}