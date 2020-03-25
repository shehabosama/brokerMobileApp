package com.android.jobber.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;

import java.util.List;

public class PhotoSelectedAdapter extends RecyclerView.Adapter<PhotoSelectedAdapter.ServiceHoder> {

private List<Uri> imageData;
private Context context;

public PhotoSelectedAdapter(List<Uri> imageData,  Context context) {
        this.imageData = imageData;
        this.context = context;
        }

@NonNull
@Override
public ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_selected_photo_itme,viewGroup,false);
        return new ServiceHoder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ServiceHoder holder, int position) {

        holder.imageView.setImageURI(imageData.get(position));
    }



@Override
public int getItemCount() {
        return imageData.size();
        }

public class ServiceHoder extends RecyclerView.ViewHolder {
    ImageView imageView;


    public ServiceHoder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);

    }
}
}
