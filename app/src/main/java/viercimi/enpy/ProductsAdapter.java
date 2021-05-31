package viercimi.enpy;

import android.content.Context;
import android.content.Intent;
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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyProducts> myProducts;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ProductsAdapter(Context c, ArrayList<MyProducts> p){
        context = c;
        myProducts = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xproduct_name.setText(myProducts.get(position).getProduct_name());
        holder.xprice.setText(formatRupiah.format(Integer.parseInt(myProducts.get(position).getPrice())));
        Glide.with(context).load(myProducts.get(position).getUrl_photo_product()).into(holder.xurl_photo_product);

        String getProduct_id = myProducts.get(position).getKey();
        String getCategory_id = myProducts.get(position).getCategory_id();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoproductdetail = new Intent(context, ProductDetail.class);
                gotoproductdetail.putExtra("product_id", getProduct_id);
                gotoproductdetail.putExtra("category_id", getCategory_id);
                context.startActivity(gotoproductdetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myProducts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView xurl_photo_product;
        TextView xproduct_name, xprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xurl_photo_product = itemView.findViewById(R.id.xurl_photo_product);
            xproduct_name = itemView.findViewById(R.id.xproduct_name);
            xprice = itemView.findViewById(R.id.xprice);
        }
    }

}
