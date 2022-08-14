//
//  LuckyFrontUserAgentModel.swift
//  lucky
//  用户基础信息
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserAgentModel : NSObject{
    var frontUser: String
    var showId: Int
    var nickname: String
    var image: String
    var imageURL: String
    
    init(data: NSDictionary) {
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.showId = Int.valueOf(any: data.value(forKey: "showId"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.image = String.valueOf(any: data.value(forKey: "image"))
        self.imageURL = String.valueOf(any: data.value(forKey: "imageURL"))
    }
}
