package vumobile.com.fitness.club.notification;

/**
 * Created by toukirul on 11/10/2017.
 */

public class NotificationList {
    private String image_title;
    private String image_url;
    private String Push_Message;
    private String content_code;
    private String zeId;
    private String pFileName;
    private String content_type;
    private String category_name;
    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getContent_code() {
        return content_code;
    }

    public void setContent_code(String content_code) {
        this.content_code = content_code;
    }

    public String getZeId() {
        return zeId;
    }

    public void setZeId(String zeId) {
        this.zeId = zeId;
    }

    public String getpFileName() {
        return pFileName;
    }

    public void setpFileName(String pFileName) {
        this.pFileName = pFileName;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPush_Message() {
        return Push_Message;
    }

    public void setPush_Message(String push_Message) {
        Push_Message = push_Message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}