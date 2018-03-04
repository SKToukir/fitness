package vumobile.com.fitness.club.model;

/**
 * Created by toukirul on 22/1/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextWiseService {

    @SerializedName("ContentImage")
    @Expose
    private String contentImage;
    @SerializedName("ContentID")
    @Expose
    private Integer contentID;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("ContentDate")
    @Expose
    private String contentDate;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("LastUpdate")
    @Expose
    private String lastUpdate;

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public Integer getContentID() {
        return contentID;
    }

    public void setContentID(Integer contentID) {
        this.contentID = contentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentDate() {
        return contentDate;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
