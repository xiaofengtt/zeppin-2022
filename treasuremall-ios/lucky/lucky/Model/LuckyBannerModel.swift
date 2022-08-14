//
//  LuckyBannerModel.swift
//  lucky
//  Banner对象
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyBannerModel : NSObject{
    var uuid: String
    var type: String
    var title: String
    var code: String
    var image: String
    var imageUrl: String
    var url: String
    var status: String
    
    //刷新时间 用于引导页
    var refreshtime: Int
    var starttime: Int
    var endtime: Int
    var sort: Int
    var frontUserLevel: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.code = String.valueOf(any: data.value(forKey: "code"))
        self.image = String.valueOf(any: data.value(forKey: "image"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        self.url = String.valueOf(any: data.value(forKey: "url"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        
        self.refreshtime = Int.valueOf(any: data.value(forKey: "refreshtime"))
        self.starttime = Int.valueOf(any: data.value(forKey: "starttime"))
        self.endtime = Int.valueOf(any: data.value(forKey: "endtime"))
        self.sort = Int.valueOf(any: data.value(forKey: "sort"))
        self.frontUserLevel = String.valueOf(any: data.value(forKey: "frontUserLevel"))
    }
}
