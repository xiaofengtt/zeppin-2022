//
//  AppDelegate.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/15.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
import CoreData

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate ,WXApiDelegate{

    var window: UIWindow?
    var httpController = HttpController()

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        IOSVersion = NSString(string: UIDevice.currentDevice().systemVersion).doubleValue
        LocalDataController.loadLocalData()
        MobClick.setAppVersion(programVersion)
        MobClick.startWithAppkey("55d2958967e58ee42300014d", reportPolicy: SEND_INTERVAL, channelId: nil)
        ShareSDK.registerApp("57ec614710ef")
        ShareSDK.connectSinaWeiboWithAppKey("828478288", appSecret: "8b38124d35cb1d1ea2ccd42d16c87345", redirectUri: "http://sns.whalecloud.com/sina2/callback")
        ShareSDK.connectQZoneWithAppKey("1104620628", appSecret: "bFXB8eqo4icRirod", qqApiInterfaceCls: QQApiInterface.classForCoder(), tencentOAuthCls: TencentOAuth.classForCoder())
        ShareSDK.connectQQWithQZoneAppKey("1104620628", qqApiInterfaceCls: QQApiInterface.classForCoder(), tencentOAuthCls: TencentOAuth.classForCoder())
        ShareSDK.connectWeChatWithAppId("wx451427d0a7a64238", appSecret: "57cf742e2810c2885e8f9330993a9f94", wechatCls: WXApi.classForCoder())
        screenWidth = UIScreen.mainScreen().bounds.width
        screenHeight = UIScreen.mainScreen().bounds.height
        return true
    }
    
    func applicationWillResignActive(application: UIApplication) {
        
    }

    func applicationDidEnterBackground(application: UIApplication) {
        
    }

    func applicationWillEnterForeground(application: UIApplication) {
        if user.id != 0{
            if user.password != "" && user.phone != ""{
                let postData = user.getSsoUserLoginString()
                let UserParams = NSDictionary(dictionary: ["postData" : postData])
                httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }else{
                let postData = user.getUserAuthLoginString()
                let UserParams = NSDictionary(dictionary: ["postData" : postData])
                httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }
        }
    }

    func applicationDidBecomeActive(application: UIApplication) {
        
    }

    func applicationWillTerminate(application: UIApplication) {
        self.saveContext()
    }

    func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject) -> Bool {
        if let sourceApp = sourceApplication{
            if sourceApp == "com.alipay.iphoneclient"{
                AlipaySDK.defaultService().processOrderWithPaymentResult(url, standbyCallback: { (redict) -> Void in
                })
                return true
            }else if sourceApp == "com.tencent.xin"{
                ShareSDK.handleOpenURL(url, sourceApplication: sourceApplication, annotation: annotation, wxDelegate: self)
                return WXApi.handleOpenURL(url, delegate: self)
            }else{
                return ShareSDK.handleOpenURL(url, sourceApplication: sourceApplication, annotation: annotation, wxDelegate: self)
            }
        }
        return true
    }
    
    func application(application: UIApplication, handleOpenURL url: NSURL) -> Bool {
        WXApi.handleOpenURL(url, delegate: self)
        return ShareSDK.handleOpenURL(url, wxDelegate: self)
    }
    
    lazy var applicationDocumentsDirectory: NSURL = {
        let urls = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)
        return urls[urls.count-1] 
    }()

    lazy var managedObjectModel: NSManagedObjectModel = {
        let modelURL = NSBundle.mainBundle().URLForResource("SelfCool", withExtension: "momd")!
        return NSManagedObjectModel(contentsOfURL: modelURL)!
    }()

    lazy var persistentStoreCoordinator: NSPersistentStoreCoordinator? = {
        var coordinator: NSPersistentStoreCoordinator? = NSPersistentStoreCoordinator(managedObjectModel: self.managedObjectModel)
        let url = self.applicationDocumentsDirectory.URLByAppendingPathComponent("SelfCool.sqlite")
        var error: NSError? = nil
        var failureReason = "There was an error creating or loading the application's saved data."
        do {
            try coordinator!.addPersistentStoreWithType(NSSQLiteStoreType, configuration: nil, URL: url, options: nil)
        } catch var error1 as NSError {
            error = error1
            coordinator = nil
            var dict = [String: AnyObject]()
            dict[NSLocalizedDescriptionKey] = "Failed to initialize the application's saved data"
            dict[NSLocalizedFailureReasonErrorKey] = failureReason
            dict[NSUnderlyingErrorKey] = error
            error = NSError(domain: "YOUR_ERROR_DOMAIN", code: 9999, userInfo: dict)
            NSLog("Unresolved error \(error), \(error!.userInfo)")
            abort()
        } catch {
            fatalError()
        }
        
        return coordinator
    }()

    lazy var managedObjectContext: NSManagedObjectContext? = {
        let coordinator = self.persistentStoreCoordinator
        if coordinator == nil {
            return nil
        }
        var managedObjectContext = NSManagedObjectContext()
        managedObjectContext.persistentStoreCoordinator = coordinator
        return managedObjectContext
    }()

    func saveContext () {
        if let moc = self.managedObjectContext {
            var error: NSError? = nil
            if moc.hasChanges {
                do {
                    try moc.save()
                } catch let error1 as NSError {
                    error = error1
                    NSLog("Unresolved error \(error), \(error!.userInfo)")
                    abort()
                }
            }
        }
    }
    
    func onResp(resp: BaseResp!) {
        if resp.isKindOfClass(PayResp.classForCoder()){
            let tittle = "支付结果"
            var message = ""
            if resp.errCode == WXSuccess.rawValue{
                message = "支付成功"
            }else if resp.errCode == -1{
                message = "支付失败"
            }else if resp.errCode == -2{
                message = "支付已取消"
            }
            let alert = UIAlertView(title: tittle, message: message, delegate: self, cancelButtonTitle: "确认")
            alert.show()
        }
    }
    
    func onReq(rep: BaseReq!){}
    
}

