//
//  LuckyProvideInfoModel.swift
//  lucky
//  发货信息
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyProvideInfoModel : NSObject{
    var name: String
    var mobile: String
    var address: String
    var company: String
    var expressNumber: String
    
    init(data: NSDictionary) {
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.mobile = String.valueOf(any: data.value(forKey: "mobile"))
        self.address = String.valueOf(any: data.value(forKey: "address"))
        self.company = String.valueOf(any: data.value(forKey: "company"))
        self.expressNumber = String.valueOf(any: data.value(forKey: "expressNumber"))
    }
}
