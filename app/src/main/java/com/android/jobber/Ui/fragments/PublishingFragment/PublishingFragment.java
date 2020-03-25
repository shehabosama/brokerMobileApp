package com.android.jobber.Ui.fragments.PublishingFragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.Adapters.PhotoSelectedAdapter;
import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.CompressPhotos;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.Listitem;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.jaiselrahman.filepicker.utils.FilePickerProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishingFragment extends BaseFragment implements PublishingContract.View,PublishingContract.Model.onFinishedListener{
    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";
    private static final int FILE_REQUEST_CODE = 100;
    private static final int PICK_FROM_GALLERY = 101;
    private static final int CAMERA_REQUEST_CODE=1001;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;
    private ImageView selectFromGallery,captureCamera,imageSpinIcon;
    private SimpleDateFormat TimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    private File lastCapturedFile;
    private RecyclerView recyclerView;
    private EditText editText,editTextPrice;
    private PresenterPublish presenter;
    private ArrayList<Uri> uri_photo;
    private ProgressDialog progressDialog;
    private Spinner spin_search;
    private Button btnLocation;
    int flatType=0,productType =0;
    private RadioButton radioBtnRent,radioBtnConv;
    private String countryItem;
    private RadioGroup radioGroup,radioGroupType;
    private Uri lastCapturedUri;
    private double lat,lang;


    public static PublishingFragment newInstance(){
        return new PublishingFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.publishing_fragment, container, false);
       initializeViews(view);
        setListeners();
        return view;
    }






    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(ContentValues.TAG, "Displaying permission rationale to provide additional context.");


        } else {
            Log.i(ContentValues.TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }





    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_publish:
                if(uri_photo !=null){

                    presenter.performPublish(uri_photo,getActivity(),
                            AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,
                                    getActivity(),
                                    ""),
                            editText.getText().toString(),
                            countryItem,
                            editTextPrice.getText().toString(),
                            String.valueOf(flatType),String.valueOf(lat),String.valueOf(lang),String.valueOf(productType));

                }else{
                    Message.message(getActivity(),"Please Select Photos First ..");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case FILE_REQUEST_CODE:
                    ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                    uri_photo = new ArrayList<>();
                    for (int i = 0; i < files.size(); i++) {
                       // int file_size = Integer.parseInt(String.valueOf(files.get(i).getSize()));

                        uri_photo.add(CompressPhotos.compressImage(getActivity(),files.get(i).getUri()));
                      //  Log.e("log", "onActivityResult: " + " "+CompressPhotos.compressImage(getActivity(),files.get(i).getUri()).toString());
                    }
                    PhotoSelectedAdapter photoSelectedAdapter = new PhotoSelectedAdapter(uri_photo, getActivity());
                    recyclerView.setAdapter(photoSelectedAdapter);
                    photoSelectedAdapter.notifyItemRangeInserted(0, uri_photo.size() - 1);
                    break;
                case CAMERA_REQUEST_CODE:
                    IntentToGallery();
                    break;
            }
        }
    }

    @Override
    protected void initializeViews(View v) {
        setHasOptionsMenu(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
        }
        selectFromGallery = v.findViewById(R.id.select_from_gallery);
        captureCamera = v.findViewById(R.id.capture);
        btnLocation = v.findViewById(R.id.btn_add_location);
        recyclerView = v.findViewById(R.id.recycler_selected_photo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        radioBtnRent = v.findViewById(R.id.btn_rent);
        radioBtnConv = v.findViewById(R.id.conveyance);
        radioGroup = v.findViewById(R.id.radio);
        radioGroupType = v.findViewById(R.id.radio_type_product);
        imageSpinIcon = v.findViewById(R.id.image_icon);
        MyCustomeAdapter myCustomeAdapter3= new MyCustomeAdapter(getActivity(),getAllCoutry());
        spin_search =v.findViewById(R.id.spin_search);
        spin_search.setAdapter(myCustomeAdapter3);
        editText = v.findViewById(R.id.editTextDescription);
        presenter = new PresenterPublish(this, this);
        progressDialog = new ProgressDialog(getActivity());
        editTextPrice = v.findViewById(R.id.editTextPrice);


    }


    @Override
    protected void setListeners() {
        selectFromGallery.setOnClickListener(selectFromgalleryListener);
        captureCamera.setOnClickListener(captureCameraListener);
        spin_search.setOnItemSelectedListener(spin_searchListener);
        btnLocation.setOnClickListener(btnLocationListener);
        radioGroup.setOnCheckedChangeListener(radioGroupListener);
        radioGroupType.setOnCheckedChangeListener(radioGroupTypeListener);
        imageSpinIcon.setOnClickListener(imageSpinIconListerner);

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

    private String getTimeStamp() {
        return TimeStamp.format(new Date());
    }
    public void openCamera(boolean forVideo) {
        Intent intent;
        String fileName;
        File dir;
        Uri externalContentUri;

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            dir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
            fileName = "/IMG_" + getTimeStamp() + ".jpeg";
            externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        if (!dir.exists() && !dir.mkdir()) {
            Log.d(TAG, "onClick: " +
                    (forVideo ? "MOVIES" : "PICTURES") + " Directory not exists");
            return;
        }
        lastCapturedFile = new File(dir.getAbsolutePath() + fileName);
        Uri fileUri = FilePickerProvider.getUriForFile(getActivity(), lastCapturedFile);
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, lastCapturedFile.getAbsolutePath());
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis());
        lastCapturedUri = getActivity().getContentResolver().insert(externalContentUri, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private View.OnClickListener btnLocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            CustomDialogMap customDialogMap = new CustomDialogMap(getActivity());
            customDialogMap.showDialog(getActivity(),presenter);
        }
    };
    private View.OnClickListener selectFromgalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentToGallery();
            recyclerView.setVisibility(View.VISIBLE);
        }
    };

    public void IntentToGallery(){
        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .enableImageCapture(true)
                .setMaxSelection(6)
                .setSkipZeroSizeFiles(true)
                .build());
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }
    private View.OnClickListener captureCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCamera(false);
        }
    };


    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            View radioButton = radioGroup.findViewById(i);
            int index = radioGroup.indexOfChild(radioButton);

            switch (index) {
                case 0:
                    flatType = 1;
                    break;
                case 1:
                    flatType = 2;
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener radioGroupTypeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            View radioButton = radioGroup.findViewById(i);
            int index = radioGroup.indexOfChild(radioButton);

            switch (index) {
                case 0:
                    productType = 1;
                    break;
                case 1:
                    productType = 2;
                    break;
            }
        }
    };
    private AdapterView.OnItemSelectedListener spin_searchListener = new AdapterView.OnItemSelectedListener() {
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
    };

    private View.OnClickListener imageSpinIconListerner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            spin_search.performClick();
        }
    };

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
        progressDialog.dismiss();
    }

    @Override
    public void onFailuer(Throwable t) {
        Message.message(getActivity(),"something went wrong");
        progressDialog.dismiss();
    }

    @Override
    public void getLatLang(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
        Message.message(getActivity(),String.valueOf(lang)+" , " +String.valueOf(lang));
    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }


}