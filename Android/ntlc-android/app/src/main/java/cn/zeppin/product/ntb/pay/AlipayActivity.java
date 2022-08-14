package cn.zeppin.product.ntb.pay;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.geng.library.commonutils.ToastUitl;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.PayContract;
import cn.zeppin.product.ntb.ui.user.model.PayModel;
import cn.zeppin.product.ntb.ui.user.presenter.PayPresenter;
import cn.zeppin.product.ntb.utils.AlipayUtil;

public class AlipayActivity extends BaseActivity<PayPresenter, PayModel> implements PayContract.View {

    @Bind(R.id.button)
    Button button;
    private String account = "0.01";

    @Override
    public int getLayoutId() {
        return R.layout.activity_alipay;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                authV2(v);
//                mPresenter.getAuthInfo4Ali(AppApplication.getInstance().getUuid());
                payV2(v);
            }
        });
    }


    /**
     * 支付宝支付业务：入参app_id
     */
    // public static final String APPID = "2088021828014590";
    public static final String APPID = "2016071901635869";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088021828014590";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "kkkkk091125";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";

    // public static final String RSA2_PRIVATE =
    // "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPYjArC1ZAJyF7UkMVxEcgxzJ0ejazJMc1yecV3sqCs7fqogaFClspelUEb+e15ym4vbLEPuMDji5Jdl5OXMzVvW6JIncI/2Xp9tNZWZNxuWWuXvoiLuHMcvhpKpxM6qdMi/pIhQqqQtXcJc6PcMYTcGMoOc22029V/QAR8RjF1xAgMBAAECgYBIFjThgB6MQNIEsVbriPhGASvN5WSt1Ios5BKbyYXvM3uDY/5pMD4//6ClSj7jiHlZ2pT6SDZDUuBUHvmM/BBgdGT+uENakz6CP1oS1eUgaiinsxdEGFd5ait+ht5kqIH4SSSgabHCqiEDHwQqQA7JlfGlzeGVIg4VWhKZ5dJMYQJBAPw3K9Ck+QHRolWeGMgcMnq5WNV4SMymb9aUsUagWX7uGG8p/0FB3qevXx76X4EykpgKt/GvaUjau0TEv0+EPn0CQQD51H08cHu9Id5dSgwrL+ZRC/yAGjs7ot1yimzrXABZIcsUy2Sr1ERdAZIAuO6AT8cEnNlOTDmoi85XbwDsM8kFAkBJLZzJ2cPh0jg+jTN1hDDlSLfMoCzHLBdQ9C2HZ2jwGhb+0fmcrobyskBwFYb2Tn0YHiwGtLVgjQ4+wrMbWCxlAkEAj0xxlTRT1XVSzaHGfxMXgY9lgrkJFrjhWmzJ8uovjPCUQtYzZVf46nwXGfD5ZIHd/uhUwNN6ExI2BfK2zcUaXQJADneNqBMs3w5J0wp9drVZ+G8/zJypJh3CtiK16xFbX6vMVXq2b+nBAWj3TbMurRe+SmEs+Ygzh0IHczEQyOLGcg==";
    // public static final String RSA_PRIVATE =
    // "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPYjArC1ZAJyF7UkMVxEcgxzJ0ejazJMc1yecV3sqCs7fqogaFClspelUEb+e15ym4vbLEPuMDji5Jdl5OXMzVvW6JIncI/2Xp9tNZWZNxuWWuXvoiLuHMcvhpKpxM6qdMi/pIhQqqQtXcJc6PcMYTcGMoOc22029V/QAR8RjF1xAgMBAAECgYBIFjThgB6MQNIEsVbriPhGASvN5WSt1Ios5BKbyYXvM3uDY/5pMD4//6ClSj7jiHlZ2pT6SDZDUuBUHvmM/BBgdGT+uENakz6CP1oS1eUgaiinsxdEGFd5ait+ht5kqIH4SSSgabHCqiEDHwQqQA7JlfGlzeGVIg4VWhKZ5dJMYQJBAPw3K9Ck+QHRolWeGMgcMnq5WNV4SMymb9aUsUagWX7uGG8p/0FB3qevXx76X4EykpgKt/GvaUjau0TEv0+EPn0CQQD51H08cHu9Id5dSgwrL+ZRC/yAGjs7ot1yimzrXABZIcsUy2Sr1ERdAZIAuO6AT8cEnNlOTDmoi85XbwDsM8kFAkBJLZzJ2cPh0jg+jTN1hDDlSLfMoCzHLBdQ9C2HZ2jwGhb+0fmcrobyskBwFYb2Tn0YHiwGtLVgjQ4+wrMbWCxlAkEAj0xxlTRT1XVSzaHGfxMXgY9lgrkJFrjhWmzJ8uovjPCUQtYzZVf46nwXGfD5ZIHd/uhUwNN6ExI2BfK2zcUaXQJADneNqBMs3w5J0wp9drVZ+G8/zJypJh3CtiK16xFbX6vMVXq2b+nBAWj3TbMurRe+SmEs+Ygzh0IHczEQyOLGcg==";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(AlipayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();

                        awarAuthor();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(AlipayActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()),
                                Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成； 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, "0.01", "选课报名费");
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        System.out.println("orderparam: " + orderParam);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;

        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AlipayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE))// && TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(AlipayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    public void awarAuthor() {
        if (AlipayUtil.hasInstalledAlipayClient(this)) {
            AlipayUtil.startAlipayClient(this, "FKX08879LCON2PIIGLMUE2");  //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
        } else {
            ToastUitl.showToastCenter("没有检测到支付宝客户端");
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        showToastCenter(msg);
    }

//    @Override
//    public void getAuthInfo(final String authInfo) {
//        Runnable authRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造AuthTask 对象
//                AuthTask authTask = new AuthTask(AlipayActivity.this);
//                // 调用授权接口，获取授权结果
//                Map<String, String> result = authTask.authV2(authInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_AUTH_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread authThread = new Thread(authRunnable);
//        authThread.start();
//    }

    @Override
    public void returnPaymentList(List<PaymentList> list) {

    }

    @Override
    public void returnAuthInfo(String authInfo) {

    }

    @Override
    public void returnAliQrCode(List<AlipayCode> list) {

    }

    @Override
    public void successBindingAli(User user) {

    }

    @Override
    public void sendCodeSuccess(String orderNum,boolean isRegain) {

    }

    @Override
    public void sendCodeFailed(String error) {

    }

    @Override
    public void SuccessByChanpay() {

    }

    @Override
    public void returnMyBankList(List<MyBank> list) {

    }

    @Override
    public void returnUserInfo(User user) {

    }
}
