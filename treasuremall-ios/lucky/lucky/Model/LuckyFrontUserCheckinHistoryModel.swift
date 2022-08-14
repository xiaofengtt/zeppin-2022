//
//  LuckyFrontUserCheckinHistoryModel.swift
//  lucky
//  签到历史记录
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserCheckinHistoryModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var activityInfoCheckinPrize: String
    
    var prizeTitle: String
    var prizeType: String
    var prizeCover: String
    var prize: String
    var dayNum: Int
    
    var createtime: Int
    var status: String
    var remark: String
    var operators: String
    var operattime: Int
    var ip: String
    
    var provideInfo: String
    var nickname: String
    var imageUrl: String
    //领奖信息
    var detailInfo: LuckyProvideInfoModel?
    var prizeCoverUrl: String
    
    var frontUserHistory: String
    //金币价值
    var dealDAmount: Double
    var activityInfo: String
    //总价格
    var dealPrice: Double
    //价格
    var price: Double
    var receiveType: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.activityInfoCheckinPrize = String.valueOf(any: data.value(forKey: "activityInfoCheckinPrize"))
        
        self.prizeTitle = String.valueOf(any: data.value(forKey: "prizeTitle"))
        self.prizeType = String.valueOf(any: data.value(forKey: "prizeType"))
        self.prizeCover = String.valueOf(any: data.value(forKey: "prizeCover"))
        self.prize = String.valueOf(any: data.value(forKey: "prize"))
        self.dayNum = Int.valueOf(any: data.value(forKey: "dayNum"))
        
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.operators = String.valueOf(any: data.value(forKey: "operator"))
        self.operattime = Int.valueOf(any: data.value(forKey: "operattime"))
        self.ip = String.valueOf(any: data.value(forKey: "ip"))
        
        self.provideInfo = String.valueOf(any: data.value(forKey: "provideInfo"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        if(data.value(forKey: "detailInfo") != nil && data.value(forKey: "detailInfo").debugDescription != "Optional(<null>)"){
            self.detailInfo = LuckyProvideInfoModel(data: data.value(forKey: "detailInfo") as! NSDictionary)
        }else{
            self.detailInfo = nil
        }
        self.prizeCoverUrl = String.valueOf(any: data.value(forKey: "prizeCoverUrl"))
        
        self.frontUserHistory = String.valueOf(any: data.value(forKey: "frontUserHistory"))
        self.dealDAmount = Double.valueOf(any: data.value(forKey: "dealDAmount"))
        self.activityInfo = String.valueOf(any: data.value(forKey: "activityInfo"))
        self.dealPrice = Double.valueOf(any: data.value(forKey: "dealPrice"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.receiveType = String.valueOf(any: data.value(forKey: "receiveType"))
    }
}
