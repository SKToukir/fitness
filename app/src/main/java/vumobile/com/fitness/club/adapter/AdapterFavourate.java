package vumobile.com.fitness.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.model.favourite.Result;

/**
 * Created by toukirul on 25/1/2018.
 */

public class AdapterFavourate extends RecyclerView.Adapter<AdapterFavourate.VideoCatHolder>{

    private List<Result> catgoryItemsClassList;
    private Context mContext;

    public AdapterFavourate(Context context, List<Result> list){
        this.mContext = context;
        this.catgoryItemsClassList = list;
    }

    @Override
    public AdapterFavourate.VideoCatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_video_cat_content, parent, false);
        return new AdapterFavourate.VideoCatHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterFavourate.VideoCatHolder holder, int position) {

        Result catgoryItemsClass = catgoryItemsClassList.get(position);
        Glide.with(mContext).load(catgoryItemsClass.getImageUrl()).into(holder.imgContent);
        holder.txtTitle.setText(catgoryItemsClass.getContentTile());
        holder.txtView.setText(String.valueOf(catgoryItemsClass.getTotalView()));
        holder.txtLike.setText(String.valueOf(catgoryItemsClass.getTotalLike()));
    }

    @Override
    public int getItemCount() {
        return catgoryItemsClassList.size();
    }

    public class VideoCatHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtLike, txtView;
        private ImageView imgContent;
        public VideoCatHolder(View itemView) {
            super(itemView);
            imgContent = (ImageView) itemView.findViewById(R.id.img_item_video);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
            txtLike = (TextView) itemView.findViewById(R.id.txtTotalLikes);
            txtView = (TextView) itemView.findViewById(R.id.txtTotalView);
        }
    }
}
