package br.liveo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.adapter.AdapterLivieHistory;
import br.liveo.model.live;
import br.liveo.navigationviewpagerliveo.R;


public class FragmentLiveHistory extends Fragment {
    String url = "http://api.vdomax.com/live/history/1301";
    private boolean mSearchCheck;
    ArrayList<live> list = new ArrayList<live>();
    AdapterLivieHistory activityLiveHistory;

    public AQuery aq;


    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    CircularImageView imageView;

    public FragmentLiveHistory newInstance(String text){
        FragmentLiveHistory mFragment = new FragmentLiveHistory();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_livehistory_main, container, false);

        aq = new AQuery(getActivity());

        activityLiveHistory = new AdapterLivieHistory(getActivity(), list);

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        recList.setAdapter(activityLiveHistory);


        aq.ajax(url, JSONObject.class, this, "getJson");



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

        menu.findItem(R.id.menu_add).setVisible(true);
        menu.findItem(R.id.menu_search).setVisible(true);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:

                break;

            case R.id.menu_search:
                mSearchCheck = true;

                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };

    public void getJson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);
        Log.d("Check_Feed:", "Test1");
        if (jo != null) {
            JSONArray ja = jo.getJSONArray("history");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.getJSONObject(i);

                Log.d("Cehci", obj.toString());

                String  nameLive = obj.optString("name");
                String urlLive = obj.optString("url");
                String photoLive = obj.optString("thumb");

                JSONObject media = obj.getJSONObject("duration");

                String hours = media.optString("hours");
                String minutes = media.optString("minutes");
                String seconds = media.optString("seconds");

                live liveList = new live(urlLive,photoLive,nameLive,hours,minutes,seconds,null);
                list.add(liveList);

            }
            activityLiveHistory.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }



}