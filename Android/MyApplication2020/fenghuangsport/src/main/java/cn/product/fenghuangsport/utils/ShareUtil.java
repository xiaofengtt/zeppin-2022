package cn.product.fenghuangsport.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

import cn.product.fenghuangsport.R;


/**
 * 描述：分享
 * 开发人: geng
 * 创建时间: 17/12/5
 */

public class ShareUtil {
    private Activity activity;

    public ShareUtil(Activity activity) {
        this.activity = activity;
    }

    public ShareAction shareMenu(final String url, final String title, final String coverUrl, final String des, final UMShareListener umShareListener) {
        final UMImage image = new UMImage(activity, coverUrl);//网络图片
        image.setThumb(new UMImage(activity, R.mipmap.ic_launcher));
        ShareAction shareAction = new ShareAction(activity).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                        if (share_media == SHARE_MEDIA.SINA) {//新浪微博
//                            shareStyleImage(share_media, imgUrl, des, videoUrl, videoTitle, umShareListener);
//                        } else {//其他
//                            shareStyleVideo(share_media, imgUrl, des, videoUrl, videoTitle, umShareListener);
//                        }
                        shareStyleWeb(share_media, url, image, title, des, umShareListener);
                    }
                });
        shareAction.open(shareConfig());
        return shareAction;
    }

    public ShareBoardConfig shareConfig() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleText("分享到");
        config.setCancelButtonText("取消");
        config.setIndicatorVisibility(false);
        return config;
    }

    /**
     * 分享类型：图片http（有链接有内容）
     *
     * @param share_media
     * @param bmp
     * @param des
     */
    public ShareAction shareStyleImage(SHARE_MEDIA share_media, Bitmap bmp, String des, UMShareListener umShareListener) {
        UMImage imageUrl = new UMImage(activity, bmp);
        imageUrl.setThumb(new UMImage(activity, R.mipmap.ic_launcher));
        ShareAction shareAction = new ShareAction(activity)
//                .withText(des)
                .withMedia(imageUrl)
                .setPlatform(share_media)
                .setCallback(umShareListener == null ? new CustomShareListener(activity) : umShareListener);
        shareAction.share();
        return shareAction;
    }

    /**
     * http链接分享（有标题有内容）
     *
     * @param share_media
     * @param url
     * @param title
     * @param des
     * @return
     */
    public ShareAction shareStyleWeb(SHARE_MEDIA share_media, String url, UMImage imageUrl, String title, String des, UMShareListener umShareListener) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(imageUrl);
        web.setDescription(des);
        ShareAction shareAction = new ShareAction(activity)
                .withText(des)
                .withMedia(web)
                .setPlatform(share_media)
                .setCallback(umShareListener == null ? new CustomShareListener(activity) : umShareListener);
        shareAction.share();
        return shareAction;
    }

    /**
     * 分享结果监听
     */
    private class CustomShareListener implements UMShareListener {
        private WeakReference<Activity> mActivity;

        public CustomShareListener(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.showToastCenter("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (UMShareAPI.get(mActivity.get()).isInstall(mActivity.get(), share_media)) {
                ToastUtil.showToastCenter(throwable.getMessage());
            } else {
                ToastUtil.showToastCenter(chinesizationByPlatform(share_media) + "应用未安装");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    }

    public static String chinesizationByPlatform(SHARE_MEDIA platform) {
        if (platform == SHARE_MEDIA.QQ) {
            return "QQ";
        }
        if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            return "微信";
        }
        if (platform == SHARE_MEDIA.SINA) {
            return "新浪微博";
        }
        return platform + "";
    }

}
