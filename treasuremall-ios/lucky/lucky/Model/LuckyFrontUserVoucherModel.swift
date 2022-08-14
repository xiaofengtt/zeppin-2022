//
//  LuckyFrontUserVoucherModel.swift
//  lucky
//  优惠券
//  Created by Farmer Zhu on 2020/8/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserVoucherModel : NSObject{
    
    var uuid: String
    var frontUser: String
    var voucher: String
    var title: String
    var discription: String
    
    //抵扣金币额
    var dAmount: Double
    //最低可用金额
    var payMin: Double
    
    //商品类型限制
    var goodsType: String
    //指定商品限制
    var goods: String
    
    //可用期限
    var starttime: Int
    var endtime: Int
    
    var createtime: Int
    var creator: String
    
    var status: String
    var operattime: Int
    var orderId: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.voucher = String.valueOf(any: data.value(forKey: "voucher"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.discription = String.valueOf(any: data.value(forKey: "discription"))
        
        self.dAmount = Double.valueOf(any: data.value(forKey: "dAmount"))
        self.payMin = Double.valueOf(any: data.value(forKey: "payMin"))
        
        self.goodsType = String.valueOf(any: data.value(forKey: "goodsType"))
        self.goods = String.valueOf(any: data.value(forKey: "goods"))
        self.starttime = Int.valueOf(any: data.value(forKey: "starttime"))
        self.endtime = Int.valueOf(any: data.value(forKey: "endtime"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.creator = String.valueOf(any: data.value(forKey: "creator"))

        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.operattime = Int.valueOf(any: data.value(forKey: "operattime"))
        self.orderId = String.valueOf(any: data.value(forKey: "orderId"))
    }
}
