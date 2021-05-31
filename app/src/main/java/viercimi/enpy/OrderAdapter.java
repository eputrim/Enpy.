package viercimi.enpy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyOrder> myOrder;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public OrderAdapter(Context c, ArrayList<MyOrder> p){
        context = c;
        myOrder = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xtotal.setText(formatRupiah.format(Integer.parseInt(myOrder.get(position).getTotal())));
        holder.xdate.setText(myOrder.get(position).getDate());
        holder.xpayment.setText(myOrder.get(position).getPayment());
    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xtotal, xdate, xpayment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xtotal = itemView.findViewById(R.id.xtotal);
            xdate = itemView.findViewById(R.id.xdate);
            xpayment = itemView.findViewById(R.id.xpayment);
        }
    }

}
