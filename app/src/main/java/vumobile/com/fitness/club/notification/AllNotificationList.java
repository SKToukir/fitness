package vumobile.com.fitness.club.notification;

import java.util.Vector;

/**
 * Created by toukirul on 11/10/2017.
 */

public class AllNotificationList {

    public static Vector<NotificationList> notificationListVector = new Vector<NotificationList>();


    public static Vector<NotificationList> getAllNotificationList() {
        return notificationListVector;
    }

    public static void setAllNotificationList(Vector<NotificationList> notificationListVector) {
        AllNotificationList.notificationListVector = notificationListVector;
    }

    public static NotificationList getNotificationList(int pos) {
        return notificationListVector.elementAt(pos);
    }

    public static void setNotificationList(NotificationList notificationList) {
        AllNotificationList.notificationListVector.addElement(notificationList);
    }

    public static void removeNotificationList() {
        AllNotificationList.notificationListVector.removeAllElements();
    }

}