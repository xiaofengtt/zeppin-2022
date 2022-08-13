package cn.zeppin.product.ntb.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.zeppin.product.ntb.R;
import jameson.io.library.util.LogUtils;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/9/30
 */

public class ImageUtil {
    public static final int GET_IMAGE_BY_CAMERA = 5001;
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    public static final int CROP_IMAGE = 5003;
    public static Uri imageUriFromCamera;
    public static Uri cropImageUri;

    /**
     * 打开相机
     *
     * @param activity
     */
    public static void openCameraImage(final Activity activity) {
        ImageUtil.imageUriFromCamera = ImageUtil.createImagePathUri(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtil.imageUriFromCamera);
        activity.startActivityForResult(intent, ImageUtil.GET_IMAGE_BY_CAMERA);
    }

    /**
     * 打开本地相册
     *
     * @param activity
     */
    public static void openLocalImage(final Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, ImageUtil.GET_IMAGE_FROM_PHONE);
    }


    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.TITLE, imageName);
        values.put(MediaStore.Images.Media.DATE_ADDED, time);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            imageFilePath = context.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver()
                    .insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        return imageFilePath;
    }


    public static void crop(Activity activity, Uri uri) {
        ImageUtil.cropImageUri = ImageUtil.createImagePathUri(activity);
        if (uri == null) {
            return;
        }
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtil.cropImageUri);
        activity.startActivityForResult(intent, CROP_IMAGE);
    }


    public static void deleteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }


    public static File uri2File(Uri uri, Activity activity) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.getContentResolver()
                .query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);

        file = new File(img_path);
        return file;
    }


    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media
                    .getBitmap(activity.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            LogUtils.e("[Android]", e.getMessage());
            LogUtils.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除多余图片
     *
     * @param context
     * @param uri
     */
    public static void deleteImg(Context context, Uri uri) {
        String path = ImageUtil.getPathByUri(uri, context);
        if (!TextUtils.isEmpty(path)) {
            ContentResolver mContentResolver = context.getContentResolver();
            String where = MediaStore.Images.Media.DATA + "='" + path + "'";
            mContentResolver.delete(uri, where, null);
        }
    }

    /**
     * 通过URI获取图片PATH
     *
     * @param uri
     * @param context
     * @return
     */
    public static String getPathByUri(Uri uri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = context.getContentResolver()
                .query(uri, proj, null, null, null);
        assert actualimagecursor != null;
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);

        return img_path;
    }


    public static File getFileByUri(Uri uri, Activity activity) {
        File file = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor actualimagecursor = activity.getContentResolver()
//                .query(uri, proj, null, null, null);
//        int actual_image_column_index = actualimagecursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor
//                .getString(actual_image_column_index);

        file = new File(getPath(activity, uri));
        return file;
    }


    /**
     * 由于4.4之前content后面是文件全路径，4.4之后不再直接表示路径
     * 所以做以下处理
     *
     * @param context
     * @param data
     */
    public static String getPath(Context context, Uri data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getPathByUri4kitkat(context, data);
        } else {
            return getPathByUriOld(context, data);
        }

    }


    public static String getPathByUriOld(Context context, Uri data) {
        String filename = null;
        if (data.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(data, new String[]{"_data"}, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (data.getScheme().toString().compareTo("file") == 0) {// file:///开头的uri
            filename = data.toString();
            filename = data.toString().replace("file://", "");// 替换file://
            if (!filename.startsWith("/mnt")) {// 加上"/mnt"头
                filename += "/mnt";
            }
        }
        return filename;
    }


    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static String getPathByUri(Context context, Uri data) {
        String filename = null;
        if (data.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(data, new String[]{"_data"}, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (data.getScheme().toString().compareTo("file") == 0) {// file:///开头的uri
            filename = data.toString();
            filename = data.toString().replace("file://", "");// 替换file://
            if (!filename.startsWith("/mnt")) {// 加上"/mnt"头
                filename += "/mnt";
            }
        }
        return filename;
    }

    public static Bitmap drawable2Bitmap(Context context, int id) {

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
        opt.inInputShareable = true;
        opt.inPurgeable = true;//设置图片可以被回收
        InputStream is = context.getResources().openRawResource(id);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static Bitmap drawTextToBitmap(Context gContext,
                                          int gResId,
                                          String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        bitmap = scaleWithWH(bitmap, bitmap.getWidth() * scale, bitmap.getHeight() * scale);

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();

        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

//        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.gift_act_bg).copy(Bitmap.Config.ARGB_8888, true);;

//        Bitmap bitmap = BitmapFactory.decodeResource(gContext.getResources(), R.drawable.gift_act_bg);
//        int width = bitmap.getWidth();
//        int hight = bitmap.getHeight();
//        //建立一个空的Bitmap
//        Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
//        // 初始化画布绘制的图像到icon上
//        Canvas canvas = new Canvas(icon);
//        // 建立画笔
//        Paint photoPaint = new Paint();
//        // 获取更清晰的图像采样，防抖动
//        photoPaint.setDither(true);
//        // 过滤一下，抗剧齿
//        photoPaint.setFilterBitmap(true);

        String unitsTxt = "元";

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(resources.getColor(R.color.color_DF443B));
        paint.setTextSize(72*scale);
        paint.setDither(true); //获取跟清晰的图像采样
        paint.setFilterBitmap(true);//过滤一些
        paint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        float x = bitmap.getWidth()/2;
        float y = canvas.getHeight() * 0.49f;
        canvas.drawText(gText, x, y , paint);

        Paint paintEnd = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paintEnd.setColor(resources.getColor(R.color.color_DF443B));
        paintEnd.setTextSize(resources.getDimension(R.dimen.text_14));
        paintEnd.setDither(true); //获取跟清晰的图像采样
        paintEnd.setFilterBitmap(true);//过滤一些
        Rect boundsEnd = new Rect();
        paintEnd.getTextBounds(unitsTxt, 0, unitsTxt.length(), boundsEnd);
        float xEnd = x  + bounds.width() / 2+20;
        float yEnd =  y;
        canvas.drawText(unitsTxt, xEnd, yEnd, paintEnd);
        return bitmap;
    }

    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}
