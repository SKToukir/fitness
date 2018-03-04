package vumobile.com.fitness.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.model.RelatedContentClass;

/**
 * Created by toukirul on 21/1/2018.
 */

public class RelatedContentAdapter extends RecyclerView.Adapter<RelatedContentAdapter.MyViewHolder> {

    private List<RelatedContentClass> relatedContentClassList;
    private Context mContext;

    public RelatedContentAdapter(Context context, List<RelatedContentClass> list){
        this.mContext = context;
        this.relatedContentClassList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_related, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        RelatedContentClass relatedContentClass = relatedContentClassList.get(position);

        Log.d("Data",relatedContentClass.getArtist());

        holder.txtTitle.setText(relatedContentClass.getContentTile());
        holder.txtLike.setText(relatedContentClass.getTotalLike());
        holder.txtView.setText(relatedContentClass.getTotalView());

        Glide.with(mContext).load(relatedContentClass.getImageUrl()).into(holder.imgContent);

    }

    @Override
    public int getItemCount() {
        return relatedContentClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtLike, txtView, txtTitle;
        private ImageView imgContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
            txtLike = (TextView) itemView.findViewById(R.id.txtTotalLikes);
            txtView = (TextView) itemView.findViewById(R.id.txtTotalView);
            imgContent = (ImageView) itemView.findViewById(R.id.img_itemsss);
        }
    }
}
