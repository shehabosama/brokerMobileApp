package com.paus.paus_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.paus.paus_app.HelperStuffs.Message;
import com.paus.paus_app.Login.LoginActivity;
import com.paus.paus_app.SqlHelper.myDbAdapter;
import com.paus.paus_app.fragments.Account_fragment;
import com.paus.paus_app.fragments.BlankFragment;
import com.paus.paus_app.fragments.Chat_rooms;
import com.paus.paus_app.fragments.Installment_fragmet_two;
import com.paus.paus_app.fragments.QrCodeFragment;
import com.paus.paus_app.fragments.Rentals_fragemnt;
import com.paus.paus_app.fragments.Service_fragment;
import com.paus.paus_app.fragments.TimeLineFragment;
import com.paus.paus_app.fragments.member_family_fragment;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CurvedBottomNavigationView mView;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private float mYVal;
    private RelativeLayout mlinId;
    PathModel outline;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mtoolbar;
    CircleImageView NavprofileImage;
    TextView Navusername;
    FrameLayout relativeLayout;
    BottomNavigationViewEx bottomNavigationViewEx;
    myDbAdapter helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new myDbAdapter(this);

        Message.message(getApplicationContext(),helper.getData());
        relativeLayout = findViewById(R.id.container_body);
        mtoolbar=(Toolbar)findViewById(R.id.homeToolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(R.string.home);

        mtoolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        bottomNavigationViewEx = findViewById(R.id.btmNav);
            bottomNavigationViewEx.inflateMenu(R.menu.main_menu);




        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView=(NavigationView)findViewById(R.id.nav);

        View nav_view=navigationView.inflateHeaderView(R.layout.header);


        //NavprofileImage=(CircleImageView)nav_view.findViewById(R.id.nav_profile_image);
       // Navusername=(TextView)nav_view.findViewById(R.id.nav_user_full_name);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                UserMenuSelector(item);
                return false;
            }
        });




        QrCodeFragment fragment = new QrCodeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment,"shehahab2").commit();




        //getting bottom navigation view and attaching the listener
        //BottomNavigationView navigation = findViewById(R.id.customBottomBar);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_timeline:

                getSupportActionBar().setTitle(R.string.home);

                TimeLineFragment fragment1 = new TimeLineFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment1,"shehahab1").commit();

                break;
            case R.id.action_qrcode:


                getSupportActionBar().setTitle(R.string.home);

                QrCodeFragment fragment2 = new QrCodeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment2,"shehahab2").commit();
                break;
            case R.id.action_chat:
                getSupportActionBar().setTitle(R.string.home);

                 Account_fragment fragment3 = new Account_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,fragment3,"shehahab3").commit();
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


    private void UserMenuSelector(MenuItem item) {

        switch(item.getItemId()){

            case R.id.action_family_nav:
                getSupportActionBar().setTitle("Family members");
                 member_family_fragment member_family_fragment= new member_family_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,member_family_fragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();


                break;
            case R.id.action_chat_nav:

                getSupportActionBar().setTitle("Chat rooms");
                Chat_rooms chat_rooms= new Chat_rooms();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,chat_rooms,"shehahab3").commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.services_nav:
                getSupportActionBar().setTitle("Service");

                Service_fragment service_fragment= new Service_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,service_fragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();

                break;
            case R.id.action_installments_nav:
                getSupportActionBar().setTitle("Installments");

                Installment_fragmet_two installment_fragemnt= new Installment_fragmet_two();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,installment_fragemnt,"shehahab3").commit();
                mDrawerLayout.closeDrawers();

                break;

            case R.id.action_rentals_nav:

                getSupportActionBar().setTitle("Rentals");

                Rentals_fragemnt rentals_fragemnt= new Rentals_fragemnt();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,rentals_fragemnt,"shehahab3").commit();
                mDrawerLayout.closeDrawers();

                break;

            case R.id.Invitation_nav:

                getSupportActionBar().setTitle("Invitation");

                BlankFragment blankFragment= new BlankFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body,blankFragment,"shehahab3").commit();
                mDrawerLayout.closeDrawers();



                break;
            case R.id.logout_nav:
                String data = helper.getEmployeeName();
                helper.delete(data);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                break;


        }

    }

}