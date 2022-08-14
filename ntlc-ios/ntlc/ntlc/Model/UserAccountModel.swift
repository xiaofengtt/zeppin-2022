//
//  UserAccountModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class UserAccountModel : NSObject{
    var uuid: String
    var accountBalance: String
    var totalAmount: String
    var totalInvest: String
    var totalReturn: String
    var totalReturnBuyDay: String
    var totalReturnBuyMonth: String
    var totalReturnBuyYear: String
    
    override init() {
        self.uuid = ""
        self.accountBalance = ""
        self.totalAmount = ""
        self.totalInvest = ""
        self.totalReturn = ""
        self.totalReturnBuyDay = ""
        self.totalReturnBuyMonth = ""
        self.totalReturnBuyYear = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.accountBalance = String.valueOf(any: data.value(forKey: "accountBalance"))
        self.totalAmount = String.valueOf(any: data.value(forKey: "totalAmount"))
        self.totalInvest = String.valueOf(any: data.value(forKey: "totalInvest"))
        self.totalReturn = String.valueOf(any: data.value(forKey: "totalReturn"))
        self.totalReturnBuyDay = String.valueOf(any: data.value(forKey: "totalReturnBuyDay"))
        self.totalReturnBuyMonth = String.valueOf(any: data.value(forKey: "totalReturnBuyMonth"))
        self.totalReturnBuyYear = String.valueOf(any: data.value(forKey: "totalReturnBuyYear"))
    }
}
