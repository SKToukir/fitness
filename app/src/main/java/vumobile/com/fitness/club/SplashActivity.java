package vumobile.com.fitness.club;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import vumobile.com.fitness.club.remote.Api;
import vumobile.com.fitness.club.utils.UserInfo;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_GET_ACCOUNT = 112;
    public static String MSISDN = "";

    public static String DEVICE_ID = "";
    public static String DEVICE_MODEL = "";
    public static String MANUFACTURE = "";
    public static String DEVICE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isOnline(this)){
            if (isReadStorageAllowed()){
                initUI();
                getUserInfo();
                getMsisdn();
            }else {
                requestStoragePermission();
            }
        }else {
            showInternetConnectionDialog();
        }
    }


    private void getUserInfo() {

        UserInfo userInfo = new UserInfo();
        DEVICE_ID = userInfo.deviceID(this);
        DEVICE_MODEL = userInfo.deviceModel(this);
        DEVICE_NAME = userInfo.deviceName(this);
        MANUFACTURE = userInfo.deviceMANUFACTURER(this);

    }

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS) || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }


        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.READ_PHONE_STATE},REQUEST_GET_ACCOUNT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

//        //Checking the request code of our request
//        if(requestCode == REQUEST_GET_ACCOUNT){
//
//            //If permission is granted
//            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//                //Displaying a toast
//                Toast.makeText(this,"Thanks You For Permission Granted ",Toast.LENGTH_LONG).show();
//                initUi();
//                initPushNotification();
//            }else{
//                //Displaying another toast if permission is not granted
//                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
//            }
//        }


        switch (requestCode) {
            case REQUEST_GET_ACCOUNT:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted){
                        onContinue();
                    }
                    //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        //Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
                        requestStoragePermission();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(android.Manifest.permission.GET_ACCOUNTS)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.READ_PHONE_STATE},
//                                                            REQUEST_GET_ACCOUNT);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }

                    }
                }


                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean isReadStorageAllowed() {

        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void initUI() {

        final Thread timerThread = new Thread() {
            @Override
            public void run() {
//				sDialog.show();
                try {

                    sleep(2000);
                    //sleep(6*960);

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //onContinue();
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }
            }
        };
        timerThread.start();
    }

    private void onContinue() {
        runOnUiThread(new Runnable() {

            public void run() {
//				sDialog.dismis();
//				busy.dismis();
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();

            }
        });
    }

    private void showInternetConnectionDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.wireless);

        alert.setTitle("Attention !");
        alert.setMessage("To Use This Application Please Connect To Internet");

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Intent intent = new Intent(
                        Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dialog.dismiss();
                finish();

            }
        });

        alert.show();
    }

    public void getMsisdn() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.URL_MSISDN_DETECTION, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("MSISDN",response.toString());

                try {
                    String result = response.getString("results");
                    if (result.equals("ERROR")){
                        //MSISDN = "8801813735883";
                        MSISDN = "wifi";
                    }else {
                        //MSISDN = "8801813735883";
                        MSISDN = result;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",""+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

    }

    public static boolean isOnline(final Context ctx) {

        try {
            final ConnectivityManager cm = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                return ni.isConnectedOrConnecting();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
}
