//
//  LuckyFrontUserScorelotteryHistoryModel.swift
//  lucky
//  积分抽奖历史
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserScorelotteryHistoryModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var activityInfoScorelotteryPrize: String
    var scoreAmount: Double
    
    //奖品
    var prizeTitle: String
    var prizeType: String
    var prizeCover: String
    var prizeCoverUrl: String
    var prize: String
    
    var createtime: Int
    var status: String
    var remark: String
    var operators: String
    var oprattime: Int
    var ip: String
    
    //中奖人信息
    var nickname: String
    var imageUrl: String
    //兑奖信息
    var provideInfo: String
    var detailInfo: LuckyProvideInfoModel?
    
    var frontUserHistory: String
    //金币价格
    var dealDAmount: Double
    var activityInfo: String
    //总价格
    var dealPrice: Double
    //价值
    var price: Double
    //领奖类型
    var receiveType: String
    
    //动态高度
    var cellHeight: CGFloat
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.activityInfoScorelotteryPrize = String.valueOf(any: data.value(forKey: "activityInfoScorelotteryPrize"))
        self.scoreAmount = Double.valueOf(any: data.value(forKey: "scoreAmount"))
        
        self.prizeTitle = String.valueOf(any: data.value(forKey: "prizeTitle"))
        self.prizeType = String.valueOf(any: data.value(forKey: "prizeType"))
        self.prizeCover = String.valueOf(any: data.value(forKey: "prizeCover"))
        self.prize = String.valueOf(any: data.value(forKey: "prize"))
        
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.operators = String.valueOf(any: data.value(forKey: "operator"))
        self.oprattime = Int.valueOf(any: data.value(forKey: "oprattime"))
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
        
        self.cellHeight = 0
    }
}
