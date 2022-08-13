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
class AppDelegate: UIResponder, UIApplicationDelegate ,HttpDataProtocol {

    var window: UIWindow?
    var httpController = HttpController()

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        httpController.delegate = self
        IOSVersion = NSString(string: UIDevice.currentDevice().systemVersion).doubleValue
        LocalDataController.loadLocalData()
        ShareSDK.registerApp("57ec614710ef")
        ShareSDK.connectSinaWeiboWithAppKey("568898243", appSecret: "38a4f8204cc784f81f9f0daaf31e02e3", redirectUri: "http://www.sharesdk.cn")
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
            var postData = user.getUserData()
            var UserParams = NSDictionary(dictionary: ["postData" : postData])
            httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }
    }

    func applicationDidBecomeActive(application: UIApplication) {
        
    }

    func applicationWillTerminate(application: UIApplication) {
        self.saveContext()
    }

    func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject?) -> Bool {
        return ShareSDK.handleOpenURL(url, sourceApplication: sourceApplication, annotation: annotation, wxDelegate: nil)
    }
    
    func application(application: UIApplication, handleOpenURL url: NSURL) -> Bool {
         return ShareSDK.handleOpenURL(url, wxDelegate: nil)
    }
    
    lazy var applicationDocumentsDirectory: NSURL = {
        let urls = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)
        return urls[urls.count-1] as! NSURL
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
        if coordinator!.addPersistentStoreWithType(NSSQLiteStoreType, configuration: nil, URL: url, options: nil, error: &error) == nil {
            coordinator = nil
            var dict = [String: AnyObject]()
            dict[NSLocalizedDescriptionKey] = "Failed to initialize the application's saved data"
            dict[NSLocalizedFailureReasonErrorKey] = failureReason
            dict[NSUnderlyingErrorKey] = error
            error = NSError(domain: "YOUR_ERROR_DOMAIN", code: 9999, userInfo: dict)
            NSLog("Unresolved error \(error), \(error!.userInfo)")
            abort()
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
            if moc.hasChanges && !moc.save(&error) {
                NSLog("Unresolved error \(error), \(error!.userInfo)")
                abort()
            }
        }
    }
    
    func onResp(resp: BaseResp!) {
        
    }
    
    func onReq(rep: BaseReq!){
        
    }
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
    }
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary , inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            var status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                UserImageDownload("userIcon", user.iconUrl)
                var userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
            }
        }
    }
}

