//
//  ModuleDataModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class ModuleDataModel : NSObject{
    var title: String
    var content: String
    var imageUrl: String
    var url: String
    var index: Int
    
    override init() {
        self.title = ""
        self.content = ""
        self.imageUrl = ""
        self.url = ""
        self.index = 0
    }
}