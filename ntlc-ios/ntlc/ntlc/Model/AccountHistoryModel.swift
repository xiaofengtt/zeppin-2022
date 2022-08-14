//
//  AccountHistoryModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/26.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class AccountHistoryModel : NSObject{
    var uuid: String
    var price : Double
    var priceCN: String
    var coupon: String
    var couponName: String
    var couponType: String
    var couponTypeCN: String
    var couponPrice: Double
    var couponPriceCN: String
    var createtime: Int
    var createtimeCN: String
    
    override init() {
        self.uuid = ""
        self.price = 0
        self.priceCN = ""
        self.coupon = ""
        self.couponName = ""
        self.couponType = ""
        self.couponTypeCN = ""
        self.couponPrice = 0
        self.couponPriceCN = ""
        self.createtime = 0
        self.createtimeCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.priceCN = String.valueOf(any: data.value(forKey: "priceCN"))
        self.coupon = String.valueOf(any: data.value(forKey: "coupon"))
        self.couponName = String.valueOf(any: data.value(forKey: "couponName"))
        self.couponType = String.valueOf(any: data.value(forKey: "couponType"))
        self.couponTypeCN = String.valueOf(any: data.value(forKey: "couponTypeCN"))
        self.couponPrice = Double.valueOf(any: data.value(forKey: "couponPrice"))
        self.couponPriceCN = String.valueOf(any: data.value(forKey: "couponPriceCN"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.createtimeCN = String.valueOf(any: data.value(forKey: "createtimeCN"))
    }
    
    static func timeStampToCreatetime(timeStamp: Int) -> String {
        let timeSta: TimeInterval = NSString(string: "\(timeStamp/1000)").doubleValue
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy年MM月dd日"
        
        let date = Date(timeIntervalSince1970: timeSta)
        return dfmatter.string(from: date)
    }
}

