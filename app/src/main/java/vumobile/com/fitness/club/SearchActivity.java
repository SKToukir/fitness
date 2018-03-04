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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vumobile.com.fitness.club.adapter.AdapterSearch;
import vumobile.com.fitness.club.model.search.SearchClas;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;
import vumobile.com.fitness.club.utils.SubscriptoClass;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static String SEARCH_TEXT = "";
    private Toolbar toolbar;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recyclerSearch;
    private GridLayoutManager mLayoutmanager;
    private RecyclerView.Adapter adapter;
    private SearchClas searchClas;
    private List<SearchClas> searchList = new ArrayList<>();
    private int c, it = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
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
                parseData(SEARCH_TEXT, 1);
            }
        });

        recyclerSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0){
                    c = mLayoutmanager.findLastVisibleItemPosition()+1;
                    if (c==searchList.size() && !swipe_refresh.isRefreshing()){
                        it = it +1;
                        Log.d("CallThisLog",String.valueOf(it));
                        parseData(SEARCH_TEXT, it);
                    }
                }
            }
        });

        recyclerSearch.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerSearch, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SearchClas slider = searchList.get(position);
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
                new SubscriptoClass(SearchActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initRecycler() {
        recyclerSearch = (RecyclerView) findViewById(R.id.recyclerSearchList);
        adapter = new AdapterSearch(this, searchList);
        mLayoutmanager = new GridLayoutManager(this, 2);
        recyclerSearch.setLayoutManager(mLayoutmanager);
        recyclerSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerSearch.setAdapter(adapter);
    }

    private void initUI() {

        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(this);
    }

    private void parseData(final String searchText, final int i) {

        JSONObject js = new JSONObject();
        try {
            js.put("Title",searchText);
            js.put("PageNumber",String.valueOf(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, "https://wap.shabox.mobi/apifitness/api/fitness/Search", js, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                swipe_refresh.setRefreshing(false);
                Log.d("Response", response.toString());
                for (int i = 0; i < response.length(); i++){
                    searchClas = new SearchClas();
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (!object.getString("ContentTile").equalsIgnoreCase("null")){
                            searchClas.setCatgorycode(object.getString("catgorycode"));
                            searchClas.setContentcode(object.getString("contentcode"));
                            searchClas.setPhysicalfilename(object.getString("physicalfilename"));
                            searchClas.setImageUrl(object.getString("imageUrl"));
                            searchClas.setTotalLike(object.getString("totalLike"));
                            searchClas.setTotalView(object.getString("totalView"));
                            searchClas.setDuration(object.getString("duration"));
                            searchClas.setInfo(object.getString("Info"));
                            searchClas.setGenre(object.getString("Genre"));
                            searchClas.setType(object.getString("Type"));
                            searchClas.setContentTile(object.getString("ContentTile"));

                            searchList.add(searchClas);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.getMessage());
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void setColorOperatorWise() {
        if (SplashActivity.MSISDN.startsWith("88019")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
        }
    }

    @Override
    public void onRefresh() {
        swipe_refresh.setRefreshing(true);
        parseData(SEARCH_TEXT, 1);
    }
}
