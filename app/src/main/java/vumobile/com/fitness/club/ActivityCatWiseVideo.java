package vumobile.com.fitness.club;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.adapter.AdapterCatgoryItemVideo;
import vumobile.com.fitness.club.model.RelatedContentClass;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;
import vumobile.com.fitness.club.utils.SubscriptoClass;

public class ActivityCatWiseVideo extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recyclerCatItemList;
    private GridLayoutManager mLayoutmanager;
    private RecyclerView.Adapter adapter;
    private List<RelatedContentClass> catgoryItemsClassList = new ArrayList<>();
    private Call<List<RelatedContentClass>> callContents;
    private NetworkOperation networkOperation;
    public static String cat_code, title;
    private int c, it = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_wise_video);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        setColorOperatorWise();

        initUI();

        initRecycler();

        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(true);
                parseData(cat_code, 1);
            }
        });

        recyclerCatItemList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0){
                    c = mLayoutmanager.findLastVisibleItemPosition()+1;
                    if (c==catgoryItemsClassList.size() && !swipe_refresh.isRefreshing()){
                        it = it +1;
                        Log.d("CallThisLog",String.valueOf(it));
                        parseData(cat_code, it);
                    }
                }
            }
        });

        recyclerCatItemList.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerCatItemList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RelatedContentClass slider = catgoryItemsClassList.get(position);
                SubscriptoClass.cat_code_video = slider.getCatgorycode();
                SubscriptoClass.content_code_Video = slider.getContentcode();
                SubscriptoClass.physical_file_name = slider.getPhysicalfilename();
                SubscriptoClass.imageUrl_video = slider.getImageUrl();
                SubscriptoClass.like_video = slider.getTotalLike();
                SubscriptoClass.duration_video = slider.getDuration();
                SubscriptoClass.views_video = slider.getTotalView();
                SubscriptoClass.info_video = slider.getInfo();
                SubscriptoClass.genre_video = slider.getGenre();
                SubscriptoClass.title_info = slider.getContentTile();
                SubscriptoClass.video_type = slider.getType();
                SubscriptoClass.isVideo = true;
                new SubscriptoClass(ActivityCatWiseVideo.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setColorOperatorWise() {
        if (SplashActivity.MSISDN.startsWith("88019")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
        }
    }

    private void initUI() {

        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(this);
    }

    private void parseData(String catCode, int i) {
        Log.d("CategoryCode", catCode);
        adapter.notifyDataSetChanged();

        HashMap<String, String> map = new HashMap<>();
        map.put("CatCode",catCode);
        map.put("PageTotal", String.valueOf(i));

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callContents = networkOperation.getRelatedData(map);

        callContents.enqueue(new Callback<List<RelatedContentClass>>() {
            @Override
            public void onResponse(Call<List<RelatedContentClass>> call, Response<List<RelatedContentClass>> response) {
                swipe_refresh.setRefreshing(false);
                catgoryItemsClassList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RelatedContentClass>> call, Throwable t) {
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    private void initRecycler() {
        recyclerCatItemList = (RecyclerView) findViewById(R.id.recyclerCatItemList);
        adapter = new AdapterCatgoryItemVideo(this, catgoryItemsClassList);
        mLayoutmanager = new GridLayoutManager(this, 2);
        recyclerCatItemList.setLayoutManager(mLayoutmanager);
        recyclerCatItemList.setItemAnimator(new DefaultItemAnimator());
        recyclerCatItemList.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipe_refresh.setRefreshing(true);
        parseData(cat_code, 1);
    }
}
