package viercimi.enpy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyCart> myCart;

    public CheckoutAdapter(Context c, ArrayList<MyCart> p){
        context = c;
        myCart = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_checkout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xproduct_name.setText(myCart.get(position).getProduct_name());
        holder.xquantity.setText(myCart.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return myCart.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xproduct_name, xquantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xproduct_name = itemView.findViewById(R.id.xproduct_name);
            xquantity = itemView.findViewById(R.id.xquantity);
        }
    }

}
