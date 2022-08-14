//
//  User.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class UserModel : NSObject{
    var uuid: String
    var nickname: String
    var publicName: String
    var realname: String
    var realnameFull: String
    var pwdFlag: Bool
    var idcard: String
    var phone: String
    var couponCount: Int
    var messageFlag: Bool
    var realnameAuthFlag: Bool
    var idcardImgStatus: String
    var idcardImgStatusCN: String
    var bankcardCount: String
    var accountBalance: String
    var bindingAliFlag: Bool
    var aliNickname: String
    var aliPhoto: String
    var aliUserid: String
    
    override init() {
        self.uuid = ""
        self.nickname = ""
        self.publicName = ""
        self.realname = ""
        self.realnameFull = ""
        self.pwdFlag = false
        self.idcard = ""
        self.phone = ""
        self.couponCount = 0
        self.messageFlag = false
        self.realnameAuthFlag = false
        self.idcardImgStatus = ""
        self.idcardImgStatusCN = ""
        self.bankcardCount = "0"
        self.accountBalance = ""
        self.bindingAliFlag = false
        self.aliNickname = ""
        self.aliPhoto = ""
        self.aliUserid = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.publicName = String.valueOf(any: data.value(forKey: "publicName"))
        self.realname = String.valueOf(any: data.value(forKey: "realname"))
        self.realnameFull = String.valueOf(any: data.value(forKey: "realnameFull"))
        self.pwdFlag = Bool.valueOf(any: data.value(forKey: "pwdFlag"))
        self.idcard = String.valueOf(any: data.value(forKey: "idcard"))
        self.phone = String.valueOf(any: data.value(forKey: "phone"))
        self.couponCount = Int.valueOf(any: data.value(forKey: "couponCount"))
        self.messageFlag = Bool.valueOf(any: data.value(forKey: "messageFlag"))
        self.realnameAuthFlag = Bool.valueOf(any: data.value(forKey: "realnameAuthFlag"))
        self.idcardImgStatus = String.valueOf(any: data.value(forKey: "idcardImgStatus"))
        self.idcardImgStatusCN = String.valueOf(any: data.value(forKey: "idcardImgStatusCN"))
        self.bankcardCount = String.valueOf(any: data.value(forKey: "bankcardCount"))
        self.accountBalance = String.valueOf(any: data.value(forKey: "accountBalance"))
        self.bindingAliFlag = Bool.valueOf(any: data.value(forKey: "bindingAliFlag"))
        self.aliNickname = String.valueOf(any: data.value(forKey: "aliNickname"))
        self.aliPhoto = String.valueOf(any: data.value(forKey: "aliPhoto"))
        self.aliUserid = String.valueOf(any: data.value(forKey: "aliUserid"))
    }
    
    func getDictionary() -> NSDictionary{
        let dictionary = NSMutableDictionary()
        dictionary.setObject(uuid, forKey: "uuid" as NSCopying)
        dictionary.setObject(nickname, forKey: "nickname" as NSCopying)
        dictionary.setObject(publicName, forKey: "publicName" as NSCopying)
        dictionary.setObject(realname, forKey: "realname" as NSCopying)
        dictionary.setObject(realnameFull, forKey: "realnameFull" as NSCopying)
        dictionary.setObject(pwdFlag, forKey: "pwdFlag" as NSCopying)
        dictionary.setObject(idcard, forKey: "idcard" as NSCopying)
        dictionary.setObject(phone, forKey: "phone" as NSCopying)
        dictionary.setObject(couponCount, forKey: "couponCount" as NSCopying)
        dictionary.setObject(messageFlag, forKey: "messageFlag" as NSCopying)
        dictionary.setObject(realnameAuthFlag, forKey: "realnameAuthFlag" as NSCopying)
        dictionary.setObject(idcardImgStatus, forKey: "idcardImgStatus" as NSCopying)
        dictionary.setObject(idcardImgStatusCN, forKey: "idcardImgStatusCN" as NSCopying)
        dictionary.setObject(bankcardCount, forKey: "bankcardCount" as NSCopying)
        dictionary.setObject(accountBalance, forKey: "accountBalance" as NSCopying)
        dictionary.setObject(bindingAliFlag, forKey: "bindingAliFlag" as NSCopying)
        dictionary.setObject(aliNickname, forKey: "aliNickname" as NSCopying)
        dictionary.setObject(aliPhoto, forKey: "aliPhoto" as NSCopying)
        dictionary.setObject(aliUserid, forKey: "aliUserid" as NSCopying)
        return dictionary
    }
}
