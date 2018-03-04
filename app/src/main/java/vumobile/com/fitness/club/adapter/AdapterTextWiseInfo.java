package vumobile.com.fitness.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.model.TextWiseService;

/**
 * Created by toukirul on 22/1/2018.
 */

public class AdapterTextWiseInfo extends RecyclerView.Adapter<AdapterTextWiseInfo.InfoViewHolder>{

    private List<TextWiseService> textWiseServiceList;
    private Context mContext;

    public AdapterTextWiseInfo(Context context, List<TextWiseService> list){
        this.mContext = context;
        this.textWiseServiceList = list;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_textwise_info, parent, false);

        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        TextWiseService textWiseService = textWiseServiceList.get(position);
        holder.txtTitle.setText(textWiseService.getTitle());
    }

    @Override
    public int getItemCount() {
        return textWiseServiceList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        public InfoViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtPostTitle);
        }
    }
}
