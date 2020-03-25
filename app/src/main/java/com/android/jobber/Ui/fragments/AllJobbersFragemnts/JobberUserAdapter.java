package com.android.jobber.Ui.fragments.AllJobbersFragemnts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;
import com.android.jobber.common.model.JobberUsers;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobberUserAdapter extends RecyclerView.Adapter<JobberUserAdapter.JobberUserViewHolder> {
    private JobberUserInterAction interAction;
    private Context context;
    private List<JobberUsers> list;

    public JobberUserAdapter(Context context, List<JobberUsers> list, JobberUserInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public JobberUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_display_jobber_users, parent, false);

        return new JobberUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobberUserViewHolder holder, int position) {
        JobberUsers jobberUsers = list.get(position);
        holder.userName.setText(jobberUsers.getUser_name());
        holder.email.setText(jobberUsers.getEmail());
        holder.phone.setText(jobberUsers.getPhone_no());
        holder.address.setText(jobberUsers.getAddress());
        Picasso.with(context)
                .load(Urls.IMAGE_URL+jobberUsers.getUser_image())
                .placeholder(R.drawable.backgroundprof)
                .into(holder.profileImage);

        holder.setListener(jobberUsers);
        holder.rateBar.setRating(0.0f);
        if (jobberUsers.getRate()>60){
            holder.rateBar.setRating(1.0f);
        }if (jobberUsers.getRate()>120){
            holder.rateBar.setRating(2.0f);
        } if (jobberUsers.getRate()>180){
            holder.rateBar.setRating(3.0f);
        } if (jobberUsers.getRate()>240){
            holder.rateBar.setRating(3.0f);
        } if (jobberUsers.getRate()>320){
            holder.rateBar.setRating(4.0f);
        } if (jobberUsers.getRate()>380){
            holder.rateBar.setRating(5.0f);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface JobberUserInterAction {

        void onClickItem(JobberUsers jobberUsers);
    }

    public class JobberUserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName,email,phone,address;
        private RatingBar rateBar;

        public JobberUserViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);
            phone = itemView.findViewById(R.id.user_phone);
            address = itemView.findViewById(R.id.user_address);
            rateBar = itemView.findViewById(R.id.rate_bar);
        }

        public void setListener(final JobberUsers jobberUsers) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(interAction!=null)
                        interAction.onClickItem(jobberUsers);
                }
            });
        }
    }
}
