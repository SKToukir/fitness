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
import vumobile.com.fitness.club.model.search.SearchClas;

/**
 * Created by toukirul on 24/1/2018.
 */

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.VideoCatHolder>{

    private List<SearchClas> catgoryItemsClassList;
    private Context mContext;

    public AdapterSearch(Context context, List<SearchClas> list){
        this.mContext = context;
        this.catgoryItemsClassList = list;
    }

    @Override
    public AdapterSearch.VideoCatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_video_cat_content, parent, false);
        return new AdapterSearch.VideoCatHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterSearch.VideoCatHolder holder, int position) {

        SearchClas catgoryItemsClass = catgoryItemsClassList.get(position);
        Glide.with(mContext).load(catgoryItemsClass.getImageUrl()).into(holder.imgContent);
        holder.txtTitle.setText(catgoryItemsClass.getContentTile());
        holder.txtView.setText(catgoryItemsClass.getTotalView());
        holder.txtLike.setText(catgoryItemsClass.getTotalLike());
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
