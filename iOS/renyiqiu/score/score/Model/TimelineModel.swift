//
//  TimelineModel.swift
//  ryqiu
//
//  Created by worker on 2019/6/27.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class TimelineModel : NSObject{
    var time: String
    var type: String
    var side: String
    var playerIn: String
    var playerOut: String
    var ryqiu: String
    
    override init() {
        self.time = ""
        self.type = ""
        self.side = ""
        self.playerIn = ""
        self.playerOut = ""
        self.ryqiu = ""
    }
}
