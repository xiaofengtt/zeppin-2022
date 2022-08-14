//
//  BankcardModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class BankcardModel : NSObject{
    var uuid: String
    var name: String
    var shortName: String
    var bankcard: String
    var phone: String
    var singleLimit: Int
    var dailyLimit: Int
    var icon: String
    var iconColor: String
    var color: String
    
    override init() {
        self.uuid = ""
        self.name = ""
        self.shortName = ""
        self.bankcard = ""
        self.phone = ""
        self.singleLimit = 0
        self.dailyLimit = 0
        self.icon = ""
        self.iconColor = ""
        self.color = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortName = String.valueOf(any: data.value(forKey: "shortName"))
        self.bankcard = String.valueOf(any: data.value(forKey: "bankcard"))
        self.phone = String.valueOf(any: data.value(forKey: "phone"))
        self.singleLimit = Int.valueOf(any: data.value(forKey: "singleLimit"))
        self.dailyLimit = Int.valueOf(any: data.value(forKey: "dailyLimit"))
        self.icon = String.valueOf(any: data.value(forKey: "icon"))
        self.iconColor = String.valueOf(any: data.value(forKey: "iconColor"))
        self.color = String.valueOf(any: data.value(forKey: "color"))
    }
}
