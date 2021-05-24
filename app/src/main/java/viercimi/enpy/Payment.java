package viercimi.enpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Payment extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton btn_pay;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_pay = findViewById(R.id.btn_pay);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prev = new Intent(Payment.this,Cart.class);
                startActivity(prev);
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay = new Intent(Payment.this,SuccessPayment.class);
                startActivity(pay);
            }
        });
    }
}