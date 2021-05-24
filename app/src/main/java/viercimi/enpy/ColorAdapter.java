package viercimi.enpy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    boolean isclick = false;
    Context context;
    ArrayList<MySizeColor> myColor;

    public ColorAdapter(Context c, ArrayList<MySizeColor> p){
        context = c;
        myColor = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_color, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.xcolor.setBackgroundColor(Color.parseColor(myColor.get(position).getChart()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isclick) {
                    holder.xcolor.setImageDrawable(context.getResources().getDrawable(R.drawable.check, context.getTheme()));
                    isclick = false;
                } else {
                    holder.xcolor.setImageResource(android.R.color.transparent);
                    isclick = true;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myColor.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView xcolor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xcolor = itemView.findViewById(R.id.xcolor);
        }
    }

}
