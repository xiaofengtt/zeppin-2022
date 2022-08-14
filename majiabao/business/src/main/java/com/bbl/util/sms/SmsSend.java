package com.bbl.util.sms;


import com.bbl.business.smslog.entity.SmsLog;
import com.bbl.business.smslog.mapper.SmsLogMapper;
import com.bbl.util.IdGen;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Component
public class SmsSend {

    @Resource
    private SmsLogMapper smsLogMapper;

    public   Map<String,Object> sendCode(String ipAddr,String phone ) {
        //一个小时同一个号码限制发送6条
        SmsLog s1 = new SmsLog();
        s1.setPhone(phone);
        s1.setCreateTime(DateUtils.addHours(new Date(), -1));
        if (smsLogMapper.findList(s1) >= 6) {
            return ImmutableMap.of("status", "0","errmsg","发送频率过快");
        }
        //一个小时同IP限制发送6条
        SmsLog s2 = new SmsLog();
        s2.setIp(ipAddr);
        s2.setCreateTime(DateUtils.addHours(new Date(), -1));
        if (smsLogMapper.findList(s2) >= 6) {
            return ImmutableMap.of("status", "0","errmsg","发送频率过快");
        }

        Map<String,Object> status = null;
        if (SMSUtils.send("86" + phone, IdGen.randomLong(6))) {
            //保存发送记录
            SmsLog smsLog = new SmsLog();
            smsLog.setPhone(phone);
            smsLog.setIp(ipAddr);
            smsLogMapper.add(smsLog);
            //记录用户操作日志
            String optIp = ipAddr;
//            LogManager.me().executeLog(LogTaskFactory.operationLog(
//                    phone, LogModularEnum.HD_PHONE.getName(), "发送手机验证码" + phone, "发送成功", optIp, 1));
            status = ImmutableMap.of("status", "1");
        } else {
            String optIp = ipAddr;
//            LogManager.me().executeLog(LogTaskFactory.operationLog(
//                    phone, LogModularEnum.HD_PHONE.getName(), "发送手机验证码" + phone, "发送失败", optIp, 0));
//            status = ImmutableMap.of("status", "0");
            return ImmutableMap.of("status", "0","errmsg","发送验证码失败,请稍候在试");
        }
        return status;
    }

}
