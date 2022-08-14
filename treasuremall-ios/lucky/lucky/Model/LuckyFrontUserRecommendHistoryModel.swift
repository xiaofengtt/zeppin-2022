//
//  LuckyFrontUserRecommendHistoryModel.swift
//  lucky
//  邀请历史
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserRecommendHistoryModel : NSObject{
    var uuid: String
    var frontUser: String
    
    //被邀请人
    var recommendFrontUser: String
    var recommendShowId: Int
    var nickname: String
    var imageURL: String
    
    var createtime: Int
    //充值额
    var rechargeAmount: Double
    //赏金
    var awardAmount: Double
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.recommendFrontUser = String.valueOf(any: data.value(forKey: "recommendFrontUser"))
        self.recommendShowId = Int.valueOf(any: data.value(forKey: "recommendShowId"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.rechargeAmount = Double.valueOf(any: data.value(forKey: "rechargeAmount"))
        self.awardAmount = Double.valueOf(any: data.value(forKey: "awardAmount"))
    }
}
