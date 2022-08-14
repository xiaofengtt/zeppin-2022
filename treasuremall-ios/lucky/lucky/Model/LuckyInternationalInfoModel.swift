//
//  LuckyInternationalInfoModel.swift
//  lucky
//  国际区号
//  Created by Farmer Zhu on 2020/9/11.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyInternationalInfoModel : NSObject{
    var uuid: String
    var parent: String
    var name: String
    var code: String
    var telCode: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.parent = String.valueOf(any: data.value(forKey: "parent"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.telCode = String.valueOf(any: data.value(forKey: "telCode"))
    }
}
