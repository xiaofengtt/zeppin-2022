//
//  CategoryModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class CategoryModel : NSObject{
    var id: String
    var name: String
    var publishList : [PublishModel]
    var children: [CategoryModel]
    
    override init() {
        self.id = ""
        self.name = ""
        self.publishList = []
        self.children = []
    }
}