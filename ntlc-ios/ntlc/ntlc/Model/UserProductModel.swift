//
//  UserProductModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class UserProductModel : NSObject{
    var uuid: String
    var investor: String
    var product: String
    var productName: String
    var productScode: String
    var bankName: String
    var iconColorUrl: String
    var incomeDate: String
    var valueDate: String
    var maturityDate: String
    var targetAnnualizedReturnRate: String
    var price: Double
    var priceCN: String
    var totalReturn: Double
    var totalReturnCN: String
    var realReturnRateCN: String
    var flagInterestsCoupon: Bool
    var flagCashCoupon: Bool
    var paytime: Int
    var paytimeCN: String
    var stage: String
    var stageCN: String
    
    override init() {
        self.uuid = ""
        self.investor = ""
        self.product = ""
        self.productName = ""
        self.productScode = ""
        self.bankName = ""
        self.iconColorUrl = ""
        self.incomeDate = ""
        self.valueDate = ""
        self.maturityDate = ""
        self.targetAnnualizedReturnRate = ""
        self.price = 0
        self.priceCN = ""
        self.totalReturn = 0
        self.totalReturnCN = ""
        self.realReturnRateCN = ""
        self.flagInterestsCoupon = false
        self.flagCashCoupon = false
        self.paytime = 0
        self.paytimeCN = ""
        self.stage = ""
        self.stageCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.investor = String.valueOf(any: data.value(forKey: "investor"))
        self.product = String.valueOf(any: data.value(forKey: "product"))
        self.productName = String.valueOf(any: data.value(forKey: "productName"))
        self.productScode = String.valueOf(any: data.value(forKey: "productScode"))
        self.bankName = String.valueOf(any: data.value(forKey: "bankName"))
        self.iconColorUrl = String.valueOf(any: data.value(forKey: "iconColorUrl"))
        self.incomeDate = String.valueOf(any: data.value(forKey: "incomeDate"))
        self.valueDate = String.valueOf(any: data.value(forKey: "valueDate"))
        self.maturityDate = String.valueOf(any: data.value(forKey: "maturityDate"))
        self.targetAnnualizedReturnRate = String.valueOf(any: data.value(forKey: "targetAnnualizedReturnRate"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.priceCN = String.valueOf(any: data.value(forKey: "priceCN"))
        self.totalReturn = Double.valueOf(any: data.value(forKey: "totalReturne"))
        self.totalReturnCN = String.valueOf(any: data.value(forKey: "totalReturn"))
        self.realReturnRateCN = String.valueOf(any: data.value(forKey: "realReturnRateCN"))
        self.flagInterestsCoupon = Bool.valueOf(any: data.value(forKey: "flagInterestsCoupon"))
        self.flagCashCoupon = Bool.valueOf(any: data.value(forKey: "flagCashCoupon"))
        self.paytime = Int.valueOf(any: data.value(forKey: "paytime"))
        self.paytimeCN = String.valueOf(any: data.value(forKey: "paytimeCN"))
        self.stage = String.valueOf(any: data.value(forKey: "stage"))
        self.stageCN = String.valueOf(any: data.value(forKey: "stageCN"))
    }
}
