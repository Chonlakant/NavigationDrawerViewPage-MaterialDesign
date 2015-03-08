package br.liveo.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.activity.ActivityVideoPlaying;
import br.liveo.activity.ActivityWritePost;
import br.liveo.adapter.AdapterFeed;
import br.liveo.adapter.AdapterJsonFeed;
import br.liveo.adapter.AdapterVideos;
import br.liveo.model.Comment;
import br.liveo.model.Post;
import br.liveo.model.Youtube;
import br.liveo.navigationviewpagerliveo.R;

public class FragmentVideos extends Fragment {

    private boolean mSearchCheck;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    private int page;
    String url = "http://ihdmovie.xyz/feed5.json";
    ArrayList<Youtube> list = new ArrayList<Youtube>();

    AdapterVideos adapterVideos;
    ListView ls;

    public AQuery aq;


	
	public FragmentVideos newInstance(String text){
		FragmentVideos mFragment = new FragmentVideos();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_videos, container, false);




        aq = new AQuery(getActivity());
        adapterVideos = new AdapterVideos(getActivity(), list);
        ls = (ListView) rootView.findViewById(R.id.list_youtube);
        ls.setAdapter(adapterVideos);
        adapterVideos.SetOnItemClickListener(new AdapterVideos.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {



                Intent i =new Intent(getActivity(), ActivityVideoPlaying.class);
                startActivity(i);

                Toast.makeText(getActivity(),"sdsd",Toast.LENGTH_SHORT).show();
            }
        });



        aq.ajax(url, JSONObject.class, this, "getjson");

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
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

                Youtube list_item = new Youtube(imageTitleUser, titleUserYouTube, shortMessage, thumbnailYouTube);


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
