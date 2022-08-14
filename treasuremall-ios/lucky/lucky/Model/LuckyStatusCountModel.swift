//
//  LuckyStatusCountModel.swift
//  lucky
//  分类计数
//  Created by Farmer Zhu on 2020/8/5.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyStatusCountModel : NSObject{
    var status: String
    var count: Int
    
    init(data: NSDictionary) {
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.count = Int.valueOf(any: data.value(forKey: "count"))
    }
}
