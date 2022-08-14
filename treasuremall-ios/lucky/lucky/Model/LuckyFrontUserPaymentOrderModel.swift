//
//  LuckyFrontUserPaymentOrderModel.swift
//  lucky
//  用户投注记录
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserPaymentOrderModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var goodsIssue: String
    var goodsId: String
    var gameType: String
    var orderNum: String
    var orderType: String
    var currentIssueUuid: String
    
    //总金币
    var totalDAmount: Double
    //实缴
    var actualDAmount: Double
    //购买量
    var buyCount: Int
    //手续费
    var poundage: Double
    var createtime: Int
    var status: String
    
    var remark: String
    //优惠活动
    var isPromotion: Bool
    var promotionId: String
    var promotionTitle: String
    //幸运号
    var isLucky: Bool
    var luckyNumber: Int
    var paymentGroup: String
    
    //商品信息
    var title: String
    var shortTitle: String
    var issueNum: Int
    var cover: String
    var shares: Int
    var code: String
    var price: Double
    var dPrice: Double
    var gameStatus: String
    var timeLine: Int
    var betShares: Int
    
    //用户信息
    var nickname: String
    var imageURL: String
    
    //是否收获
    var isRecevice: Bool
    //获奖信息id
    var winningInfo: String
    //是否评价
    var isComment: Bool
    //领奖类型
    var receviceType: String
    //交易完成时间
    var finishedtime: Int
    //PK类型信息
    var groupShares: NSDictionary
    
    //动态高度
    var cellHeight: CGFloat
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.goodsIssue = String.valueOf(any: data.value(forKey: "goodsIssue"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.gameType = String.valueOf(any: data.value(forKey: "gameType"))
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        self.orderType = String.valueOf(any: data.value(forKey: "orderType"))
        self.currentIssueUuid = String.valueOf(any: data.value(forKey: "currentIssueUuid"))
        
        self.totalDAmount = Double.valueOf(any: data.value(forKey: "totalDAmount"))
        self.actualDAmount = Double.valueOf(any: data.value(forKey: "actualDAmount"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        self.poundage = Double.valueOf(any: data.value(forKey: "poundage"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        
        self.remark = String.valueOf(any: data.value(forKey: "remark"))
        self.isPromotion = Bool.valueOf(any: data.value(forKey: "isPromotion"))
        self.promotionId = String.valueOf(any: data.value(forKey: "promotionId"))
        self.promotionTitle = String.valueOf(any: data.value(forKey: "promotionTitle"))
        self.isLucky = Bool.valueOf(any: data.value(forKey: "isLucky"))
        self.luckyNumber = Int.valueOf(any: data.value(forKey: "luckyNumber"))
        self.paymentGroup = String.valueOf(any: data.value(forKey: "paymentGroup"))
        
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.shortTitle = String.valueOf(any: data.value(forKey: "shortTitle"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        self.cover = String.valueOf(any: data.value(forKey: "cover"))
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.dPrice = Double.valueOf(any: data.value(forKey: "dPrice"))
        self.gameStatus = String.valueOf(any: data.value(forKey: "gameStatus"))
        self.timeLine = Int.valueOf(any: data.value(forKey: "timeLine"))
        self.betShares = Int.valueOf(any: data.value(forKey: "betShares"))
        
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
        self.isRecevice = Bool.valueOf(any: data.value(forKey: "isRecevice"))
        self.winningInfo = String.valueOf(any: data.value(forKey: "winningInfo"))
        self.isComment = Bool.valueOf(any: data.value(forKey: "isComment"))
        self.receviceType = String.valueOf(any: data.value(forKey: "receviceType"))
        self.finishedtime = Int.valueOf(any: data.value(forKey: "finishedtime"))
        
        if(data.value(forKey: "groupShares") != nil && data.value(forKey: "groupShares").debugDescription != "Optional(<null>)"){
            self.groupShares = data.value(forKey: "groupShares") as! NSDictionary
        }else{
            self.groupShares = NSDictionary()
        }
        
        if(self.timeLine != 0 && Int.valueOf(any: data.value(forKey: "finishedtime")) == 0){
            self.timeLine = self.timeLine + 2000
            self.finishedtime = Date().timestamp + self.timeLine
        }
        
        self.cellHeight = 0
    }
}
