package vumobile.com.fitness.club;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.adapter.RelatedContentAdapter;
import vumobile.com.fitness.club.model.RelatedContentClass;
import vumobile.com.fitness.club.model.Result;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;
import vumobile.com.fitness.club.utils.SubscriptoClass;

public class VideoViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private String VIDEO_CLIPS = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20clips/D480x320/";
    private String FULL_VIDEO = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/";

    private int it = 1;
    String video_url;
    private SwipeRefreshLayout swipe_refresh_layout_cat_wise;
    private RecyclerView recycler_view_related_items;
    private RecyclerView.Adapter adapter;
    private RelatedContentClass relatedContentClass;
    private List<RelatedContentClass> relatedContentClassList = new ArrayList<>();
    private Call<List<RelatedContentClass>> callRelatedContents;
    private Call<Result> callResult;
    private NetworkOperation networkOperation;
    private GridLayoutManager mLayoutManager;

    private ImageView imgVideoFrame, imgPlayButton, imgLoading;
    //  private JWPlayerFragment fragment;
    private MediaController mediaController;
    private int c;
    private FullscreenVideoLayout videoView;
    private ImageView btnLike, btnFvrt;
    private TextView txtTitle, txtTotalViews, txtTotalLikes, txtInfo, txtGenre, txtDuration;
    public static String cat_code, content_code, imageUrl, like, views, info, genre, duration, title, physical_file_name, video_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initUI();


        swipe_refresh_layout_cat_wise.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout_cat_wise.setRefreshing(true);
                parseRelatedItems(1, cat_code);
            }
        });

        recycler_view_related_items.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    c = mLayoutManager.findLastVisibleItemPosition() + 1;
                    if (c == relatedContentClassList.size() && !swipe_refresh_layout_cat_wise.isRefreshing()) {
                        it = it + 1;
                        Log.d("CallThisLog", "171");
                        parseRelatedItems(it, cat_code);
                    }
                }
            }
        });

        recycler_view_related_items.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_related_items, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RelatedContentClass slider = relatedContentClassList.get(position);
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
                new SubscriptoClass(VideoViewActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        // parse data from notification utils
        try {
            String notificationString = getIntent().getStringExtra("notific");

            if (notificationString.equalsIgnoreCase("1")) {
                VideoViewActivity.title = getIntent().getStringExtra("ContentTile");
                VideoViewActivity.video_type = getIntent().getStringExtra("Type");
                VideoViewActivity.imageUrl = getIntent().getStringExtra("imageUrl");
                VideoViewActivity.content_code = getIntent().getStringExtra("contentcode");
                VideoViewActivity.cat_code = getIntent().getStringExtra("cat_code");
                VideoViewActivity.physical_file_name = getIntent().getStringExtra("physicalfilename");
                VideoViewActivity.like = getIntent().getStringExtra("totalLike");
                VideoViewActivity.views = getIntent().getStringExtra("totalView");
                VideoViewActivity.duration = getIntent().getStringExtra("duration");
                VideoViewActivity.info = getIntent().getStringExtra("Info");
                VideoViewActivity.genre = getIntent().getStringExtra("Genre");
                String notific = getIntent().getStringExtra("notific");
                Log.d("Notification", notificationString);

                txtTitle.setText(title);
                txtTitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));
                txtTotalLikes.setText(like);
                txtTotalViews.setText(views);
                txtInfo.setText(info);
                txtGenre.setText(genre);
                txtDuration.setText(duration);

                Glide.with(getApplicationContext()).load(imageUrl).into(imgVideoFrame);

                //TODO
                // store notification click log to server
                storeNotificationClickLog();
            }

        } catch (NullPointerException e) {
            Log.d("Exception", "" + e.getMessage());
            e.printStackTrace();
        }


        parseRelatedItems(1, cat_code);

        imgPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPlayButton.setVisibility(View.GONE);
                imgVideoFrame.setVisibility(View.GONE);
                playVideo();

            }
        });

        // playVideo();
    }

    private void storeNotificationClickLog() {

    }

    private void playVideo() {

        i += 1;
        int view = Integer.valueOf(views);
        view += 1;
        views = String.valueOf(view);
        txtTotalViews.setText(views);
        try {
            postFavViewLike("view", SplashActivity.MSISDN);
            //giveView("wifi","View",ContentCode);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (video_type.equalsIgnoreCase("VD")) {
            video_url = VIDEO_CLIPS + physical_file_name + ".mp4";
        } else if (video_type.equalsIgnoreCase("FV")) {
            video_url = FULL_VIDEO + physical_file_name + ".mp4";
        }


        Log.d("Video_Url", video_url);
        Uri uri = Uri.parse(video_url);
        try {
            videoView.setVideoURI(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                if (mediaPlayer.isPlaying()) {
                    imgVideoFrame.setVisibility(View.GONE);
                    videoView.hideControls();
                }
            }
        });
//        PlayerConfig playerConfig = new PlayerConfig.Builder()
//                .file(video_url).controls(true).timeSliderAbove(true).image(imageUrl)
//                .autostart(false)
//                .logoPosition(PlayerConfig.LOGO_POSITION_BOTTOM_RIGHT).logoHide(true)
//                .build();
//        fragment = JWPlayerFragment.newInstance(playerConfig);
//        fragment.setFullscreenOnDeviceRotate(true);
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(R.id.container, fragment);
//        ft.commit();

    }


    private void parseRelatedItems(int i, String cat_code) {

        swipe_refresh_layout_cat_wise.setRefreshing(true);
        adapter.notifyDataSetChanged();

        HashMap<String, String> map = new HashMap<>();
        map.put("CatCode", cat_code);
        map.put("PageTotal", String.valueOf(i));

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callRelatedContents = networkOperation.getRelatedData(map);

        callRelatedContents.enqueue(new Callback<List<RelatedContentClass>>() {
            @Override
            public void onResponse(Call<List<RelatedContentClass>> call, Response<List<RelatedContentClass>> response) {
                Log.d("Data", response.body().get(1).getArtist());
                swipe_refresh_layout_cat_wise.setRefreshing(false);
                relatedContentClassList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RelatedContentClass>> call, Throwable t) {
                Log.d("Data", t.getMessage());
            }
        });
    }

    private void initUI() {
        imgPlayButton = (ImageView) findViewById(R.id.imgPlayButton);
        imgPlayButton.setVisibility(View.VISIBLE);
        imgVideoFrame = (ImageView) findViewById(R.id.imgVideoFrame);
        videoView = (FullscreenVideoLayout) findViewById(R.id.videoviews);
        videoView.setActivity(this);


        swipe_refresh_layout_cat_wise = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cat_wise);
        swipe_refresh_layout_cat_wise.setOnRefreshListener(this);


        btnFvrt = (ImageView) findViewById(R.id.btnFvrt);
        txtDuration = (TextView) findViewById(R.id.txtDuration);
        txtGenre = (TextView) findViewById(R.id.txtGenre);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnLike = (ImageView) findViewById(R.id.btnLike);
        txtTotalLikes = (TextView) findViewById(R.id.txtTotalLikes);
        txtTotalViews = (TextView) findViewById(R.id.txtTotalViews);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        recycler_view_related_items = (RecyclerView) findViewById(R.id.recycler_view_related_items);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycler_view_related_items.setLayoutManager(mLayoutManager);
        recycler_view_related_items.setItemAnimator(new DefaultItemAnimator());
        adapter = new RelatedContentAdapter(this, relatedContentClassList);
        recycler_view_related_items.setAdapter(adapter);

        btnLike.setOnClickListener(this);
        btnFvrt.setOnClickListener(this);

        txtTitle.setText(title);
        txtTitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));
        txtTotalLikes.setText(like);
        txtTotalViews.setText(views);
        txtInfo.setText(info);
        txtGenre.setText(genre);
        txtDuration.setText(duration);

        Glide.with(getApplicationContext()).load(imageUrl).into(imgVideoFrame);

    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout_cat_wise.setRefreshing(true);
        parseRelatedItems(1, cat_code);
    }

    int i = 0;

    @Override
    protected void onResume() {
        super.onResume();

//        fragment.onResume();
//
//        fragment.getPlayer().addOnControlBarVisibilityListener(new VideoPlayerEvents.OnControlBarVisibilityListener() {
//            @Override
//            public void onControlBarVisibilityChanged(ControlBarVisibilityEvent controlBarVisibilityEvent) {
//
//                String state = String.valueOf(fragment.getPlayer().getState());
//
//                if (state.equals("BUFFERING") && i == 0) {
//                    i += 1;
//                    int view = Integer.valueOf(views);
//                    view += 1;
//                    views = String.valueOf(view);
//                    txtTotalViews.setText(views);
//                    try {
//
//                        postFavViewLike("view", SplashActivity.MSISDN);
//                        //giveView("wifi","View",ContentCode);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    Log.d("ViewCount", state + "--first viewcount " + String.valueOf(i));
//                } else {
//                    Log.d("ViewCount", state + "--already viewcount ");
//                }
//
//                Log.d("StatePlayer", state);
//
//            }
//        });

//        playerView.onResume();
    }

    private void postFavViewLike(final String type, String msisdn) {
        Log.d("Response", msisdn);
        Log.d("Response", type);
        Log.d("Response", content_code);
        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN", msisdn);
        map.put("Type", type);
        map.put("ContentCode", content_code);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callResult = networkOperation.getResult(map);

        callResult.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("Responsee", "Fav " + response.body().getResult());
                if (response.body().getResult().equals("Success")) {
                    if (type.equalsIgnoreCase("fav")) {
                        // add as favourate
                        Log.d("Response", "Fav " + response.body().getResult());
                        Toast.makeText(getApplicationContext(), "Add as Favourate", Toast.LENGTH_LONG).show();
                    } else if (type.equalsIgnoreCase("like")) {
                        // like count
                        Log.d("Response", "Like " + response.body().getResult());
                    } else {
                        // View count
                        Log.d("Response", "View " + response.body().getResult());
                    }
                } else {
                    return;
                    //showSubsCriptionDialog(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("Response", "Error " + t.getMessage());
            }
        });
    }

    private void showSubsCriptionDialog(String result) {

        final Dialog dialogMNO = new Dialog(VideoViewActivity.this,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.subscription_dialog);
        dialogMNO.setCancelable(false);

        WebView webView = (WebView) dialogMNO.findViewById(R.id.webviewSubcription);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("Subscription", url);
                if (url != null) {
                    if (url.contains("isChargeSuccess=0")) {
                        Log.d("Subscription", "Subscribed");
                        Toast.makeText(VideoViewActivity.this, "Successfully subscrobed!", Toast.LENGTH_LONG).show();
                        dialogMNO.dismiss();
                    } else if (url.contains("msisdn=")) {
//                        fragment.onPause();
//                        fragment.getPlayer().destroySurface();
                        startActivity(new Intent(VideoViewActivity.this, MainActivity.class));
                        finish();
                        Log.d("Subscription", "Subscription failed");
                        dialogMNO.dismiss();
                    }
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(result);
        dialogMNO.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLike:
                int l = Integer.parseInt(like);
                l++;
                like = String.valueOf(l);
                txtTotalLikes.setText(like);
                postFavViewLike("like", "wifi");
                break;
            case R.id.btnFvrt:
                postFavViewLike("fav", SplashActivity.MSISDN);
                break;
        }
    }

    @Override
    protected void onPause() {
        //fragment.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //fragment.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
