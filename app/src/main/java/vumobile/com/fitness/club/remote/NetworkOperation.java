package vumobile.com.fitness.club.remote;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vumobile.com.fitness.club.model.RelatedContentClass;
import vumobile.com.fitness.club.model.RelatedHealthyClass;
import vumobile.com.fitness.club.model.Result;
import vumobile.com.fitness.club.model.Services;
import vumobile.com.fitness.club.model.Slider;
import vumobile.com.fitness.club.model.TextWiseService;
import vumobile.com.fitness.club.model.favourite.InfoServiceClass;
import vumobile.com.fitness.club.model.search.SearchClas;
import vumobile.com.fitness.club.notification.firebase.NotificationContent;

/**
 * Created by toukirul on 21/1/2018.
 */

public interface NetworkOperation {

    @POST(Api.SEARCH)
    Call<SearchClas> getSearchItems(@Body HashMap<String, String> body);

    @POST(Api.SUBSCRIBE_USER)
    Call<Result> subscribeUser(@Body HashMap<String, String> body);

    @POST(Api.UN_SUBSCRIBE_USER)
    Call<Result> unSubscribeUser(@Body HashMap<String, String> body);

    @POST(Api.CHECK_SUBSCRIPTION)
    Call<Result> checkSubscription(@Body HashMap<String, String> body);

    @GET(Api.SLIDER)
    Call<List<Slider>> getAllSlideData();

    @POST(Api.CAT_WISE)
    Call<List<RelatedContentClass>> getRelatedData(@Body HashMap<String, String> body);

    @POST(Api.CAT_WISE)
    Call<List<RelatedHealthyClass>> getRelatedHealthyData(@Body HashMap<String, String> body);

    @POST(Api.LIKE_FAV_VIEW)
    Call<Result> getResult(@Body HashMap<String, String> body);

    @GET(Api.SERVICE_INFO)
    Call<List<Services>> getAllServices();

    @POST(Api.TEXT_WISE_SERVICE)
    Call<List<TextWiseService>> getTextWiseService(@Body HashMap<String, String> body);

    @POST(Api.FAV_LIST)
    Call<InfoServiceClass> getFavourateList(@Body HashMap<String, String> body);

    @POST(Api.NOTIFICATION_CONTENT_URL)
    Call<NotificationContent> getNotificationContent(@Body JSONObject object);

}
