package br.liveo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.adapter.AdapterJsonFeed;
import br.liveo.model.Comment;
import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;
import br.liveo.widget.NotifyScrollView;

public class ProfileDetail extends ActionBarActivity implements NotifyScrollView.Callback {

    String url = "http://ihdmovie.xyz/root/api/feed_get.php?uid=1";
    String url2 = "http://ihdmovie.xyz/feed.json";
    String url3 = "http://ihdmovie.xyz/feed3.json";
    String urlMain = "http://ihdmovie.xyz/main_feed.json";
    ArrayList<Post> list = new ArrayList<Post>();
    AdapterJsonFeed adapter;
    RelativeLayout layoutMenu;

    public AQuery aq;



    private NotifyScrollView mNotifyScrollView;

    private FrameLayout mImageFrameLayout;
    private ImageView mImageView;

    private RelativeLayout mContentLinearLayout;

    private LinearLayout mToolbarLinearLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail);
        aq = new AQuery(getApplicationContext());
        adapter = new AdapterJsonFeed(getApplication(), list);
        // view matching
        mNotifyScrollView = (NotifyScrollView) findViewById(R.id.notify_scroll_view);

        mImageFrameLayout = (FrameLayout) findViewById(R.id.image_frame_layout);
        mImageView = (ImageView) findViewById(R.id.image_view);

        mContentLinearLayout = (RelativeLayout) findViewById(R.id.content_linear_layout);

        mToolbarLinearLayout = (LinearLayout) findViewById(R.id.toolbar_linear_layout);
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recList.setLayoutManager(llm);


        recList.setAdapter(adapter);

        // more setup
        setupNotifyScrollView();
        //setupToolbar();
        aq.ajax(urlMain, JSONObject.class, this, "getJson");
    }

    private void setupNotifyScrollView() {
        mNotifyScrollView.setCallback(this);

        ViewTreeObserver viewTreeObserver = mNotifyScrollView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // get size
                    int toolbarLinearLayoutHeight = mToolbarLinearLayout.getHeight();
                    int imageHeight = mImageView.getHeight();

                    // adjust image frame layout height
                    ViewGroup.LayoutParams layoutParams = mImageFrameLayout.getLayoutParams();
                    if (layoutParams.height != imageHeight) {
                        layoutParams.height = imageHeight;
                        mImageFrameLayout.setLayoutParams(layoutParams);
                    }

                    // adjust top margin of content linear layout
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mContentLinearLayout.getLayoutParams();
                    if (marginLayoutParams.topMargin != toolbarLinearLayoutHeight + imageHeight) {
                        marginLayoutParams.topMargin = toolbarLinearLayoutHeight + imageHeight;
                        mContentLinearLayout.setLayoutParams(marginLayoutParams);
                    }

                    // call onScrollChange to update initial properties.
                    onScrollChanged(0, 0, 0, 0);
                }
            });
        }
    }

    private void setupToolbar() {
        // set ActionBar as Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        // get scroll y
        int scrollY = mNotifyScrollView.getScrollY();

        // calculate new y (for toolbar translation)
        float newY = Math.max(mImageView.getHeight(), scrollY);

        // translate toolbar linear layout and image frame layout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mToolbarLinearLayout.setTranslationY(newY);
            mImageFrameLayout.setTranslationY(scrollY * 0.5f);
        } else {
            ViewCompat.setTranslationY(mToolbarLinearLayout, newY);
            ViewCompat.setTranslationY(mImageFrameLayout, scrollY * 0.5f);
        }
    }

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
                        , message, shortMessage, viewCount, imagePhotoFullUrl,null);
                post.setComments(comments);

                list.add(post);
            }
            adapter.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }


}