//
//  DateModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/6.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class DateModel: NSObject{
    
    var date: String
    var weekday: String
    var matchList: Array<MatchModel>
    
    override init() {
        self.date = ""
        self.weekday = ""
        self.matchList = []
    }
}
