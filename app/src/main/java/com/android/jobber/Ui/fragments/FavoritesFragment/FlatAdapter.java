package com.android.jobber.Ui.fragments.FavoritesFragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.MyFavorite;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FlatAdapter extends RecyclerView.Adapter<FlatAdapter.FlatsHolder> implements ImageAdapter.ImageAdapterInterAction {


    private List<MyFavorite> myFavorites;
    private Context context;

    private MyFavAdapterInterAction myFavAdapterInterAction;



    public FlatAdapter(List<MyFavorite> myFavorites ,Context context, MyFavAdapterInterAction myFavAdapterInterAction) {

        this.myFavorites = myFavorites;
        this.context = context;
        this.myFavAdapterInterAction = myFavAdapterInterAction;
    }

    @NonNull
    @Override
    public FlatsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_flat_itme,viewGroup,false);
        return new FlatsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FlatsHolder holder, int position) {
        final MyFavorite flat = myFavorites.get(position);
        holder.setListener(flat,position);
        holder.expandIcon.setVisibility(View.GONE);
        holder.getFavorites(flat);
        if(AppPreferences.getBoolean(flat.getId()+holder.userId,context,false)){
            holder.favImage.setImageResource(R.drawable.ic_star_gold);
            Log.e(TAG, "onClick: i am here "+flat.getId()+" "+flat.getFlatLocation()+" "+holder.userId+" "+AppPreferences.getBoolean(flat.getId()+holder.userId,context,false) );


        }else if (!AppPreferences.getBoolean(flat.getId()+holder.userId,context,false)){
            holder.favImage.setImageResource(R.drawable.ic_star_border_whit);
            Log.e(TAG, "onBindViewHolder: "+flat.getFlatLocation()+"  "+false );
        }

        if (myFavorites.isEmpty()){
            myFavAdapterInterAction.onRefrishing();
        }
        Picasso.with(context)
                .load(Urls.IMAGE_URL+flat.getUserImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.name.setText(flat.getUsername());
        holder.descroption.setText(flat.getFlatDscription());
        holder.expandIcon.setVisibility(flat.getFlatDscription().length()>150?View.VISIBLE:View.INVISIBLE);
        holder.date.setText(flat.getPublishDate());
        holder.priceOrder.setText("Price :"+flat.getFlatPrice());
        holder.location.setText(flat.getFlatLocation());
        ImageAdapter imageAdapter = new ImageAdapter(flat.getFlatImage(),context,this);
        holder.recyclerView.setAdapter(imageAdapter);



    }



    @Override
    public int getItemCount() {
        return myFavorites.size();
    }

    @Override
    public void onClickPhoto(FlatsImage flatsImage) {
        myFavAdapterInterAction.onClickPhoto(flatsImage);
    }

    interface MyFavAdapterInterAction {
        void onRefrishing();

         void onClickFavorite(MyFavorite car);
         void onClickMenu(MyFavorite flat);
         void onClickUnFavorite(MyFavorite car);
         void onClickPhoto(FlatsImage flatsImage);

        void onClickItem(MyFavorite flat);
    }
    public class FlatsHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView name,date,location,descroption,priceOrder;

        CircleImageView imageView;
        ImageView menu,expandIcon,favImage;
        RecyclerView recyclerView;
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
            location = itemView.findViewById(R.id.location_flat_item);
            favImage = itemView.findViewById(R.id.fav_icon);
            priceOrder = itemView.findViewById(R.id.price_flat_item);
            menu = itemView.findViewById(R.id.menu_flat_item);
            imageView = itemView.findViewById(R.id.imageview);
            recyclerView = itemView.findViewById(R.id.recycler_image_item);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            userId = AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,context,"0");


        }
        public void getFavorites(MyFavorite flat){
            for(int i =0 ;i<myFavorites.size();i++){
                // Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId() );
                if(myFavorites.get(i).getId().equals(flat.getId())){
                   // AppPreferences.setBoolean(flat.getId()+userId,true,context);

                        favImage.setImageResource(R.drawable.ic_star_gold);
                        toggleCheckFav = false;


                   /* if(toggleCheckFav){
                        favImage.setImageResource(R.drawable.ic_star_gold);
                        toggleCheckFav = false;
                        Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getId()+" || "+flat.getId() +" || "+toggleCheckFav );
                    }else{
                        favImage.setImageResource(R.drawable.ic_star_gold);
                        toggleCheckFav = false ;
                        Log.e(TAG, "onBindViewHolder2: "+myFavorites.get(i).getId()+" || "+flat.getId() +" || "+toggleCheckFav );

                    }*/

                }else{
//                    if(!toggleCheck){
//                        toggleCheck = true;
//                        Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId()+" || "+car.getCarId() +" || "+toggleCheck );
//                    }else{
//                        toggleCheck = false;
//                    }

                }
            }
        }

        public void setListener(final MyFavorite flat, final int position){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myFavAdapterInterAction.onClickItem(flat);
                }
            });
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myFavAdapterInterAction.onClickMenu(flat);
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

            favImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(toggleCheckFav){
                        if(myFavAdapterInterAction !=null)
                        {
                            AppPreferences.setBoolean(flat.getId()+userId,true,context);
                            favImage.setImageResource(R.drawable.ic_star_gold);
                            myFavAdapterInterAction.onClickFavorite(flat);
                            toggleCheckFav = false;
                        }
                    }else {
                        if (myFavAdapterInterAction != null) {
                            AppPreferences.setBoolean(flat.getId()+userId,false,context);
                            Log.e(TAG, "onClick: i am here "+flat.getId()+" "+flat.getFlatLocation()+" "+userId+" "+AppPreferences.getBoolean(flat.getId()+userId,context,false) );
                            favImage.setImageResource(R.drawable.ic_star_border_whit);
                            myFavAdapterInterAction.onClickUnFavorite(flat);
                            toggleCheckFav=true;

                        }
                    }
                }
            });

    }
    }
}