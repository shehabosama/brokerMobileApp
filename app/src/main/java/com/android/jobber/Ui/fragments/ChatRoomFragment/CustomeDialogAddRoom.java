package com.android.jobber.Ui.fragments.ChatRoomFragment;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.model.ChatModel.ChatRoom;

public class CustomeDialogAddRoom {




    Activity activity;
    private EditText editTextRoomName, editTextRoomDescription;


    private TextView btnCancel,btnUpdate;



    public void showDialog(final Activity activity, final PresenterChatRoom presenter){
        this.activity = activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge_add_room);
        dialog.show();

        editTextRoomDescription = dialog.findViewById(R.id.txt_name);
        editTextRoomName = dialog.findViewById(R.id.txt_description);
        btnCancel = dialog.findViewById(R.id.close);
        btnUpdate = dialog.findViewById(R.id.update);





        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextRoomDescription.getText().toString())||TextUtils.isEmpty(editTextRoomName.getText().toString())) {
                    Message.message(activity,"please fill all the fields");
                }else{
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.room_name = editTextRoomName.getText().toString();
                    chatRoom.room_desc = editTextRoomDescription.getText().toString();
                    presenter.performAddChatRoom(chatRoom);
                }

            }
        });




    }

}

