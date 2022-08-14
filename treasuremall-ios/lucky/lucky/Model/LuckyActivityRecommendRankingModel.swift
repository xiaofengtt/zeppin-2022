//
//  LuckyActivityRecommendRankingModel.swift
//  lucky
//  邀请活动排行
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityRecommendRankingModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserName: String
    var frontUserShowId: Int
    //排名
    var recommend: Int
    //金额
    var award: Double
    var imageURL: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserName = String.valueOf(any: data.value(forKey: "frontUserName"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.recommend = Int.valueOf(any: data.value(forKey: "recommend"))
        self.award = Double.valueOf(any: data.value(forKey: "award"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
    }
}
