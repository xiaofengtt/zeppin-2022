package com.zixueku.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.animation.BounceInterpolator;
/**
 * 
 * @author Crist
 *
 */
//创建本地的图片  
public class ImageHelper {
	
	/**
	 * 根据手机屏幕的宽等比放大或缩小图片
	 * @param w
	 * @return
	 */
	public static Drawable resizeImage(DisplayMetrics metrics,Bitmap bitmap, int screenWidth) {
		long w = (long) (screenWidth*metrics.density);
	     int width = bitmap.getWidth();
	     int height = bitmap.getHeight();
	     int newWidth = (int) w;
	     int newHeight = (int) (w*height/width); // 根据屏幕的宽度，计算按比较缩放后的高度
	     
	     // calculate the scale
	     float scaleWidth = ((float) newWidth) / width;
	     float scaleHeight = ((float) newHeight) / height;

	     // create a matrix for the manipulation
	     Matrix matrix = new Matrix();
	     // resize the Bitmap
	     matrix.postScale(scaleWidth, scaleHeight);
	     // if you want to rotate the Bitmap
	     // matrix.postRotate(45);
	     // recreate the new Bitmap
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	     // make a Drawable from Bitmap to allow to set the Bitmap
	     // to the ImageView, ImageButton or what ever
	     return new BitmapDrawable(resizedBitmap);
	} 
	
	
	public static Bitmap getCompleteBitmap(String filePath, int size){
		return get1000px(filePath, 1,size);
	}
	
	public static Bitmap gethalfBitmap(String filePath, int size){
		return get1000px(filePath, 2,size);
	}

	/**
	 * 通过nul返回最长行为1000像素的bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	private static Bitmap get1000px(String filePath,int scale, int size) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;//只获取图片的属性
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
		//图片的宽高
		int width = opts.outWidth;
		int height = opts.outHeight;
		double s;
		if (width > height) {
			s = (double)width / height;
			double w1 = size;
			double h1 = size/s;
			System.out.println("width > height-----width="+width+"height="+height);
			return getBitmapFromUrl(filePath,h1/scale,w1/scale);
		} else {
			s = (double)height / width;
			double h2 = size;
			double w2 = size/s;
			System.out.println("width <= height-----width="+width+"height="+height);
			return getBitmapFromUrl(filePath, w2/scale, h2/scale);
		}
	}
	
	/**
	 * 根据路径获取图片资源（已缩放）
	 * 
	 * @param url
	 *            图片存储路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 * @return
	 */
	private static Bitmap getBitmapFromUrl(String url, double width, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
		Bitmap bitmap = BitmapFactory.decodeFile(url);
		// 防止OOM发生
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		// 按照固定宽高进行缩放
		// 这里希望知道照片是横屏拍摄还是竖屏拍摄
		// 因为两种方式宽高不同，缩放效果就会不同
		// 这里用了比较笨的方式
		if (mWidth <= mHeight) {
			scaleWidth = (float) (width / mWidth);
			scaleHeight = (float) (height / mHeight);
		} else {
			scaleWidth = (float) (height / mWidth);
			scaleHeight = (float) (width / mHeight);
		}
		// matrix.postRotate(90); /* 翻转90度 */
		// 按照固定大小对图片进行缩放
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		// 用完了记得回收
		bitmap.recycle();
		return newBitmap;
	}
	
	public static Bitmap getScaleBitmap(Bitmap bitmap, float scale) {
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		// matrix.postRotate(90); /* 翻转90度 */
		// 按照固定大小对图片进行缩放
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		return newBitmap;
	}


	/**
	 * @param filePath
	 *            本地保存的文件路径
	 * @return 返回压缩过的图片
	 */
	public static Bitmap createSmallImage(String filePath) {
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);

		opts.inSampleSize = computeSampleSize(opts, -1, 150 * 150);
		opts.inJustDecodeBounds = false;

		try {
			bitmap = BitmapFactory.decodeFile(filePath, opts);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	/**
	 * 保存压缩的图片
	 * 
	 * @param bitmap
	 *            需要保存的图片
	 * @param file
	 *            图片的保存完整路径
	 * @return true 保存成功 ，false 否则保存失败
	 */
	public static boolean saveCompressBitmap(File file) {
		FileOutputStream fileOutputStream = null;
		Bitmap bitmap = createImage(file.toString());
		try {
			fileOutputStream = new FileOutputStream(file);
			return bitmap.compress(Bitmap.CompressFormat.JPEG, 90,
					fileOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * 保存压缩的图片
	 * 
	 * @param bitmap
	 *            需要保存的图片
	 * @param file
	 *            图片的保存完整路径
	 * @return true 保存成功 ，false 否则保存失败
	 */
	public static boolean saveCompressBitmap(Bitmap bitmap, File file) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			return bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					fileOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap createImage(String filePath) {
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		// 获取缩放比例
		opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
		opts.inJustDecodeBounds = false;

		try {
			bitmap = BitmapFactory.decodeFile(filePath, opts);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	public static int computeSampleSize(BitmapFactory.Options options,

	int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * Decode and sample down a bitmap from a file to the requested width and
	 * height.
	 * 
	 * @param filename
	 *            The full path of the file to decode
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static Bitmap decodeSamllBitmapFromFile(String filename,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filename, options);
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
	 * object when decoding bitmaps using the decode* methods from
	 * {@link BitmapFactory}. This implementation calculates the closest
	 * inSampleSize that will result in the final decoded bitmap having a width
	 * and height equal to or larger than the requested width and height. This
	 * implementation does not ensure a power of 2 is returned for inSampleSize
	 * which can be faster when decoding but results in a larger bitmap which
	 * isn't as useful for caching purposes.
	 * 
	 * @param options
	 *            An options object with out* params already populated (run
	 *            through a decode* method with inJustDecodeBounds==true
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee a final image
			// with both dimensions larger than or equal to the requested height
			// and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

}