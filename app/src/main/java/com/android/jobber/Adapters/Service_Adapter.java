package com.android.jobber.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;

public class  Service_Adapter extends RecyclerView.Adapter<Service_Adapter.ServiceHolder> {

    private int[] imageData;
    private String[] mDate;
    private Context context;

    public Service_Adapter(int[] imageData, String[] mDate, Context context) {
        this.imageData = imageData;
        this.mDate = mDate;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.custome_service_itme,viewGroup,false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder serviceHolder, int i) {

        serviceHolder.textView.setText(mDate[i]);
        serviceHolder.imageView.setImageResource(imageData[i]);
    }

    @Override
    public int getItemCount() {
        return mDate.length;
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
