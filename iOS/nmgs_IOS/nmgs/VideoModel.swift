//
//  VideoModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class VideoModel : NSObject{
    var title: String
    var context: String
    var thumbanil: String
    var timeLength: String
    var timeLengthSeconds: Double
    var urls: [String]
    var videoPointList: [VideoPointModel]
    
    override init() {
        self.title = ""
        self.context = ""
        self.thumbanil = ""
        self.timeLength = ""
        self.timeLengthSeconds = 0
        self.urls = []
        self.videoPointList = []
    }
}