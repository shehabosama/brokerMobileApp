package com.android.jobber.Ui.fragments.TimeLineFragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.Ui.fragments.PublishingFragment.MyCustomeAdapter;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.model.Listitem;

import java.util.ArrayList;

public class CustomeDialogFilterTimeLine {




    Activity activity;
    private EditText editTextPriceForm,editTextPriceTo;
    private RadioGroup radioGroup,radioGroup2;
    String productType = "";
    String typeOwner = "";
    private Spinner spin_search;
    private String countryItem;
    private TextView btnUpload,btnClose;
    Dialog dialog;

    public CustomeDialogFilterTimeLine(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog(final Activity activity, final PresenterTimeLine presenter){
        this.activity = activity;
       // dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge_filter);
        dialog.show();

        final MyCustomeAdapter myCustomeAdapter3= new MyCustomeAdapter(activity,getAllCoutry());
        editTextPriceForm =dialog.findViewById(R.id.txt_price_from);
        editTextPriceTo =dialog.findViewById(R.id.txt_price_to);
        btnUpload = dialog.findViewById(R.id.btn_filter);
        btnClose = dialog.findViewById(R.id.close);
        radioGroup2 =dialog.findViewById(R.id.radio_group_owner);
        radioGroup =dialog.findViewById(R.id.radio_group_type);

        spin_search =dialog.findViewById(R.id.spin_search);
        spin_search.setAdapter(myCustomeAdapter3);

        spin_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0).findViewById(R.id.textname)) .setTextColor(Color.BLACK); /* if you want your countryItem to be white */
                if(i !=0) {
                    countryItem = adapterView.getSelectedItem().toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        productType = "1";
                        break;
                    case 1:
                        productType = "2";
                        break;
                    case 2:
                        productType = "";
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        typeOwner = "1";
                        break;
                    case 1:
                        typeOwner = "2";
                        break;
                    case 2:
                        typeOwner = "";
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             presenter.performFilterProducts(productType,editTextPriceForm.getText().toString(),
                     editTextPriceTo.getText().toString(),
                     typeOwner,
                     countryItem,
                     AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,activity,"0"));
             productType="";
             typeOwner="";


            }
        });
    }
    public void hideDialog(){
        dialog.dismiss();
    }
    public ArrayList<Listitem> getAllCoutry(){
        final ArrayList<Listitem> listitems=new ArrayList<Listitem>();
        listitems.add(new Listitem("Select Governorate"));
        listitems.add(new Listitem("Cairo"));
        listitems.add(new Listitem("Alexandria"));
        listitems.add(new Listitem("Sharqia"));
        listitems.add(new Listitem("Asyut"));
        listitems.add(new Listitem("Beheira"));
        listitems.add(new Listitem("Beni Suef"));
        listitems.add(new Listitem("Dakahlia"));
        listitems.add(new Listitem("Damietta"));
        listitems.add(new Listitem("Faiyum"));
        listitems.add(new Listitem("Gharbia"));
        listitems.add(new Listitem("Giza"));
        listitems.add(new Listitem("Ismailia"));
        listitems.add(new Listitem("Kafr El Sheikh"));
        listitems.add(new Listitem("Luxor"));
        listitems.add(new Listitem("Matruh"));
        listitems.add(new Listitem("Minya"));
        listitems.add(new Listitem("Monufia"));
        listitems.add(new Listitem("New Valley"));
        listitems.add(new Listitem("North Sinai"));
        listitems.add(new Listitem("Port Said"));
        listitems.add(new Listitem("Qalyubia"));
        listitems.add(new Listitem("Qena"));
        listitems.add(new Listitem("Red Sea"));
        listitems.add(new Listitem("Sohag"));
        listitems.add(new Listitem("South Sinai"));
        listitems.add(new Listitem("Suez"));
        return listitems;
    }
}

