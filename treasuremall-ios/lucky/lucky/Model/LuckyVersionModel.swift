//
//  LuckyVersionModel.swift
//  lucky
//  版本
//  Created by Farmer Zhu on 2020/10/19.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyVersionModel : NSObject{
    var type: String
    var bundleid: String
    var displayname: String
    var channel: String
    var name: String
    var code: Int
    var flagupdate: Bool
    var flagcompel: Bool
    //是否主包
    var flag: Bool
    //是否可提现
    var flagFund: Bool
    
    init(data: NSDictionary) {
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.bundleid = String.valueOf(any: data.value(forKey: "bundleid"))
        self.displayname = String.valueOf(any: data.value(forKey: "displayname"))
        self.channel = String.valueOf(any: data.value(forKey: "channel"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.code = Int.valueOf(any: data.value(forKey: "code"))
        self.flagupdate = Bool.valueOf(any: data.value(forKey: "flagupdate"))
        self.flagcompel = Bool.valueOf(any: data.value(forKey: "flagcompel"))
        self.flag = Bool.valueOf(any: data.value(forKey: "flag"))
        self.flagFund = Bool.valueOf(any: data.value(forKey: "flagfund"))
    }
}
