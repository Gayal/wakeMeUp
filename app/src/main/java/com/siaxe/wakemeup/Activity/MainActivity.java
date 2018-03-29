package com.siaxe.wakemeup.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.siaxe.wakemeup.R;
import com.siaxe.wakemeup.Services.LocationUpdateService;
import com.siaxe.wakemeup.Utils.UISetup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpNavigationAndToolbar();

        viewStyle();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        promptToAskStart();
//        TODO: validate if not enough credit to send sms
//        sendSMS("+94715895271","ado this is send by machine");

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
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UISetup.sizeBaseOnHeight(1091));
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
}
