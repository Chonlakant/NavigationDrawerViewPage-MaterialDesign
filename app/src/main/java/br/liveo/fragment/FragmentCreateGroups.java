package br.liveo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import br.liveo.navigationviewpagerliveo.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;


public class FragmentCreateGroups extends Fragment {
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public FragmentCreateGroups newInstance(String text) {
        FragmentCreateGroups mFragment = new FragmentCreateGroups();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    ImageViewTouch imageFeedBig;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_creategroup, container, false);


        return rootView;
    }


}