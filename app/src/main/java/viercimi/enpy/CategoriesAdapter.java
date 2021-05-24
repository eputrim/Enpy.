package viercimi.enpy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyCategories> myCategories;

    public CategoriesAdapter(Context c, ArrayList<MyCategories> p){
        context = c;
        myCategories = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String getCategories_id = myCategories.get(position).getKey();
        holder.btn_kategori.setText(getCategories_id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoproductcategories = new Intent(context, ProductCategory.class);
                gotoproductcategories.putExtra("category_id", getCategories_id);
                context.startActivity(gotoproductcategories);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCategories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView btn_kategori;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_kategori = itemView.findViewById(R.id.btn_kategori);
        }
    }

}
