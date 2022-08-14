//
//  LuckyFrontUserRechargeAmountSetModel.swift
//  lucky
//  可用充值金额列表
//  Created by Farmer Zhu on 2020/8/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserRechargeAmountSetModel : NSObject{
    var uuid: String
    //金额
    var amount: Double
    //金币数
    var dAmount: Double
    //赠送金币
    var giveDAmount: Double
    var status: String
    var sort: Int
    //是否优惠
    var isPreferential: Bool
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.amount = Double.valueOf(any: data.value(forKey: "amount"))
        self.dAmount = Double.valueOf(any: data.value(forKey: "dAmount"))
        self.giveDAmount = Double.valueOf(any: data.value(forKey: "giveDAmount"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        self.isPreferential = Bool.valueOf(any: data.value(forKey: "isPreferential"))
    }
}
