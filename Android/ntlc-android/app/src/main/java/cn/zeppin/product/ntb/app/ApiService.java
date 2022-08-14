package cn.zeppin.product.ntb.app;

import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.Finance;
import cn.zeppin.product.ntb.bean.FinanceDetail;
import cn.zeppin.product.ntb.bean.Gift;
import cn.zeppin.product.ntb.bean.ImgResource;
import cn.zeppin.product.ntb.bean.Message;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.MyBankDetail;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.Product;
import cn.zeppin.product.ntb.bean.ProductDetail;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.TradingRecordDetail;
import cn.zeppin.product.ntb.bean.TradingRecordList;
import cn.zeppin.product.ntb.bean.UpdateVersionInfo;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserFinance;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by geng on 17/9/7.
 * class:
 */

public interface ApiService {
    String FINANCIAL = "web/financial/";
    String USER = "web/user/";
    String PAY = "web/pay/";//支付
    String SMS = "web/sms/";//短信
    String LOGIN = "web/login/";//登录
    String PRODUCT = "web/product/";//理财产品
    String INFO = "web/info/";//消息
    String COUPON = "web/coupon/";//优惠券
    String ADVERT = "web/advert/";//优惠券

    /**
     * 银行理财产品列表
     *
     * @param name      //搜索内容
     * @param custodian //银行UUID
     * @param term      //期限取值
     * @param sorts     //排序参数，可用值
     * @param pageNum   //页码
     * @param pageSize
     * @param device    Base64
     * @return
     */
    @GET(PRODUCT + "list")
    Observable<ResultData<Product>> getProductList(
            @Query("name") String name,
            @Query("custodian") String custodian,
            @Query("term") String term,
            @Query("sorts") String sorts,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("device") String device);

    /**
     * 银行列表
     *
     * @return
     * @Query("flagBinding") String flagBinding
     * @@Query("device") String  Base64
     * "web/product/bankList"
     */
    @GET
    Observable<ResultData<Bank>> getBankList(@Url() String url);

    /**
     * 银行理财产品详情
     *
     * @param uuid
     * @param device Base64
     * @return
     */
    @GET(PRODUCT + "get")
    Observable<ResultData2<ProductDetail>> getProductDetail(@Query("uuid") String uuid,
                                                            @Query("device") String device);


    //Login-----------------------------------------------

    /**
     * 注册
     *
     * @param token Base64(来源编码+timestamp+pwd+MD5(key+timestamp+phone+code))
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN + "register")
    Observable<ResultData2<User>> register(
            @Field("token") String token,
            @Field("phone") String phone);


    /**
     * 登录
     *
     * @param token Base64(来源编码+timestamp+phone+MD5(key+timestamp+pwd))
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN + "login")
    Observable<ResultData2<User>> login(@Field("token") String token);

    /**
     * 短信验证码登录
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN + "loginBycode")
    Observable<ResultData2<User>> loginBycode(@Field("token") String token);


    /**
     * 发送验证码
     *
     * @param phone    手机号
     * @param codeType register--注册； resetpwd--重置密码
     * @return
     */
    @GET(SMS + "sendCode")
    Observable<ResultData2> sendCode(
            @Query("phone") String phone,
            @Query("codeType") String codeType,
            @Query("token") String token);

    /**
     * 用户重置密码--验证手机验证码
     *
     * @param token
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN + "resetCheck")
    Observable<ResultData2> resetCheck(
            @Field("token") String token,
            @Field("phone") String phone);

    /**
     * 重置密码
     *
     * @param token
     * @param phone //手机号
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN + "resetpwd")
    Observable<ResultData2> resetPwd(
            @Field("token") String token,
            @Field("phone") String phone);

    /**
     * 获取应用商店版本开关
     *
     * @param webmarket base64加密
     * @param version   base64加密
     * @return
     */
    @GET(LOGIN + "getWebmarketSwitch")
    Observable<ResultData2<Boolean>> getWebmarketSwitch(
            @Query("token") String token,
            @Query("webmarket") String webmarket,
            @Query("version") String version);

    /**
     * 获取最新版本信息
     * 1. 如果当前无最新版本，则不需要更新；
     * 2. 如何最新版本不是强制更新，判断当前的版本至最新版本之间是否有强制更新的版本，如果有，则提示强制更新，否则则不强制更新；
     * 3. 当前版本失效，必须强制更新
     *
     * @param token
     * @param version Base64(device(设备号)+timestamp+md5(key+timestamp+version))
     * @return
     */
    @GET(LOGIN + "getVersionInfo")
    Observable<ResultData2<UpdateVersionInfo>> getVersionInfo(
            @Query("token") String token,
            @Query("version") String version);

//---------------------------------------------------------------

    /**
     * 通过uuid获取用户信息
     *
     * @param uuid 用户uuid
     * @return
     */
    @GET(USER + "get")
    Observable<ResultData2<User>> getUser(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 实名认证
     *
     * @param uuid    用户uuid
     * @param name    姓名
     * @param idcard  身份证
     * @param imgface 正面照
     * @param imgback 反面照
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "certification")
    Observable<ResultData2> certification(
            @Field("uuid") String uuid,
            @Field("name") String name,
            @Field("idcard") String idcard,
            @Field("imgface") String imgface,
            @Field("imgback") String imgback,
            @Field("token") String token);

    /**
     * 实名认证（证件照片认证）
     *
     * @param uuid    用户编号
     * @param imgface 正面照
     * @param imgback 反面照
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "certificationImg")
    Observable<ResultData2> certificationImg(
            @Field("uuid") String uuid,
            @Field("imgface") String imgface,
            @Field("imgback") String imgback,
            @Field("token") String token);


    /**
     * 文件上传
     *
     * @param url  动态url
     * @param file 文件
     * @return
     */
    @Multipart
    @POST
    Observable<ResultData2<ImgResource>> upload(
            @Url() String url,
            @Part MultipartBody.Part file);


    /**
     * 检查银行卡所属银行
     *
     * @param bankcard 银行卡号 base64
     * @return
     */
    @GET(USER + "bindingCheckCard")
    Observable<ResultData2<Bank>> bindingCheckCard(
            @Query("bankcard") String bankcard,
            @Query("token") String token);

    /**
     * 发送银行短信验证码
     *
     * @param bankcard   银行卡号 base64
     * @param phone      手机号 base64
     * @param bank       银行卡所属银行 id
     * @param cardholder 持卡人 base64
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "bindingSendCode")
    Observable<ResultData2<String>> bindingSendCode(
            @Field("uuid") String uuid,
            @Field("bankcard") String bankcard,
            @Field("phone") String phone,
            @Field("bank") String bank,
            @Field("cardholder") String cardholder,
            @Field("token") String token);

    /**
     * 用户绑定银行卡
     *
     * @param uuid       用户编号
     * @param bankcard   银行卡号 base64
     * @param phone      银行预留手机号 base64
     * @param code       验证码 base64
     * @param bank       银行卡所属行id
     * @param cardholder 持卡人 base64
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "bindingCard")
    Observable<ResultData2> bindingCard(
            @Field("uuid") String uuid,
            @Field("bankcard") String bankcard,
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("bank") String bank,
            @Field("cardholder") String cardholder,
            @Field("orderNum") String orderNum,
            @Field("token") String token);


    /**
     * 获取用户绑定的银行卡列表
     *
     * @param uuid 用户编号
     * @return
     */
    @GET(USER + "getBindingCard")
    Observable<ResultData<MyBank>> getBindingCard(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 获取用户绑定的银行卡详细信息
     *
     * @param uuid     用户编号
     * @param bankcard 银行卡编号id
     * @return
     */
    @GET(USER + "bindingCardInfo")
    Observable<ResultData2<MyBankDetail>> bindingCardInfo(
            @Query("uuid") String uuid,
            @Query("bankcard") String bankcard,
            @Query("token") String token);


    /**
     * 发送解绑银行卡短信验证码
     *
     * @param uuid     用户编号
     * @param bankcard 银行卡编号
     * @param phone    银行预留手机号 base64
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "unbindSendCode")
    Observable<ResultData2> unbindSendCode(
            @Field("uuid") String uuid,
            @Field("bankcard") String bankcard,
            @Field("phone") String phone,
            @Field("token") String token);


    /**
     * 解绑银行卡
     *
     * @param uuid     用户编号
     * @param phone    手机号 base64
     * @param code     验证码 base64
     * @param bankcard 银行卡编号
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "unbindBankcard")
    Observable<ResultData2> unbindBankcard(
            @Field("uuid") String uuid,
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("bankcard") String bankcard,
            @Field("token") String token);

    /**
     * 交易记录
     *
     * @param uuid  用户编号
     * @param token 公共token
     * @return
     */
    @GET(USER + "getBill")
    Observable<ResultData<TradingRecordList>> getTradingRecordList(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 交易记录详情
     *
     * @param uuid
     * @param billid
     * @param token
     * @return
     */
    @GET(USER + "getBillInfo")
    Observable<ResultData2<TradingRecordDetail>> getTradingRecordInfo(
            @Query("uuid") String uuid,
            @Query("billid") String billid,
            @Query("token") String token);


    /**
     * 获取用户持仓列表
     *
     * @param uuid     用户编号
     * @param stage    持仓阶段
     * @param pageNum
     * @param pageSize
     * @param sorts    排序
     * @param token    公共token
     * @return
     */
    @GET(FINANCIAL + "getList")
    Observable<ResultData<Finance>> getFinanceList(
            @Query("uuid") String uuid,
            @Query("stage") String stage,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("sorts") String sorts,
            @Query("token") String token);

    /**
     * 获取用户账户信息
     *
     * @param uuid  用户编号
     * @param token 公共token
     * @return
     */
    @GET(FINANCIAL + "get")
    Observable<ResultData2<UserFinance>> getFinanceUser(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 获取用户持仓详情
     *
     * @param uuid      用户编号
     * @param financial 持仓记录编号
     * @param token     公共token
     * @return
     */
    @GET(FINANCIAL + "getInfo")
    Observable<ResultData2<FinanceDetail>> getFinanceInfo(
            @Query("uuid") String uuid,
            @Query("financial") String financial,
            @Query("token") String token);


    /**
     * 购买银行理财产品
     *
     * @param uuid
     * @param bankcard
     * @param price    购买金额（已分为单位base64）
     * @param type
     * @param code
     * @param orderNum 订单号
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(PAY + "productPayByChanpay")
    Observable<ResultData2<String>> productPayByChanpay(
            @Field("uuid") String uuid,
            @Field("bankcard") String bankcard,
            @Field("price") String price,
            @Field("product") String product,
            @Field("type") String type,
            @Field("code") String code,
            @Field("orderNum") String orderNum,
            @Field("token") String token);

    /**
     * 发送提现短信验证码
     *
     * @param uuid
     * @param token
     * @return
     */
    @GET(SMS + "sendCodeById")
    Observable<ResultData2<String>> sendCodeById(
            @Query("uuid") String uuid,
            @Query("token") String token);


    /**
     * 获取服务器时间
     *
     * @return
     */
    @GET(PRODUCT + "getTime")
    Observable<ResultData2<String>> getTime();

    /**
     * 获取服务器时间
     *
     * @return
     */
    @GET(PRODUCT + "getTime")
    Call<ResultData2<String>> getTime1();

    /**
     * 获取授权登录签名验证信息
     *
     * @return
     */
    @GET(USER + "getAuthInfo4Ali")
    Observable<ResultData2<String>> getAuthInfo4Ali(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 获取授权登录用户支付宝信息
     *
     * @param uuid
     * @param code  支付宝授权成功后获取的code
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "bindingUserInfoByAli")
    Observable<ResultData2<User>> bindingUserInfoByAli(
            @Field("uuid") String uuid,
            @Field("code") String code,
            @Field("token") String token);

    /**
     * 获取支付宝转账二维码列表
     *
     * @param uuid
     * @param token
     * @return
     */
    @GET(PAY + "getAliQrCode")
    Observable<ResultData<AlipayCode>> getAliQrCode(
            @Header("Cache-Control") String cacheControl,
            @Query("uuid") String uuid,
            @Query("token") String token);


//支付------------------------------

    /**
     * 获取后台配置的当前可用支付方式
     *
     * @param uuid
     * @param pageNum
     * @param pageSize
     * @param sorts
     * @param token
     * @return
     */
    @GET(PAY + "getPaymentList")
    Observable<ResultData<PaymentList>> getPaymentList(
            @Query("uuid") String uuid,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("sorts") String sorts,
            @Query("token") String token);


    /**
     * 购买理财产品（余额支付）
     *
     * @param uuid    用户编码
     * @param price   买入金额 （Base64）
     * @param product 买入产品编号
     * @param type    支付方式
     * @param code    短信验证码
     * @param coupon  优惠券
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(PAY + "productPay")
    Observable<ResultData2<String>> productPay(
            @Field("uuid") String uuid,
    @Field("price") String price,
    @Field("product") String product,
    @Field("type") String type,
    @Field("code") String code,
    @Field("coupon") String coupon,
    @Field("token") String token);

    /**
     * 畅捷支付
     *
     * @param uuid     用户编号
     * @param price    充值金额（以分为单位base64）
     * @param bankcard 用户选择银行的编号
     * @param type
     * @param body     充值描述
     * @param code     短信验证码
     * @param orderNum 订单编号
     * @param token    公共token
     * @return
     */
    @FormUrlEncoded
    @POST(PAY + "rechargeByChanpay")
    Observable<ResultData2<String>> rechargeByChanpay(
            @Field("uuid") String uuid,
            @Field("price") String price,
            @Field("bankcard") String bankcard,
            @Field("type") String type,
            @Field("body") String body,
            @Field("code") String code,
            @Field("orderNum") String orderNum,
            @Field("token") String token);

    /**
     * 提现
     *
     * @param uuid     用户编号
     * @param price    提现金额（以分为单位）
     * @param bankcard 提现银行卡编号
     * @param code     短信验证码
     * @param token    公共token
     * @return
     */
    @FormUrlEncoded
    @POST(PAY + "withdraw")
    Observable<ResultData2<String>> withdraw(
            @Field("uuid") String uuid,
            @Field("price") String price,
            @Field("bankcard") String bankcard,
            @Field("code") String code,
            @Field("token") String token);

    /**
     * 购买发短信
     *
     * @param uuid
     * @param token
     * @return
     */
    @GET(PAY + "sendCodeById")
    Observable<ResultData2<String>> sendCodeById(
            @Query("uuid") String uuid,
            @Query("token") String token,
            @Query("price") String price,
            @Query("product") String product);

    //--------------------------------------

    //--------------消息-----------------------

    /**
     * 获取"我的消息"列表
     *
     * @param uuid     用户编号
     * @param pageNum  页码
     * @param pageSize 条数
     * @param token    公共的token
     * @return
     */
    @GET(INFO + "getList")
    Observable<ResultData<Message>> getMessageList(
            @Query("uuid") String uuid,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("token") String token);


    /**
     * 通过消息id获取消息的详情，含已读
     *
     * @param uuid  消息编号
     * @param token 公共的token
     * @return
     */
    @GET(INFO + "get")
    Observable<ResultData2<Message>> getMessage(
            @Query("uuid") String uuid,
            @Query("token") String token,
            @Query("infoUuid") String infoUuid);

    /**
     * 消息"全部标为已读"
     *
     * @param uuid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(INFO + "read")
    Observable<ResultData2> setMessageAllRead(
            @Field("uuid") String uuid,
            @Field("token") String token);

    /**
     * 设置密码
     *
     * @param uuid    用户编码
     * @param token   公共token
     * @param encrypt 规则Base64(timestamps+md5(pwd)+MD5(key+time+md5(pwd)))
     * @return
     */
    @FormUrlEncoded
    @POST(USER + "resetpwd")
    Observable<ResultData2> setUserPwd(
            @Field("uuid") String uuid,
            @Field("token") String token,
            @Field("encrypt") String encrypt);

    //---------------优惠券---------------

    /**
     * 获取优惠券列表
     *
     * @param uuid   用户编号
     * @param status use-未使用；history-已使用+已过期
     * @param price  购买金额（Base641，以分为单位）
     * @param token  公共token
     * @return
     */
    @GET(COUPON + "getList")
    Observable<ResultData<Coupon>> getCouponList(
            @Query("uuid") String uuid,
            @Query("status") String status,
            @Query("price") String price,
            @Query("token") String token);

    //-----------------------------------
    //-------------大礼包-----------------

    /**
     * 领取优惠券
     *
     * @param uuid
     * @param token
     * @return
     */
    @GET(COUPON + "getNotActiveList")
    Observable<ResultData2<Gift>> getNotActiveList(
            @Query("uuid") String uuid,
            @Query("token") String token);

    /**
     * 激活优惠券和现金红包
     *
     * @param uuid
     * @param token
     * @param coupons    优惠券编码（数组）
     * @param flagShare  是否分享
     * @param redPackets 红包编号
     * @return
     */
    @FormUrlEncoded
    @POST(COUPON + "activate")
    Observable<ResultData2<String>> receiveActivate(
            @Field("uuid") String uuid,
            @Field("token") String token,
            @Field("coupons") String coupons,
            @Field("flagShare") boolean flagShare,
            @Field("redPackets") String redPackets);
    //-----------------------------------

    @GET(ADVERT + "getAdvertList")
    Observable<ResultData2<Gift>> getAdvertList();
}
