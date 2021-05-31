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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyCart> myCart;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public CartAdapter(Context c, ArrayList<MyCart> p){
        context = c;
        myCart = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(myCart.get(position).getUrl_photo_product()).into(holder.xurl_photo_product);

        holder.xproduct_name.setText(myCart.get(position).getProduct_name());
        holder.xsize.setText(myCart.get(position).getSize());
        holder.xprice.setText(formatRupiah.format(Integer.parseInt(myCart.get(position).getPrice())));
        holder.xquantity.setText(myCart.get(position).getQuantity());
        holder.xcolor.setBackgroundColor(Color.parseColor(myCart.get(position).getColor()));

        String getCart_id = myCart.get(position).getKey();
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoproductdetail = new Intent(context, ProductDetail.class);
                gotoproductdetail.putExtra("product_id", getProduct_id);
                gotoproductdetail.putExtra("category_id", getCategory_id);
                context.startActivity(gotoproductdetail);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return myCart.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView xurl_photo_product, xcolor;
        TextView xproduct_name, xsize, xprice, xquantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xurl_photo_product = itemView.findViewById(R.id.xurl_photo_product);
            xcolor = itemView.findViewById(R.id.xcolor);
            xproduct_name = itemView.findViewById(R.id.xproduct_name);
            xsize = itemView.findViewById(R.id.xsize);
            xprice = itemView.findViewById(R.id.xprice);
            xquantity = itemView.findViewById(R.id.xquantity);
        }
    }

}
