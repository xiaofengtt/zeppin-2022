//
//  LuckyUserModel.swift
//  lucky
//  用户信息
//  Created by Farmer Zhu on 2020/7/30.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserModel : NSObject{
    var uuid: String
    var showId: Int
    //真实姓名
    var realname: String
    var idcard: String
    //昵称
    var nickname: String
    //所在地区
    var areaCode: String
    var mobile: String
    var email: String
    var sex: String
    var password: String
    var realnameFlag: Bool
    
    var type: String
    var level: String
    var status: String
    
    var registerChannel: String
    var openid: String
    var wechaticon: String
    
    var ip: String
    var area: String
    
    var lastonline: Int
    var lastaccessip: String
    //邀请人
    var agent: String
    var createtime: Int
    
    var image: String
    var imageURL: String
    
    override init() {
        self.uuid = ""
        self.showId = 0
        self.realname = ""
        self.idcard = ""
        self.nickname = ""
        self.areaCode = ""
        self.mobile = ""
        self.email = ""
        self.sex = ""
        self.password = ""
        self.realnameFlag = false
        
        self.type = ""
        self.level = ""
        self.status = ""
        
        self.registerChannel = ""
        self.openid = ""
        self.wechaticon = ""
        
        self.ip = ""
        self.area = ""
        
        self.lastonline = 0
        self.lastaccessip = ""
        self.agent = ""
        self.createtime = 0
        
        self.image = ""
        self.imageURL = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.showId = Int.valueOf(any: data.value(forKey: "showId"))
        self.realname = String.valueOf(any: data.value(forKey: "realname"))
        self.idcard = String.valueOf(any: data.value(forKey: "idcard"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.mobile = String.valueOf(any: data.value(forKey: "mobile"))
        self.areaCode = String.valueOf(any: data.value(forKey: "areaCode"))
        self.email = String.valueOf(any: data.value(forKey: "email"))
        self.sex = String.valueOf(any: data.value(forKey: "sex"))
        self.password = String.valueOf(any: data.value(forKey: "password"))
        self.realnameFlag = Bool.valueOf(any: data.value(forKey: "realnameFlag"))
        
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.level = String.valueOf(any: data.value(forKey: "level"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        
        self.registerChannel = String.valueOf(any: data.value(forKey: "registerChannel"))
        self.openid = String.valueOf(any: data.value(forKey: "openid"))
        self.wechaticon = String.valueOf(any: data.value(forKey: "wechaticon"))
        
        self.ip = String.valueOf(any: data.value(forKey: "ip"))
        self.area = String.valueOf(any: data.value(forKey: "area"))
        
        self.lastonline = Int.valueOf(any: data.value(forKey: "lastonline"))
        self.lastaccessip = String.valueOf(any: data.value(forKey: "lastaccessip"))
        self.agent = String.valueOf(any: data.value(forKey: "agent"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        
        self.image = String.valueOf(any: data.value(forKey: "image"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
    }
    
    func getDictionary() -> NSDictionary{
        let dictionary = NSMutableDictionary()
        dictionary.setObject(uuid, forKey: "uuid" as NSCopying)
        dictionary.setObject(showId, forKey: "showId" as NSCopying)
        dictionary.setObject(realname, forKey: "realname" as NSCopying)
        dictionary.setObject(idcard, forKey: "idcard" as NSCopying)
        dictionary.setObject(nickname, forKey: "nickname" as NSCopying)
        dictionary.setObject(mobile, forKey: "mobile" as NSCopying)
        dictionary.setObject(areaCode, forKey: "areaCode" as NSCopying)
        dictionary.setObject(email, forKey: "email" as NSCopying)
        dictionary.setObject(sex, forKey: "sex" as NSCopying)
        dictionary.setObject(password, forKey: "password" as NSCopying)
        dictionary.setObject(realnameFlag, forKey: "realnameFlag" as NSCopying)
        
        dictionary.setObject(type, forKey: "type" as NSCopying)
        dictionary.setObject(level, forKey: "level" as NSCopying)
        dictionary.setObject(status, forKey: "status" as NSCopying)
        
        dictionary.setObject(registerChannel, forKey: "registerChannel" as NSCopying)
        dictionary.setObject(openid, forKey: "openid" as NSCopying)
        dictionary.setObject(wechaticon, forKey: "wechaticon" as NSCopying)
        
        dictionary.setObject(ip, forKey: "ip" as NSCopying)
        dictionary.setObject(area, forKey: "area" as NSCopying)
        
        dictionary.setObject(lastonline, forKey: "lastonline" as NSCopying)
        dictionary.setObject(lastaccessip, forKey: "lastaccessip" as NSCopying)
        dictionary.setObject(agent, forKey: "agent" as NSCopying)
        dictionary.setObject(createtime, forKey: "createtime" as NSCopying)
        
        dictionary.setObject(image, forKey: "image" as NSCopying)
        dictionary.setObject(imageURL, forKey: "imageURL" as NSCopying)
        return dictionary
    }
}
