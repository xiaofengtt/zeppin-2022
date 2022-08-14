package com.xingyunmiaosha.www.app;

import com.xingyunmiaosha.www.bean.ResultData2;
import com.xingyunmiaosha.www.bean.VersionInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String SWITCH = "apponoff/";//getversion

    @GET(SWITCH + "getversion")
    Observable<ResultData2<VersionInfo>> getVersionDetail(@Query("appid") String appid,
                                                          @Query("version") String version);

    /**
     * 检查版本更新
     * @param appid
     * @return
     */
    @GET(SWITCH + "getversion2")
    Observable<ResultData2<VersionInfo>> getVersionDetail2(@Query("appid") String appid);
}
