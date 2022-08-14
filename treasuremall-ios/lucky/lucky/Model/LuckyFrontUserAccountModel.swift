//
//  LuckyFrontUserAccountModel.swift
//  lucky
//  用户账户信息
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserAccountModel : NSObject{
    var uuid: String
    var showId: Int
    var realname: String
    var idcard: String
    var nickname: String
    var mobile: String
    var email: String
    var sex: String
    var realnameFlag: Bool
    var level: String
    var registerChannel: String
    var image: String
    var imageURL: String
    var paypalAccount: String
    
    //余额
    var balance: Double
    //冻结余额
    var balanceLock: Double
    //剩余积分
    var scoreBalance: Double
    
    var bankcardCount: Int
    var voucherCount: Int
    //排行榜排名
    var rankNum: Int
    
    //排行榜信息
    var rankInfo: LuckyFrontUserRanklistModel?
    //邀请排行榜
    var recommendRankInfo: LuckyActivityRecommendRankingModel?
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.showId = Int.valueOf(any: data.value(forKey: "showId"))
        self.realname = String.valueOf(any: data.value(forKey: "realname"))
        self.idcard = String.valueOf(any: data.value(forKey: "idcard"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.mobile = String.valueOf(any: data.value(forKey: "mobile"))
        self.email = String.valueOf(any: data.value(forKey: "email"))
        self.sex = String.valueOf(any: data.value(forKey: "sex"))
        self.realnameFlag = Bool.valueOf(any: data.value(forKey: "realnameFlag"))
        self.level = String.valueOf(any: data.value(forKey: "level"))
        self.registerChannel = String.valueOf(any: data.value(forKey: "registerChannel"))
        self.image = String.valueOf(any: data.value(forKey: "image"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
        self.paypalAccount = String.valueOf(any: data.value(forKey: "paypalAccount"))
        
        self.balance = Double.valueOf(any: data.value(forKey: "balance"))
        self.balanceLock = Double.valueOf(any: data.value(forKey: "balanceLock"))
        self.scoreBalance = Double.valueOf(any: data.value(forKey: "scoreBalance"))
        
        self.bankcardCount = Int.valueOf(any: data.value(forKey: "bankcardCount"))
        self.voucherCount = Int.valueOf(any: data.value(forKey: "voucherCount"))
        self.rankNum = Int.valueOf(any: data.value(forKey: "rankNum"))
        
        if(data.value(forKey: "rankInfo") != nil && data.value(forKey: "rankInfo").debugDescription != "Optional(<null>)"){
            self.rankInfo = LuckyFrontUserRanklistModel(data: data.value(forKey: "rankInfo") as! NSDictionary)
        }else{
            self.rankInfo = nil
        }
        if(data.value(forKey: "recommendRankInfo") != nil && data.value(forKey: "recommendRankInfo").debugDescription != "Optional(<null>)"){
            self.rankInfo = LuckyFrontUserRanklistModel(data: data.value(forKey: "recommendRankInfo") as! NSDictionary)
        }else{
            self.rankInfo = nil
        }
    }
}
