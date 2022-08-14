//
//  AppDelegate.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit
//import AssetsLibrary
//import Photos

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        LocalDataController.loadLocalData()
        HttpController.loadSwitch()
        
        UMConfigure.initWithAppkey("5a338badb27b0a7d790000af", channel: "App Store")
        MobClick.setScenarioType(eScenarioType.E_UM_NORMAL)
        
        UMSocialManager.default().umSocialAppkey = "5a338badb27b0a7d790000af"
        UMSocialManager.default().setPlaform(UMSocialPlatformType.wechatSession, appKey: "wxd91a9c85747f2f7f", appSecret: "4846ceadae1f895fa9903846ec0c882b", redirectURL: "http://mobile.umeng.com/social")
//        UMSocialManager.default().setPlaform(UMSocialPlatformType.QQ, appKey: "1105768868", appSecret: "dercsd0zL5qibMeQ", redirectURL: "http://mobile.umeng.com/social")
//        UMSocialManager.default().setPlaform(UMSocialPlatformType.sina, appKey: "566340920", appSecret: "5ac6e9179b04449376176033922a290f", redirectURL: "http://sns.whalecloud.com/sina2/callback")
        
//        let cameraStatus = AVCaptureDevice.authorizationStatus(for: AVMediaType.video)
//        let photoStatus = ALAssetsLibrary.authorizationStatus()
        
        if(user != nil){
            HttpController.getUser(uuid: user!.uuid, data: { (data) in
                HttpController.getUserAccount(data: { (data) in }) { (error) in }
            }, errors: {(error) in })
//            HttpController.getUser(uuid: "ec411fd6-9984-422e-975c-f37572e17cdd", data: { (data) in
//                HttpController.getUserAccount(data: { (data) in }) { (error) in }
//            }, errors: {(error) in })//25
        }
        return true
    }
    
    func application(_ application: UIApplication, open url: URL, sourceApplication: String?, annotation: Any) -> Bool {
        //跳转支付宝钱包进行支付，处理支付结果
        if(url.host == "safepay"){
            AlipaySDK.defaultService().processAuth_V2Result(url) { (data) in
                AlipayController.alipayResult(result: data)
            }
        }else{
            UMSocialManager.default().handleOpen(url, sourceApplication: sourceApplication, annotation: annotation)
        }
        return true
    }
    
    
    
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

