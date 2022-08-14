//
//  LuckyGoodsTypeModel.swift
//  lucky
//  商品分类
//  Created by Farmer Zhu on 2020/9/2.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGoodsTypeModel : NSObject{
    var code: String
    var name: String
    var level: Int
    var parent: String
    var icon: String
    var status: String
    var sort: Int
    
    init(data: NSDictionary) {
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.level = Int.valueOf(any: data.value(forKey: "level"))
        self.parent = String.valueOf(any: data.value(forKey: "parent"))
        self.icon = String.valueOf(any: data.value(forKey: "icon"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
    }
    
    override init() {
        self.code = ""
        self.name = NSLocalizedString("All", comment: "")
        self.level = 1
        self.parent = ""
        self.icon = ""
        self.status = ""
        self.sort = 0
    }
}
