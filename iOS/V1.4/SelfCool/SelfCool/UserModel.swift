//
//  UserModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserModel : NSObject{
    var id: Int
    var password: String
    var name: String
    var gender : Int
    var phone : String
    var age : Int
    var profession : String
    var iconUrl: String
    var uid : String
    var authType : Int
    var screenName : String
    var isFirstLogin : Bool
    var token : String
        
    override init() {
        self.id = 0
        self.password = ""
        self.name = ""
        self.gender = -1
        self.phone = ""
        self.age = -1
        self.profession = ""
        self.iconUrl = ""
        self.uid = ""
        self.authType = 0
        self.screenName = ""
        self.isFirstLogin = false
        self.token = ""
    }
    
    func getDictionary() -> NSDictionary{
        let dictionary = NSMutableDictionary()
        dictionary.setObject(String(id), forKey: "id")
        dictionary.setObject(password, forKey: "password")
        dictionary.setObject(name, forKey: "name")
        dictionary.setObject(String(gender), forKey: "gender")
        dictionary.setObject(String(phone), forKey: "phone")
        dictionary.setObject(String(age), forKey: "age")
        dictionary.setObject(String(profession), forKey: "profession")
        dictionary.setObject(iconUrl, forKey: "iconUrl")
        dictionary.setObject(uid, forKey: "uid")
        dictionary.setObject(String(authType), forKey: "authType")
        dictionary.setObject(screenName, forKey: "screenName")
        dictionary.setObject(token, forKey: "token")
        return dictionary
    }
    
    func getSsoUserLoginString() -> String{
        var json = "{\"type\":1,\"data\":{"
        json += "\"phone\":\(self.phone)"
        json += ",\"password\":\"\(self.password)\""
        json += "}}"
        return json
    }
    
    func getUserAuthLoginString() -> String{
        var json = "{\"type\":2,\"data\":{"
        json += "\"auth_type\":\(self.authType)"
        json += ",\"gender\":\(self.gender)"
        json += ",\"icon\":\"\(self.iconUrl)\""
        json += ",\"screen_name\":\"\(self.screenName)\""
        json += ",\"uid\":\"\(self.uid)\""
        json += ",\"username\":\"\(self.screenName)\""
        json += "}}"
        return json
    }
}
