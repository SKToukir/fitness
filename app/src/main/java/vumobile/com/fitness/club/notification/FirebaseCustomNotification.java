package vumobile.com.fitness.club.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vumobile.com.fitness.club.MainActivity;
import vumobile.com.fitness.club.R;
import vumobile.com.fitness.club.VideoViewActivity;

/**
 * Created by toukirul on 19/2/2018.
 */

@SuppressWarnings("ALL")
public class FirebaseCustomNotification extends AsyncTask<String, Void, Bitmap> {

    private static NotificationManager mNotificationManager;
    public static int clickCount = 0;

    private Context mContext;
    private String title;
    private String type;
    private String TimeStamp;
    private String physicalfilename;
    private String totalLike;
    private String totalView;
    private String duration;
    private String Info;
    private String Genre;
    private String imageUrl;
    private String content_code;
    private String cat_code;

    public FirebaseCustomNotification(Context mContext, String title, String type,
                                      String timeStamp, String physicalfilename, String totalLike, String totalView,
                                      String duration, String info, String genre, String imageUrl, String content_code, String cat_code) {
        super();
        this.mContext = mContext;
        this.title = title;
        this.type = type;
        TimeStamp = timeStamp;
        this.physicalfilename = physicalfilename;
        this.totalLike = totalLike;
        this.totalView = totalView;
        this.duration = duration;
        Info = info;
        Genre = genre;
        this.imageUrl = imageUrl;
        this.content_code = content_code;
        this.cat_code = cat_code;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        InputStream in;
        try {
            URL url = new URL(this.imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            return myBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        clickCount = clickCount + 1;

        NotificationCompat.BigPictureStyle notifyStyle = new NotificationCompat.BigPictureStyle();
        notifyStyle.setBigContentTitle(title);
        notifyStyle.setSummaryText(Info);
        notifyStyle.bigPicture(result);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String strDate = sdf.format(c.getTime());

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // Creates an explicit intent for an ResultActivity to receive.

        Intent resultIntent = new Intent(mContext, VideoViewActivity.class);


        resultIntent.putExtra("ContentTile", title);
        resultIntent.putExtra("Type", type);
        resultIntent.putExtra("imageUrl", imageUrl);
        resultIntent.putExtra("contentcode", content_code);
        resultIntent.putExtra("cat_code", cat_code);
        resultIntent.putExtra("TimeStamp", TimeStamp);
        resultIntent.putExtra("physicalfilename", physicalfilename);
        resultIntent.putExtra("totalLike", totalLike);
        resultIntent.putExtra("totalView", totalView);
        resultIntent.putExtra("duration", duration);
        resultIntent.putExtra("Info", Info);
        resultIntent.putExtra("Genre", Genre);
        resultIntent.putExtra("notific", "1");



        Log.d("LogNotific", title);
        Log.d("LogNotific", type);
        Log.d("LogNotific", imageUrl);
        Log.d("LogNotific", TimeStamp);
        Log.d("LogNotific", physicalfilename);
        Log.d("LogNotific", totalLike);
        Log.d("LogNotific", totalView);
        Log.d("LogNotific", duration);
        Log.d("LogNotific", Info);
        Log.d("LogNotific", Genre);


        Intent backIntent = new Intent(mContext, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivities(mContext, clickCount,
                new Intent[] {backIntent, resultIntent}, PendingIntent.FLAG_ONE_SHOT);

        Notification notif = new NotificationCompat.Builder(mContext)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(Info)
                .setStyle(notifyStyle)
                .build();
        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(clickCount, notif);
    }

        private static int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}
