package com.android.jobber.Ui.fragments.JobberAccountFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ServiceHoder> {

    private List<FlatsImage> flatsImages;
    private Context context;
    private ImageAdapterInterAction imageAdapterInterAction;

    public ImageAdapter(List<FlatsImage> car_images, Context context,ImageAdapterInterAction imageAdapterInterAction) {
        this.flatsImages = car_images;
        this.context = context;
        this.imageAdapterInterAction = imageAdapterInterAction;
    }

    @NonNull
    @Override
    public ImageAdapter.ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_image_item, viewGroup, false);
        return new ImageAdapter.ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ServiceHoder holder, int position) {

        FlatsImage flatsImage = flatsImages.get(position);
        Picasso.with(context)
                .load(Urls.IMAGE_URL+flatsImage.getImageName())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.setLitener(flatsImage);


    }


    public interface ImageAdapterInterAction{
        void onClickPhoto(FlatsImage flatsImage);
    }
    @Override
    public int getItemCount() {
        return flatsImages.size();
    }

    public class ServiceHoder extends RecyclerView.ViewHolder {
        private ImageView imageView;



        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);


        }
        public void setLitener(final FlatsImage flatsImage){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(imageAdapterInterAction!=null){
                        imageAdapterInterAction.onClickPhoto(flatsImage);
                    }
                }
            });
        }
    }
}