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
        let path = documentsDirectory.stringByAppendingPathComponent("DataBase.plist")
        let fileManager = NSFileManager.defaultManager()
        
        var myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            dict.setValue(data, forKey: name)
            dict.writeToFile(path, atomically: true)
        }
    }

    class func loadLocalData() {
        // 获取沙盒中文件地址
        let paths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory.stringByAppendingPathComponent("DataBase.plist")
        let fileManager = NSFileManager.defaultManager()
        //判断文件是否存在
        if(!fileManager.fileExistsAtPath(path)) {
            // 不存在则复制新文件
            if let bundlePath = NSBundle.mainBundle().pathForResource("DataBase", ofType: "plist") {
                let resultDictionary = NSMutableDictionary(contentsOfFile: bundlePath)
                fileManager.copyItemAtPath(bundlePath, toPath: path, error: nil)
            }
        }
        
        var myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            var userDictionary : NSDictionary? = dict.objectForKey("user") as? NSDictionary
            if userDictionary != nil {
                user.id = (userDictionary!.objectForKey("id") as! String).toInt()!
                user.name = userDictionary!.objectForKey("name") as! String
                user.gender = (userDictionary!.objectForKey("gender") as! String).toInt()!
                user.iconUrl = userDictionary!.objectForKey("iconUrl") as! String
                user.uid = userDictionary!.objectForKey("uid") as! String
                user.authType = (userDictionary!.objectForKey("authType") as! String).toInt()!
                user.screenName = userDictionary!.objectForKey("screenName") as! String
            }
            versionStatus = dict.objectForKey("versionStatus") as? Bool
            if versionStatus == nil{
                versionStatus = false
            }
        }
    }
}