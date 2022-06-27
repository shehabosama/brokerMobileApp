package com.android.jobber.Ui.fragments.PrdouctRequestsFragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.LastseenTime;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.ProductRequest;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class ProductRequestAdapter extends RecyclerView.Adapter<ProductRequestAdapter.FlatsHolder> implements ImageAdapter.ImageAdapterInterAction {

    private List<ProductRequest> productRequests;

    private Context context;

    private FlatAdapterInterAction flatAdapterInterAction;



    public ProductRequestAdapter(List<ProductRequest> productRequests, Context context, FlatAdapterInterAction flatAdapterInterAction) {
        this.productRequests = productRequests;
        this.context = context;
        this.flatAdapterInterAction = flatAdapterInterAction;
    }

    @NonNull
    @Override
    public FlatsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_request_itme,viewGroup,false);
        return new FlatsHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull FlatsHolder holder) {
        Resources res = context.getResources();
        holder.requestStatus.setTextColor(Color.BLACK);
        holder.requestStatus.setBackgroundResource(R.drawable.button_pending);
        holder.requestStatus.setText(res.getString(R.string.pinding));

        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull final FlatsHolder holder, int position) {
        final ProductRequest flat = productRequests.get(position);
        holder.setListener(flat);
        holder.expandIcon.setVisibility(View.GONE);
        if (productRequests.isEmpty()){
            flatAdapterInterAction.onRefrishing();
        }
        Picasso.with(context)
                .load(Urls.IMAGE_URL+flat.getUserImage())
                .placeholder(R.drawable.backgroundprof)
                .into(holder.imageView);
        Picasso.with(context)
                .load(Urls.IMAGE_URL+flat.getSender_user_image())
                .placeholder(R.drawable.backgroundprof)
                .into(holder.senderImageView);
        holder.senderName.setText(flat.getSender_user_name());
        holder.name.setText(flat.getUsername());
        holder.descroption.setText(flat.getFlatDscription());
        holder.expandIcon.setVisibility(flat.getFlatDscription().length()>150?View.VISIBLE:View.INVISIBLE);
        holder.date.setText(flat.getPublishDate());
        holder.location.setText(flat.getFlatLocation());
        Resources res = context.getResources();
        holder.priceOrder.setText(res.getString(R.string.price)+": "+flat.getFlatPrice());
        ImageAdapter imageAdapter = new ImageAdapter(flat.getFlatsImage(),context,this);
        holder.recyclerView.setAdapter(imageAdapter);
        final LastseenTime getTime=new LastseenTime();
        long lastseen=Long.parseLong(flat.getTimestamp());
        String lastseenDisplayTime=getTime.getDisplayTimeAgo(lastseen,context).toString();
        holder.request_time_order.setText(lastseenDisplayTime);


        if(flat.getConfirmation().equals("0")){
            holder.requestStatus.setTextColor(Color.BLACK);
            holder.requestStatus.setBackgroundResource(R.drawable.button_pending);
            holder.requestStatus.setText(res.getString(R.string.pinding));

        }else if(flat.getConfirmation().equals("1")){
            holder.requestStatus.setTextColor(Color.WHITE);
            holder.requestStatus.setBackgroundResource(R.drawable.button_accept);
            holder.requestStatus.setText(res.getString(R.string.accepted));

        }else{
            holder.requestStatus.setTextColor(Color.WHITE);
            holder.requestStatus.setBackgroundResource(R.drawable.button_reject);
            holder.requestStatus.setText(res.getString(R.string.rejected));
        }
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,context,false)){

            if(AppPreferences.getBoolean(flat.getId()+holder.userId,context,false)){
                holder.btnReject.setVisibility(View.GONE);
                holder.btnAccept.setVisibility(View.GONE);
            }else{
                holder.btnReject.setVisibility(View.VISIBLE);
                holder.btnAccept.setVisibility(View.VISIBLE);
            }
        }else{
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return productRequests.size();
    }

    @Override
    public void onClickPhoto(FlatsImage flatsImage) {
        flatAdapterInterAction.onClickPhoto(flatsImage);
    }

    interface FlatAdapterInterAction {
        void onRefrishing();

         void onClickFavorite(ProductRequest product);
         void onClickMenu(ProductRequest product);
         void onClickUnFavorite(ProductRequest product);
         void onClickPhoto(FlatsImage flatsImage);

        void onClickItem(ProductRequest product);

        void onClickRejectRequest(ProductRequest product);

        void onClickAcceptRequest(ProductRequest product);
    }
    public class FlatsHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView name,date,location,descroption,senderName,requestStatus,request_time_order,priceOrder;
        CircleImageView imageView,senderImageView;
        ImageView menu,expandIcon;
        RecyclerView recyclerView;
        Button btnAccept,btnReject;
        boolean checkExpanText=true;
        boolean toggleCheckFav = true;
        String userId;
      //  ExpandableTextView expTv1;

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display

        public FlatsHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            name = itemView.findViewById(R.id.username_flat_item);
            expandIcon = itemView.findViewById(R.id.expand_icon);
            descroption = itemView.findViewById(R.id.description_flat_item);
            date = itemView.findViewById(R.id.date_flat_item);
            request_time_order = itemView.findViewById(R.id.request_time_order);
            location = itemView.findViewById(R.id.location_flat_item);
            priceOrder = itemView.findViewById(R.id.price_flat_item);
            menu = itemView.findViewById(R.id.menu_flat_item);
            imageView = itemView.findViewById(R.id.imageview);
            recyclerView = itemView.findViewById(R.id.recycler_image_item);
            senderName = itemView.findViewById(R.id.sender_username);
            senderImageView = itemView.findViewById(R.id.sender_image);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnReject = itemView.findViewById(R.id.btn_reject);
            requestStatus = itemView.findViewById(R.id.request_status);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            userId = AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,context,"0");




        }


        public void setListener(final ProductRequest product){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flatAdapterInterAction.onClickItem(product);
                }
            });
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flatAdapterInterAction.onClickMenu(product);
                }
            });
            descroption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkExpanText){
                        ObjectAnimator animation = ObjectAnimator.ofInt(
                                descroption,
                                "maxLines",
                                25);
                        animation.setDuration(400);
                        animation.start();
                        descroption.setEllipsize(null);
                        checkExpanText = false;
                    }else{
                        descroption.setMaxLines(2);
                        checkExpanText=true;
                    }

                }
            });

            expandIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkExpanText){
                        ObjectAnimator animation = ObjectAnimator.ofInt(
                                descroption,
                                "maxLines",
                                25);
                        animation.setDuration(400);
                        animation.start();
                        descroption.setEllipsize(null);
                        checkExpanText = false;
                    }else{
                        descroption.setMaxLines(2);
                        checkExpanText=true;
                    }
                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppPreferences.setBoolean(product.getId()+userId,true,context);
                    if(flatAdapterInterAction !=null)
                        flatAdapterInterAction.onClickRejectRequest(product);
                }
            });
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppPreferences.setBoolean(product.getId()+userId,true,context);
                    if(flatAdapterInterAction !=null)
                        flatAdapterInterAction.onClickAcceptRequest(product);
                }
            });



    }
    }
}
