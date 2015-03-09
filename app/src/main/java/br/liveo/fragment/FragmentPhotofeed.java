package br.liveo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import br.liveo.navigationviewpagerliveo.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class FragmentPhotofeed extends Fragment {

    Context context;
    ImageViewTouch imageFeedBig;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_feed_big, container, false);

        context = getActivity();
        Bundle extras = getArguments();
        String url = extras.getString("url");

        imageFeedBig = (ImageViewTouch) rootView.findViewById(R.id.image);

        Picasso.with(context)
                .load(url)
                .into(imageFeedBig);

        return rootView;
    }


}