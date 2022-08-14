//
//  VideoPointModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class VideoPointModel : NSObject{
    var commodityId: String
    var commodityCover: String
    var showBannerUrl: String
    var showMessage: String
    var showTime: Double
    var timepoint: String
    var timepointSecond: Double
    
    override init() {
        self.commodityId = ""
        self.commodityCover = ""
        self.showBannerUrl = ""
        self.showMessage = ""
        self.showTime = 0
        self.timepoint = ""
        self.timepointSecond = 0
    }
    
}