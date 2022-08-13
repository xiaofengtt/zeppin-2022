//
//  SsoUserModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class SsoUserModel : NSObject{
    var id: String
    var name: String
    var phone : String
    var age : String
    var iconUrl: String
    var token : String
    var isAutoLogin : Bool
    
    override init() {
        self.id = ""
        self.name = ""
        self.phone = ""
        self.age = ""
        self.iconUrl = ""
        self.token = ""
        self.isAutoLogin = false
    }
    
    func getDictionary() -> NSDictionary{
        let dictionary = NSMutableDictionary()
        dictionary.setObject(id, forKey: "id" as NSCopying)
        dictionary.setObject(name, forKey: "name" as NSCopying)
        dictionary.setObject(phone, forKey: "phone" as NSCopying)
        dictionary.setObject(age, forKey: "age" as NSCopying)
        dictionary.setObject(iconUrl, forKey: "iconUrl" as NSCopying)
        dictionary.setObject(token, forKey: "token" as NSCopying)
        dictionary.setObject(isAutoLogin, forKey: "isAutoLogin" as NSCopying)
        return dictionary
    }
}
