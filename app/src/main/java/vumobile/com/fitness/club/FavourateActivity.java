package vumobile.com.fitness.club;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.adapter.AdapterFavourate;
import vumobile.com.fitness.club.model.favourite.InfoServiceClass;
import vumobile.com.fitness.club.model.favourite.Result;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;
import vumobile.com.fitness.club.utils.SubscriptoClass;

public class FavourateActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    private RecyclerView recyclerViewFav;
    private GridLayoutManager mLayoutmanager;
    private List<Result> favList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private Call<InfoServiceClass> callFav;
    private NetworkOperation networkOperation;
    public static String TITLE = "";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourate);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(TITLE);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });


        if (SplashActivity.MSISDN.startsWith("88019")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
        }

        initRecycler();

        parseFavData();

        recyclerViewFav.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewFav, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Result slider = favList.get(position);
                SubscriptoClass.cat_code_video = slider.getCatgorycode();
                SubscriptoClass.content_code_Video = slider.getContentcode();
                SubscriptoClass.physical_file_name = slider.getPhysicalfilename();
                SubscriptoClass.imageUrl_video = slider.getImageUrl();
                SubscriptoClass.like_video = String.valueOf(slider.getTotalLike());
                SubscriptoClass.duration_video = slider.getDuration();
                SubscriptoClass.views_video = String.valueOf(slider.getTotalView());
                SubscriptoClass.info_video = slider.getInfo();
                SubscriptoClass.genre_video = slider.getGenre();
                SubscriptoClass.title_info = slider.getContentTile();
                SubscriptoClass.video_type = slider.getType();
                SubscriptoClass.isVideo = true;
                new SubscriptoClass(FavourateActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void parseFavData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN",SplashActivity.MSISDN);
        map.put("PageTotal","1");

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callFav = networkOperation.getFavourateList(map);

        callFav.enqueue(new Callback<InfoServiceClass>() {
            @Override
            public void onResponse(Call<InfoServiceClass> call, Response<InfoServiceClass> response) {
                pDialog.dismiss();
                favList.addAll(response.body().getResult());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<InfoServiceClass> call, Throwable t) {

            }
        });
    }

    private void initRecycler() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.show();

        recyclerViewFav = (RecyclerView) findViewById(R.id.recycler_favourate);
        adapter = new AdapterFavourate(this, favList);
        mLayoutmanager = new GridLayoutManager(this,2);
        recyclerViewFav.setLayoutManager(mLayoutmanager);
        recyclerViewFav.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFav.setAdapter(adapter);

    }
}
