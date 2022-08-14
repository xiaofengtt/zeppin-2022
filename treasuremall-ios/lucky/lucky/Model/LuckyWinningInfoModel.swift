//
//  LuckyWinningInfoModel.swift
//  lucky
//  获胜信息
//  Created by Farmer Zhu on 2020/8/5.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyWinningInfoModel : NSObject{
    var uuid: String
    var goodsIssue: String
    var goodsId: String
    var frontUser: String
    var gameType: String
    
    //幸运号
    var luckynum: Int
    //中奖组
    var luckyGroup: String
    //支付金币数
    var paymentDAmount: Double
    //交易量
    var buyCount: Int
    //总价格
    var dealPrice: Double
    //获奖时间
    var winningTime: Int
    
    //商品信息
    var title: String
    var shortTitle: String
    var cover: String
    var shares: Int
    var code: String
    var price: Double
    var source: String
    var sourceUrl: String
    var issueNum: Int
    
    //中奖用户信息
    var nickname: String
    var showId: Int
    var imageUrl: String
    
    //订单
    var orderNum: String
    var orderId: String
    var status: String
    var operattime: Int
    var frontUserHistory: String
    
    var type: String
    var ip: String
    //发货信息
    var provideInfo: String
    var detailInfo: LuckyProvideInfoModel?
    
    var createtime: Int
    var starttime: Int
    var finishedtime: Int
    //是否已领奖
    var isRecevice: Bool
    //是否已评论
    var isComment: Bool
    
    //当前期信息
    var currentIssueNum: Int
    var currentIssueUuid: String
    
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.goodsIssue = String.valueOf(any: data.value(forKey: "goodsIssue"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.gameType = String.valueOf(any: data.value(forKey: "gameType"))
        
        self.luckynum = Int.valueOf(any: data.value(forKey: "luckynum"))
        self.luckyGroup = String.valueOf(any: data.value(forKey: "luckyGroup"))
        self.paymentDAmount = Double.valueOf(any: data.value(forKey: "paymentDAmount"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        self.dealPrice = Double.valueOf(any: data.value(forKey: "dealPrice"))
        self.winningTime = Int.valueOf(any: data.value(forKey: "winningTime"))
        
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.shortTitle = String.valueOf(any: data.value(forKey: "shortTitle"))
        self.cover = String.valueOf(any: data.value(forKey: "cover"))
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.source = String.valueOf(any: data.value(forKey: "source"))
        self.sourceUrl = String.valueOf(any: data.value(forKey: "sourceUrl"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.showId = Int.valueOf(any: data.value(forKey: "showId"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        self.orderId = String.valueOf(any: data.value(forKey: "orderId"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.operattime = Int.valueOf(any: data.value(forKey: "operattime"))
        
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.ip = String.valueOf(any: data.value(forKey: "ip"))
        self.provideInfo = String.valueOf(any: data.value(forKey: "provideInfo"))
        if(data.value(forKey: "detailInfo") != nil && data.value(forKey: "detailInfo").debugDescription != "Optional(<null>)"){
            self.detailInfo = LuckyProvideInfoModel(data: data.value(forKey: "detailInfo") as! NSDictionary)
        }else{
            self.detailInfo = nil
        }
        
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.starttime = Int.valueOf(any: data.value(forKey: "starttime"))
        self.finishedtime = Int.valueOf(any: data.value(forKey: "finishedtime"))
        self.isRecevice = Bool.valueOf(any: data.value(forKey: "isRecevice"))
        self.isComment = Bool.valueOf(any: data.value(forKey: "isComment"))
        
        self.currentIssueNum = Int.valueOf(any: data.value(forKey: "currentIssueNum"))
        self.currentIssueUuid = String.valueOf(any: data.value(forKey: "currentIssueUuid"))
        self.frontUserHistory = String.valueOf(any: data.value(forKey: "frontUserHistory"))
    }
}
