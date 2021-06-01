package viercimi.enpy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.collection.LLRBBlackValueNode;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyPayment> myPayment;
    private int checkedPosition = 0;

    public PaymentAdapter(Context c, ArrayList<MyPayment> p){
        context = c;
        myPayment = p;
        notifyDataSetChanged();
    }

    public void setSize(ArrayList<MyPayment> payment){
        myPayment = new ArrayList<>();
        myPayment = payment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(myPayment.get(position));
        Glide.with(context).load(myPayment.get(position).getUrl_photo_payment()).into(holder.xurl_photo_payment);
    }

    @Override
    public int getItemCount() {
        return myPayment.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout xbackground;
        ImageView xurl_photo_payment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xbackground = itemView.findViewById(R.id.xbackground);
            xurl_photo_payment = itemView.findViewById(R.id.xurl_photo_payment);
        }
        void bind(final MyPayment payment){
            if(checkedPosition == -1){
                xbackground.setBackground(context.getResources().getDrawable(R.drawable.btn_line_pink));
            } else {
                if(checkedPosition == getAdapterPosition()){
                    xbackground.setBackground(context.getResources().getDrawable(R.drawable.btn_pink));
                } else {
                    xbackground.setBackground(context.getResources().getDrawable(R.drawable.btn_line_pink));
                }
            }

            xbackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xbackground.setBackground(context.getResources().getDrawable(R.drawable.btn_pink));
                    if(checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public MyPayment getSelected() {
        if(checkedPosition != -1){
            return myPayment.get(checkedPosition);
        }
        return null;
    }

}
