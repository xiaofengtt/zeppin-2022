//
//  CouponModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/19.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class CouponModel : NSObject{
    var uuid: String
    var coupon: String
    var couponName: String
    var couponType: String
    var couponTypeCN: String
    var couponPrice: Double
    var couponPriceCN: String
    var minInvestAccount: Double
    var minInvestAccountCN: String
    var status: String
    var expiryDate: Int
    var expiryDateCN: String
    
    override init() {
        self.uuid = ""
        self.coupon = ""
        self.couponName = ""
        self.couponType = ""
        self.couponTypeCN = ""
        self.couponPrice = 0
        self.couponPriceCN = ""
        self.minInvestAccount = 0
        self.minInvestAccountCN = ""
        self.status = ""
        self.expiryDate = 0
        self.expiryDateCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.coupon = String.valueOf(any: data.value(forKey: "coupon"))
        self.couponName = String.valueOf(any: data.value(forKey: "couponName"))
        self.couponType = String.valueOf(any: data.value(forKey: "couponType"))
        self.couponTypeCN = String.valueOf(any: data.value(forKey: "couponTypeCN"))
        self.couponPrice = Double.valueOf(any: data.value(forKey: "couponPrice"))
        self.couponPriceCN = String.valueOf(any: data.value(forKey: "couponPriceCN"))
        self.minInvestAccount = Double.valueOf(any: data.value(forKey: "minInvestAccount"))
        self.minInvestAccountCN = String.valueOf(any: data.value(forKey: "minInvestAccountCN"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.expiryDate = Int.valueOf(any: data.value(forKey: "expiryDate"))
        self.expiryDateCN = String.valueOf(any: data.value(forKey: "expiryDateCN"))
    }
}
