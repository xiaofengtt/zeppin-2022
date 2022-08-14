//
//  LuckyLuckyNumModel.swift
//  lucky
//  幸运号
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyLuckyNumModel : NSObject{
    var luckynum: Int
    var isLuck: Bool
    
    init(data: NSDictionary) {
        self.luckynum = Int.valueOf(any: data.value(forKey: "luckynum"))
        self.isLuck = Bool.valueOf(any: data.value(forKey: "isLuck"))
    }
}
