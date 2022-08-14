//
//  LuckyGroupWinningInfoModel.swift
//  lucky
//  PK获胜信息
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupWinningInfoModel : NSObject{
    var uuid: String
    var luckygameGoods: String
    var goodsId: String
    var gameType: String
    var title: String
    var shortTitle: String
    
    //总份数
    var shares: Int
    //期数
    var issueNum: Int
    
    var createtime: Int
    //开奖时间
    var lotterytime: Int
    //结束时间
    var finishedtime: Int
    //幸运号
    var luckyNumber: Int
    //胜利队
    var luckyGroup: String

    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.luckygameGoods = String.valueOf(any: data.value(forKey: "luckygameGoods"))
        self.goodsId = String.valueOf(any: data.value(forKey: "goodsId"))
        self.gameType = String.valueOf(any: data.value(forKey: "gameType"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.shortTitle = String.valueOf(any: data.value(forKey: "shortTitle"))
        
        self.shares = Int.valueOf(any: data.value(forKey: "shares"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.lotterytime = Int.valueOf(any: data.value(forKey: "lotterytime"))
        self.finishedtime = Int.valueOf(any: data.value(forKey: "finishedtime"))
        self.luckyNumber = Int.valueOf(any: data.value(forKey: "luckyNumber"))
        self.luckyGroup = String.valueOf(any: data.value(forKey: "luckyGroup"))
    }
}
