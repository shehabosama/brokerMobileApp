package com.android.jobber.Ui.fragments.ReviewFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;
import com.android.jobber.common.model.Reviews;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private ReviewInterAction interAction;
    private Context context;
    private List<Reviews> list;


    public ReviewAdapter(Context context, List<Reviews> list, ReviewInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_display_review, parent, false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Reviews reviews = list.get(position);
        holder.rateBar.setRating(0.0f);
        holder.userName.setText(reviews.getUsername());
        holder.reviewDescription.setText(reviews.getReview_description());
        Picasso.with(context)
                .load(Urls.IMAGE_URL+reviews.getUser_image())
                .placeholder(R.drawable.backgroundprof)
                .into(holder.profileImage);
        if(reviews.getRate_degree().equals("-20")){
            holder.rateBar.setRating(0.5f);
        }else if(reviews.getRate_degree().equals("-10")){
            holder.rateBar.setRating(1.0f);
        }else if(reviews.getRate_degree().equals("-5")){
            holder.rateBar.setRating(1.5f);
        }else if(reviews.getRate_degree().equals("0")){
            holder.rateBar.setRating(2.0f);
        }else if(reviews.getRate_degree().equals("10")){
            holder.rateBar.setRating(2.5f);
        }else if(reviews.getRate_degree().equals("20")){
            holder.rateBar.setRating(3.0f);
        }else if(reviews.getRate_degree().equals("30")){
            holder.rateBar.setRating(3.5f);
        }else if(reviews.getRate_degree().equals("40")){
            holder.rateBar.setRating(4.0f);
        }else if(reviews.getRate_degree().equals("50")){
            holder.rateBar.setRating(4.5f);
        }else if(reviews.getRate_degree().equals("60")){
            holder.rateBar.setRating(5.0f);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ReviewInterAction {

    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName,reviewDescription;
        private RatingBar rateBar;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            reviewDescription = itemView.findViewById(R.id.user_description);
            rateBar = itemView.findViewById(R.id.rate_bar);

        }

        public void setListener() {

        }
    }
}
