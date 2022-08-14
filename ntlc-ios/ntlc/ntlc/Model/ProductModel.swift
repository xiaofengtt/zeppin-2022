//
//  Product.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class ProductModel : NSObject{
    var uuid: String
    var name: String
    var shortname: String
    var scode: String
    var iconColorUrl: String
    var url: String
    var type: String
    var typeCN: String
    var status: String
    var stage: String
    var stageCN: String
    var target: String
    var targetCN: String
    var custodian: String
    var custodianName: String
    var currencyType: String
    var currencyTypeCN: String
    var targetAnnualizedReturnRate: String
    var totalAmount: Double
    var collectAmount: Double
    var collectAmountCN: String
    var collectStarttime: String
    var collectEndtime: String
    var collectEndtimeCN: String
    var term: Int
    var recordDate: String
    var valueDate: String
    var maturityDate: String
    var flagPurchase: Bool
    var flagRedemption: Bool
    var flagFlexible: Bool
    var riskLevel: String
    var riskLevelCN: String
    var guaranteeStatus: String
    var guaranteeStatusCN: String
    var area: String
    var minInvestAmount: Double
    var minInvestAmountCN: String
    var minInvestAmountLess: String
    var minInvestAmountAdd: Double
    var maxInvestAmount: Double
    var flagBuy: Bool
    var realReturnRate: String
    var realReturnRateCN: String
    
    override init() {
        self.uuid = ""
        self.name = ""
        self.shortname = ""
        self.scode = ""
        self.iconColorUrl = ""
        self.url = ""
        self.type = ""
        self.typeCN = ""
        self.status = ""
        self.stage = ""
        self.stageCN = ""
        self.target = ""
        self.targetCN = ""
        self.custodian = ""
        self.custodianName = ""
        self.currencyType = ""
        self.currencyTypeCN = ""
        self.targetAnnualizedReturnRate = ""
        self.totalAmount = 0
        self.collectAmount = 0
        self.collectAmountCN = ""
        self.collectStarttime = ""
        self.collectEndtime = ""
        self.collectEndtimeCN = ""
        self.term = 0
        self.recordDate = ""
        self.valueDate = ""
        self.maturityDate = ""
        self.flagPurchase = false
        self.flagRedemption = false
        self.flagFlexible = false
        self.riskLevel = ""
        self.riskLevelCN = ""
        self.guaranteeStatus = ""
        self.guaranteeStatusCN = ""
        self.area = ""
        self.minInvestAmount = 0
        self.minInvestAmountCN = ""
        self.minInvestAmountLess = ""
        self.minInvestAmountAdd = 0
        self.maxInvestAmount = 0
        self.flagBuy = false
        self.realReturnRate = ""
        self.realReturnRateCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortname = String.valueOf(any: data.value(forKey: "shortname"))
        self.scode = String.valueOf(any: data.value(forKey: "scode"))
        self.iconColorUrl = String.valueOf(any: data.value(forKey: "iconColorUrl"))
        self.url = String.valueOf(any: data.value(forKey: "url"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.typeCN = String.valueOf(any: data.value(forKey: "typeCN"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.stage = String.valueOf(any: data.value(forKey: "stage"))
        self.stageCN = String.valueOf(any: data.value(forKey: "stageCN"))
        self.target = String.valueOf(any: data.value(forKey: "target"))
        self.targetCN = String.valueOf(any: data.value(forKey: "targetCN"))
        self.custodian = String.valueOf(any: data.value(forKey: "custodian"))
        self.custodianName = String.valueOf(any: data.value(forKey: "custodianName"))
        self.currencyType = String.valueOf(any: data.value(forKey: "currencyType"))
        self.currencyTypeCN = String.valueOf(any: data.value(forKey: "currencyTypeCN"))
        self.targetAnnualizedReturnRate = String.valueOf(any: data.value(forKey: "targetAnnualizedReturnRate"))
        self.totalAmount = Double.valueOf(any: data.value(forKey: "totalAmount"))
        self.collectAmount = Double.valueOf(any: data.value(forKey: "collectAmount"))
        self.collectAmountCN = String.valueOf(any: data.value(forKey: "collectAmountCN"))
        self.collectStarttime = String.valueOf(any: data.value(forKey: "collectStarttime"))
        self.collectEndtime = String.valueOf(any: data.value(forKey: "collectEndtime"))
        self.collectEndtimeCN = String.valueOf(any: data.value(forKey: "collectEndtimeCN"))
        self.term = Int.valueOf(any: data.value(forKey: "term"))
        self.recordDate = String.valueOf(any: data.value(forKey: "recordDate"))
        self.valueDate = String.valueOf(any: data.value(forKey: "valueDate"))
        self.maturityDate = String.valueOf(any: data.value(forKey: "maturityDate"))
        self.flagPurchase = Bool.valueOf(any: data.value(forKey: "flagPurchase"))
        self.flagRedemption = Bool.valueOf(any: data.value(forKey: "flagRedemption"))
        self.flagFlexible = Bool.valueOf(any: data.value(forKey: "flagFlexible"))
        self.riskLevel = String.valueOf(any: data.value(forKey: "riskLevel"))
        self.riskLevelCN = String.valueOf(any: data.value(forKey: "riskLevelCN"))
        self.guaranteeStatus = String.valueOf(any: data.value(forKey: "guaranteeStatus"))
        self.guaranteeStatusCN = String.valueOf(any: data.value(forKey: "guaranteeStatusCN"))
        self.area = String.valueOf(any: data.value(forKey: "area"))
        self.minInvestAmount = Double.valueOf(any: data.value(forKey: "minInvestAmount"))
        self.minInvestAmountCN = String.valueOf(any: data.value(forKey: "minInvestAmountCN"))
        self.minInvestAmountLess = String.valueOf(any: data.value(forKey: "minInvestAmountLess"))
        self.minInvestAmountAdd = Double.valueOf(any: data.value(forKey: "minInvestAmountAdd"))
        self.maxInvestAmount = Double.valueOf(any: data.value(forKey: "maxInvestAmount"))
        self.flagBuy = Bool.valueOf(any: data.value(forKey: "flagBuy"))
        self.realReturnRate = String.valueOf(any: data.value(forKey: "realReturnRate"))
        self.realReturnRateCN = String.valueOf(any: data.value(forKey: "realReturnRateCN"))
    }
}
