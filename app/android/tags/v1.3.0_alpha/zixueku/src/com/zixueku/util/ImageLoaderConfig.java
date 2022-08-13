package com.zixueku.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zixueku.R;

public class ImageLoaderConfig {

	/**
	 * 返回默认的参数配置
	 * 
	 * @param isDefaultShow
	 *            true：显示默认的加载图片 false：不显示默认的加载图片
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(boolean isShowDefault) {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.no_image).showImageOnFail(R.drawable.no_image).resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();

		return options;
	}

	/**
	 * 异步图片加载ImageLoader的初始化操作，在Application中调用此方法
	 * 
	 * @param context
	 *            上下文对象
	 * @param cacheDisc
	 *            图片缓存到SDCard的目录，只需要传入SDCard根目录下的子目录即可，默认会建立在SDcard的根目录下
	 */
	public static void initImageLoader(Context context, String cacheDisc) {
		// 配置ImageLoader
		// 获取本地缓存的目录，该目录在SDCard的根目录下
		//File cacheDir = StorageUtils.getCacheDirectory(context);
		// File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		// cacheDisc);
		// 实例化Builder
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		// 设置线程数量
		builder.threadPoolSize(3);
		// 设定线程等级比普通低一点
		builder.threadPriority(Thread.NORM_PRIORITY - 2);

		// 设定内存缓存为弱缓存
		builder.memoryCache(new WeakMemoryCache());
		builder.diskCacheSize(50 * 1024 * 1024); // 50 Mb
		// 设定内存图片缓存大小限制，不设置默认为屏幕的宽高
		builder.memoryCacheExtraOptions(480, 800);
		// 设定只保存同一尺寸的图片在内存
		builder.denyCacheImageMultipleSizesInMemory();
		// 设定网络连接超时 timeout: 10s 读取网络连接超时read timeout: 60s
		builder.imageDownloader(new BaseImageDownloader(context, 10000, 60000));
		// 设置ImageLoader的配置参数
		builder.defaultDisplayImageOptions(initDisplayOptions(true));
		// 初始化ImageLoader
		ImageLoader.getInstance().init(builder.build());
	}
}