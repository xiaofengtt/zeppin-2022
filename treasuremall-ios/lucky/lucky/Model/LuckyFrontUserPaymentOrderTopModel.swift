//
//  LuckyFrontUserPaymentOrderTopModel.swift
//  lucky
//  单商品用户榜
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserPaymentOrderTopModel : NSObject{
    //沙发榜
    var shafa: NSDictionary?
    //土豪榜
    var tuhao: NSDictionary?
    //包尾榜
    var baowei: NSDictionary?
    
    init(data: NSDictionary) {
        if(data.value(forKey: "shafa") != nil && data.value(forKey: "shafa").debugDescription != "Optional(<null>)"){
            self.shafa = data.value(forKey: "shafa") as? NSDictionary
        }else{
            self.shafa = nil
        }
        if(data.value(forKey: "tuhao") != nil && data.value(forKey: "tuhao").debugDescription != "Optional(<null>)"){
            self.tuhao = data.value(forKey: "tuhao") as? NSDictionary
        }else{
            self.tuhao = nil
        }
        if(data.value(forKey: "baowei") != nil && data.value(forKey: "baowei").debugDescription != "Optional(<null>)"){
            self.baowei = data.value(forKey: "baowei") as? NSDictionary
        }else{
            self.baowei = nil
        }
    }
}
