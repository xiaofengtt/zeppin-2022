//
//  LuckyCategoryCountModel.swift
//  lucky
//  分类商品计数
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation


class LuckyCategoryCountModel : NSObject{
    var category: String
    var categoryName: String
    var count: Int
    
    init(data: NSDictionary) {
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.categoryName = String.valueOf(any: data.value(forKey: "categoryName"))
        self.count = Int.valueOf(any: data.value(forKey: "count"))
    }
}
