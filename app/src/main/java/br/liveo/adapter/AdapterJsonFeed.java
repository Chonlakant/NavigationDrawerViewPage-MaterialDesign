package br.liveo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.liveo.activity.ActivityComment;
import br.liveo.activity.MainProfileFriends;
import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;
import br.liveo.widget.RoundedTransformation;

public class AdapterJsonFeed extends RecyclerView.Adapter<AdapterJsonFeed.ViewHolder> {

    private ArrayList<Post> list = new ArrayList<Post>();
    private static Context context;

    private OnItemClickListener mItemClickListener;
    private OnItemClickListener mItemLove;
    private OnItemClickListener mItemShare;

    public AdapterJsonFeed(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post item = list.get(position);

        holder.month.setText(item.getName());

        holder.date.setText(item.getDate());
        holder.nLove.setText(item.getLoveCount());
        holder.nComment.setText(item.getCommentCount());
        holder.nShare.setText(item.getShareCount());

        holder.msg.setText(Html.fromHtml("<strong><em>" + item.getMessage() + "</em></strong>"));

        Picasso.with(context)
                .load(item.getImageProfileUrl())
                .centerCrop()
                .resize(100, 100)
                .transform(new RoundedTransformation(50, 4))
                .into(holder.avatar);

        Picasso.with(context)
                .load(item.getThumbUrl())
                .fit().centerCrop()
                .into(holder.thumb);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView month;
        TextView date;
        TextView nLove;
        TextView nComment;
        TextView nShare;
        TextView msg;
        ImageView avatar;
        ImageView thumb;

        ButtonFlat btnLove;
        ButtonFlat btnComment;
        ButtonFlat btnShare;

        public ViewHolder(View view) {
            super(view);
            month = (TextView) view.findViewById(R.id.Aung);
            date = (TextView) view.findViewById(R.id.day);
            nLove = (TextView) view.findViewById(R.id.number1);
            nComment = (TextView) view.findViewById(R.id.number2);
            nShare = (TextView) view.findViewById(R.id.number3);
            msg = (TextView) view.findViewById(R.id.messng);
            // view = (TextView) v.findViewById(R.id.view);
            avatar = (ImageView) view.findViewById(R.id.imageView);
            thumb = (ImageView) view.findViewById(R.id.image_center);

            btnComment = (ButtonFlat) view.findViewById(R.id.btn_comment);
            btnLove = (ButtonFlat) view.findViewById(R.id.btn_love);
            btnShare = (ButtonFlat) view.findViewById(R.id.btn_share);

            thumb.setOnClickListener(this);
            avatar.setOnClickListener(this);
            btnComment.setOnClickListener(this);
            btnLove.setOnClickListener(this);
            btnShare.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.image_center:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                    break;
                case R.id.btn_comment:

                    Intent i = new Intent(context, ActivityComment.class);
                    context.startActivity(i);
                    break;
                case R.id.imageView:
                    Intent intent = new Intent(context, MainProfileFriends.class);
                    context.startActivity(intent);
                    break;
                case R.id.btn_love:
                    if (mItemLove != null) {
                        mItemLove.onItemClick(v, getPosition());
                    }
                    break;
                case R.id.btn_share:
                    if (mItemShare != null) {
                        mItemShare.onItemClick(v, getPosition());
                    }
                    break;


            }
        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemLoveClick {
        public void onItemClick(View view, int position);
    }
    public void OnItemLoveClick(final OnItemClickListener mItemLove) {
        this.mItemLove = mItemLove;
    }


    public interface OnItemShareClick {
        public void onItemClick(View view, int position);
    }
    public void OnItemShareClick(final OnItemClickListener mItemShare) {
        this.mItemShare = mItemShare;
    }
}