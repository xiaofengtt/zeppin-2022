//
//  LuckyYearMonthModel.swift
//  lucky
//  用于用户金币历史记录
//  Created by Farmer Zhu on 2020/9/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyYearMonthModel : NSObject{
    var year: String
    var monthList: Array<String>
    
    init(year: String) {
        self.year = year
        self.monthList = []
    }
}
