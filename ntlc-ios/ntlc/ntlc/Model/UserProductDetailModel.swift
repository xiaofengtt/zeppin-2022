//
//  UserProductDetailModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class UserProductDetailModel : NSObject{
    var uuid: String
    var investor: String
    var product: String
    var productName: String
    var productScode: String
    var bankName: String
    var incomeDate: String
    var valueDate: String
    var maturityDate: String
    var targetAnnualizedReturnRate: String
    var term: String
    var flagBuy: Bool
    var price: Double
    var priceCN: String
    var totalReturn: Double
    var totalReturnCN: String
    var paytime: Int
    var paytimeCN: String
    var stage: String
    var stageCN: String
    var agreementName: String
    var agreementUrl: String
    var realReturnRateCN: String
    var accountHistoryList: Array<AccountHistoryModel>
    
    override init() {
        self.uuid = ""
        self.investor = ""
        self.product = ""
        self.productName = ""
        self.productScode = ""
        self.bankName = ""
        self.incomeDate = ""
        self.valueDate = ""
        self.maturityDate = ""
        self.targetAnnualizedReturnRate = ""
        self.term = ""
        self.flagBuy = false
        self.price = 0
        self.priceCN = ""
        self.totalReturn = 0
        self.totalReturnCN = ""
        self.paytime = 0
        self.paytimeCN = ""
        self.stage = ""
        self.stageCN = ""
        self.agreementName = ""
        self.agreementUrl = ""
        self.realReturnRateCN = ""
        self.accountHistoryList = []
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.investor = String.valueOf(any: data.value(forKey: "investor"))
        self.product = String.valueOf(any: data.value(forKey: "product"))
        self.productName = String.valueOf(any: data.value(forKey: "productName"))
        self.productScode = String.valueOf(any: data.value(forKey: "productScode"))
        self.bankName = String.valueOf(any: data.value(forKey: "bankName"))
        self.incomeDate = String.valueOf(any: data.value(forKey: "incomeDate"))
        self.valueDate = String.valueOf(any: data.value(forKey: "valueDate"))
        self.maturityDate = String.valueOf(any: data.value(forKey: "maturityDate"))
        self.targetAnnualizedReturnRate = String.valueOf(any: data.value(forKey: "targetAnnualizedReturnRate"))
        self.term = String.valueOf(any: data.value(forKey: "term"))
        self.flagBuy = Bool.valueOf(any: data.value(forKey: "flagBuy"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.priceCN = String.valueOf(any: data.value(forKey: "priceCN"))
        self.totalReturn = Double.valueOf(any: data.value(forKey: "totalReturn"))
        self.totalReturnCN = String.valueOf(any: data.value(forKey: "totalReturnCN"))
        self.paytime = Int.valueOf(any: data.value(forKey: "paytime"))
        self.paytimeCN = String.valueOf(any: data.value(forKey: "paytimeCN"))
        self.stage = String.valueOf(any: data.value(forKey: "stage"))
        self.stageCN = String.valueOf(any: data.value(forKey: "stageCN"))
        self.agreementName = String.valueOf(any: data.value(forKey: "agreementName"))
        self.agreementUrl = String.valueOf(any: data.value(forKey: "agreementUrl"))
        self.realReturnRateCN = String.valueOf(any: data.value(forKey: "realReturnRateCN"))
        
        var ahList: Array<AccountHistoryModel> = []
        if(data.value(forKey: "accountHistory") != nil && data.value(forKey: "accountHistory").debugDescription != "Optional(<null>)"){
            let accountHistorys = data.value(forKey: "accountHistory") as! NSArray
            for i in 0 ..< accountHistorys.count{
                let accountHistoryData = accountHistorys[i] as! NSDictionary
                let accountHistory = AccountHistoryModel(data: accountHistoryData)
                ahList.append(accountHistory)
            }
        }
        self.accountHistoryList = ahList
    }
}
