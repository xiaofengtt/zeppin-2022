//
//  CommodityModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class CommodityModel : NSObject{
    var commodityId: String
    var commodityCover: String
    var showBannerURL: String
    var showMessage: String
    var showTime: String
    var timepoint: String
    var timepointSecond: String
    
    override init() {
        self.commodityId = ""
        self.commodityCover = ""
        self.showBannerURL = ""
        self.showMessage = ""
        self.showTime = ""
        self.timepoint = ""
        self.timepointSecond = ""
    }
}