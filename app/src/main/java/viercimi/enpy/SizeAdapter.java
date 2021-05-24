package viercimi.enpy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {

    boolean isclick = false;
    Context context;
    ArrayList<MySizeColor> mySize;

    public SizeAdapter(Context c, ArrayList<MySizeColor> p){
        context = c;
        mySize = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_size, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.xsize.setText(mySize.get(position).getChart());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isclick) {
                    holder.xsize.setBackgroundColor(context.getResources().getColor(R.color.pink_enpy));
                    isclick = false;
                } else {
                    holder.xsize.setBackgroundColor(context.getResources().getColor(R.color.lightgray_enpy));
                    isclick = true;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mySize.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xsize;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xsize = itemView.findViewById(R.id.xsize);
        }
    }

}
