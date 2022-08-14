//
//  LuckyFrontUserBuyfreeOrderModel.swift
//  lucky
//  零元购订单
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserBuyfreeOrderModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var activityInfoBuyfree: String
    var goodsId: String
    var sharenum: Int
    var isLucky: Bool
    var createtime: Int
    var status: String
    var remark: String

    var operattime: Int
    var winningTime: Int
    var ip: String
    
    var provideInfo: String
    var title: String
    var shortTitle: String
    var cover: String
    var shares: Int
    var code: String
    var price: Double
    var issueNum: Int
    
    var nickname: String
    var imageUrl: String
    var frontUserHistory: String
    //交易金币数
    var dealDAmount: Double
    var activityInfo: String
    //总价格
    var dealPrice: Double
    
    //发货信息
    var detailInfo: LuckyProvideInfoModel?
    var receiveType: String
    
    var cellHeight: CGFloat
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.activityInfoBuyfree = String.valueOf(any: data.value(forKey: "activityInfoBuyfree"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.sharenum = Int.valueOf(any: data.value(forKey: "sharenum"))
        self.isLucky = Bool.valueOf(any: data.value(forKey: "isLucky"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        
        self.operattime = Int.valueOf(any: data.value(forKey: "operattime"))
        self.winningTime = Int.valueOf(any: data.value(forKey: "winningTime"))
        self.ip = String.valueOf(any: data.value(forKey: "ip"))
        
        self.provideInfo = String.valueOf(any: data.value(forKey: "provideInfo"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.shortTitle = String.valueOf(any: data.value(forKey: "shortTitle"))
        self.cover = String.valueOf(any: data.value(forKey: "cover"))
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        self.frontUserHistory = String.valueOf(any: data.value(forKey: "frontUserHistory"))
        self.dealDAmount = Double.valueOf(any: data.value(forKey: "dealDAmount"))
        self.activityInfo = String.valueOf(any: data.value(forKey: "activityInfo"))
        self.dealPrice = Double.valueOf(any: data.value(forKey: "dealPrice"))
        
        if(data.value(forKey: "detailInfo") != nil && data.value(forKey: "detailInfo").debugDescription != "Optional(<null>)"){
            self.detailInfo = LuckyProvideInfoModel(data: data.value(forKey: "detailInfo") as! NSDictionary)
        }else{
            self.detailInfo = nil
        }
        self.receiveType = String.valueOf(any: data.value(forKey: "receiveType"))
        
        self.cellHeight = 0
    }
}
