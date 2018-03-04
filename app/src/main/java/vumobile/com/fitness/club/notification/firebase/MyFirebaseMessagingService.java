package vumobile.com.fitness.club.notification.firebase;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import vumobile.com.fitness.club.notification.FirebaseCustomNotification;
import vumobile.com.fitness.club.remote.NetworkOperation;

/**
 * Created by toukirul on 8/2/2018.
 */
@SuppressWarnings("ALL")
public class MyFirebaseMessagingService extends FirebaseMessagingService {



    private NetworkOperation networkOperation;
    private Call<NotificationContent> notificationContentCall;

    public static final String PUSH_NOTIFICATION = "pushNotification";
    private JSONObject obj;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("Firebase", "From: 1" + remoteMessage.getData());
        obj = new JSONObject(remoteMessage.getData());
        try {
            String contentCode = obj.getString("contentcode");
            Log.e("Firebase", "From: 1" + contentCode);
            parseNotificatioData(contentCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseNotificatioData(String contentCode) {
        Log.d("Responsee",contentCode);
        JSONObject js = new JSONObject();
        try {
            js.put("ContentCode", contentCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, "https://wap.shabox.mobi/globalfbase/api/android/TT", js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Responsee",response.toString());

                        if (!response.toString().isEmpty()){

                            try {
                                JSONArray array = response.getJSONArray("result");

                                JSONObject object = array.getJSONObject(0);
                                String ContentTile = object.getString("ContentTile");
                                String Type = object.getString("Type");
                                String content_code = object.getString("contentcode");
                                String cat_code = object.getString("catgorycode");
                                String imageUrl = object.getString("imageUrl").replace(" ","%20");
                                String TimeStamp = object.getString("TimeStamp");
                                String physicalfilename = object.getString("physicalfilename");
                                String totalLike = object.getString("totalLike");
                                String totalView = object.getString("totalView");
                                String duration = object.getString("duration");
                                String Info = object.getString("Info");
                                String Genre = object.getString("Genre");

//                                try {
//                                    //Log.d("Response Tariqul sample_url", ImageURL.toString());
//                                    remote_picture = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }

                                new FirebaseCustomNotification(getApplicationContext(),ContentTile,Type,
                                        TimeStamp,physicalfilename,totalLike,totalView,duration,Info,Genre,
                                        imageUrl, content_code, cat_code).execute();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

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

//        HashMap<String, String> body = new HashMap<>();
//        body.put("ContentCode",contentCode);

//        networkOperation = RetrofitClient.getRetrofitClient(Api.BASE_URL).create(NetworkOperation.class);
//        notificationContentCall = networkOperation.getNotificationContent(js);
//
//        notificationContentCall.enqueue(new Callback<NotificationContent>() {
//            @Override
//            public void onResponse(Call<NotificationContent> call, Response<NotificationContent> response) {
//                Log.d("Responseee", response.raw().toString());
//            }
//
//            @Override
//            public void onFailure(Call<NotificationContent> call, Throwable t) {
//                Log.d("Responseee", t.getMessage());
//            }
//        });


    }

    /**
     * Showing notification with text only
     */
//    private void handleNotification(String message) {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                        .setContentTitle("Fitness Club")
//                        .setContentText(message);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
}
