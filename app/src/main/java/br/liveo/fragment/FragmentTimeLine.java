package br.liveo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.liveo.navigationviewpagerliveo.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;


public class FragmentTimeLine extends Fragment {


    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    CircularImageView imageView;

    public FragmentTimeLine newInstance(String text){
        FragmentTimeLine mFragment = new FragmentTimeLine();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        imageView = (CircularImageView) rootView.findViewById(R.id.image_title);
        Picasso.with(getActivity())
                .load("https://www.vdomax.com/photos/2015/01/6oCCf_88845_fb60d93c210068b4a03cd16c0018d8dd.jpg")
                .into(imageView);



        return rootView;
    }



}