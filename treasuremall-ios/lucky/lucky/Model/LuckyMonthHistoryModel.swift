//
//  LuckyMonthHistoryModel.swift
//  lucky
//  月交易结构
//  Created by Farmer Zhu on 2020/9/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMonthHistoryModel : NSObject{
    var month: String
    var monthTitle: String
    var historyList: [LuckyFrontUserHistoryModel]
    
    init(month: String) {
        self.month = month
        let monthStrs = month.split(separator: "-")
        if(monthStrs.count == 2){
            monthTitle = "\(LuckyUtils.getMonthWord(monthNumber: String(monthStrs[1]))) \(monthStrs[0])"
        }else{
            monthTitle = ""
        }
        self.historyList = []
    }
}
