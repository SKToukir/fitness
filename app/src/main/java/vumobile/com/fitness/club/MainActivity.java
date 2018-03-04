package vumobile.com.fitness.club;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.adapter.AdapterCatgoryItemVideo;
import vumobile.com.fitness.club.adapter.AdapterHealthy;
import vumobile.com.fitness.club.adapter.AdapterServices;
import vumobile.com.fitness.club.adapter.SliderAdapter;
import vumobile.com.fitness.club.model.RelatedContentClass;
import vumobile.com.fitness.club.model.RelatedHealthyClass;
import vumobile.com.fitness.club.model.Result;
import vumobile.com.fitness.club.model.Services;
import vumobile.com.fitness.club.model.Slider;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;
import vumobile.com.fitness.club.utils.SubscriptoClass;
import vumobile.com.fitness.club.utils.TokenPreference;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , SearchView.OnQueryTextListener, View.OnFocusChangeListener, View.OnClickListener {

    private PendingIntent pendingIntent;
    private Button btnUnSubsCribe, btnSubscription;
    private ScrollView scroll;
    private DrawerLayout drawer;
    private ProgressDialog pDialog = null;
    private Toolbar toolbar;
    private AdapterCatgoryItemVideo adapterCatWise;
    private AdapterHealthy adapterHealthy;
    private List<RelatedContentClass> relatedDailyWorkOutList = new ArrayList<>();
    private List<RelatedHealthyClass> relatedContentClassHealthyList = new ArrayList<>();
    private Call<List<RelatedContentClass>> callVideoContents;
    private Call<List<RelatedHealthyClass>> callHealthyVideo;
    private RelativeLayout layoutDailyExercise, layoutInfoTainment, layoutWorkOut, layoutHealthy, layout_nav;
    private LinearLayout layoutFooterOne, layoutFooterTwo;
    private RecyclerView.LayoutManager mLayoutManager, infoLayoutManager, mLayoutManagerHealthy;
    private RecyclerView slidingRecycler, recyclerDailyExercise, recyclerDailyInfo, recyclerWorkOut, recyclerHealthyReceipy;
    private RecyclerView.Adapter adapterSliding, adapterRecyclerDailyExercise, adapterInfo;
    private List<Slider> sliderList = new ArrayList<>();
    private List<Services> servicesList = new ArrayList<>();
    private List<Services> infoList = new ArrayList<>();
    private Call<List<Services>> callServices;
    private Call<List<Slider>> getSliderData;
    private NetworkOperation networkOperation;
    private TextView txtMoreWorkOut, txtMoreCelebrityWorkOut, txtSubscription;
    private LinearLayout txtHome, txtWLEV, txtDYP, txtHRC, txtFT, txtHT, txtCWO, txtHR, txtFAV;
    private ImageView imgFaq, imgHome;
    private SearchView searchView;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ফিটনেস ক্লাব");

        try{
            if (TokenPreference.getToken(this).equals("null")){
                token = FirebaseInstanceId.getInstance().getToken();
                TokenPreference.storeToken(this,token);
                storeTokenIntoServer(token);
            }else {
                token = TokenPreference.getToken(this);
                storeTokenIntoServer(token);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initUI();

        setColorOperatorWise();

        initHealthyReceipyRecycler();
        parseHealthyReceipyData();

        initRecyclerWorkOut();
        parseWorkOutVideo();

        initSliderRecycler();
        parseSliderApi();

        initRecyclerExercise();
        parseDailyExercise();

        checkSubsCription();

        slidingRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), slidingRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Slider slider = sliderList.get(position);
                SubscriptoClass.cat_code_video = slider.getCatgorycode();
                SubscriptoClass.content_code_Video = slider.getContentcode();
                SubscriptoClass.physical_file_name = slider.getPhysicalfilename();
                SubscriptoClass.imageUrl_video = slider.getImageUrl();
                SubscriptoClass.like_video = slider.getTotalLike();
                SubscriptoClass.duration_video = slider.getDuration();
                SubscriptoClass.views_video = slider.getTotalView();
                SubscriptoClass.info_video = slider.getInfo();
                SubscriptoClass.genre_video = slider.getGenre();
                SubscriptoClass.video_type = slider.getType();
                SubscriptoClass.title_info = slider.getContentTile();
                SubscriptoClass.isVideo = true;
                new SubscriptoClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerDailyInfo.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerDailyInfo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Services services = infoList.get(position);
                SubscriptoClass.title_info = services.getBanglaServiceTitle();
                SubscriptoClass.service_id_info = services.getServiceID();
                SubscriptoClass.content_id_info = "";
                SubscriptoClass.isVideo = false;
                new SubscriptoClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerDailyExercise.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerDailyExercise, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Services services = servicesList.get(position);
                ActivityCatWiseVideo.cat_code = services.getCategoryCode();
                ActivityCatWiseVideo.title = services.getBanglaServiceTitle();
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerHealthyReceipy.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerHealthyReceipy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RelatedHealthyClass slider = relatedContentClassHealthyList.get(position);
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
                new SubscriptoClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerWorkOut.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerWorkOut, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RelatedContentClass slider = relatedDailyWorkOutList.get(position);
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
                new SubscriptoClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // Bangla notification process
//        try {
//
//            Intent serviceIntent = new Intent(MainActivity.this, NetworkedService.class);
//            startService(serviceIntent);
//            Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
//
//            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 90 * 1000, pendingIntent);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    private void storeTokenIntoServer(String token) {
        Log.d("Tokenn", "Store "+token+"\nMSISDN "+SplashActivity.MSISDN);

        JSONObject js = new JSONObject();
        try {
            js.put("Token", token);
            js.put("ServiceName", "FF");
            js.put("Msisdn", SplashActivity.MSISDN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, "https://wap.shabox.mobi/globalfbase/api/android/posttoken", js,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Responsee", ""+response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Responsee",""+error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void checkSubsCription() {

        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN", SplashActivity.MSISDN);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        Call<Result> checkSub = networkOperation.checkSubscription(map);

        checkSub.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("Subscription", response.body().getResult());
                if (response.body().getResult().equalsIgnoreCase("Deactive")) {
                    btnUnSubsCribe.setVisibility(View.GONE);
                    btnSubscription.setVisibility(View.VISIBLE);
                    txtSubscription.setVisibility(View.VISIBLE);
                } else {
                    txtSubscription.setVisibility(View.GONE);
                    btnSubscription.setVisibility(View.GONE);
                    btnUnSubsCribe.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public void unSubscribe() {
        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN", SplashActivity.MSISDN);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        Call<Result> subscribe = networkOperation.unSubscribeUser(map);

        subscribe.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String result = response.body().getResult();

                Log.d("Response", result);

                if (result.equalsIgnoreCase("Success")) {
                    Toast.makeText(MainActivity.this, "Unsubscribed user!", Toast.LENGTH_LONG).show();
                    btnUnSubsCribe.setVisibility(View.GONE);
                    btnSubscription.setVisibility(View.VISIBLE);
                    txtSubscription.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private void setColorOperatorWise() {

        layoutFooterOne = (LinearLayout) findViewById(R.id.layoutFooterOne);
        layoutFooterTwo = (LinearLayout) findViewById(R.id.layoutFooterTwo);


        layoutDailyExercise = (RelativeLayout) findViewById(R.id.layoutDailyExercise);
        layoutInfoTainment = (RelativeLayout) findViewById(R.id.layoutInfoTainment);
        layoutWorkOut = (RelativeLayout) findViewById(R.id.layoutWorkOut);
        layoutHealthy = (RelativeLayout) findViewById(R.id.layoutHealthy);
        layout_nav = (RelativeLayout) findViewById(R.id.layout_nav);

        if (SplashActivity.MSISDN.startsWith("88019")) {
            btnSubscription.setBackgroundColor(getResources().getColor(R.color.other_color));
            btnUnSubsCribe.setBackgroundColor(getResources().getColor(R.color.other_color));
            layout_nav.setBackgroundColor(getResources().getColor(R.color.other_color));
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutDailyExercise.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutInfoTainment.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutWorkOut.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutHealthy.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutFooterOne.setBackgroundColor(getResources().getColor(R.color.other_color));
            layoutFooterTwo.setBackgroundColor(getResources().getColor(R.color.other_color));
        }

    }

    private void parseHealthyReceipyData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("CatCode", Api.CAT_CODE_HEALTHY_RECEIPY);
        map.put("PageTotal", String.valueOf(1));

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callHealthyVideo = networkOperation.getRelatedHealthyData(map);

        callHealthyVideo.enqueue(new Callback<List<RelatedHealthyClass>>() {
            @Override
            public void onResponse(Call<List<RelatedHealthyClass>> call, Response<List<RelatedHealthyClass>> response) {
                relatedContentClassHealthyList.addAll(response.body().subList(0, 5));
                adapterHealthy.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RelatedHealthyClass>> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });

    }

    private void initHealthyReceipyRecycler() {
        recyclerHealthyReceipy = (RecyclerView) findViewById(R.id.recyclerHealthyReceipy);
        adapterHealthy = new AdapterHealthy(this, relatedContentClassHealthyList);
        mLayoutManagerHealthy = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerHealthyReceipy.setLayoutManager(mLayoutManagerHealthy);
        recyclerHealthyReceipy.setItemAnimator(new DefaultItemAnimator());
        recyclerHealthyReceipy.setAdapter(adapterHealthy);
    }

    private void parseWorkOutVideo() {

        HashMap<String, String> map = new HashMap<>();
        map.put("CatCode", Api.CAT_CODE_WORKOUT);
        map.put("PageTotal", String.valueOf(1));

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callVideoContents = networkOperation.getRelatedData(map);

        callVideoContents.enqueue(new Callback<List<RelatedContentClass>>() {
            @Override
            public void onResponse(Call<List<RelatedContentClass>> call, Response<List<RelatedContentClass>> response) {
                relatedDailyWorkOutList.addAll(response.body().subList(0, 5));
                adapterCatWise.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RelatedContentClass>> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    private void initRecyclerWorkOut() {
        recyclerWorkOut = (RecyclerView) findViewById(R.id.recyclerWorkOut);
        adapterCatWise = new AdapterCatgoryItemVideo(this, relatedDailyWorkOutList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerWorkOut.setLayoutManager(mLayoutManager);
        recyclerWorkOut.setItemAnimator(new DefaultItemAnimator());
        recyclerWorkOut.setAdapter(adapterCatWise);
    }

    private void initUI() {

        btnSubscription = (Button) findViewById(R.id.btnSubscription);
        btnSubscription.setOnClickListener(this);
        txtSubscription = (TextView) findViewById(R.id.txtSubscription);

        btnUnSubsCribe = (Button) findViewById(R.id.btnUnsubscribe);
        btnUnSubsCribe.setOnClickListener(this);

        scroll = (ScrollView) findViewById(R.id.scroll);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgFaq = (ImageView) findViewById(R.id.imgFaq);

        imgFaq.setOnClickListener(this);
        imgHome.setOnClickListener(this);

        txtHome = (LinearLayout) findViewById(R.id.txtHome);
        txtWLEV = (LinearLayout) findViewById(R.id.txtWLEV);
        txtDYP = (LinearLayout) findViewById(R.id.txtDYP);
        txtHRC = (LinearLayout) findViewById(R.id.txtHRC);
        txtFT = (LinearLayout) findViewById(R.id.txtFT);
        txtHT = (LinearLayout) findViewById(R.id.txtHT);
        txtCWO = (LinearLayout) findViewById(R.id.txtCWO);
        txtHR = (LinearLayout) findViewById(R.id.txtHR);
        txtFAV = (LinearLayout) findViewById(R.id.txtFAV);

        txtHome.setOnClickListener(this);
        txtWLEV.setOnClickListener(this);
        txtDYP.setOnClickListener(this);
        txtHRC.setOnClickListener(this);
        txtFT.setOnClickListener(this);
        txtHT.setOnClickListener(this);
        txtCWO.setOnClickListener(this);
        txtHR.setOnClickListener(this);
        txtFAV.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.show();


        txtMoreCelebrityWorkOut = (TextView) findViewById(R.id.txtMoreHealthyReceipy);
        txtMoreWorkOut = (TextView) findViewById(R.id.txtMoreWorkOut);

        txtMoreCelebrityWorkOut.setOnClickListener(this);
        txtMoreWorkOut.setOnClickListener(this);

    }

    private void parseDailyExercise() {

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callServices = networkOperation.getAllServices();

        callServices.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>> response) {
                Services serviceDaily, serviceInfo;
                servicesList.clear();
                infoList.clear();
                for (int i = 0; i < response.body().size(); i++) {

                    if (response.body().get(i).getType().equalsIgnoreCase("Video")) {
                        serviceDaily = new Services();
                        serviceDaily.setTitle(response.body().get(i).getTitle());
                        serviceDaily.setBanglaServiceTitle(response.body().get(i).getBanglaServiceTitle());
                        serviceDaily.setImageUrl(response.body().get(i).getImageUrl());
                        serviceDaily.setServiceID(response.body().get(i).getServiceID());
                        serviceDaily.setCategoryCode(response.body().get(i).getCategoryCode());
                        serviceDaily.setType(response.body().get(i).getType());
                        servicesList.add(serviceDaily);
                    } else if (response.body().get(i).getType().equalsIgnoreCase("Text")) {
                        serviceInfo = new Services();
                        serviceInfo.setTitle(response.body().get(i).getTitle());
                        serviceInfo.setBanglaServiceTitle(response.body().get(i).getBanglaServiceTitle());
                        serviceInfo.setImageUrl(response.body().get(i).getImageUrl());
                        serviceInfo.setServiceID(response.body().get(i).getServiceID());
//                        serviceInfo.setCategoryCode(response.body().get(i).getCategoryCode());
                        serviceInfo.setType(response.body().get(i).getType());
                        infoList.add(serviceInfo);
                    }

                }
                adapterRecyclerDailyExercise.notifyDataSetChanged();
                adapterInfo.notifyDataSetChanged();

//                servicesList.addAll(response.body().subList(0,3));
//                adapterRecyclerDailyExercise.notifyDataSetChanged();
//
//                infoList.addAll(response.body().subList(3,5));
//                adapterInfo.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerExercise() {

        adapterRecyclerDailyExercise = new AdapterServices(this, servicesList);
        adapterInfo = new AdapterServices(this, infoList);

        recyclerDailyExercise = (RecyclerView) findViewById(R.id.recyclerDailyExercise);
        recyclerDailyInfo = (RecyclerView) findViewById(R.id.recyclerDailyInfo);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        infoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerDailyExercise.setLayoutManager(mLayoutManager);
        recyclerDailyExercise.setItemAnimator(new DefaultItemAnimator());

        recyclerDailyInfo.setLayoutManager(infoLayoutManager);
        recyclerDailyInfo.setItemAnimator(new DefaultItemAnimator());

        recyclerDailyExercise.setAdapter(adapterRecyclerDailyExercise);
        recyclerDailyInfo.setAdapter(adapterInfo);
    }

    private void parseSliderApi() {
        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        getSliderData = networkOperation.getAllSlideData();

        getSliderData.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                hideDialog();
                sliderList.addAll(response.body());
                adapterSliding.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Log.d("Response", "Error " + t.getMessage());
            }
        });
    }

    private void initSliderRecycler() {
        adapterSliding = new SliderAdapter(this, sliderList);
        slidingRecycler = (RecyclerView) findViewById(R.id.slidingRecycler);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        slidingRecycler.setLayoutManager(mLayoutManager);
        slidingRecycler.setItemAnimator(new DefaultItemAnimator());
        slidingRecycler.setAdapter(adapterSliding);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.ic_action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        // hide keyboard
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (query != null && !query.isEmpty()) {
            SearchActivity.SEARCH_TEXT = query;
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to search!", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtMoreWorkOut:
                ActivityCatWiseVideo.cat_code = Api.CAT_CODE_WORKOUT;
                ActivityCatWiseVideo.title = "সেলিব্রিটি ওয়ার্ক আউট ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtMoreHealthyReceipy:
                ActivityCatWiseVideo.cat_code = Api.CAT_CODE_HEALTHY_RECEIPY;
                ActivityCatWiseVideo.title = "হেলদি রেসিপি ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtHome:
                drawer.closeDrawers();
                break;
            case R.id.txtWLEV:
                drawer.closeDrawers();
                ActivityCatWiseVideo.cat_code = Api.CAT_WET_LOSE_EXERCISE;
                ActivityCatWiseVideo.title = "ওয়েট লস এক্সারসাইজ ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtDYP:
                drawer.closeDrawers();
                ActivityCatWiseVideo.cat_code = Api.CAT_YOGA_PRACTICE;
                ActivityCatWiseVideo.title = " ডেইলি ইয়োগা প্রাকটিস ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtHRC:
                drawer.closeDrawers();
                ActivityCatWiseVideo.cat_code = Api.CAT_HOME_REMEDY_CHANNEL;
                ActivityCatWiseVideo.title = " হোম রেমেডি চ্যানেল ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtFT:
                drawer.closeDrawers();
                SubscriptoClass.title_info = "ফুড টিপস";
                SubscriptoClass.service_id_info = "46";
                SubscriptoClass.content_id_info = "";
                SubscriptoClass.isVideo = false;
                new SubscriptoClass(MainActivity.this).checkSubscription();
                break;
            case R.id.txtHT:
                drawer.closeDrawers();
                SubscriptoClass.title_info = "হেলথ টিপস";
                SubscriptoClass.service_id_info = "44";
                SubscriptoClass.content_id_info = "";
                SubscriptoClass.isVideo = false;
                new SubscriptoClass(MainActivity.this).checkSubscription();
                break;
            case R.id.txtCWO:
                drawer.closeDrawers();
                ActivityCatWiseVideo.cat_code = Api.CAT_CELEBRITY;
                ActivityCatWiseVideo.title = " সেলিব্রিটি ওয়ার্ক আউট ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtHR:
                drawer.closeDrawers();
                ActivityCatWiseVideo.cat_code = Api.CAT_HEALTHY;
                ActivityCatWiseVideo.title = " হেলদি রেসিপি ভিডিও";
                startActivity(new Intent(MainActivity.this, ActivityCatWiseVideo.class));
                break;
            case R.id.txtFAV:
                drawer.closeDrawers();
                if (!SplashActivity.MSISDN.equalsIgnoreCase("wifi")) {
                    FavourateActivity.TITLE = " ফেভারিট";
                    startActivity(new Intent(MainActivity.this, FavourateActivity.class));
                } else {
                    showAlert();
                }
                break;
            case R.id.imgFaq:
                startActivity(new Intent(MainActivity.this, FaqActivity.class));
                break;
            case R.id.imgHome:
                scroll.pageScroll(View.FOCUS_UP);
                break;
            case R.id.btnUnsubscribe:
                unSubscribe();
                break;
            case R.id.btnSubscription:
                Log.d("Subscription","button click");
                if (SplashActivity.MSISDN.equalsIgnoreCase("wifi")){
                    Log.d("Subscription","wifi user");
                    showAlert();
                }else if (SplashActivity.MSISDN.startsWith("88019") || SplashActivity.MSISDN.startsWith("88018") || SplashActivity.MSISDN.startsWith("88016")){
                    subscribeUser();
                }else {
                    Log.d("Subscription","else no operator");
                    showAlert();
                }
                break;
        }
    }

    private void subscribeUser() {
        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN", SplashActivity.MSISDN);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        Call<Result> subscribe = networkOperation.subscribeUser(map);

        subscribe.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String result = response.body().getResult();

                if (result.startsWith("http")) {
                    Log.d("Subscription", "Show subscription dialog");
                    showSubsCriptionDialog(result);
                } else {
                    return;
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private void showSubsCriptionDialog(String result) {
        final Dialog dialogMNO = new Dialog(MainActivity.this,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.subscription_dialog);
        dialogMNO.setCancelable(true);

        WebView webView = (WebView) dialogMNO.findViewById(R.id.webviewSubcription);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("Subscription", url);
                if (url != null) {
                    if (SplashActivity.MSISDN.startsWith("88019")) {
                        if (url.contains("isChargeSuccess=0")) {
                            Log.d("Subscription", "Subscribed");
                            btnSubscription.setVisibility(View.GONE);
                            txtSubscription.setVisibility(View.GONE);
                            btnUnSubsCribe.setVisibility(View.VISIBLE);
                            putUserInfoSubscription();
                            Toast.makeText(MainActivity.this, "Successfully subscribed!", Toast.LENGTH_LONG).show();
                            dialogMNO.dismiss();
                        } else if (url.contains("msisdn=")) {
                            Log.d("Subscription", "Subscription failed");
                            dialogMNO.dismiss();
                        }
                    } else if (SplashActivity.MSISDN.startsWith("88018")) {
                        if (url.contains("resultCode=-1")) {
                            Log.d("working", "1");
                            dialogMNO.dismiss();
                        } else if (url.contains("resultCode=0")) {
                            dialogMNO.dismiss();
                            btnSubscription.setVisibility(View.GONE);
                            txtSubscription.setVisibility(View.GONE);
                            btnUnSubsCribe.setVisibility(View.VISIBLE);
                            // store user info log after successfull subscription
                            putUserInfoSubscription();
                            Toast.makeText(MainActivity.this, "Successfully subscribed!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(result);
        dialogMNO.show();
    }

    public void hideDialog(){
        if (pDialog!=null){
            pDialog.dismiss();
            pDialog = null;
        }
    }


    private void showAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please use Robi/Airtel/Banglalink apn!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","callOnResume");
        checkSubsCription();
        //setColorOperatorWise();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onResume","callOnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void putUserInfoSubscription() {

        Log.d("UserInfos", SplashActivity.DEVICE_ID+"\n"
                + SplashActivity.MANUFACTURE+"\n"
                + SplashActivity.DEVICE_NAME+"\n"
                + SplashActivity.DEVICE_MODEL+"\n"
                + SplashActivity.MSISDN);


        Map<String, String> params = new HashMap();
        params.put("MSISDN", SplashActivity.MSISDN);
        params.put("DeviceModel", SplashActivity.DEVICE_MODEL);
        params.put("DeviceName", SplashActivity.DEVICE_NAME);
        params.put("DeviceManufacture", SplashActivity.MANUFACTURE);
        params.put("Type", "Click");
        params.put("AppName", "FitnessClub");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "https://wap.shabox.mobi/bdnewapi/DataOther/Log", parameters, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.d("SuccessLog",response.toString());
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("SuccessLog",error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonRequest);

    }
}
