package cn.zeppin.product.ntb.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 项目名称：视频客服系统
 * 类描述：父类
 * 创建人：niulongxiang
 * 创建时间：16/7/22
 * 修改人：niulongxiang
 * 修改时间：16/7/22
 * 修改备注： 图片处理工具类
 */
public class FrescoUtils {

    public FrescoUtils() {
        super();
    }

    public static void loadAvatar(Uri uri, SimpleDraweeView simpleDraweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(request)
                .setOldController(simpleDraweeView
                        .getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    public static void loadImage(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri;
        if (TextUtils.isEmpty(url)) {
            uri= Uri.EMPTY;
        } else {
            if (!url.contains("http")) return;
            uri = Uri.parse(url);
        }
        try {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setImageRequest(request)
                    .setOldController(simpleDraweeView
                            .getController()).build();
            simpleDraweeView.setController(controller);
        } catch (Exception e) {
            return;
        }
    }

    public static void loadImage(Uri uri, SimpleDraweeView simpleDraweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(request)
                .setOldController(simpleDraweeView
                        .getController()).build();
        simpleDraweeView.setController(controller);
    }
}
