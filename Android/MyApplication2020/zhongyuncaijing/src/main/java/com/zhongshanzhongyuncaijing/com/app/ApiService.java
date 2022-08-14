package com.zhongshanzhongyuncaijing.com.app;

import com.zhongshanzhongyuncaijing.com.bean.ResultData2;
import com.zhongshanzhongyuncaijing.com.bean.VersionInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String SWITCH = "apponoff/";//getversion

    @GET(SWITCH + "getversion")
    Observable<ResultData2<VersionInfo>> getVersionDetail(@Query("appid") String appid,
                                                          @Query("version") String version);
}
