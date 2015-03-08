package br.liveo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import br.liveo.activity.ActivityLivePlaying;
import br.liveo.model.live;
import br.liveo.navigationviewpagerliveo.R;


public class AdapterLivieChannels extends RecyclerView.Adapter<AdapterLivieChannels.ViewHolder> {


    ArrayList<live> list = new ArrayList<live>();
    public static Context context;

    OnItemClickListener mItemClickListener;


    public AdapterLivieChannels(Context context, ArrayList<live> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.cardview_channels, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        live item = list.get(position);

        holder.name_user.setText(item.getNameLive());




        Picasso.with(context)
                .load(item.getPhotoLive())
                .into(holder.image_title_user);

        Picasso.with(context)
                .load(item.getThumb())
                .into(holder.image_short_live);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name_user;

        ImageView image_title_user;
        ImageView image_short_live;


        TextView vName, vSex, vId, vAge;

        public ViewHolder(View view) {
            super(view);
            name_user = (TextView) view.findViewById(R.id.name_user);

            image_title_user = (ImageView) view.findViewById(R.id.image_title_user);
            image_short_live = (ImageView) view.findViewById(R.id.image_short_live);





            image_title_user.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.image_center:

                    Intent i =new Intent(context, ActivityLivePlaying.class);
                    context.startActivity(i);

            }
        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    /*
     * Snippet from http://stackoverflow.com/a/363692/1008278
     */
    public static int randInt(int min, int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /* ==========This Part is not necessary========= */

}