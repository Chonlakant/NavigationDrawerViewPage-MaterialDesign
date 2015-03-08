package br.liveo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import br.liveo.navigationviewpagerliveo.R;
import br.liveo.widget.NotifyingScrollView2;

public class ScrollViewFragment extends ScrollTabHolderFragment implements NotifyingScrollView2.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";


    private NotifyingScrollView2 mScrollView;

    TextView title;
    TextView titleShortDescription;
    TextView titleDescription;
    TextView textSendEmail;
    TextView textEmail;
    LinearLayout layout1;
    LinearLayout layout2;

    ImageView titleImage;

    private int mPosition;
    private CardView cardView;

    public static Fragment newInstance(int position) {
        ScrollViewFragment f = new ScrollViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scrollview, null);

        mScrollView = (NotifyingScrollView2) v.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangedListener(this);
        layout1 = (LinearLayout) v.findViewById(R.id.layout1);
        titleDescription = (TextView) v.findViewById(R.id.titleDescription);
        titleShortDescription = (TextView) v.findViewById(R.id.titleShortDescription);
        title = (TextView) v.findViewById(R.id.title);


        titleImage = (ImageView) v.findViewById(R.id.titleImage);
        cardView = (CardView) v.findViewById(R.id.cardView);
        cardView.setPadding(30, 30, 30, 30);

        if (mPosition == 0) {
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
           
            titleDescription.setText("On April 27, 2009, the Android 1.5 update was released, based on Linux kernel 2.6.27. This was the first release to officially use a codename based on a dessert item (\"Cupcake\"), a theme which would be used for all releases henceforth. The update included several new features and UI amendments");
            title.setText("ABOUT");
            titleShortDescription.setText("Api level 3");
            titleImage.setImageResource(R.drawable.ic_thumbnail);
        }
        if (mPosition == 1) {

        }
        if (mPosition == 2) {


        }

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY) {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);

    }


}