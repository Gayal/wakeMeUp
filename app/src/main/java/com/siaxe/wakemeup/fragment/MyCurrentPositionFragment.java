package com.siaxe.wakemeup.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siaxe.wakemeup.R;

/**
 * Created by sudesh on 3/30/2018.
 */

public class MyCurrentPositionFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.my_current_position_fragment_layout, container, false);

//        findViews(view);

        return view;
    }


}
