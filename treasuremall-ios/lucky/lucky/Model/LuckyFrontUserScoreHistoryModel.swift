//
//  LuckyFrontUserScoreHistoryModel.swift
//  lucky
//  积分历史
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserScoreHistoryModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var orderNum: String
    var orderId: String
    var orderType: String
    
    var type: String
    var createtime: Int
    //交易前积分
    var scoreBalanceBefore: Double
    //交易积分
    var sAmount: Double
    //交易后积分
    var scoreBalanceAfter: Double
    
    var reason: String
    var remark: String
    var goodsIssue: String
    var goodsTitle: String
    //总金币变动
    var totalDAmount: Double
    //抵扣后金币变动
    var actualDAmount: Double
    //交易量
    var buyCount: Int
    
    //法币
    var amount: Double
    var poundage: Double
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        self.orderId = String.valueOf(any: data.value(forKey: "orderId"))
        self.orderType = String.valueOf(any: data.value(forKey: "orderType"))
        
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.scoreBalanceBefore = Double.valueOf(any: data.value(forKey: "scoreBalanceBefore"))
        self.sAmount = Double.valueOf(any: data.value(forKey: "sAmount"))
        self.scoreBalanceAfter = Double.valueOf(any: data.value(forKey: "scoreBalanceAfter"))
        
        self.reason = String.valueOf(any: data.value(forKey: "reason"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.goodsIssue = String.valueOf(any: data.value(forKey: "goodsIssue"))
        self.goodsTitle = String.valueOf(any: data.value(forKey: "goodsTitle"))
        self.totalDAmount = Double.valueOf(any: data.value(forKey: "totalDAmount"))
        self.actualDAmount = Double.valueOf(any: data.value(forKey: "actualDAmount"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        
        self.amount = Double.valueOf(any: data.value(forKey: "amount"))
        self.poundage = Double.valueOf(any: data.value(forKey: "poundage"))
    }
}
