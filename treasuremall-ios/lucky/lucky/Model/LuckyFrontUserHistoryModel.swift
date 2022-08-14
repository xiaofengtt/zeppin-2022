//
//  LuckyFrontUserHistoryModel.swift
//  lucky
//  用户历史交易记录
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserHistoryModel : NSObject{
    var uuid: String
    var frontUser: String
    var orderNum: String
    
    var type: String
    var orderId: String
    var orderType: String
    //交易前金币
    var balanceBefore: Double
    //交易金币
    var dAmount: Double
    //交易后金币
    var balanceAfter: Double
    
    var reason: String
    var remark: String
    var createtime: Int
    var goodsIssue: String
    var goodsTitle: String
    var gameType: String
    
    //法币交易额
    var amount: Double
    //手续费
    var poundage: Double
    //目标币种交易额
    var currencyAmount: String
    //目标币种手续费
    var currencyAmountPoundage: String
    
    //优惠券
    var isVoucherUsed: Bool
    var voucher: String
    var voucherTitle: String
    var voucherDAmount: Double
    
    //积分
    var points: Int
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.orderId = String.valueOf(any: data.value(forKey: "orderId"))
        self.orderType = String.valueOf(any: data.value(forKey: "orderType"))
        self.balanceBefore = Double.valueOf(any: data.value(forKey: "balanceBefore"))
        self.dAmount = Double.valueOf(any: data.value(forKey: "dAmount"))
        self.balanceAfter = Double.valueOf(any: data.value(forKey: "balanceAfter"))
        
        self.reason = String.valueOf(any: data.value(forKey: "reason"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.goodsIssue = String.valueOf(any: data.value(forKey: "goodsIssue"))
        self.goodsTitle = String.valueOf(any: data.value(forKey: "goodsTitle"))
        self.gameType = String.valueOf(any: data.value(forKey: "gameType"))
        
        self.amount = Double.valueOf(any: data.value(forKey: "amount"))
        self.poundage = Double.valueOf(any: data.value(forKey: "poundage"))
        self.currencyAmount = String.valueOf(any: data.value(forKey: "currencyAmount"))
        self.currencyAmountPoundage = String.valueOf(any: data.value(forKey: "currencyAmountPoundage"))
        
        self.isVoucherUsed = Bool.valueOf(any: data.value(forKey: "isVoucherUsed"))
        self.voucher = String.valueOf(any: data.value(forKey: "voucher"))
        self.voucherTitle = String.valueOf(any: data.value(forKey: "voucherTitle"))
        self.voucherDAmount = Double.valueOf(any: data.value(forKey: "voucherDAmount"))
        self.points = Int.valueOf(any: data.value(forKey: "points"))
    }
}
