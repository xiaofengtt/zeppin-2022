//
//  LuckyActivityModel.swift
//  lucky
//  活动种类
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityModel : NSObject{
    var name: String
    var title: String
    var status: String
    var configuration: String
    var starttime: Int
    var endtime: Int
    var bannerUrl: String
    var linkUrl: String
    var sort: Int
    
    //活动详情
    var config: NSDictionary?
    var currentTimes: Int
    //是否可参加
    var isPartake: Bool

    init(data: NSDictionary) {
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.configuration = String.valueOf(any: data.value(forKey: "configuration"))
        self.starttime = Int.valueOf(any: data.value(forKey: "starttime"))
        self.endtime = Int.valueOf(any: data.value(forKey: "endtime"))
        self.bannerUrl = String.valueOf(any: data.value(forKey: "bannerUrl"))
        self.linkUrl = String.valueOf(any: data.value(forKey: "linkUrl"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        
        if(data.value(forKey: "config") != nil && data.value(forKey: "config").debugDescription != "Optional(<null>)"){
            self.config = data.value(forKey: "config") as? NSDictionary
        }else{
            self.config = nil
        }
        self.currentTimes = Int.valueOf(any: data.value(forKey: "currentTimes"))
        self.isPartake = Bool.valueOf(any: data.value(forKey: "isPartake"))
    }
}
