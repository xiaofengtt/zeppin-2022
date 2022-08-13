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
        httpController.delegate = self
        super.viewDidLoad()
        
    }
    
    override func viewDidAppear(animated: Bool) {
        if !(versionStatus != nil && versionStatus!){
            var versionParams = NSDictionary()
            httpController.getNSDataByParams("versionVerify", paramsDictionary: versionParams)
        }else{
            userVerify()
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            var status = dataDictionary.objectForKey("Status") as! String
            let sb = UIStoryboard(name: "Main", bundle: nil)
            if status == "success" {
                UserImageDownload("userIcon", user.iconUrl)
                var userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
                
                var userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                
                let vc = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! UIViewController
                self.presentViewController(vc, animated: true, completion: nil)
            }else if !versionStatus!{
                httpController.getDataError(self)
            }else{
                let vc = sb.instantiateViewControllerWithIdentifier("loginViewController") as! UIViewController
                self.presentViewController(vc, animated: false, completion: nil)
            }
        }else if recieveType == "versionVerify"{
            var status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                var userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                versionStatus = userDictionary.objectForKey("status") as? Bool
                LocalDataController.writeLocalData("versionStatus", data: versionStatus)
            }
            userVerify()
        }
    }
    
    func userVerify(){
        if user.id != 0{
            var postData = user.getUserData()
            var UserParams = NSDictionary(dictionary: ["postData" : postData])
            httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }else if !versionStatus!{
            user.authType = 1
            user.name = "用户"
            user.screenName = "用户"
            user.iconUrl = UrlBase + "img/defaultPhoto.gif"
            user.gender = 1
            user.uid = UIDevice.currentDevice().identifierForVendor.UUIDString + programVersion
            var postData = user.getUserData()
            var UserParams = NSDictionary(dictionary: ["postData" : postData])
            self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewControllerWithIdentifier("loginViewController") as! UIViewController
            self.presentViewController(vc, animated: false, completion: nil)
        }
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        userVerify()
    }
}