//
//  LocalDataController.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class LocalDataController{
    class func writeLocalData(_ name : String , data : AnyObject?){
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        
        let myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            dict.setValue(data, forKey: name)
            dict.write(toFile: path, atomically: true)
        }
    }
    
    class func loadLocalData() {
        // 获取沙盒中文件地址
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let fileManager = FileManager.default
        //判断文件是否存在
        if(!fileManager.fileExists(atPath: path)) {
            // 不存在则复制新文件
            if let bundlePath = Bundle.main.path(forResource: "DataBase", ofType: "plist") {
                do {
                    try fileManager.copyItem(atPath: bundlePath, toPath: path)
                } catch _ {
                }
            }
        }
        
        let myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            let userDictionary : NSDictionary? = dict.object(forKey: "user") as? NSDictionary
            if userDictionary != nil {
                nmgsUser.id = userDictionary!.object(forKey: "id") as! String
                nmgsUser.phone = userDictionary!.object(forKey: "phone") as! String
                if userDictionary!.object(forKey: "token") != nil{
                    nmgsUser.token = userDictionary!.object(forKey: "token") as! String
                }
                nmgsUser.isAutoLogin = userDictionary!.object(forKey: "isAutoLogin") as! Bool
            }
        }
    }
}
