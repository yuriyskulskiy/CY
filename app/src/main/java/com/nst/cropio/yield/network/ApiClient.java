package com.nst.cropio.yield.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jakewharton.retrofit.Ok3Client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by yuriy on 9/28/17.
 */

public class ApiClient {

    public static final int LIMIT = 1000;

    public static final String DOMAIN = "https://cropio.com";
    public static final String API_V3 = DOMAIN + "/api/v3";

    private static final boolean ENABLE_LOGGING = false;
    private static final boolean ENABLE_GZIP = true;

    private interface SimpleApi {

        //        @GET("/{entities}")
//        void getAll(@Path("entities") String entities,
//                    @Query("limit") int limit,
//                    CustomCallback<DataMetaListResponse> callback);
//
//        @GET("/{entities}")
//        void getUserDataById(@Path("entities") String entities,
//                             @Query("id") String userId,
//                             @Query("limit") int limit,
//                             CustomCallback<DataMetaListResponse> callback);
//
//        @GET("/{entities}")
//        void getFromId(@Path("entities") String entities,
//                       @Query("from_id") int id,
//                       @Query("limit") int limit,
//                       CustomCallback<DataMetaListResponse> callback);
//
//        @GET("/{entities}/ids")
//        void getIds(@Path("entities") String entities,
//                    CustomCallback<DataBody<List<Integer>>> callback);
//
//        @GET("/{entities}/changes")
//        void getChanges(@Path("entities") String entities,
//                        @Query("from_time") String fromTime,
//                        @Query("to_time") String toTime,
//                        @Query("limit") int limit,
//                        CustomCallback<DataChangesResponse> callback);
//
//        @PUT("/{entities}/{server_id}")
//        <T extends SyncEntity> void update(@Path("entities") String entities,
//                                           @Path("server_id") int server_id,
//                                           @Body DataBody<T> dataBody,
//                                           CustomCallback<DataItemResponse> callback);
//
//        @POST("/{entities}")
//        <T extends SyncEntity> void insert(@Path("entities") String entities,
//                                           @Body DataBody<T> dataBody,
//                                           CustomCallback<DataItemResponse> callback);
//
        @POST("/sign_in")
        void login(@Body LoginRequest request, Callback<LoginResponse> callback);
        //
//        @PUT("/user_api_sessions/{user_api_token}")
//        void sendGcmToken(@Path("user_api_token") String token,
//                          @Body GcmRequest request,
//                          Callback<Void> callback);
//
//        @POST("/{entities}/mass_request")
//        <T> void getDataByIds(@Path("entities") String entities,
//                              @Body DataBody<List<T>> dataBody,
//                              CustomCallback<DataListResponse> callback);
//
        @GET("/d/users/adfs_statuses")
        void getAdfsStatus(@Query("email") String email,
                           CustomCallback<AdfsResponse> callback);
//
//        @GET("/satellite_images")
//        void getImagesByTimePeriod(@Query("date_gt") String fromTime,
//                                   @Query("date_lt") String toTime,
//                                   CustomCallback<DataMetaListResponse> callback);
//
//        @GET("/satellite_images")
//        void getImagesFromIdByTimePeriod(@Query("from_id") int fromId,
//                                         @Query("date_gt") String fromTime,
//                                         @Query("date_lt") String toTime,
//                                         CustomCallback<DataMetaListResponse> callback);

    }

    private interface DeleteApi {
        @DELETE("/{entities}/{server_id}")
        void delete(@Path("entities") String entities,
                    @Path("server_id") int server_id,
                    CustomCallback<Void> callback);
    }

    private final SimpleApi api;
    private final DeleteApi deleteApi;

    public ApiClient(String endpoint, final String token) {

        OkHttpClient client = newOkHttpClient(token, false);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setClient(new Ok3Client(client))
                .setConverter(new JacksonConverter());

        RestAdapter restAdapter = builder.build();
        api = restAdapter.create(SimpleApi.class);

        RestAdapter.Builder dBuilder = new RestAdapter.Builder()
                .setClient(new Ok3Client(newOkHttpClient(token, true)))
                .setEndpoint(endpoint);

        deleteApi = dBuilder.build().create(DeleteApi.class);
    }

    @NonNull
    public static OkHttpClient newOkHttpClient(final String token, final boolean tokenOnly) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        if (!tokenOnly) {
                            builder.addHeader("Content-Type", "application/json; text/html; charset=UTF-8");
                            builder.addHeader("Accept", "application/json");
                        }
                        if (!ENABLE_GZIP) {
                            removeGZipCompression(builder);
                        }

                        if (!TextUtils.isEmpty(token)) {
                            builder.addHeader("X-User-Api-Token", token);
                        }
                        return chain.proceed(builder.build());
                    }
                });

        if (ENABLE_LOGGING) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(logging);
        }

        return builder.build();
    }

    protected static void removeGZipCompression(Request.Builder builder) {
        builder.addHeader("Accept-Encoding", "identity");
    }

    //yura - use this one in my service
//    public DataMetaListResponse getAll(String name) throws RetrofitException {
//        CustomCallback<DataMetaListResponse> callback = new CustomCallback<>();
//        api.getAll(name, LIMIT, callback);
//        return callback.handleCallBack();
//    }
//
//    public DataMetaListResponse getByCurrentUserId(String name, String userId) throws RetrofitException {
//        CustomCallback<DataMetaListResponse> callback = new CustomCallback<>();
//        api.getUserDataById(name, userId, LIMIT, callback);
//        return callback.handleCallBack();
//    }
//
//
//    public DataMetaListResponse getFromId(String name, int id) throws RetrofitException {
//        CustomCallback<DataMetaListResponse> callback = new CustomCallback<>();
//        api.getFromId(name, id, LIMIT, callback);
//        return callback.handleCallBack();
//    }
//
//    public DataBody<List<Integer>> getIds(String name) throws RetrofitException {
//        CustomCallback<DataBody<List<Integer>>> callback = new CustomCallback<>();
//        api.getIds(name, callback);
//        return callback.handleCallBack();
//    }
//
//    public DataChangesResponse getChanges(String name, String fromDate, String toDate)
//            throws RetrofitException {
//        CustomCallback<DataChangesResponse> callback = new CustomCallback<>();
//        api.getChanges(name, fromDate, toDate, LIMIT, callback);
//        return callback.handleCallBack();
//    }
//
//    public <T extends SyncEntity> DataItemResponse update(String name, T entity)
//            throws RetrofitException {
//        DataBody<T> body = new DataBody<>();
//        body.setData(entity);
//        CustomCallback<DataItemResponse> callback = new CustomCallback<>();
//        api.update(name, entity.getServerId(), body, callback);
//        return callback.handleCallBack();
//    }
//
//    public <T extends SyncEntity> DataItemResponse insert(String name, T entity)
//            throws RetrofitException {
//        DataBody<T> body = new DataBody<>();
//        CustomCallback<DataItemResponse> callback = new CustomCallback<>();
//        body.setData(entity);
//        api.insert(name, body, callback);
//        return callback.handleCallBack();
//    }
//
//    public void delete(String name, int serverId) throws RetrofitException {
//        CustomCallback<Void> callback = new CustomCallback<>();
//        deleteApi.delete(name, serverId, callback);
//        callback.handleCallBack();
//    }
//
    public LoginResponse login(final LoginRequest request) throws RetrofitException {
        CustomCallback<LoginResponse> callback = new CustomCallback<>();
        api.login(request, callback);
        return callback.handleCallBack();
    }

    public AdfsResponse getAdfsStatus(String email) throws RetrofitException {
        CustomCallback<AdfsResponse> callback = new CustomCallback<>();
        api.getAdfsStatus(email, callback);
        return callback.handleCallBack();
    }
//
//    public boolean sendGcmRequest(String token, GcmRequest request) {
//        CustomCallback<Void> callback = new CustomCallback<>();
//        api.sendGcmToken(token, request, callback);
//        try {
//            callback.handleCallBack();
//            return true;
//        } catch (RetrofitException e) {
//            return false;
//        }
//    }
//
//    public DataListResponse getDataByIds(String name, List<Integer> ids)
//            throws RetrofitException {
//        CustomCallback<DataListResponse> customCallback = new CustomCallback<>();
//        DataBody<List<Integer>> dataBody = new DataBody<>();
//        dataBody.setData(ids);
//        api.getDataByIds(name, dataBody, customCallback);
//        return customCallback.handleCallBack();
//    }
//
//    public static ApiClient newV3ApiAdapterBySeasonType() {
//        SharedPreferencesHandler handler = SharedPreferencesHandler.get();
//        return new ApiClient(API_V3, handler.getAuthToken());
//    }
//
//    public DataMetaListResponse getImagesByTimePeriod(String fromDate, String toDate)
//            throws RetrofitException {
//        CustomCallback<DataMetaListResponse> callback = new CustomCallback<>();
//        api.getImagesByTimePeriod(fromDate, toDate, callback);
//        return callback.handleCallBack();
//    }
//
//    public DataMetaListResponse getImagesFromIdByTimePeriod(int id, String fromDate, String toDate)
//            throws RetrofitException {
//        CustomCallback<DataMetaListResponse> callback = new CustomCallback<>();
//        api.getImagesFromIdByTimePeriod(id, fromDate, toDate, callback);
//        return callback.handleCallBack();
//    }

}
