//package vumobile.com.fitness.club.notification;
//
///**
// * Created by toukirul on 11/10/2017.
// */
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//import android.widget.RemoteViews;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import vumobile.com.fitness.club.MainActivity;
//import vumobile.com.fitness.club.R;
//import vumobile.com.fitness.club.VideoViewActivity;
//
//
//public class Utils {
//    private static NotificationManager mNotificationManager;
//    public static NotificationManager mManager;
//
//    public static int clickCount = 0;
//    public static int clickCount1 = 0;
//    public static String resultMno = null;
//    PHPRequest phpRequest = new PHPRequest();
//
//    public static void setmNotificationManager(NotificationManager mNotificationManager) {
//        Utils.mNotificationManager = mNotificationManager;
//    }
//
//    @SuppressWarnings("static-access")
//
//    public static void setCustomViewNotification(Context context, String ContentTile, String type, String TimeStamp, String physicalfilename,
//                                                 String totalLike, String totalView, String duration, String Info, String Genre, String imageUrl) {
//        clickCount = clickCount + 1;
//
//
//
//
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
//        String strDate = sdf.format(c.getTime());
//
//        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        // Creates an explicit intent for an ResultActivity to receive.
//
//        Intent resultIntent = new Intent(context, VideoViewActivity.class);
//
//
//        resultIntent.putExtra("ContentTile", ContentTile);
//        resultIntent.putExtra("Type", type);
//        resultIntent.putExtra("imageUrl", imageUrl);
//        resultIntent.putExtra("TimeStamp", TimeStamp);
//        resultIntent.putExtra("physicalfilename", physicalfilename);
//        resultIntent.putExtra("totalLike", totalLike);
//        resultIntent.putExtra("totalView", totalView);
//        resultIntent.putExtra("duration", duration);
//        resultIntent.putExtra("Info", Info);
//        resultIntent.putExtra("Genre", Genre);
//
//
//
//        Log.d("LogNotific", ContentTile);
//        Log.d("LogNotific", type);
//        Log.d("LogNotific", imageUrl);
//        Log.d("LogNotific", TimeStamp);
//        Log.d("LogNotific", physicalfilename);
//        Log.d("LogNotific", totalLike);
//        Log.d("LogNotific", totalView);
//        Log.d("LogNotific", duration);
//        Log.d("LogNotific", Info);
//        Log.d("LogNotific", Genre);
//     /*   DownloadTask downloadTask = new DownloadTask();
//
//        *//** Starting the task created above *//*
//        downloadTask.execute(Image);*/
//        // This ensures that the back button follows the recommended convention for the back key.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(MainActivity.class);
//
//
//
//        // Adds the Intent that starts the Activity to the top of the stack.
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Create remote view and set bigContentView.
//        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_remote);
//
//        //expandedView.setImageViewUri(R.id.imageViewTest, Uri.parse(imageUrl));
////        Glide.with(context).load(imageUrl).into(expandedView.)
//
//        Intent volume = new Intent(context, VideoViewActivity.class);
//        volume.putExtra("do_action", "play");
//
//
//        PendingIntent pVolume = PendingIntent.getActivity(context, 1, volume, 0);
//        expandedView.setOnClickPendingIntent(R.id.MainlayoutCustom, pVolume);
//        expandedView.setTextViewText(R.id.text_view, ContentTile);
//
//        //expandedView.setTextViewText(R.id.notificationTime, strDate);
//
//        try {
//            expandedView.setImageViewBitmap(R.id.imageViewTest, remote_picture );
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//        }
//
//        Notification notification = new NotificationCompat.Builder(context)
//                .setSmallIcon(getNotificationIcon())
//                .setLargeIcon(remote_picture)
//                .setAutoCancel(true)
//                .setContentIntent(resultPendingIntent)
//                .setContentTitle(ContentTile)
//
//                //  .setDeleteIntent(pendintIntent)
//                .build();
//
//        notification.bigContentView = expandedView;
//
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        mNotificationManager.notify(0, notification);
//    }
//
//
//
//    private static int getNotificationIcon() {
//        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
//    }
//
//
//}
