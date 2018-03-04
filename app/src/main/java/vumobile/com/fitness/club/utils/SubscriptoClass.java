package vumobile.com.fitness.club.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vumobile.com.fitness.club.ActivityFoodTips;
import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.SplashActivity;
import vumobile.com.fitness.club.VideoViewActivity;
import vumobile.com.fitness.club.model.Result;
import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.remote.NetworkOperation;
import vumobile.com.fitness.club.remote.RetrofitClient;

/**
 * Created by toukirul on 23/1/2018.
 */

public class SubscriptoClass {

    Intent intent;
    private NetworkOperation networkOperation;
    private Call<Result> checkSub;
    private Call<Result> subscribe;
    public static boolean isVideo;
    public static String title_info, service_id_info, content_id_info;
    public static String cat_code_video, content_code_Video, imageUrl_video, like_video, views_video, info_video, genre_video, duration_video, physical_file_name,video_type, appVersion;
    private Context mContext;

    public SubscriptoClass(Context context){
        this.mContext = context;
    }


    public void checkSubscription() {

        if (SplashActivity.MSISDN.equalsIgnoreCase("wifi")){
            showAlert();
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put("MSISDN", SplashActivity.MSISDN);

            networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
            checkSub = networkOperation.checkSubscription(map);

            checkSub.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Log.d("Subscription",response.body().getResult());

                    if (response.body().getResult().equalsIgnoreCase("Deactive")){
                        Log.d("Subscription","Not subscribe");
                        if (SplashActivity.MSISDN.startsWith("88018") || SplashActivity.MSISDN.startsWith("88019") || SplashActivity.MSISDN.startsWith("88016")){
                            subscribeUser(SplashActivity.MSISDN);
                        }else {
                            Toast.makeText(mContext, "Please ROBI,AIRTEL OR Banglalink Apn!",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Log.d("Subscription","Subscribe");
                        doAction();
                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });

        }
    }

    private void showAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Please use mobile data.");
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

    private void doAction() {
        if (isVideo){
            VideoViewActivity.cat_code = cat_code_video;
            VideoViewActivity.content_code = content_code_Video;
            VideoViewActivity.physical_file_name = physical_file_name;
            VideoViewActivity.imageUrl = imageUrl_video;
            VideoViewActivity.like = like_video;
            VideoViewActivity.duration = duration_video;
            VideoViewActivity.views = views_video;
            VideoViewActivity.info = info_video;
            VideoViewActivity.genre = genre_video;
            VideoViewActivity.title = title_info;
            VideoViewActivity.video_type = video_type;
            intent = new Intent(mContext, VideoViewActivity.class);
            mContext.startActivity(intent);
        }else if (!isVideo){
            ActivityFoodTips.title = title_info;
            ActivityFoodTips.service_id = service_id_info;
            ActivityFoodTips.content_id = "";
            intent = new Intent(mContext, ActivityFoodTips.class);
            mContext.startActivity(intent);
        }
    }

    private void subscribeUser(String msisdn) {


        HashMap<String, String> map = new HashMap<>();
        map.put("MSISDN", msisdn);

        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
        subscribe = networkOperation.subscribeUser(map);

        subscribe.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String result = response.body().getResult();

                if (result.startsWith("http")){
                    Log.d("Subscription","Show subscription dialog");
                    showSubsCriptionDialog(result);
                }else {
                    return;
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private void showSubsCriptionDialog(String result) {

        final Dialog dialogMNO = new Dialog(mContext,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.subscription_dialog);
        dialogMNO.setCancelable(true);

        WebView webView = (WebView) dialogMNO.findViewById(R.id.webviewSubcription);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("Subscription", url);
                if (url!=null){
                    if (SplashActivity.MSISDN.startsWith("88019")){
                        if (url.contains("isChargeSuccess=0")){
                            Log.d("Subscription", "Subscribed");
                            putUserInfoSubscription();
                            Toast.makeText(mContext,"Successfully subscribed!",Toast.LENGTH_LONG).show();
                            dialogMNO.dismiss();
                        }else if (url.contains("msisdn=")){
                            Log.d("Subscription", "Subscription failed");
                            dialogMNO.dismiss();
                        }
                    }else if (SplashActivity.MSISDN.startsWith("88018")){
                        if (url.contains("resultCode=-1")) {
                            Log.d("working", "1");
                            dialogMNO.dismiss();
                        }else if (url.contains("resultCode=0")){

                            dialogMNO.dismiss();
                            putUserInfoSubscription();
                            Toast.makeText(mContext,"Successfully subscribed!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(result);
        dialogMNO.show();

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

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonRequest);

    }
}
