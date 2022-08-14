//
//  LuckyBankModel.swift
//  lucky
//  银行
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyBankModel : NSObject{
    var uuid: String
    var name: String
    var shortName: String
    var logo: String
    var logoUrl: String
    var status: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortName = String.valueOf(any: data.value(forKey: "shortName"))
        self.logo = String.valueOf(any: data.value(forKey: "logo"))
        self.logoUrl = String.valueOf(any: data.value(forKey: "logoUrl"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
}
