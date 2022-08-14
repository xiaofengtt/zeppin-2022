//
//  ProductDetailModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class ProductDetailModel : NSObject{
    var uuid: String
    var name: String
    var shortname: String
    var scode: String
    var series: String
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
    var custodianCN: String
    var currencyType: String
    var currencyTypeCN: String
    var targetAnnualizedReturnRate: String
    var minAnnualizedReturnRate: String
    var totalAmount: Double
    var collectAmount: Double
    var collectAmountCN: String
    var collectStarttime: Int
    var collectStarttimeCN: String
    var collectStarttimeWeb: String
    var collectEndtime: Int
    var collectEndtimeCN: String
    var collectEndtimeWeb: String
    var term: Int
    var recordDate: Int
    var recordDateCN: String
    var valueDate: Int
    var valueDateCN: String
    var valueDateWeb: String
    var maturityDate: Int
    var maturityDateCN: String
    var maturityDateWeb: String
    var flagPurchase: Bool
    var flagRedemption: Bool
    var flagFlexible: Bool
    var flagCloseend: Bool
    var riskLevel: String
    var riskLevelCN: String
    var guaranteeStatus: String
    var guaranteeStatusCN: String
    var area: String
    var minInvestAmount: Double
    var minInvestAmountNum: String
    var minInvestAmountCN: String
    var minInvestAmountLess: String
    var minInvestAmountAdd: Double
    var minInvestAmountAddNum: String
    var minInvestAmountAddCN: String
    var minInvestAmountAddLess: String
    var maxInvestAmount: Double
    var maxInvestAmountNum: String
    var maxInvestAmountCN: String
    var maxInvestAmountLess: String
    var subscribeFee: String
    var purchaseFee: String
    var redemingFee: String
    var managementFee: String
    var custodyFee: String
    var networkFee: String
    var investScope: String
    var revenueFeature: String
    var remark: String
    var flagBuy: Bool
    var realReturnRate: String
    var realReturnRateCN: String
    var totalRaise: Double
    var totalRaiseCN: String
    var paymentType: String
    var paymentTypeCN: String
    var documentURL: String
    
    override init() {
        self.uuid = ""
        self.name = ""
        self.shortname = ""
        self.scode = ""
        self.series = ""
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
        self.custodianCN = ""
        self.currencyType = ""
        self.currencyTypeCN = ""
        self.targetAnnualizedReturnRate = ""
        self.minAnnualizedReturnRate = ""
        self.totalAmount = 0
        self.collectAmount = 0
        self.collectAmountCN = ""
        self.collectStarttime = 0
        self.collectStarttimeCN = ""
        self.collectStarttimeWeb = ""
        self.collectEndtime = 0
        self.collectEndtimeCN = ""
        self.collectEndtimeWeb = ""
        self.term = 0
        self.recordDate = 0
        self.recordDateCN = ""
        self.valueDate = 0
        self.valueDateCN = ""
        self.valueDateWeb = ""
        self.maturityDate = 0
        self.maturityDateCN = ""
        self.maturityDateWeb = ""
        self.flagPurchase = false
        self.flagRedemption = false
        self.flagFlexible = false
        self.flagCloseend = false
        self.riskLevel = ""
        self.riskLevelCN = ""
        self.guaranteeStatus = ""
        self.guaranteeStatusCN = ""
        self.area = ""
        self.minInvestAmount = 0
        self.minInvestAmountNum = ""
        self.minInvestAmountCN = ""
        self.minInvestAmountLess = ""
        self.minInvestAmountAdd = 0
        self.minInvestAmountAddNum = ""
        self.minInvestAmountAddCN = ""
        self.minInvestAmountAddLess = ""
        self.maxInvestAmount = 0
        self.maxInvestAmountNum = ""
        self.maxInvestAmountCN = ""
        self.maxInvestAmountLess = ""
        self.subscribeFee = ""
        self.purchaseFee = ""
        self.redemingFee = ""
        self.managementFee = ""
        self.custodyFee = ""
        self.networkFee = ""
        self.investScope = ""
        self.revenueFeature = ""
        self.remark = ""
        self.flagBuy = false
        self.realReturnRate = ""
        self.realReturnRateCN = ""
        self.totalRaise = 0
        self.totalRaiseCN = ""
        self.paymentType = ""
        self.paymentTypeCN = ""
        self.documentURL = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortname = String.valueOf(any: data.value(forKey: "shortname"))
        self.scode = String.valueOf(any: data.value(forKey: "scode"))
        self.series = String.valueOf(any: data.value(forKey: "series"))
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
        self.custodianCN = String.valueOf(any: data.value(forKey: "custodianCN"))
        self.currencyType = String.valueOf(any: data.value(forKey: "currencyType"))
        self.currencyTypeCN = String.valueOf(any: data.value(forKey: "currencyTypeCN"))
        self.targetAnnualizedReturnRate = String.valueOf(any: data.value(forKey: "targetAnnualizedReturnRate"))
        self.minAnnualizedReturnRate = String.valueOf(any: data.value(forKey: "minAnnualizedReturnRate"))
        self.totalAmount = Double.valueOf(any: data.value(forKey: "totalAmount"))
        self.collectAmount = Double.valueOf(any: data.value(forKey: "collectAmount"))
        self.collectAmountCN = String.valueOf(any: data.value(forKey: "collectAmountCN"))
        self.collectStarttime = Int.valueOf(any: data.value(forKey: "collectStarttime"))
        self.collectStarttimeCN = String.valueOf(any: data.value(forKey: "collectStarttimeCN"))
        self.collectStarttimeWeb = String.valueOf(any: data.value(forKey: "collectStarttimeWeb"))
        self.collectEndtime = Int.valueOf(any: data.value(forKey: "collectEndtime"))
        self.collectEndtimeCN = String.valueOf(any: data.value(forKey: "collectEndtimeCN"))
        self.collectEndtimeWeb = String.valueOf(any: data.value(forKey: "collectEndtimeWeb"))
        self.term = Int.valueOf(any: data.value(forKey: "term"))
        self.recordDate = Int.valueOf(any: data.value(forKey: "recordDate"))
        self.recordDateCN = String.valueOf(any: data.value(forKey: "recordDateCN"))
        self.valueDate = Int.valueOf(any: data.value(forKey: "valueDate"))
        self.valueDateCN = String.valueOf(any: data.value(forKey: "valueDateCN"))
        self.valueDateWeb = String.valueOf(any: data.value(forKey: "valueDateWeb"))
        self.maturityDate = Int.valueOf(any: data.value(forKey: "maturityDate"))
        self.maturityDateCN = String.valueOf(any: data.value(forKey: "maturityDateCN"))
        self.maturityDateWeb = String.valueOf(any: data.value(forKey: "maturityDateWeb"))
        self.flagPurchase = Bool.valueOf(any: data.value(forKey: "flagPurchase"))
        self.flagRedemption = Bool.valueOf(any: data.value(forKey: "flagRedemption"))
        self.flagFlexible = Bool.valueOf(any: data.value(forKey: "flagFlexible"))
        self.flagCloseend = Bool.valueOf(any: data.value(forKey: "flagCloseend"))
        self.riskLevel = String.valueOf(any: data.value(forKey: "riskLevel"))
        self.riskLevelCN = String.valueOf(any: data.value(forKey: "riskLevelCN"))
        self.guaranteeStatus = String.valueOf(any: data.value(forKey: "guaranteeStatus"))
        self.guaranteeStatusCN = String.valueOf(any: data.value(forKey: "guaranteeStatusCN"))
        self.area = String.valueOf(any: data.value(forKey: "areaCN"))
        self.minInvestAmount = Double.valueOf(any: data.value(forKey: "minInvestAmount"))
        self.minInvestAmountNum = String.valueOf(any: data.value(forKey: "minInvestAmountNum"))
        self.minInvestAmountCN = String.valueOf(any: data.value(forKey: "minInvestAmountCN"))
        self.minInvestAmountLess = String.valueOf(any: data.value(forKey: "minInvestAmountLess"))
        self.minInvestAmountAdd = Double.valueOf(any: data.value(forKey: "minInvestAmountAdd"))
        self.minInvestAmountAddNum = String.valueOf(any: data.value(forKey: "minInvestAmountAddNum"))
        self.minInvestAmountAddCN = String.valueOf(any: data.value(forKey: "minInvestAmountAddCN"))
        self.minInvestAmountAddLess = String.valueOf(any: data.value(forKey: "minInvestAmountAddLess"))
        self.maxInvestAmount = Double.valueOf(any: data.value(forKey: "maxInvestAmount"))
        self.maxInvestAmountNum = String.valueOf(any: data.value(forKey: "maxInvestAmountNum"))
        self.maxInvestAmountCN = String.valueOf(any: data.value(forKey: "maxInvestAmountCN"))
        self.maxInvestAmountLess = String.valueOf(any: data.value(forKey: "maxInvestAmountLess"))
        self.subscribeFee = String.valueOf(any: data.value(forKey: "subscribeFee"))
        self.purchaseFee = String.valueOf(any: data.value(forKey: "purchaseFee"))
        self.redemingFee = String.valueOf(any: data.value(forKey: "redemingFee"))
        self.managementFee = String.valueOf(any: data.value(forKey: "managementFee"))
        self.custodyFee = String.valueOf(any: data.value(forKey: "custodyFee"))
        self.networkFee = String.valueOf(any: data.value(forKey: "networkFee"))
        self.investScope = String.valueOf(any: data.value(forKey: "investScopeWeb"))
        self.revenueFeature = String.valueOf(any: data.value(forKey: "revenueFeatureWeb"))
        self.remark = String.valueOf(any: data.value(forKey: "remarkWeb"))
        self.flagBuy = Bool.valueOf(any: data.value(forKey: "flagBuy"))
        self.realReturnRate = String.valueOf(any: data.value(forKey: "realReturnRate"))
        self.realReturnRateCN = String.valueOf(any: data.value(forKey: "realReturnRateCN"))
        self.totalRaiseCN = String.valueOf(any: data.value(forKey: "totalRaise"))
        self.totalRaise = Double(self.totalRaiseCN.replacingOccurrences(of: ",", with: ""))!
        self.paymentType = String.valueOf(any: data.value(forKey: "paymentType"))
        self.paymentTypeCN = String.valueOf(any: data.value(forKey: "paymentTypeCN"))
        self.documentURL = String.valueOf(any: data.value(forKey: "documentURL"))
    }
}
