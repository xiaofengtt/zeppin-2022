//
//  LuckyActivityCheckinPrizeModel.swift
//  lucky
//  签到活动
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityCheckinPrizeModel : NSObject{
    var uuid: String
    var activityInfo: String
    var status: String
    var createtime: Int
    var creator: String
    
    //奖品
    var prizeTitle: String
    var prizeType: String
    var prizeCover: String
    var prize: String
    //天数
    var dayNum: Int
    var sort: Int
    
    //奖品信息
    var prizeDetail: String
    //奖品封面
    var prizeCoverUrl: String
    //是否已参加
    var isCheckin: Bool
    
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
        self.dayNum = Int.valueOf(any: data.value(forKey: "dayNum"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        
        self.prizeDetail = String.valueOf(any: data.value(forKey: "prizeDetail"))
        self.prizeCoverUrl = String.valueOf(any: data.value(forKey: "prizeCoverUrl"))
        self.isCheckin = Bool.valueOf(any: data.value(forKey: "isCheckin"))
    }
}
