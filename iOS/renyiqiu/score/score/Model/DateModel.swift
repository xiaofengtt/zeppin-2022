//
//  DateModel.swift
//  ryqiu
//
//  Created by worker on 2019/6/6.
//  Copyright © 2019 worker. All rights reserved.
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
