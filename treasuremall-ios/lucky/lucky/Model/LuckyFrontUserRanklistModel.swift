//
//  LuckyFrontUserRanklistModel.swift
//  lucky
//  排行榜
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserRanklistModel : NSObject{
    var frontUser: String
    var showId: Int
    var nickname: String
    var imageUrl: String
    
    //总投注
    var totalPayment: Double
    //总获奖
    var totalWinning: Double
    //投注次数
    var paymentTimes: Int
    //获奖次数
    var winningTimes: Int
    var buyCount: Int
    //排名
    var rankNum: Int
    
    init(data: NSDictionary) {
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.showId = Int.valueOf(any: data.value(forKey: "showId"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        
        self.totalPayment = Double.valueOf(any: data.value(forKey: "totalPayment"))
        self.totalWinning = Double.valueOf(any: data.value(forKey: "totalWinning"))
        self.paymentTimes = Int.valueOf(any: data.value(forKey: "paymentTimes"))
        self.winningTimes = Int.valueOf(any: data.value(forKey: "winningTimes"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        self.rankNum = Int.valueOf(any: data.value(forKey: "rankNum"))
    }
}
