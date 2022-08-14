//
//  LuckyLuckygameGoodsIssueModel.swift
//  lucky
//  商品信息
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyLuckygameGoodsIssueModel : NSObject{
    var uuid: String
    var luckygameGoods: String
    var luckygameGoodsStatus: String
    var goodsId: String
    var gameType: String
    var title: String
    var shortTitle: String
    //金币价格
    var dPrice: Double
    //价值
    var price: Double
    //每份价格
    var betPerShare: Double
    //标签
    var tabs: String
    var descript: String
    
    //份数
    var shares: Int
    //期数
    var issueNum: Int
    var sort: Int
    var promotionFlag: Bool
    //已投数
    var betShares: Int
    //未投数
    var remainShares: Int
    
    var status: String
    var createtime: Int
    var lotterytime: Int
    var finishedtime: Int
    //幸运号
    var luckyNumber: Int
    //获胜组
    var luckyGroup: String
    
    //中奖用户信息
    var showIdStr: String
    var nickname: String
    //投注金币
    var dAmount: Double
    //实缴金币
    var actualDAmount: Double
    var imageUrl: String
    //购买份数
    var buyCount: Int
    var frontUser: String
    
    //商品信息
    var coverImg: String
    var code: String
    var goodsType: String
    var imgList: Array<String>
    var imgDetail: Array<String>
    var imgShow: Array<String>
    
    //当前进行期信息
    var currentIssueNum: Int
    var currentIssueUuid: String
    var currentIssueStatus: String
    
    //倒计时
    var timeLine: Int
    //首次购买
    var isFirstBuy: Bool
    //PK组信息
    var groupShares: NSDictionary
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.luckygameGoods = String.valueOf(any: data.value(forKey: "luckygameGoods"))
        self.luckygameGoodsStatus = String.valueOf(any: data.value(forKey: "luckygameGoodsStatus"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.gameType = String.valueOf(any: data.value(forKey: "gameType"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.shortTitle = String.valueOf(any: data.value(forKey: "shortTitle"))
        self.dPrice = Double.valueOf(any: data.value(forKey: "dPrice"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
        self.betPerShare = Double.valueOf(any: data.value(forKey: "betPerShare"))
        self.tabs = String.valueOf(any: data.value(forKey: "tabs"))
        self.descript = String.valueOf(any: data.value(forKey: "description"))
        
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        self.promotionFlag = Bool.valueOf(any: data.value(forKey: "promotionFlag"))
        self.betShares = Int.valueOf(any: data.value(forKey: "betShares"))
        self.remainShares = Int.valueOf(any: data.value(forKey: "remainShares"))
        
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.lotterytime = Int.valueOf(any: data.value(forKey: "lotterytime"))
        self.finishedtime = Int.valueOf(any: data.value(forKey: "finishedtime"))
        self.luckyNumber = Int.valueOf(any: data.value(forKey: "luckyNumber"))
        self.luckyGroup = String.valueOf(any: data.value(forKey: "luckyGroup"))
        
        self.showIdStr = String.valueOf(any: data.value(forKey: "showIdStr"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.dAmount = Double.valueOf(any: data.value(forKey: "dAmount"))
        self.actualDAmount = Double.valueOf(any: data.value(forKey: "actualDAmount"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        
        self.coverImg = String.valueOf(any: data.value(forKey: "coverImg"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.goodsType = String.valueOf(any: data.value(forKey: "goodsType"))
        
        if(data.value(forKey: "imgList") != nil && data.value(forKey: "imgList").debugDescription != "Optional(<null>)"){
            self.imgList = data.value(forKey: "imgList") as! Array<String>
        }else{
            self.imgList = []
        }
        if(data.value(forKey: "imgDetail") != nil && data.value(forKey: "imgDetail").debugDescription != "Optional(<null>)"){
            self.imgDetail = data.value(forKey: "imgDetail") as! Array<String>
        }else{
            self.imgDetail = []
        }
        if(data.value(forKey: "imgShow") != nil && data.value(forKey: "imgShow").debugDescription != "Optional(<null>)"){
            self.imgShow = data.value(forKey: "imgShow") as! Array<String>
        }else{
            self.imgShow = []
        }
        
        self.currentIssueNum = Int.valueOf(any: data.value(forKey: "currentIssueNum"))
        self.currentIssueUuid = String.valueOf(any: data.value(forKey: "currentIssueUuid"))
        self.currentIssueStatus = String.valueOf(any: data.value(forKey: "currentIssueStatus"))
        self.timeLine = Int.valueOf(any: data.value(forKey: "timeLine"))
        self.isFirstBuy = Bool.valueOf(any: data.value(forKey: "isFirstBuy"))
        
        if(data.value(forKey: "groupShares") != nil && data.value(forKey: "groupShares").debugDescription != "Optional(<null>)"){
            self.groupShares = data.value(forKey: "groupShares") as! NSDictionary
        }else{
            self.groupShares = NSDictionary()
        }
        
        if(self.timeLine != 0 && Int.valueOf(any: data.value(forKey: "finishedtime")) == 0){
            self.timeLine = self.timeLine + 2000
            self.finishedtime = Date().timestamp + self.timeLine
        }
    }
}
