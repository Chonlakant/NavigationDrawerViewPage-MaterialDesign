package br.liveo.adapter;

import android.content.Context;
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

import br.liveo.model.Post;
import br.liveo.navigationviewpagerliveo.R;
import br.liveo.widget.RoundedTransformation;


public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.ContactViewHolder>  {


    public static Context context;
    public static ArrayList<Post> list = new ArrayList<Post>();
    public static String url1 = "http://ihdmovie.xyz/root/api/feed_get.php?uid=1";
    public static   ContactViewHolder.OnItemClickListener mItemClickListener;

    public AdapterFeed(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public void onBindViewHolder(ContactViewHolder contactViewHolder, int position) {

        Post item = list.get(position);

        contactViewHolder.month.setText(item.getName());

        contactViewHolder.date.setText(item.getDate());
        contactViewHolder.number1.setText(item.getLoveCount());
        contactViewHolder.number2.setText(item.getCommentCount());
        contactViewHolder.number3.setText(item.getShareCount());
        //contactViewHolder.messen.setText(item.getMessen());




        contactViewHolder.messen.setText(Html.fromHtml("<strong><em>" + item.getMessage() + "</em></strong>"));


        Picasso.with(context)
                .load(item.getImageProfileUrl())
                .centerCrop()
                .resize(100, 100)
                .transform(new RoundedTransformation(50, 4))
                .into(contactViewHolder.ImageUrl);

        Picasso.with(context)
                .load(item.getImagePostUrl())
                .fit().centerCrop()
                .into(contactViewHolder.image_messen);


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);



        return new ContactViewHolder(itemView);
    }



    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        TextView month;
        TextView date;
        TextView number1;
        TextView number2;
        TextView number3;
        TextView messen;
        //TextView view;
        ImageView ImageUrl;
        ImageView image_messen;
        ButtonFlat btnComment;

        public ContactViewHolder(View v) {
            super(v);
//            vName =  (TextView) v.findViewById(R.id.txtName);
//            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
//            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
//            vTitle = (TextView) v.findViewById(R.id.title);


            month = (TextView) v.findViewById(R.id.Aung);
            date = (TextView) v.findViewById(R.id.day);
            number1 = (TextView) v.findViewById(R.id.number1);
            number2 = (TextView) v.findViewById(R.id.number2);
            number3 = (TextView) v.findViewById(R.id.number3);
            messen = (TextView) v.findViewById(R.id.messng);
            // view = (TextView) v.findViewById(R.id.view);
            ImageUrl = (ImageView) v.findViewById(R.id.imageView);
            image_messen = (ImageView) v.findViewById(R.id.image_center);

            btnComment = (ButtonFlat) v.findViewById(R.id.btn_comment);

            image_messen.setOnClickListener(this);
            ImageUrl.setOnClickListener(this);
            btnComment.setOnClickListener(this);

        }

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }


        @Override
        public void onClick(View view ) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }




        }



    }
}