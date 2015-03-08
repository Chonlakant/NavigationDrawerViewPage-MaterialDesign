package br.liveo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.adapter.AdapterFeed;
import br.liveo.adapter.AdapterJsonFeed;
import br.liveo.adapter.AdapterLivieHistory;
import br.liveo.model.Comment;
import br.liveo.model.Post;
import br.liveo.model.live;
import br.liveo.navigationviewpagerliveo.R;

/**
 * Created by root1 on 3/8/15.
 */
public class ActivityLiveHistory extends Activity {

    String url = "http://api.vdomax.com/live/history/1301";

    ArrayList<live> list = new ArrayList<live>();
    AdapterLivieHistory activityLiveHistory;

    public AQuery aq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livehistory_main);
        aq = new AQuery(getApplicationContext());

        activityLiveHistory = new AdapterLivieHistory(getApplication(), list);

        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        recList.setAdapter(activityLiveHistory);


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
