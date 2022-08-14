//
//  BankModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class BankModel : NSObject{
    var uuid: String
    var name: String
    var shortName: String
    var status: String
    var singleLimit: Int
    var dailyLimit: Int
    var iconColor: String
    var iconColorUrl: String
    var selected: Bool
    
    override init() {
        self.uuid = ""
        self.name = ""
        self.shortName = ""
        self.status = ""
        self.singleLimit = 0
        self.dailyLimit = 0
        self.iconColor = ""
        self.iconColorUrl = ""
        self.selected = false
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortName = String.valueOf(any: data.value(forKey: "shortName"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.singleLimit = Int.valueOf(any: data.value(forKey: "singleLimit"))
        self.dailyLimit = Int.valueOf(any: data.value(forKey: "dailyLimit"))
        self.iconColor = String.valueOf(any: data.value(forKey: "iconColor"))
        self.iconColorUrl = String.valueOf(any: data.value(forKey: "iconColorUrl"))
        self.selected = false
    }
    
    func copyModel() -> BankModel{
        let copy = BankModel()
        copy.uuid = self.uuid
        copy.name = self.name
        copy.shortName = self.name
        copy.status = self.status
        copy.singleLimit = self.singleLimit
        copy.dailyLimit = self.dailyLimit
        copy.iconColor = self.iconColor
        copy.iconColorUrl = self.iconColorUrl
        copy.selected = self.selected
        return copy
    }
}
