import UIKit
import FBSDKCoreKit

@UIApplicationMain

class LuckyAppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        //初始化非死不可
//        ApplicationDelegate.shared.application(application, didFinishLaunchingWithOptions: launchOptions)
        
        //设备id 用于推送记录设备
        if let deviceUuid = LuckyLocalDataManager.getForKey(key: LuckyLocalDataManager.deviceUuid) as? String{
            globalDeviceUuid = deviceUuid
        }else{
            //没有则生成
            globalDeviceUuid = UUID().uuidString
            LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.deviceUuid, data: globalDeviceUuid as AnyObject)
        }
        
        //记载推送token
        if let deviceToken = LuckyLocalDataManager.getForKey(key: LuckyLocalDataManager.deviceToken) as? String{
            globalDeviceToken = deviceToken
        }
        
        //默认选择国家区号
        if let countryUuid = LuckyLocalDataManager.getForKey(key: LuckyLocalDataManager.countryKey) as? String{
            globalSelectedCountry = countryUuid
        }
        
        //底部控制器
        let tabbarController = LuckyBaseTabBarController()
        tabbarController.tabBar.isTranslucent = false
        tabbarController.tabBar.backgroundImage = UIImage.getImageByColor(UIColor.white)
        tabbarController.tabBar.unselectedItemTintColor = UIColor.darkGray
        
        //首页
        let homeController = LuckyHomeViewController()
        let homeNavigationController = LuckyBaseNavigationController(rootViewController: homeController)
        homeNavigationController.tabBarItem.image = UIImage(named: "tab_home_unselected")
        homeNavigationController.tabBarItem.selectedImage = UIImage(named: "tab_home_selected")
        homeNavigationController.title = NSLocalizedString("Home", comment: "")
        
        //分类
        let categoriesController = LuckyCategoriesViewController()
        let categoriesNavigationController = LuckyBaseNavigationController(rootViewController: categoriesController)
        categoriesNavigationController.tabBarItem.image = UIImage(named: "tab_categories_unselected")
        categoriesNavigationController.tabBarItem.selectedImage = UIImage(named: "tab_categories_selected")
        categoriesNavigationController.title = NSLocalizedString("Categories", comment: "")
        
        //开奖
        let recordController = LuckyRecordViewController()
        let recordNavigationController = LuckyBaseNavigationController(rootViewController: recordController)
        recordNavigationController.tabBarItem.image = UIImage(named: "tab_record_unselected")
        recordNavigationController.tabBarItem.selectedImage = UIImage(named: "tab_record_selected")
        recordNavigationController.title = NSLocalizedString("Record", comment: "")
        
        //晒单
        let storyController = LuckyStoryViewController()
        let storyNavigationController = LuckyBaseNavigationController(rootViewController: storyController)
        storyNavigationController.tabBarItem.image = UIImage(named: "tab_story_unselected")
        storyNavigationController.tabBarItem.selectedImage = UIImage(named: "tab_story_selected")
        storyNavigationController.title = NSLocalizedString("Story", comment: "")
        
        //账户
        let accountController = LuckyAccountViewController()
        let accountNavigationController = LuckyBaseNavigationController(rootViewController: accountController)
        accountNavigationController.tabBarItem.image = UIImage(named: "tab_account_unselected")
        accountNavigationController.tabBarItem.selectedImage = UIImage(named: "tab_account_selected")
        accountNavigationController.title = NSLocalizedString("Account", comment: "")
        
        tabbarController.viewControllers = [homeNavigationController, categoriesNavigationController, recordNavigationController, storyNavigationController, accountNavigationController]
        window?.rootViewController = tabbarController
        
        //注册远程推送
        let center = UNUserNotificationCenter.current()
        center.requestAuthorization(options: [UNAuthorizationOptions.alert, UNAuthorizationOptions.badge, UNAuthorizationOptions.sound]) { (success, error) in
            if(success){
                center.getNotificationSettings { (settings) in
                    if(settings.authorizationStatus == UNAuthorizationStatus.authorized){
                        let queue = DispatchQueue.main
                        queue.async {
                            application.registerForRemoteNotifications()
                        }
                    }
                }
            }
        }
        
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        //facebook跳转
        ApplicationDelegate.shared.application(app, open: url, sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String, annotation: options[UIApplication.OpenURLOptionsKey.annotation])
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        //接推送发token给服务器
        let token: String = deviceToken.map{ String(format: "%02.2hhx", $0) }.joined()
        if(token != globalDeviceToken){
            //token有变化
            globalDeviceToken = token
            LuckyRemotePushManager.uploadToken(deviceToken: token)
        }
    }
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        //接推送处理
        if(globalFlagUser){
            if(application.applicationState == UIApplication.State.active){
                //接收时在前台
                if let dataDic = userInfo["data"] as? NSDictionary{
                    if let noticeType = dataDic["noticeType"] as? String{
                        if(noticeType == "user_win"){
                            LuckyGradingManager.showGrading()
                        }
                        if(noticeType == "user_recharge" || noticeType == "user_withdraw"){
                            NotificationCenter.default.post(name: NSNotification.Name.RefreshUserAccount, object: nil)
                        }
                    }
                }
            }else{
                //接收时不在前台
                if let dataDic = userInfo["data"] as? NSDictionary{
                    if let noticeType = dataDic["noticeType"] as? String{
                        if(noticeType == "user_win"){
                            LuckyGradingManager.showGrading()
                        }
                    }
                }
            }
        }
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        NotificationCenter.default.post(name: NSNotification.Name.AppWillShow, object: nil)
        //回到前台长连接重连
        if(socketManager == nil || socketManager?.reconnectTimer == nil){
            socketManager = nil
            socketManager = LuckySocketManager()
        }
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        //退到后台长连接断开
        socketManager?.socket?.disconnect()
        socketManager = nil
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        //关闭时长连接断开
        socketManager?.socket?.disconnect()
        socketManager = nil
    }
}
