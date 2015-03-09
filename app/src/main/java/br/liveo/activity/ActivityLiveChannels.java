package br.liveo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.adapter.AdapterLivieChannels;
import br.liveo.model.live;
import br.liveo.navigationviewpagerliveo.R;

/**
 * Created by root1 on 3/8/15.
 */
public class ActivityLiveChannels extends Activity {

    String url = "http://api.vdomax.com/live/history/1301";

    ArrayList<live> list = new ArrayList<live>();
    AdapterLivieChannels activityLiveHistory;

    public AQuery aq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels_main);
        aq = new AQuery(getApplicationContext());

        activityLiveHistory = new AdapterLivieChannels(getApplication(), list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView.setAdapter(activityLiveHistory);
        activityLiveHistory.SetOnItemClickListener(new AdapterLivieChannels.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String url = list.get(position).getUrlLive();

                Intent i = new Intent(getApplication(),ActivityLivePlaying.class);
                startActivity(i);
            }
        });


        aq.ajax(url, JSONObject.class, this, "getJson");
    }

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

                String[] parts = nameLive.split("-");
                String part1 = parts[0];

                JSONObject media = obj.getJSONObject("duration");

                String hours = media.optString("hours");
                String minutes = media.optString("minutes");
                String seconds = media.optString("seconds");

                live liveList = new live(urlLive,photoLive,part1,hours,minutes,seconds,photoLive);
                list.add(liveList);

            }
            activityLiveHistory.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }
}
