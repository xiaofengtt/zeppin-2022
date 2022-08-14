//
//  LuckyFrontUserRateModel.swift
//  lucky
//  平台费率汇率
//  Created by Farmer Zhu on 2020/9/7.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserRateModel : NSObject{
    //手续费
    var poundageRate: Double
    //金币美元汇率
    var goldExcRate: Double
    //积分抵扣率
    var scoreAmountRate: Double
    
    init(data: NSDictionary) {
        self.poundageRate = Double(String.valueOf(any: data.value(forKey: "poundageRate")))!
        self.goldExcRate = Double(String.valueOf(any: data.value(forKey: "goldExcRate")))!
        self.scoreAmountRate = Double(String.valueOf(any: data.value(forKey: "scoreAmountRate")))!
    }
}
