//
//  LuckyWinningRollModel.swift
//  lucky
//  首页获胜滚动
//  Created by Farmer Zhu on 2020/9/2.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyWinningRollModel : NSObject{
    var nickname: String
    var goodsname: String
    var price: Double
    
    init(data: NSDictionary) {
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.goodsname = String.valueOf(any: data.value(forKey: "goodsname"))
        self.price = Double.valueOf(any: data.value(forKey: "price"))
    }
}
