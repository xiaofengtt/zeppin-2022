package cn.zeppin.product.ntb.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.zeppin.product.ntb.service.AlarmService;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 18/1/23
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }
}