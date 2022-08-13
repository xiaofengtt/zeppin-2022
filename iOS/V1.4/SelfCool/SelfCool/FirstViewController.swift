//
//  FirstViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/17.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController , UIAlertViewDelegate , HttpDataProtocol{
    
    var httpController = HttpController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        httpController.delegate = self
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
    }
    
    override func viewDidAppear(animated: Bool) {
        let versionParams = NSDictionary(dictionary: ["device" : "1"])
        httpController.getNSDataByParams("versionVerify", paramsDictionary: versionParams)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            let status = dataDictionary.objectForKey("Status") as! String
            let sb = UIStoryboard(name: "Main", bundle: nil)
            if status == "success" {
                let userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user = UserModel()
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
                user.name = userDictionary.objectForKey("nickname") as! String
                user.age = userDictionary.objectForKey("age") as! Int
                user.gender = userDictionary.objectForKey("gender") as! Int
                user.profession = userDictionary.objectForKey("professional") as! String
                user.iconUrl = userDictionary.objectForKey("imageUrl") as! String
                user.phone = userDictionary.objectForKey("phone") as! String
                user.password = userDictionary.objectForKey("password") as! String
                UserImageDownload("userIcon", urlString: user.iconUrl)
                let userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                
                let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") as! MainPageViewController
                vc.isLogin = true
                self.presentViewController(vc, animated: true, completion: nil)
            }else{
                let vc = sb.instantiateViewControllerWithIdentifier("accountChoiceViewController") 
                self.presentViewController(vc, animated: false, completion: nil)
            }
        }else if recieveType == "versionVerify"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                let userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                versionStatus = userDictionary.objectForKey("status") as? Bool
                LocalDataController.writeLocalData("versionStatus", data: versionStatus)
                if userDictionary.objectForKey("hasNew") as! Bool{
                    let versionDictionary = userDictionary.objectForKey("newVersion") as! NSDictionary
                    if versionDictionary.objectForKey("forced") as! Bool{
                        UIAlertView(title: "版本太旧了", message: "请连接AppStore升级至最新版本", delegate: self, cancelButtonTitle: nil, otherButtonTitles: "去AppStore升级").show()
                    }else{
                        newVersion = versionDictionary.objectForKey("version") as! String
                        userVerify()
                    }
                }else{
                    userVerify()
                }
            }else{
                httpController.getDataError(self)
            }
        }
    }
    
    func userVerify(){
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
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewControllerWithIdentifier("accountChoiceViewController") 
            self.presentViewController(vc, animated: false, completion: nil)
        }
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        if alertView.title == "版本太旧了"{
            UIApplication.sharedApplication().openURL(NSURL(string: AppStoreUrl)!)
            UIAlertView(title: "版本太旧了", message: "请连接AppStore升级至最新版本", delegate: self, cancelButtonTitle: nil, otherButtonTitles: "去AppStore升级").show()
        }else{
            userVerify()
        }
    }
}