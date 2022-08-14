//
//  LuckyFrontUserMessageModel.swift
//  lucky
//  用户信息
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserMessageModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var title: String
    var content: String
    var sourceId: String
    var sourceType: String
    var sourceUrl: String
    var sourceImage: String
    var sourceImageUrl: String
    var type: String
    var status: String
    var createtime: Int
    
    //是否展开
    var isOpen: Bool
    //动态高度
    var cellHeight: CGFloat
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.content = String.valueOf(any: data.value(forKey: "content"))
        self.sourceId = String.valueOf(any: data.value(forKey: "sourceId"))
        self.sourceType = String.valueOf(any: data.value(forKey: "sourceType"))
        self.sourceUrl = String.valueOf(any: data.value(forKey: "sourceUrl"))
        self.sourceImage = String.valueOf(any: data.value(forKey: "sourceImage"))
        self.sourceImageUrl = String.valueOf(any: data.value(forKey: "sourceImageUrl"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        
        self.isOpen = false
        self.cellHeight = 0
    }
}
