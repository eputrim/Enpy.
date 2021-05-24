package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    Animation app_splash, btt;
    TextView title, subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
        title.startAnimation(app_splash);
        subtitle.startAnimation(btt);

        //membuat timer untuk pindah activity secara otomatis
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //melakukan sesuatu setelah delay selama 2000
                Intent start = new Intent(SplashScreen.this, LandingPage.class);
                startActivity(start);
                finish();
            }
        }, 2000);
    }
}