//
//  LoadLocalData.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class LocalDataController{
    class func writeLocalData(name : String , data : AnyObject?){
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory.stringByAppendingString("/DataBase.plist")
        
        let myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            dict.setValue(data, forKey: name)
            dict.writeToFile(path, atomically: true)
        }
    }

    class func loadLocalData() {
        // 获取沙盒中文件地址
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory.stringByAppendingString("/DataBase.plist")
        let fileManager = NSFileManager.defaultManager()
        //判断文件是否存在
        if(!fileManager.fileExistsAtPath(path)) {
            // 不存在则复制新文件
            if let bundlePath = NSBundle.mainBundle().pathForResource("DataBase", ofType: "plist") {
                do {
                    try fileManager.copyItemAtPath(bundlePath, toPath: path)
                } catch _ {
                }
            }
        }
        
        let myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            let userDictionary : NSDictionary? = dict.objectForKey("user") as? NSDictionary
            if userDictionary != nil {
                user.id = Int((userDictionary!.objectForKey("id") as! String))!
                user.name = userDictionary!.objectForKey("name") as! String
                user.gender = Int((userDictionary!.objectForKey("gender") as! String))!
                user.iconUrl = userDictionary!.objectForKey("iconUrl") as! String
                user.uid = userDictionary!.objectForKey("uid") as! String
                user.authType = Int((userDictionary!.objectForKey("authType") as! String))!
                user.screenName = userDictionary!.objectForKey("screenName") as! String
                if userDictionary!.objectForKey("profession") != nil{
                    user.profession = userDictionary!.objectForKey("profession") as! String
                }
                if userDictionary!.objectForKey("phone") != nil{
                    user.phone = userDictionary!.objectForKey("phone") as! String
                }
                if userDictionary!.objectForKey("password") != nil{
                    user.password = userDictionary!.objectForKey("password") as! String
                }
                if userDictionary!.objectForKey("age") != nil{
                    user.age = Int((userDictionary!.objectForKey("age") as! String))!
                }
            }
        }
    }
}