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
import vumobile.com.fitness.club.model.Services;

/**
 * Created by toukirul on 22/1/2018.
 */

public class AdapterServices extends RecyclerView.Adapter<AdapterServices.ServiceViewHolder> {

    private List<Services> servicesList;
    private Context mCOntext;

    public AdapterServices(Context context, List<Services> list){
        this.mCOntext = context;
        this.servicesList = list;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCOntext).inflate(R.layout.row_services, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {

        Services services = servicesList.get(position);

        holder.txtServiceTitle.setText(services.getBanglaServiceTitle());
        Glide.with(mCOntext).load(services.getImageUrl()).into(holder.imgService);

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        private TextView txtServiceTitle;
        private ImageView imgService;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            txtServiceTitle = (TextView) itemView.findViewById(R.id.txtServiceTitle);
            imgService = (ImageView) itemView.findViewById(R.id.imgService);
        }
    }
}
