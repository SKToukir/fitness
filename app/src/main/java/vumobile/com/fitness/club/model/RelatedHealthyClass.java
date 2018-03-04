package vumobile.com.fitness.club.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by toukirul on 23/1/2018.
 */

public class RelatedHealthyClass {

    @SerializedName("RID")
    @Expose
    private String rID;
    @SerializedName("contentcode")
    @Expose
    private String contentcode;
    @SerializedName("catgorycode")
    @Expose
    private String catgorycode;
    @SerializedName("ContentTile")
    @Expose
    private String contentTile;
    @SerializedName("BigPreview")
    @Expose
    private String bigPreview;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("ContentZedCode")
    @Expose
    private String contentZedCode;
    @SerializedName("Artist")
    @Expose
    private String artist;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("LiveDate")
    @Expose
    private String liveDate;
    @SerializedName("TimeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("physicalfilename")
    @Expose
    private String physicalfilename;
    @SerializedName("totalLike")
    @Expose
    private String totalLike;
    @SerializedName("totalView")
    @Expose
    private String totalView;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("Info")
    @Expose
    private String info;
    @SerializedName("Genre")
    @Expose
    private String genre;
    @SerializedName("RelatedLink")
    @Expose
    private String relatedLink;

    public String getRID() {
        return rID;
    }

    public void setRID(String rID) {
        this.rID = rID;
    }

    public String getContentcode() {
        return contentcode;
    }

    public void setContentcode(String contentcode) {
        this.contentcode = contentcode;
    }

    public String getCatgorycode() {
        return catgorycode;
    }

    public void setCatgorycode(String catgorycode) {
        this.catgorycode = catgorycode;
    }

    public String getContentTile() {
        return contentTile;
    }

    public void setContentTile(String contentTile) {
        this.contentTile = contentTile;
    }

    public String getBigPreview() {
        return bigPreview;
    }

    public void setBigPreview(String bigPreview) {
        this.bigPreview = bigPreview;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentZedCode() {
        return contentZedCode;
    }

    public void setContentZedCode(String contentZedCode) {
        this.contentZedCode = contentZedCode;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPhysicalfilename() {
        return physicalfilename;
    }

    public void setPhysicalfilename(String physicalfilename) {
        this.physicalfilename = physicalfilename;
    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public String getTotalView() {
        return totalView;
    }

    public void setTotalView(String totalView) {
        this.totalView = totalView;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRelatedLink() {
        return relatedLink;
    }

    public void setRelatedLink(String relatedLink) {
        this.relatedLink = relatedLink;
    }

}
