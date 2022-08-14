package com.happyxmall.www.app;

import com.happyxmall.www.bean.ResultData;
import com.happyxmall.www.bean.VersionInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    String SWITCH = "apponoff/";//getversion
    String FRONT_LOGIN = "front/login/";//api

    @GET(SWITCH + "getversion")
    Observable<ResultData<VersionInfo>> getVersionDetail(@Query("appid") String appid,
                                                          @Query("version") String version);


    /**
     * register the deviceToken to Api Server
     * @param token Base64(device+time+DES(key+time+uuid))
     * @param uniqueToken unique
     * @param deviceToken from Fcm server
     * @param countryCode get from local
     * @return
     */
    @FormUrlEncoded
    @POST(FRONT_LOGIN + "checkin")
    Observable<ResultData> checkin(@Field("token") String token,
                                   @Field("uniqueToken") String uniqueToken,
                                   @Field("deviceToken") String deviceToken,
                                   @Field("countryCode") String countryCode);

    /**
     * 获取服务器时间
     *
     * @return
     */
    @GET(FRONT_LOGIN + "getTime")
    Observable<ResultData<String>> getTime();
}
