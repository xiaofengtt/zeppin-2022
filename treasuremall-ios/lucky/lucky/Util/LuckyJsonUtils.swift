//
//  LuckyJsonUtils.swift
//  lucky
//  Json转换
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyJsonUtils {
    
    //json转字典
    static func jsonToDictionary(jsonString:String) ->NSDictionary{
        let jsonData:Data = jsonString.data(using: .utf8)!
        let dict = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        if dict != nil {
            return dict as! NSDictionary
        }
        return NSDictionary()
    }
    
    //json转数组
    static func jsonToArray(jsonString:String) ->NSArray{
        let jsonData:Data = jsonString.data(using: .utf8)!
        let array = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        if array != nil {
            return array as! NSArray
        }
        return []
    }
    
    //字典转json
    static func dictionaryToJson(dictionary: NSDictionary) -> String {
        if (!JSONSerialization.isValidJSONObject(dictionary)) {
            return ""
        }
        let data : NSData! = try? JSONSerialization.data(withJSONObject: dictionary, options: []) as NSData
        let JSONString = NSString(data:data as Data,encoding: String.Encoding.utf8.rawValue)
        return JSONString! as String
    }
    
    //数组转json
    static func arrayToJson(array:NSArray) -> String {
        if (!JSONSerialization.isValidJSONObject(array)) {
            return ""
        }
        let data : NSData! = try? JSONSerialization.data(withJSONObject: array, options: []) as NSData
        let JSONString = NSString(data:data as Data,encoding: String.Encoding.utf8.rawValue)
        return JSONString! as String
    }
}
