package viercimi.enpy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyColor> myColor;

    private int checkedPosition = 0;

    public ColorAdapter(Context c, ArrayList<MyColor> p){
        context = c;
        myColor = p;
    }

    public void setColor(ArrayList<MyColor> color){
        myColor = new ArrayList<>();
        myColor = color;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_color, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(myColor.get(position));
        holder.xcolor.setBackgroundColor(Color.parseColor(myColor.get(position).getChart()));
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

        void bind(final MyColor color){
            if(checkedPosition == -1){
                xcolor.setImageResource(android.R.color.transparent);
            } else {
                if(checkedPosition == getAdapterPosition()){
                    xcolor.setImageDrawable(context.getResources().getDrawable(R.drawable.check, context.getTheme()));
                } else {
                    xcolor.setImageResource(android.R.color.transparent);
                }
            }

            xcolor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xcolor.setImageDrawable(context.getResources().getDrawable(R.drawable.check, context.getTheme()));
                    if(checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public MyColor getSelected() {
        if(checkedPosition != -1){
            return myColor.get(checkedPosition);
        }
        return null;
    }

}
