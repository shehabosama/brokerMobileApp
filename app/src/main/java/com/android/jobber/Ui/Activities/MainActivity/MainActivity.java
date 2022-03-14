package com.android.jobber.Ui.Activities.MainActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.android.jobber.R;
import com.android.jobber.Services.FCM.FCMRegisterationService;
import com.android.jobber.Ui.Activities.Login.LoginActivity;
import com.android.jobber.Ui.fragments.AllJobbersFragemnts.AllJobberFragment;
import com.android.jobber.Ui.fragments.ChatRoomFragment.Chat_rooms;
import com.android.jobber.Ui.fragments.FavoritesFragment.FavoritesFragment;
import com.android.jobber.Ui.fragments.Installment_fragmet_two;
import com.android.jobber.Ui.fragments.InvitationFragment.InvitationFragment;
import com.android.jobber.Ui.fragments.PrdouctRequestsFragment.PrdouctRequestsFragment;
import com.android.jobber.Ui.fragments.ProfileFragment.ProfileFragment;
import com.android.jobber.Ui.fragments.PublishingFragment.PublishingFragment;
import com.android.jobber.Ui.fragments.ReviewFragment.ReviewFragment;
import com.android.jobber.Ui.fragments.TimeLineFragment.TimeLineFragment;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.Patterns.PatternsContract;
import com.android.jobber.common.Patterns.PresenterPatterns;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.base.BaseActivity;
import com.android.jobber.common.network.Urls;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener, PatternsContract.View {

    private static final int REQUEST_MICROPHONE = 101;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mtoolbar;
    private CircleImageView NavprofileImage;
    private TextView Navusername;
    private FrameLayout relativeLayout;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private myDbAdapter helper;
    MaterialSearchView searchView;
    private FloatingActionButton floatingActionButton;
    private boolean check_voic=true;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private TextToSpeech textToSpeech;
    private String textRekognation;
    private List<String> patterns;
    private PresenterPatterns presenter;
    private int mBindFlag;
    private Messenger mServiceMessenger;
    static final int MSG_RECOGNIZER_START_LISTENING = 1;
    private  ArrayList<String> matches;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setListeners();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_timeline:
                getSupportActionBar().setTitle(R.string.home);
                TimeLineFragment fragment1 = new TimeLineFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment1, String.valueOf(Constants.BottomNavConstants.MENU_TIME_LINE_SCREEN)).commit();
                break;
            case R.id.action_publishing:

                getSupportActionBar().setTitle(R.string.home);
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.BottomNavConstants.MENU_MY_PUBLISHING_SCREEN)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.BottomNavConstants.MENU_MY_PUBLISHING_SCREEN))));
                } else {
                    replaceFragment(R.id.container_body, PublishingFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.MENU_MY_PUBLISHING_SCREEN));
                }

                break;
            case R.id.action_profile:
                getSupportActionBar().setTitle(R.string.home);

                 ProfileFragment fragment3 = new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment3, String.valueOf(Constants.BottomNavConstants.ACTION_PROFILE)).commit();
                break;
            case R.id.action_rate_Reviews:
                getSupportActionBar().setTitle("Rates");

                ReviewFragment reviewFragment = new ReviewFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,reviewFragment, String.valueOf(Constants.BottomNavConstants.ACTION_REVIEWS)).commit();
                break;
            case R.id.action_voice:

                if (check_voic){
                    floatingActionButton.setVisibility(View.VISIBLE);
                    check_voic = false;
                }else{
                    floatingActionButton.setVisibility(View.GONE);
                    check_voic = true;

                }
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void checkPermissionAudio() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.RECORD_AUDIO},
//                        REQUEST_MICROPHONE);
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_MICROPHONE){
//
//        }
//    }

    private void UserMenuSelector(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_search:

            case R.id.action_chat_nav:

                getSupportActionBar().setTitle("Chat rooms");
                Chat_rooms chat_rooms= new Chat_rooms();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,chat_rooms,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.favorite_nav:
                getSupportActionBar().setTitle("Favorites");
                FavoritesFragment favoritesFragment= new FavoritesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,favoritesFragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_brokers:
                getSupportActionBar().setTitle("Brokers");
                AllJobberFragment allJobberFragment= new AllJobberFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,allJobberFragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_requests_nav:
                getSupportActionBar().setTitle("Requests");
                PrdouctRequestsFragment requestsFragment= new PrdouctRequestsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,requestsFragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.Invitation_nav:
                getSupportActionBar().setTitle("Invitation");
                InvitationFragment invitationFragment= new InvitationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,invitationFragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.logout_nav:
                String data = helper.getEmployeeName("name");
                helper.delete(data);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initializeViews() {
        FirebaseApp.initializeApp(getApplicationContext());
        startService(new Intent(this, FCMRegisterationService.class));
        helper = new myDbAdapter(this);
        relativeLayout = findViewById(R.id.container_body);
        mtoolbar = findViewById(R.id.homeToolbar);
        floatingActionButton = findViewById(R.id.btn_floating);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(R.string.home);
        mtoolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        bottomNavigationViewEx = findViewById(R.id.btmNav);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        patterns = new ArrayList<>();
        textToSpeech = new TextToSpeech(MainActivity.this,this, "com.google.android.tts");

        Set<String> a=new HashSet<>();
        a.add("male");//here you can give male if you want to select male voice.
        Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
        textToSpeech.setVoice(v);
        textToSpeech.setSpeechRate(0.8f);

        presenter = new PresenterPatterns(this);
//        Intent installIntent = new Intent();
//        installIntent.setAction(
//                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//        startActivity(installIntent);
//        Intent intent = new Intent();
//        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(intent, 0);

        //checkPermission();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //  mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
        // mSpeechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en-US"});
        mSpeechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES",
                Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(mSpeechRecognizerListener);


        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,this,false)){
            bottomNavigationViewEx.inflateMenu(R.menu.admin_menu);
        }else{
            bottomNavigationViewEx.inflateMenu(R.menu.user_menu);

        }
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.setTextVisibility(true);


        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.nav);
        View nav_view=navigationView.inflateHeaderView(R.layout.header);

        NavprofileImage=(CircleImageView)nav_view.findViewById(R.id.nav_profile_image);
         Navusername=(TextView)nav_view.findViewById(R.id.nav_user_full_name);
         Navusername.setText(helper.getEmployeeName("name"));
        Picasso.with(this)
                .load(Urls.IMAGE_URL +helper.getEmployeeName("image"))
                .placeholder(R.drawable.backgroundprof)
                .into(NavprofileImage);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                UserMenuSelector(item);
                return false;
            }
        });
        bottomNavigationViewEx.setSelectedItemId(
                AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false)
                        ? R.id.action_publishing : R.id.action_timeline);
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false)){

            ProfileFragment fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment, String.valueOf(Constants.BottomNavConstants.ACTION_PROFILE)).commit();
        }else{
            TimeLineFragment fragment = new TimeLineFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment, String.valueOf(Constants.BottomNavConstants.MENU_TIME_LINE_SCREEN)).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MainActivity", "onActivityResult: i am here" );
        if(requestCode == 0){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
               // Toast.makeText(getApplicationContext(),"Already Installed", Toast.LENGTH_LONG).show();
            } else {
//                Intent installIntent = new Intent();
//                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                startActivity(installIntent);
//                Toast.makeText(getApplicationContext(),"Installed Now", Toast.LENGTH_LONG).show();

            }
        }else{
            new ProfileFragment().onActivityResult(requestCode, resultCode, data);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListeners() {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(MainActivity.this);
        floatingActionButton.setOnTouchListener(floatingActionButtonListener);
      //  mtoolbar.setNavigationOnClickListener(mtoolbarListener);
    }





    private RecognitionListener mSpeechRecognizerListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {


        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResults(Bundle bundle) {
            //getting all the matches
             matches = bundle
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            //displaying the first match
            if (matches != null){
                Log.e("speshhh", "onResults: "+matches.get(0) );
                search_array(matches.get(0).toLowerCase(),presenter.pat_en);
                speakOut();
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
//    private View.OnClickListener mtoolbarListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
//            {
//                mDrawerLayout.closeDrawer(Gravity.LEFT);
//            }else
//            {
//                mDrawerLayout.openDrawer(Gravity.LEFT);
//            }
//        }
//    };
    private View.OnTouchListener floatingActionButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Snackbar.make(view, "processing your order.....", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    mSpeechRecognizer.stopListening();
                    break;
                case MotionEvent.ACTION_DOWN:
                    //finger is on the button
                    Snackbar.make(view, "Recording now.....", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Action", null).show();
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    return true;
            }
            return false;
        }
    };

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,Uri.parse("package:" + MainActivity.this.getPackageName()));
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){



            Set<String> a=new HashSet<>();
            a.add("male");//here you can give male if you want to select male voice.
            //Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
            Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
            textToSpeech.setVoice(v);
            textToSpeech.setSpeechRate(0.8f);

            // int result = T2S.setLanguage(Locale.US);
            int result = textToSpeech.setVoice(v);



           // int result =textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA||result == TextToSpeech.LANG_NOT_SUPPORTED){

            }else{
                speakOut();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speakOut(){
        textToSpeech.speak(textRekognation,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void search_array(String txt, String[] pat) {
        int M = 0;
        int N = 0;
        for (int i = 0; i < pat.length; i++) {
            M = pat[i].length();
            N = txt.length();
            for (int _i = 0; _i <= N - M; _i++) {

                int j;
            /* For current index i, check for pattern
              match */
                for (j = 0; j < M; j++)
                    if (txt.charAt(_i + j) != pat[i].charAt(j)) {
                        textRekognation = "i don't understand you";
                        break;
                    }
                if (j == M) { // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
                    // System.out.println("Pattern found at index " + _i);
                    System.out.println(pat[i]);
                    String sb = "";
                    patterns.add(pat[i]);

                }else{
                    textRekognation = "i don't understand you";
                }
            }
        }
        for (int i =0 ;i<patterns.size();i++){

            Log.e("tesxt", "search_array: "+patterns.toString() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textRekognation = presenter.PatternsFunc(patterns,i,MainActivity.this);
            }
            patterns.clear();
        }
    }

    @Override
    public void onListenerToFragment(String key) {
        if(key.equals(String.valueOf(Constants.BottomNavConstants.MENU_TIME_LINE_SCREEN))){
            replaceFragment(R.id.container_body, TimeLineFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.MENU_TIME_LINE_SCREEN));
            getSupportActionBar().setTitle("Timeline");
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_FAVORITES))){
            getSupportActionBar().setTitle("Favorites");
            FavoritesFragment favoritesFragment= new FavoritesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,favoritesFragment, String.valueOf(Constants.BottomNavConstants.ACTION_FAVORITES)).commit();
            getSupportActionBar().setTitle("Favorites");
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.MENU_MY_PUBLISHING_SCREEN))){
            replaceFragment(R.id.container_body, PublishingFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.MENU_MY_PUBLISHING_SCREEN));
            getSupportActionBar().setTitle("Jobber");
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_REVIEWS))){
            getSupportActionBar().setTitle("Rates");
            ReviewFragment reviewFragment = new ReviewFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,reviewFragment, String.valueOf(Constants.BottomNavConstants.ACTION_REVIEWS)).commit();
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_PROFILE))){
            getSupportActionBar().setTitle(R.string.home);
            ProfileFragment fragment3 = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment3, String.valueOf(Constants.BottomNavConstants.ACTION_PROFILE)).commit();
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_REQUESTS_PRODUCTS))){
            getSupportActionBar().setTitle("Requests");
            PrdouctRequestsFragment requestsFragment= new PrdouctRequestsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,requestsFragment, String.valueOf(Constants.BottomNavConstants.ACTION_REQUESTS_PRODUCTS)).commit();
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_BROKERS))){
            getSupportActionBar().setTitle("Brokers");
            AllJobberFragment allJobberFragment= new AllJobberFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,allJobberFragment, String.valueOf(Constants.BottomNavConstants.ACTION_BROKERS)).commit();
        }else if(key.equals(String.valueOf(Constants.BottomNavConstants.ACTION_CHAT_ROOMS))){
            getSupportActionBar().setTitle("Chat rooms");
            Chat_rooms chat_rooms= new Chat_rooms();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,chat_rooms, String.valueOf(Constants.BottomNavConstants.ACTION_CHAT_ROOMS)).commit();
        }
    }
}