package vumobile.com.fitness.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.model.Slider;

/**
 * Created by toukirul on 21/1/2018.
 */

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder>{

    int lastPosition = -1;
    private List<Slider> sliderList;
    private Context mContext;

    public SliderAdapter(Context context, List<Slider> list){
        this.mContext = context;
        this.sliderList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Slider slider = sliderList.get(position);

        Glide.with(mContext).load(slider.getImageUrl()).into(holder.imgItem);

        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.zoom_in);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
        }
    }


}
