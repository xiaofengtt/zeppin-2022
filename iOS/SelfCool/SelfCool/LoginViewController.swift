//
//  ViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/15.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController , HttpDataProtocol{
    
    @IBOutlet weak var qqButton: UIButton!
    @IBOutlet weak var weixinButton: UIButton!
    
    var httpController = HttpController()
    var loadingView : UIView?
    var authOptions = ShareSDK.authOptionsWithAutoAuth(true, allowCallback: true, scopes: nil, powerByHidden: true, followAccounts: nil, authViewStyle: SSAuthViewStyleModal, viewDelegate: nil, authManagerViewDelegate: nil)
    
    override func viewDidLoad() {
        httpController.delegate = self
        
        loadingView = LoadingView(self)
//        if versionStatus != nil && versionStatus!{
//            loadingView!.removeFromSuperview()
//            qqButton.hidden = false
//            weixinButton.hidden = false
//        }
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            var status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                UserImageDownload("userIcon", user.iconUrl)
                var userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
                
                var userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! UIViewController
                self.presentViewController(vc, animated: true, completion: nil)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    @IBAction func qqLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeQQSpace, authOptions: nil) { (result, userInfo, error) -> Void in
            if result{
                self.view.addSubview(self.loadingView!)
                user.authType = 1
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                println(userInfo.sourceData())
                var postData = user.getUserData()
                var UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }else if error.errorCode() == -6004 {
                UIAlertView(title: "登陆失败", message: "您尚未安装QQ客户端", delegate: self, cancelButtonTitle: "确认").show()
            }
        }
    }
    
    @IBAction func weiboLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeSinaWeibo, authOptions: authOptions) { (result, userInfo, error) -> Void in
            if result{
                self.view.addSubview(self.loadingView!)
                user.authType = 3
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                
                var postData = user.getUserData()
                var UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }
        }
    }
    
    @IBAction func weixinLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeWeixiSession, authOptions: authOptions) { (result, userInfo, error) -> Void in
            if result{
                self.view.addSubview(self.loadingView!)
                user.authType = 2
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                
                var postData = user.getUserData()
                var UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }else if error.errorCode() == -22003{
                UIAlertView(title: "登陆失败", message: "您尚未安装微信客户端", delegate: self, cancelButtonTitle: "确认").show()
            }
        }
    }
    
    override func prefersStatusBarHidden() -> Bool {
        return true
    }

}

