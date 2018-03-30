package com.siaxe.wakemeup.Activity;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.siaxe.wakemeup.R;
import com.siaxe.wakemeup.Utils.SampleSlide;

public class IntroActivity extends AppIntro {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addSlide(AppIntroFragment.newInstance("Slide title One","description here One",R.drawable.ic_home_black_24dp,getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Slide title Two","description here Two",R.drawable.ic_notifications_black_24dp,getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Slide title Three","description here Three",R.drawable.ic_done_white,getResources().getColor(R.color.colorAccent)));
//        addSlide(SampleSlide.newInstance(R.layout.slide2));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        finish();
    }


}
