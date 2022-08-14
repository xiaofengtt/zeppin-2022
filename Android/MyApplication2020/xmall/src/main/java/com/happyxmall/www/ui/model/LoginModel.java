package com.happyxmall.www.ui.model;

import com.happyxmall.www.app.Api;
import com.happyxmall.www.bean.ResultData;
import com.happyxmall.www.ui.contract.LoginContract;
import com.happyxmall.www.utils.SpfUtil;
import com.happyxmall.www.utils.Utility;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<ResultData<String>> checkin(String uniqueToken, String token, String deviceToken) {
        String countryCode = Utility.getCountryCode();
        return Api.getDefault().checkin(token, uniqueToken, deviceToken, countryCode).map(new Function<ResultData, ResultData<String>>() {
            @Override
            public ResultData<String> apply(ResultData resultData) throws Exception {
                return resultData;
            }
        });
    }
}
