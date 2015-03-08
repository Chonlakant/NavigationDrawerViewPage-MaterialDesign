package br.liveo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.adapter.AdapterVideos;
import br.liveo.model.Youtube;
import br.liveo.navigationviewpagerliveo.R;

public class ActivityVideos extends Activity {



    public AQuery aq;
    private int page;
    String url = "http://ihdmovie.xyz/feed5.json";
    ArrayList<Youtube> list = new ArrayList<Youtube>();

    AdapterVideos adapterVideos;
    ListView ls;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_videos);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Sam Savek (@samsavek)");
        //BusProvider.getInstance().register(this);



        aq = new AQuery(this);
        adapterVideos = new AdapterVideos(this, list);
        ls = (ListView) findViewById(R.id.list_youtube);
        ls.setAdapter(adapterVideos);



        adapterVideos.SetOnItemClickListener(new AdapterVideos.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                String idYoutube = list.get(1).getId();
                String title = list.get(1).getTitle();
                String description = list.get(1).getDescription();
                String userProfile = list.get(1).getUserProfile();
                Log.d("IDdfsafsdfsdf:",idYoutube);

                Intent i = new Intent(getApplication(),ActivityVideoPlaying.class);
                i.putExtra("url",idYoutube);
                i.putExtra("title",title);
                i.putExtra("description",description);
                i.putExtra("userProfile",userProfile);
                startActivity(i);
            }
        });



        aq.ajax(url, JSONObject.class, this, "getjson");

    }



    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        if (jo != null) {
            JSONArray ja = jo.getJSONArray("posts");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.getJSONObject(i);


                JSONObject youtube = obj.getJSONObject("youtube");
                String idUserYourTube = youtube.optString("id");
                String titleUserYouTube = youtube.optString("title");
                String description = youtube.optString("description");
                String thumbnailYouTube = youtube.optString("thumbnail");

                String shortMessage;
                if (description.length() > 200)
                    shortMessage = description.substring(0, 70);
                else
                    shortMessage = description;

                JSONObject author = obj.getJSONObject("author");
                String imageUser = author.optString("avatar");
                String imageTitleUser = "https://www.vdomax.com/" + imageUser + "";

                Youtube list_item = new Youtube(idUserYourTube, titleUserYouTube, shortMessage, thumbnailYouTube,imageTitleUser);


                Log.d("chonlakant", idUserYourTube);

                list.add(list_item);

            }
            adapterVideos.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }

}