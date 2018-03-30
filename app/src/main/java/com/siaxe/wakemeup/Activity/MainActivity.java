package com.siaxe.wakemeup.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.siaxe.wakemeup.R;
import com.siaxe.wakemeup.Services.LocationUpdateService;
import com.siaxe.wakemeup.Utils.Constants;
import com.siaxe.wakemeup.Utils.UISetup;
import com.siaxe.wakemeup.fragment.FragmentSelectContact;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static String TAG = "TAGMain";
    ImageView imageViewToolbarIcon;
    TextView textviewTitle;


    @BindView(R.id.navigationView)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        set intro activity
        /*Intent newIntent = new Intent(this,IntroActivity.class);
        startActivity(newIntent);*/

//        set navigation view
        ButterKnife.bind(this);
        setUpNavigationAndToolbar();
        viewStyle();

//        bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

//        promptToAskStart();
//        TODO: validate if not enough credit to send sms
//        sendSMS("+94715895271","ado this is send by machine");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            return false;
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.GET_PHONE_NUMBER){
            if(resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);

                Log.d(TAG, "onActivityResult: --> "+results.size());

            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }

    private void setUpNavigationAndToolbar() {

        drawerToggle = setupDrawerToggle();
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }

//        toolbar textView
        textviewTitle = (TextView) findViewById(R.id.actionbar_textview);

//        toolbar image
        imageViewToolbarIcon = findViewById(R.id.imageView_toolbarIcon);

    }

    private void viewStyle() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        UISetup.setHeight(metrics.heightPixels);
        UISetup.setWidth(metrics.widthPixels);

        DrawerLayout.LayoutParams paramss = (DrawerLayout.LayoutParams) navView.getLayoutParams();
        paramss.width = metrics.widthPixels;
        navView.setLayoutParams(paramss);

        Toolbar.LayoutParams prmTvToolbar =   new Toolbar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        textviewTitle.setLayoutParams(prmTvToolbar);
        textviewTitle.setText(getResources().getString(R.string.wake_me_up));

        Toolbar.LayoutParams prmIvToolbar =   new Toolbar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.RIGHT);
        prmIvToolbar.rightMargin = UISetup.sizeBaseOnWidth(60);
        imageViewToolbarIcon.setLayoutParams(prmIvToolbar);

//        fragmentContainer
        FrameLayout gragmentContainer = findViewById(R.id.flContent);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UISetup.sizeBaseOnHeight(1091));
        gragmentContainer.setLayoutParams(lp);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void promptToAskStart() {

        new MaterialStyledDialog.Builder(MainActivity.this)
                .withDialogAnimation(true)
                .setTitle(getResources().getString(R.string.wake_me_up))
                .setDescription(getResources().getString(R.string.do_you_need_to_wake_up))
                .setCancelable(false)
                .withDarkerOverlay(false)
                .withIconAnimation(true)
                .setHeaderDrawable(R.drawable.logo_banner)

                .setPositive(getResources().getString(R.string.yes), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        stopService(new Intent(MainActivity.this,LocationUpdateService.class));
                       // startService(new Intent(MainActivity.this,LocationUpdateService.class));
                        dialog.dismiss();

                    }
                })

                .setNegative(getResources().getString(R.string.no), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        stopService(new Intent(MainActivity.this,LocationUpdateService.class));
                        dialog.dismiss();

                    }
                })
                .show();

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment) {


        Log.d("TEST", "replaceFragment: "+fragment.getClass().getName());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(fragment.getClass().getName());
        ft.replace(R.id.flContent, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MainActivity.this,LocationUpdateService.class));
      //  startService(new Intent(MainActivity.this,LocationUpdateService.class));
        Log.d(TAG, "onResume: --> start");

    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MainActivity.this,LocationUpdateService.class));
       // startService(new Intent(MainActivity.this,LocationUpdateService.class));
        Log.d(TAG, "onPause: --> start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(MainActivity.this,LocationUpdateService.class));
       // startService(new Intent(MainActivity.this,LocationUpdateService.class));

        Log.d(TAG, "onDestroy: --> start");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.navigation_settings:
//                   set contact list
                Fragment fragmentZero = new FragmentSelectContact();
                replaceFragment(fragmentZero);

                return true;
            case R.id.navigation_dashboard:

                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    }
}
