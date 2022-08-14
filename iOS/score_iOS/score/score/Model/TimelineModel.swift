//
//  TimelineModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/27.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class TimelineModel : NSObject{
    var time: String
    var type: String
    var side: String
    var playerIn: String
    var playerOut: String
    var score: String
    
    override init() {
        self.time = ""
        self.type = ""
        self.side = ""
        self.playerIn = ""
        self.playerOut = ""
        self.score = ""
    }
}
