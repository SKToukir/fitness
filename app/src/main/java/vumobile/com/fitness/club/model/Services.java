package vumobile.com.fitness.club.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by toukirul on 22/1/2018.
 */

public class Services {

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Bangla_Service_Title")
    @Expose
    private String banglaServiceTitle;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("ServiceID")
    @Expose
    private String serviceID;
    @SerializedName("categoryCode")
    @Expose
    private String categoryCode;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanglaServiceTitle() {
        return banglaServiceTitle;
    }

    public void setBanglaServiceTitle(String banglaServiceTitle) {
        this.banglaServiceTitle = banglaServiceTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}