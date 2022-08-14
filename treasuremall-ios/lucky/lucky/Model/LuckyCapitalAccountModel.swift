//
//  LuckyCapitalAccountModel.swift
//  lucky
//  充值通道信息
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCapitalAccountModel : NSObject{
    var uuid: String
    var capitalPlatform: String
    var name: String
    var accountNum: String
    //费率
    var poundageRate: Double
    //最高额度
    var max: Double
    //最低额度
    var min: Double
    var data: NSDictionary?
    var remark: String
    //类型 区别业务
    var type: String
    //交易类型 暂与类型同
    var transType: String
    var status: String
    var sort: Int
    var logo: String
    var logoUrl: String
    
    //渠道说明
    var explanation: String
    var explanImg: String
    var explanImgUrl: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.capitalPlatform = String.valueOf(any: data.value(forKey: "capitalPlatform"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.accountNum = String.valueOf(any: data.value(forKey: "accountNum"))
        self.poundageRate = Double.valueOf(any: data.value(forKey: "poundageRate"))
        self.max = Double.valueOf(any: data.value(forKey: "max"))
        self.min = Double.valueOf(any: data.value(forKey: "min"))
        if(data.value(forKey: "data") != nil && data.value(forKey: "data").debugDescription != "Optional(<null>)"){
            self.data = data.value(forKey: "data") as? NSDictionary
        }else{
            self.data = nil
        }
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.transType = String.valueOf(any: data.value(forKey: "transType"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        self.logo = String.valueOf(any: data.value(forKey: "logo"))
        self.logoUrl = String.valueOf(any: data.value(forKey: "logoUrl"))
        
        self.explanation = String.valueOf(any: data.value(forKey: "explanation"))
        self.explanImg = String.valueOf(any: data.value(forKey: "explanImg"))
        self.explanImgUrl = String.valueOf(any: data.value(forKey: "explanImgUrl"))
    }
}
