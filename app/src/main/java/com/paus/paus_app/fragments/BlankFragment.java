package com.paus.paus_app.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paus.paus_app.Dialogs.Custome_dialogs_group;
import com.paus.paus_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    String name="";
    String number = "";

    private static final int pickContact =1;
   // AlertDialog alertDialog=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_blank, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.float_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        getContact(view);


            }
        });



      //  textView = view.findViewById(R.id.text_phone);

        return view;
    }
    public void getContact(View view){

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 10);


//
        }else
        {
            Intent contactsIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactsIntent, this.pickContact);
        }


    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == this.pickContact){
            if (resultCode == Activity.RESULT_OK) {
                Log.d("ContactsH", "ResOK");
                Uri contactData = data.getData();
                Cursor contact =  getActivity().getContentResolver().query(contactData, null, null, null, null);

                if (contact.moveToFirst()) {
                      name = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    // TODO Whatever you want to do with the selected contact's name.

                    ContentResolver cr = getActivity().getContentResolver();
                    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                            "DISPLAY_NAME = '" + name + "'", null, null);
                    if (cursor.moveToFirst()) {
                        String contactId =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //
                        //  Get all phone numbers.
                        //
                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    // do something with the Home number here...
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    // do something with the Mobile number here...
                                    Log.d("ContactsH", number);


                                    if (TextUtils.isEmpty(name)||TextUtils.isEmpty(number))
                                    {
                                        Toast.makeText(getActivity(), "pleases select contact", Toast.LENGTH_SHORT).show();
                                    }else
                                    {
                                        Custome_dialogs_group custome_dialogs_group =new  Custome_dialogs_group();
                                        custome_dialogs_group.showDialog(getActivity(),name,number);
                                    }


                                    /*
                                      alertDialog =new AlertDialog.Builder(getActivity())
                                            .setMessage("are you sure send the invitation to"+"\n"+name+"\n"+number)
                                            .setCancelable(false)
                                            .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                                public void onClick(DialogInterface dialog, int id) {

                                                    alertDialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alertDialog.dismiss();
                                                    return;

                                                }
                                            })
                                            .show();*/





                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    // do something with the Work number here...
                                    break;
                            }
                        }
                        phones.close();
                    }
                    cursor.close();
                }
            }
        }else{
            Log.d("ContactsH", "Canceled");
        }
    }



}
