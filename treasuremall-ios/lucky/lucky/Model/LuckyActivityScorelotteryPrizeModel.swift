//
//  LuckyActivityScorelotteryPrizeModel.swift
//  lucky
//  积分抽奖活动
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityScorelotteryPrizeModel : NSObject{
    var uuid: String
    var activityInfo: String
    var status: String
    var createtime: Int
    var creator: String
    var prizeTitle: String
    var prizeType: String
    var prizeCover: String
    var prize: String
    var sort: Int
    
    var prizeDetail: String
    var prizeCoverUrl: String
    var isScorelottery: Bool
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.activityInfo = String.valueOf(any: data.value(forKey: "activityInfo"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.creator = String.valueOf(any: data.value(forKey: "creator"))
        self.prizeTitle = String.valueOf(any: data.value(forKey: "prizeTitle"))
        self.prizeType = String.valueOf(any: data.value(forKey: "prizeType"))
        self.prizeCover = String.valueOf(any: data.value(forKey: "prizeCover"))
        self.prize = String.valueOf(any: data.value(forKey: "prize"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        
        self.prizeDetail = String.valueOf(any: data.value(forKey: "prizeDetail"))
        self.prizeCoverUrl = String.valueOf(any: data.value(forKey: "prizeCoverUrl"))
        self.isScorelottery = Bool.valueOf(any: data.value(forKey: "isCheckin"))
    }
}
