package vumobile.com.fitness.club;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.adapter.AdapterTextWiseInfo;
import vumobile.com.fitness.club.model.TextWiseService;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;
import vumobile.com.fitness.club.utils.RecyclerTouchListener;

public class ActivityFoodTips extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog pDialog;
    private TextView txtDate,txtTitle, txtContent, txtPurboBorti;
    private ImageView imgInfo, imgHome, imgFaq;
    private Button btnHome,btnFiraeJan;
    private LinearLayout footerOne, footerTwo;
    private RecyclerView recycler_post;
    private AdapterTextWiseInfo adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Call<List<TextWiseService>> callTextWiseService;
    private Toolbar toolbar;
    private List<TextWiseService> textWiseServiceList = new ArrayList<>();
    private TextWiseService textWiseService;
    private NetworkOperation networkOperation;
    public static String title, content_id, content_image, content, last_update, service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tips);
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

        initUI();
        setColorOperatorWise();
        initRecycler();

        parseInfoData(service_id,content_id);

        recycler_post.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_post, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextWiseService textWiseService = textWiseServiceList.get(position);
                ActivityFoodTips.content_id = textWiseService.getContentID().toString();
                startActivity(new Intent(getApplicationContext(), ActivityFoodTips.class));

                //parseInfoData(content_id,textWiseService.getContentID().toString() );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initUI() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.show();



        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);

        imgInfo = (ImageView) findViewById(R.id.imgInfo);
        imgFaq = (ImageView) findViewById(R.id.imgFaq);
        imgHome = (ImageView) findViewById(R.id.imgHome);

        btnFiraeJan = (Button) findViewById(R.id.btnFiraeJan);
        btnHome = (Button) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(this);
        btnFiraeJan.setOnClickListener(this);

        imgHome.setOnClickListener(this);
        imgFaq.setOnClickListener(this);
        imgInfo.setOnClickListener(this);
    }

    private void initRecycler() {

        recycler_post = (RecyclerView) findViewById(R.id.recycler_post);
        adapter = new AdapterTextWiseInfo(this, textWiseServiceList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_post.setLayoutManager(mLayoutManager);
        recycler_post.setItemAnimator(new DefaultItemAnimator());
        recycler_post.setAdapter(adapter);

    }

    private void parseInfoData(String service_id, String contentId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ServiceId", service_id);
        map.put("ContentId", contentId);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        callTextWiseService = networkOperation.getTextWiseService(map);

        callTextWiseService.enqueue(new Callback<List<TextWiseService>>() {
            @Override
            public void onResponse(Call<List<TextWiseService>> call, Response<List<TextWiseService>> response) {

                pDialog.dismiss();

                txtTitle.setText(response.body().get(0).getTitle());
                txtContent.setText(response.body().get(0).getContent());
                txtDate.setText(response.body().get(0).getLastUpdate());
                Glide.with(getApplicationContext()).load(response.body().get(0).getContentImage()).into(imgInfo);


                textWiseServiceList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TextWiseService>> call, Throwable t) {

            }
        });

    }

//    private String getTime(String lastUpdate) {
//        Timestamp ts = Timestamp.valueOf(lastUpdate);
//        Date date = new Date();
//        date.setTime(ts.getTime());
//        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//        Log.d("TimeStamp",formattedDate);
//        return formattedDate;
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHome:
                Intent newIntent = new Intent(ActivityFoodTips.this,MainActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                break;
            case R.id.btnFiraeJan:
                finish();
                break;
            case R.id.imgInfo:
                break;
            case R.id.imgHome:
                Intent newIntent1 = new Intent(ActivityFoodTips.this,MainActivity.class);
                newIntent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent1);
                break;
            case R.id.imgFaq:
                startActivity(new Intent(ActivityFoodTips.this, FaqActivity.class));
                break;

        }
    }

    private void setColorOperatorWise() {
        txtPurboBorti = (TextView) findViewById(R.id.txtPurboBorti);
        footerOne = (LinearLayout) findViewById(R.id.footerOnes);
        footerTwo = (LinearLayout) findViewById(R.id.footerTwos);

        if (SplashActivity.MSISDN.startsWith("88019")){
            footerOne.setBackgroundColor(getResources().getColor(R.color.other_color));
            footerTwo.setBackgroundColor(getResources().getColor(R.color.other_color));
            txtPurboBorti.setBackgroundColor(getResources().getColor(R.color.other_color));
            btnFiraeJan.setBackgroundColor(getResources().getColor(R.color.other_color));
            btnHome.setBackgroundColor(getResources().getColor(R.color.other_color));
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
        }
    }
}
