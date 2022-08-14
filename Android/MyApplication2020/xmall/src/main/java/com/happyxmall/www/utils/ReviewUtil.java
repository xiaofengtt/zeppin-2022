package com.happyxmall.www.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.Task;

public class ReviewUtil {

    private static void goGoogleReview(Context context){
        String playPackage = "com.android.vending";
        try {
            Log.i("params println", "跳转应用商店去评价");
            String currentPackageName = context.getPackageName();
            if (currentPackageName != null) {
                Uri currentPackageUri = Uri.parse("market://details?id="+context.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, currentPackageUri);
                intent.setPackage(playPackage);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Uri currentPackageUri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, currentPackageUri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void review(Context context, Activity activity){
//        goGoogleReview(context);
        ReviewManager manager = ReviewManagerFactory.create(context);
//        ReviewManager manager = new FakeReviewManager(context);//测试
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //goGoogleReview(context);
                // We can get the ReviewInfo object
                Log.i("params println", "review1Successful："+task.getResult());
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    Log.i("params println", "review2Successful："+task2.getResult());
                });
            } else {
                // There was some problem, continue regardless of the result.
                Log.i("params println", "review Failed!：Goto GooglePlay and review"+task.getResult());
                //goGoogleReview(context);
            }
        });
    }
}
