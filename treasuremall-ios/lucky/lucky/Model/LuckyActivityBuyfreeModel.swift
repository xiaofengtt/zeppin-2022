//
//  LuckyActivityBuyfreeModel.swift
//  lucky
//  零元购活动
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityBuyfreeModel : NSObject{
    var uuid: String
    var activityInfo: String
    var activityInfoBuyfreeGoods: String
    var status: String
    var createtime: Int
    var goodsId: String
    var goodsCover: String
    var goodsPrice: Double
    var goodsTitle: String
    var goodsShortTitle: String
    //期数
    var issueNum: Int
    //总份数
    var shares: Int
    //已参加份数
    var betShares: Int
    //剩余份数
    var remainShares: Int
    var sort: Int
    //开奖时间
    var lotterytime: Int
    //结束时间
    var finishedtime: Int
    //中奖号
    var luckyNumber: Int
    
    //封面图
    var goodsCoverUrl: String
    //活动状态
    var avtivityInfoBuyfreeGoodsStatus: String
    //当前开放期期数
    var currentIssueNum: Int
    var currentIssueUuid: String
    
    //开奖倒计时
    var timeLine: Int
    //是否第一次参加
    var isFirstBuy: Bool
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.activityInfo = String.valueOf(any: data.value(forKey: "activityInfo"))
        self.activityInfoBuyfreeGoods = String.valueOf(any: data.value(forKey: "activityInfoBuyfreeGoods"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.goodsCover = String.valueOf(any: data.value(forKey: "goodsCover"))
        self.goodsPrice = Double.valueOf(any: data.value(forKey: "goodsPrice"))
        self.goodsTitle = String.valueOf(any: data.value(forKey: "goodsTitle"))
        self.goodsShortTitle = String.valueOf(any: data.value(forKey: "goodsShortTitle"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.betShares = Int.valueOf(any: data.value(forKey: "betShares"))
        self.remainShares = Int.valueOf(any: data.value(forKey: "remainShares"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        self.lotterytime = Int.valueOf(any: data.value(forKey: "lotterytime"))
        self.finishedtime = Int.valueOf(any: data.value(forKey: "finishedtime"))
        self.luckyNumber = Int.valueOf(any: data.value(forKey: "luckyNumber"))
        
        self.goodsCoverUrl = String.valueOf(any: data.value(forKey: "goodsCoverUrl"))
        self.avtivityInfoBuyfreeGoodsStatus = String.valueOf(any: data.value(forKey: "avtivityInfoBuyfreeGoodsStatus"))
        self.currentIssueNum = Int.valueOf(any: data.value(forKey: "currentIssueNum"))
        self.currentIssueUuid = String.valueOf(any: data.value(forKey: "currentIssueUuid"))
        self.timeLine = Int.valueOf(any: data.value(forKey: "timeLine"))
        self.isFirstBuy = Bool.valueOf(any: data.value(forKey: "isFirstBuy"))
    }
}
