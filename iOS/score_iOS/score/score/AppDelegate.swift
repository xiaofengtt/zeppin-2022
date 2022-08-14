//
//  AppDelegate.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/22.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        LocalDataController.loadLocalData()
        
        UMConfigure.initWithAppkey("5ceb4ef53fc195d817000325", channel: "App Store")
        MobClick.setScenarioType(eScenarioType.E_UM_NORMAL)
        
        UMSocialManager.default().setPlaform(UMSocialPlatformType.wechatSession, appKey: "wxd91a9c85747f2f7f", appSecret: "4846ceadae1f895fa9903846ec0c882b", redirectURL: "http://mobile.umeng.com/social")
        Thread.sleep(forTimeInterval: 1)
        UMSocialManager.default().setPlaform(UMSocialPlatformType.QQ, appKey: "1105768868", appSecret: "dercsd0zL5qibMeQ", redirectURL: "http://mobile.umeng.com/social")
//        UMSocialManager.default().setPlaform(UMSocialPlatformType.sina, appKey: "566340920", appSecret: "5ac6e9179b04449376176033922a290f", redirectURL: "http://sns.whalecloud.com/sina2/callback")
        UMSocialUIManager.setPreDefinePlatforms(sharePlatforms)
        
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

