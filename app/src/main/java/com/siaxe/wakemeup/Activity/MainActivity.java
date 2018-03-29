package com.siaxe.wakemeup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.siaxe.wakemeup.R;
import com.siaxe.wakemeup.Services.LocationUpdateService;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "TAGMain";

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        promptToAskStart();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sendSMS("+94783833064","ado this is send by machine");

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
                        startService(new Intent(MainActivity.this,LocationUpdateService.class));
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
