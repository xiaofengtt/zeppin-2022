//
//  HistoryMonthModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class HistoryMonthModel : NSObject{
    var time: String
    var dataList: [HistoryModel]
    
    override init() {
        self.time = ""
        self.dataList = []
    }
    
    init(data: NSDictionary) {
        self.time = String.valueOf(any: data.value(forKey: "time"))
        
        var list: [HistoryModel] = []
        for data in data.value(forKey: "dataList") as! NSArray{
            list.append(HistoryModel(data: data as! NSDictionary))
        }
        self.dataList = list
    }
}
