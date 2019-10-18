package com.paus.paus_app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paus.paus_app.R;

public class Service_Adapter extends RecyclerView.Adapter<Service_Adapter.ServiceHoder> {

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
    public ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.custome_service_itme,viewGroup,false);
        return new ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Service_Adapter.ServiceHoder serviceHoder, int i) {

        serviceHoder.textView.setText(mDate[i]);
        serviceHoder.imageView.setImageResource(imageData[i]);
    }

    @Override
    public int getItemCount() {
        return mDate.length;
    }

    public class ServiceHoder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
