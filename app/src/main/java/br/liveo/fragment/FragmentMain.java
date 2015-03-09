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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.activity.ActivityLivePlaying;
import br.liveo.activity.ActivityTypePhoto;
import br.liveo.activity.ActivityVideoPlaying;
import br.liveo.activity.ActivityWritePost;
import br.liveo.adapter.AdapterFeed;
import br.liveo.adapter.AdapterJsonFeed;
import br.liveo.model.Comment;
import br.liveo.model.Media;
import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;

public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    String mainFeed = "http://ihdmovie.xyz/main_feed.json";
    String allFeed = "http://ihdmovie.xyz/all_feed.json";
    ArrayList<Post> list = new ArrayList<Post>();
    AdapterJsonFeed adapter;
    AdapterFeed adapterJson;
    RelativeLayout layoutMenu;

    public AQuery aq;

    public FragmentMain newInstance(String text) {
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

                String mediaType = list.get(position).getImagePostUrl();

                Intent i = new Intent(getActivity(), ActivityTypePhoto.class);
                i.putExtra("url", mediaType);
                startActivity(i);
            }
        });


        adapter.OnItemLoveClick(new AdapterJsonFeed.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Love", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), ActivityLivePlaying.class);
                startActivity(i);
            }
        });

        adapter.OnItemShareClick(new AdapterJsonFeed.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ActivityVideoPlaying.class);
                startActivity(i);
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

        aq.ajax(mainFeed, JSONObject.class, this, "getJson");


        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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

        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
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
            if (mSearchCheck) {
                // implement your search here
            }
            return false;
        }
    };

    public void getJson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);
        if (jo != null) {
            JSONArray ja = jo.optJSONArray("posts");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.optJSONObject(i);

                String mediaType = obj.optString("media_type");

                String avatarId = "";
                String imagePhotoUrl = "";
                String extension = "";
                String imagePhotoFullUrl = "";
                String imageAvatarUrl = "";


                JSONObject media = obj.optJSONObject("media");

                Media mediaPhoto = new Media(null, null, null, null, extension, null, null, null, null, null, imagePhotoFullUrl);
                if (media != null) {
                    avatarId = media.optString("id");
                    imagePhotoUrl = media.optString("url");
                    extension = media.optString("extension");
                    imagePhotoFullUrl = "https://www.vdomax.com/" + imagePhotoUrl + "." + extension + "";

                    imageAvatarUrl = "https://graph.facebook.com/v2.1/" + avatarId + "/picture?type=large";
                }


                JSONObject author = obj.optJSONObject("author");
                String name = author.optString("name");

                //Log.d("Check",obj.toString());

                String name_title = obj.optString("type1");
                String loveCount = obj.optString("love_count");
                String number2 = obj.optString("follow_count");
                String commentCount = obj.optString("comment_count");
                String viewCount = obj.optString("view");
                String message = obj.optString("text");
                String date = obj.optString("timestamp");

                int commentNuber = Integer.parseInt(viewCount.toString());
                int num_comment = 0;

                if (commentNuber > 1000)
                    num_comment = commentNuber / 1000;
                String num_comment2 = num_comment + "k";

                String shortMessage;
                if (message.length() > 200)
                    shortMessage = message.substring(0, 199);
                else
                    shortMessage = message;


                ArrayList<Comment> comments = new ArrayList<>();

                if (Integer.parseInt(commentCount) > 0) {
                    JSONArray commentJsonArray = obj.optJSONArray("comment");

                    for (int a = 0; a < commentJsonArray.length(); a++) {
                        JSONObject commentJsonObject = commentJsonArray.optJSONObject(a);
                        String commentText = commentJsonObject.optString("text");
                        JSONObject accountJsonObject = commentJsonObject.optJSONObject("account");
                        String commentId = accountJsonObject.optString("id");
                        String commentName = accountJsonObject.optString("name");

                        Comment comment = new Comment(null, commentName, null, null, commentText, commentId);
                        comments.add(comment);
                    }

                }


                // Use view_count instead of share_count (share_count data is empty now)
                Post post = new Post(imageAvatarUrl, name, date, loveCount, commentCount, num_comment2
                        , message, shortMessage, viewCount, imagePhotoFullUrl, null);
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
