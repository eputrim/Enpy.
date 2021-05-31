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

    Context context;
    ArrayList<MySize> mySize;

    private int checkedPosition = 0;

    public SizeAdapter(Context c, ArrayList<MySize> p){
        context = c;
        mySize = p;
    }

    public void setSize(ArrayList<MySize> size){
        mySize = new ArrayList<>();
        mySize = size;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_size, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mySize.get(position));
        holder.xsize.setText(mySize.get(position).getChart());
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

        void bind(final MySize size){
            if(checkedPosition == -1){
                xsize.setBackgroundColor(context.getResources().getColor(R.color.lightgray_enpy));
            } else {
                if(checkedPosition == getAdapterPosition()){
                    xsize.setBackgroundColor(context.getResources().getColor(R.color.pink_enpy));
                } else {
                    xsize.setBackgroundColor(context.getResources().getColor(R.color.lightgray_enpy));
                }
            }

            xsize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xsize.setBackgroundColor(context.getResources().getColor(R.color.pink_enpy));
                    if(checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public MySize getSelected() {
        if(checkedPosition != -1){
            return mySize.get(checkedPosition);
        }
        return null;
    }

}
