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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class Payment extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton btn_pay;
    ImageView back;
    TextView xtotal, xtotalprice, xdelivery;

    RecyclerView payment_place, checkout_place;

    DatabaseReference reference_cart, reference_payment, reference_order;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    ArrayList<MyCart> list_checkout;
    CheckoutAdapter checkoutAdapter;

    ArrayList<MyPayment> list_payment;
    PaymentAdapter paymentAdapter;

    Integer order_number = new Random().nextInt();
    Integer harga_akhir;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getUsernameLocal();

        btn_pay = findViewById(R.id.btn_pay);
        back = findViewById(R.id.back);
        xtotal = findViewById(R.id.xtotal);
        xtotalprice = findViewById(R.id.xtotalprice);
        xdelivery = findViewById(R.id.xdelivery);
        payment_place = findViewById(R.id.payment_place);
        checkout_place = findViewById(R.id.checkout_place);

        checkout_place.setLayoutManager(new LinearLayoutManager(this));
        list_checkout = new ArrayList<MyCart>();

        payment_place.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_payment = new ArrayList<MyPayment>();

        reference_cart = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("cart");
        reference_cart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer total_harga = 0;
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyCart p = dataSnapshot1.getValue(MyCart.class);
                    Integer total = Integer.valueOf(p.getTotal());
                    total_harga = total_harga + total;
                    p.key = dataSnapshot1.getKey();
                    list_checkout.add(p);
                }
                checkoutAdapter = new CheckoutAdapter(Payment.this, list_checkout);
                checkout_place.setAdapter(checkoutAdapter);
                xtotal.setText(formatRupiah.format(total_harga));
                xdelivery.setText(formatRupiah.format(20000));
                harga_akhir = total_harga + 20000;
                xtotalprice.setText(formatRupiah.format(harga_akhir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference_payment = FirebaseDatabase.getInstance().getReference().child("Payment");
        reference_payment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyPayment p = dataSnapshot1.getValue(MyPayment.class);
                    p.key = dataSnapshot1.getKey();
                    list_payment.add(p);
                }
                paymentAdapter = new PaymentAdapter(Payment.this, list_payment);
                payment_place.setAdapter(paymentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                if(paymentAdapter.getSelected() == null){
                    Toast.makeText(Payment.this, "Please pick a payment method!", Toast.LENGTH_SHORT).show();
                } else {
                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("DD MMM YYYY");

                    reference_order = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("order_history").child(String.valueOf(order_number));
                    reference_order.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reference_order.child("date").setValue(dateFormat.format(calendar.getTime()));
                            reference_order.child("payment").setValue(paymentAdapter.getSelected().getKey());
                            reference_order.child("total").setValue(harga_akhir.toString());
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Intent pay = new Intent(Payment.this,SuccessPayment.class);
                    startActivity(pay);
                }
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}