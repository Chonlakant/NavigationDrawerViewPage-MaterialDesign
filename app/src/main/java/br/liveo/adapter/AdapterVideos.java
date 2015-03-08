package br.liveo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.model.Youtube;
import br.liveo.navigationviewpagerliveo.R;
import br.liveo.widget.RoundedTransformation;


public class AdapterVideos extends BaseAdapter implements AdapterView.OnClickListener {

    Context context;
    OnItemClickListener mItemClickListener;

    public ArrayList<Youtube> list = new ArrayList<Youtube>();

    public AdapterVideos(Context context, ArrayList<Youtube> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.item_videos, parent, false);
        TextView title;
        TextView detail;

        ImageView ImageUrl;
        ImageView image_title;

        Youtube item = list.get(position);
        title = (TextView) row.findViewById(R.id.textView10);
        detail = (TextView) row.findViewById(R.id.textView11);

        ImageUrl = (ImageView) row.findViewById(R.id.imageView10);
        image_title = (ImageView) row.findViewById(R.id.image_title);

        title.setText(item.getTitle());
        detail.setText(item.getDescription());


        Picasso.with(context)
                .load(item.getThumbnail())
                .into(image_title);

        Picasso.with(context)
                .load(item.getId())
                .transform(new RoundedTransformation(50, 4))
                .resize(100, 100)
                .into(ImageUrl);

        image_title.setOnClickListener(this);


        return row;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.image_title:
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view);
                }
                break;

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }




}

