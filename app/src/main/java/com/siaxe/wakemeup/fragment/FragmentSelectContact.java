package com.siaxe.wakemeup.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.siaxe.wakemeup.Activity.MainActivity;
import com.siaxe.wakemeup.R;
import com.siaxe.wakemeup.Utils.Constants;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

/**
 * Created by sudesh on 3/30/2018.
 */




public class FragmentSelectContact extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

 new MultiContactPicker.Builder(getActivity()) //Activity/fragment context
                .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                .hideScrollbar(false) //Optional - default: false
                .showTrack(true) //Optional - default: true
                .searchIconColor(Color.WHITE) //Option - default: White
                .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                .handleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                .bubbleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                .bubbleTextColor(Color.WHITE) //Optional - default: White
                .showPickerForResult(Constants.GET_PHONE_NUMBER);

        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
