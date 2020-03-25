package com.android.jobber.Ui.Activities.ChatActivity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.model.ChatModel.Messages;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class _Adapter_Messageing extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Messages> messages_list;
    private Context context;

    myDbAdapter helper;
    public _Adapter_Messageing(List<Messages> messages, myDbAdapter helper,Context context) {
        this.messages_list = messages;
        this.context = context;
        this.helper = helper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Constants.Message_type.SENT_TEXT) {
            return new SentTextHolder(LayoutInflater.from(context).inflate(R.layout.row_sent_message_text, viewGroup, false));
        } else if (i == Constants.Message_type.SENT_IMAGE) {
            return new SentImageHolder(LayoutInflater.from(context).inflate(R.layout.row_sent_message_img, viewGroup, false));
        } else if (i == Constants.Message_type.RECEIVED_TEXT) {
            return new ReceivedTextHolder(LayoutInflater.from(context).inflate(R.layout.row_received_message_text, viewGroup, false));
        } else if (i == Constants.Message_type.RECEIVED_IMAGE) {
            return new ReceivedImageHolder(LayoutInflater.from(context).inflate(R.layout.row_received_message_img, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int type = getItemViewType(i);
        Messages message = messages_list.get(i);
        /**
         * check message type and init holder to user it and set data in the right place for every view
         */
        if (type == Constants.Message_type.SENT_TEXT) {
            SentTextHolder holder = (SentTextHolder) viewHolder;
            holder.tvTime.setText(message.getTimestamp());
            holder.tvMessageContent.setText(message.getContent());

        } else if (type == Constants.Message_type.SENT_IMAGE) {
            SentImageHolder holder = (SentImageHolder) viewHolder;
            holder.tvTime.setText(message.getTimestamp());
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                }
            });
            builder.build().load(Urls.IMAGE_URL + message.getContent()).into(holder.imgMsg);


        } else if (type == Constants.Message_type.RECEIVED_TEXT) {
            ReceivedTextHolder holder = (ReceivedTextHolder) viewHolder;
            holder.tvTime.setText(message.getTimestamp());
            holder.tvUsername.setText(message.getUser_name());
            holder.tvMessageContent.setText(message.getContent());


        } else if (type == Constants.Message_type.RECEIVED_IMAGE) {
            ReceivedImageHolder holder = (ReceivedImageHolder) viewHolder;
            holder.tvTime.setText(message.getTimestamp());
            holder.tvUsername.setText(message.getUser_name());
           // Picasso.with(context).load().into(holder.imgMsg);

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                }
            });
            builder.build().load(Urls.IMAGE_URL + message.getContent())
                    .into(holder.imgMsg);

        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int userID = Integer.parseInt(helper.getEmployeeName("user_id"));
        Messages messages = messages_list.get(position);
        if (userID == Integer.parseInt(messages.getUser_id()))
        {
            if(messages.getType().equals("1"))
            {
                return Constants.Message_type.SENT_TEXT;
            }else if(messages.getType().equals("2"))
            {
                return Constants.Message_type.SENT_IMAGE;
            }
        }else
        {
            if(messages.getType().equals("1"))
            {
                return Constants.Message_type.RECEIVED_TEXT;
            }else if(messages.getType().equals("2"))
            {
                return Constants.Message_type.RECEIVED_IMAGE;
            }
        }
        return super.getItemViewType(position);
    }
    // sent message holders
    class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public SentMessageHolder(View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    // sent message with type text
    class SentTextHolder extends SentMessageHolder {
        TextView tvMessageContent;

        public SentTextHolder(View itemView) {
            super(itemView);

            tvMessageContent = itemView.findViewById(R.id.tv_message_content);
        }
    }

    // sent message with type image
    class SentImageHolder extends SentMessageHolder {
        ImageView imgMsg;

        public SentImageHolder(View itemView) {
            super(itemView);

            imgMsg =itemView.findViewById(R.id.img_msg);
        }
    }

    // received message holders
    class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    // received message with type text
    class ReceivedTextHolder extends ReceivedMessageHolder {
        TextView tvMessageContent;

        public ReceivedTextHolder(View itemView) {
            super(itemView);

            tvMessageContent = itemView.findViewById(R.id.tv_message_content);
        }

    }

    // received message with type image

    class ReceivedImageHolder extends ReceivedMessageHolder {
        ImageView imgMsg;

        public ReceivedImageHolder(View itemView) {
            super(itemView);

            imgMsg = itemView.findViewById(R.id.img_msg);
        }
    }



}
