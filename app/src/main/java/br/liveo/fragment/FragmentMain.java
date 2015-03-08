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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.activity.ActivityWritePost;
import br.liveo.activity.ProfileDetail;
import br.liveo.adapter.AdapterFeed;
import br.liveo.adapter.AdapterJsonFeed;
import br.liveo.model.Comment;
import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;

public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";


    String url = "http://ihdmovie.xyz/root/api/feed_get.php?uid=1";
    String url2 = "http://ihdmovie.xyz/feed.json";
    String url3 = "http://ihdmovie.xyz/feed3.json";
    String urlMain = "http://ihdmovie.xyz/main_feed.json";
    ArrayList<Post> list = new ArrayList<Post>();
    AdapterJsonFeed adapter;
    AdapterFeed adapterJson;
    RelativeLayout layoutMenu;

    public AQuery aq;


	
	public FragmentMain newInstance(String text){
		FragmentMain mFragment = new FragmentMain();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        layoutMenu = (RelativeLayout) rootView.findViewById(R.id.layoutMenu);

        aq = new AQuery(getActivity());

        adapter = new AdapterJsonFeed(getActivity(), list);

        adapter.SetOnItemClickListener(new AdapterJsonFeed.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                String photo = list.get(position).getImagePostUrl();

                Toast.makeText(getActivity(), "id" + view.getId(), Toast.LENGTH_LONG).show();


                Bundle data = new Bundle();
                data.putString("url", photo);
                FragmentPhotofeed fragment = new FragmentPhotofeed();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.svScroll, fragment);
                fragment.setArguments(data);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        recList.setAdapter(adapter);

        recList.setOnTouchListener(new View.OnTouchListener() {

            final int DISTANCE = 3;

            float startY = 0;
            float dist = 0;
            boolean isMenuHide = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    startY = event.getY();
                } else if (action == MotionEvent.ACTION_MOVE) {
                    dist = event.getY() - startY;

                    if ((pxToDp((int) dist) <= -DISTANCE) && !isMenuHide) {
                        isMenuHide = true;
                        hideMenuBar();
                    } else if ((pxToDp((int) dist) > DISTANCE) && isMenuHide) {
                        isMenuHide = false;
                        showMenuBar();
                    }

                    if ((isMenuHide && (pxToDp((int) dist) <= -DISTANCE))
                            || (!isMenuHide && (pxToDp((int) dist) > 0))) {
                        startY = event.getY();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    startY = 0;
                }

                return false;
            }
        });

        FloatingActionButton buttonWritePost = (FloatingActionButton) rootView.findViewById(R.id.action_h);
        buttonWritePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ActivityWritePost.class);
                startActivity(i);
            }
        });

        aq.ajax(urlMain, JSONObject.class, this, "getJson");



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

    public void getJson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);
        Log.d("Check_Feed:", "Test1");
        if (jo != null) {
            JSONArray ja = jo.getJSONArray("posts");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.getJSONObject(i);

                JSONObject media = obj.getJSONObject("media");
                String avatarId = media.getString("id");
                String imagePhotoUrl = media.getString("url");
                String extension = media.getString("extension");

                String imagePhotoFullUrl = "https://www.vdomax.com/" + imagePhotoUrl + "." + extension + "";
                Log.i(".......", imagePhotoFullUrl);

                String imageAvatarUrl = "https://graph.facebook.com/v2.1/" + avatarId + "/picture?type=large";


                JSONObject author = obj.getJSONObject("author");
                String name = author.getString("name");

                //Log.d("Check",obj.toString());

                String name_title = obj.getString("type1");
                String loveCount = obj.getString("love_count");
                String number2 = obj.getString("follow_count");
                String commentCount = obj.getString("comment_count");
                String viewCount = obj.getString("view");
                String message = obj.getString("text");
                String date = obj.getString("timestamp");


                int commentNuber = Integer.parseInt(viewCount.toString());
                int num_comment = 0;

                if (commentNuber > 1000)
                    num_comment = commentNuber / 1000;
                String num_comment2 = num_comment + "k";


//                String view = obj.getString("view");
//                String image_messen = obj.getString("image_messen");
//                String number4 = obj.getString("number4");
//              String date = obj.getString("date");

                String shortMessage;
                if (message.length() > 200)
                    shortMessage = message.substring(0, 199);
                else
                    shortMessage = message;


                ArrayList<Comment> comments = new ArrayList<>();
                if (Integer.parseInt(commentCount) > 0) {
                    JSONArray commentJsonArray = obj.getJSONArray("comment");

                    for (int a = 0; a < commentJsonArray.length(); a++) {
                        JSONObject commentJsonObject = commentJsonArray.getJSONObject(a);
                        String commentText = commentJsonObject.optString("text");
                        JSONObject accountJsonObject = commentJsonObject.getJSONObject("account");
                        String commentId = accountJsonObject.optString("id");
                        String commentName = accountJsonObject.optString("name");

                        Comment comment = new Comment(null, commentName, null, null, commentText, commentId);
                        comments.add(comment);
                    }

                }

                // Use view_count instead of share_count (share_count data is empty now)
                Post post = new Post(imageAvatarUrl, name, date, loveCount, commentCount, num_comment2
                        , message, shortMessage, viewCount, imagePhotoFullUrl);
                post.setComments(comments);

                list.add(post);
            }
            adapterJson.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }

    public int pxToDp(int px) {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int dp = Math.round(px / (dm.densityDpi
                / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public void showMenuBar() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu
                , View.TRANSLATION_Y, 0);

        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }

    public void hideMenuBar() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu
                , View.TRANSLATION_Y, layoutMenu.getHeight());

        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }
}
