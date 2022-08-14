package cn.zeppin.product.ntb.service;

import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.allenliu.versionchecklib.core.AVersionService;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/11/24
 */

public class ApkDownloadService extends AVersionService {
    public ApkDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        Log.e("DemoService", response);
//        UpdateVersionInfo updateVersionInfo = JSON.parseObject(response, UpdateVersionInfo.class);
        //可以在判断版本之后在设置是否强制更新或者VersionParams
        //eg
        // versionParams.isForceUpdate=true;
        String updateMsg = "是否升级到最新版本？";
        if (!TextUtils.isEmpty(versionParams.getUpdateMsg())) {
            updateMsg = versionParams.getUpdateMsg().replace("<br/>", "\n").replace("&#92;n", "\n");
        }
        showVersionDialog(versionParams.getDownloadUrl(), versionParams.getTitle(), updateMsg);
//        or
//        showVersionDialog("http://www.apk3.com/uploads/soft/guiguangbao/UCllq.apk", "检测到新版本", getString(R.string.updatecontent),bundle);

    }
}
