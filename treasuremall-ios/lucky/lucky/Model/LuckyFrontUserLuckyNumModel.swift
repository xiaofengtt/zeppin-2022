//
//  LuckyFrontUserLuckyNumModel.swift
//  lucky
//  用户幸运号
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserLuckyNumModel : NSObject{
    //幸运号
    var luckynum: Int
    //投注数
    var buyCount: Int
    //投注号列表
    var listNums: Array<LuckyLuckyNumModel>

    init(data: NSDictionary) {
        self.luckynum = Int.valueOf(any: data.value(forKey: "luckynum"))
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        if(data.value(forKey: "listNums") != nil && data.value(forKey: "listNums").debugDescription != "Optional(<null>)"){
            var listNumsArray: Array<LuckyLuckyNumModel> = []
            let listNumsArr: NSArray = data.value(forKey: "listNums") as! NSArray
            for nums in listNumsArr {
                let numsDic = nums as! NSDictionary
                let numsModel = LuckyLuckyNumModel(data: numsDic)
                listNumsArray.append(numsModel)
            }
            self.listNums = listNumsArray
        }else{
            self.listNums = []
        }
    }
}
