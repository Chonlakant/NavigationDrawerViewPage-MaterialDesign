package br.liveo.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.activity.MainProfileFriends;
import br.liveo.adapter.AdapterFriends;
import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;

public class FragmentFriends extends Fragment {
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    String url = "http://ihdmovie.xyz/root/api/feed_get.php?uid=1";
    String url2 = "http://ihdmovie.xyz/feed.json";
    String url3 = "http://ihdmovie.xyz/feed2.json";

    ArrayList<Post> list = new ArrayList<Post>();
    AdapterFriends adapterJson;
    GridView gridView;

    public AQuery aq;


    public FragmentFriends newInstance(String text){
        FragmentFriends mFragment = new FragmentFriends();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        aq = new AQuery(getActivity());
        adapterJson = new AdapterFriends(getActivity(), list);
        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        gridView.setAdapter(adapterJson);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = list.get(position).getImageProfileUrl();

                Intent i = new Intent(getActivity(), MainProfileFriends.class);
                i.putExtra("url",url);
                startActivity(i);
            }
        });


        aq.ajax(url3, JSONObject.class, this, "getjson");

        return rootView;
    }
    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        if (jo != null) {
            JSONArray ja = jo.optJSONArray("users");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.optJSONObject(i);

                JSONObject location = obj.optJSONObject("avatar");

                String url_avatra;

                if(location != null)
                    url_avatra = location.optString("url");
                else
                    url_avatra = "themes/vdomax1.1/images/default-female-avatar.png";


                String Avatra = "https://www.vdomax.com/"+url_avatra+"";
                Log.i("Test_chcl",Avatra);

                String name_title = obj.optString("name");


                Post list_item = new Post(Avatra, name_title, null, null, null, null, null, null, null,null);
//                list_item.setImageProfileUrl(Avatra);
//                list_item.setMonth(name_title);

//
//
                list.add(list_item);
            }
            adapterJson.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }
}