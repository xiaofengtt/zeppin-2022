package cn.jiuzhousport.www.app;


import cn.jiuzhousport.www.bean.ResultData2;
import cn.jiuzhousport.www.bean.VersionInfo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String SWITCH = "front/";//getversion

    @GET(SWITCH + "version/get")
    Observable<ResultData2<VersionInfo>> getVersionDetail(@Query("channel") String channel,
                                                          @Query("version") String version,
                                                          @Query("bundleid") String bundleid);

    /**
     * 检查版本更新
     * @param appid
     * @return
     */
    @GET(SWITCH + "getversion2")
    Observable<ResultData2<VersionInfo>> getVersionDetail2(@Query("appid") String appid);
}
