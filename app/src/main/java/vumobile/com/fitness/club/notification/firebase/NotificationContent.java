
package vumobile.com.fitness.club.notification.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationContent {

    @SerializedName("result")
    @Expose
    private List<vumobile.com.fitness.club.notification.firebase.Result> result = null;

    public List<vumobile.com.fitness.club.notification.firebase.Result> getResult() {
        return result;
    }

    public void setResult(List<vumobile.com.fitness.club.notification.firebase.Result> result) {
        this.result = result;
    }

}
