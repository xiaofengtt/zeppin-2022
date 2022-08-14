//
//  HistoryModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class HistoryModel : NSObject{
    var uuid: String
    var investor: String
    var product: String
    var productType: String
    var order: String
    var orderNum: String
    var orderType: String
    var orderTypeCN: String
    var price: String
    var priceflag: Bool
    var type: String
    var typeCN: String
    var status: String
    var statusCN: String
    var remark: String
    var time: String
    var accountBalance: Double
    var accountBalanceCN: String
    var createtime: Int
    var createtimeCN: String
    var createtimeLessCN: String
    
    override init() {
        self.uuid = ""
        self.investor = ""
        self.product = ""
        self.productType = ""
        self.order = ""
        self.orderNum = ""
        self.orderType = ""
        self.orderTypeCN = ""
        self.price = ""
        self.priceflag = false
        self.type = ""
        self.typeCN = ""
        self.status = ""
        self.statusCN = ""
        self.remark = ""
        self.time = ""
        self.accountBalance = 0
        self.accountBalanceCN = ""
        self.createtime = 0
        self.createtimeCN = ""
        self.createtimeLessCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.investor = String.valueOf(any: data.value(forKey: "investor"))
        self.product = String.valueOf(any: data.value(forKey: "product"))
        self.productType = String.valueOf(any: data.value(forKey: "productType"))
        self.order = String.valueOf(any: data.value(forKey: "order"))
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        self.orderType = String.valueOf(any: data.value(forKey: "orderType"))
        self.orderTypeCN = String.valueOf(any: data.value(forKey: "orderTypeCN"))
        self.price = String.valueOf(any: data.value(forKey: "price"))
        self.priceflag = Bool.valueOf(any: data.value(forKey: "priceflag"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.typeCN = String.valueOf(any: data.value(forKey: "typeCN"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.statusCN = String.valueOf(any: data.value(forKey: "statusCN"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.time = String.valueOf(any: data.value(forKey: "time"))
        self.accountBalance = Double.valueOf(any: data.value(forKey: "accountBalance"))
        self.accountBalanceCN = String.valueOf(any: data.value(forKey: "accountBalanceCN"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.createtimeCN = String.valueOf(any: data.value(forKey: "createtimeCN"))
        self.createtimeLessCN = String.valueOf(any: data.value(forKey: "createtimeLessCN"))
    }
}
