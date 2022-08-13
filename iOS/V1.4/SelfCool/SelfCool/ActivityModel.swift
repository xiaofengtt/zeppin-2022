//
//  ActivityModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/4.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ActivityModel : NSObject {
    var id : Int
    var title : String
    var urlString : String
    var imageUrl : String
    
    override init() {
        self.id = 0
        self.title = ""
        self.urlString = ""
        self.imageUrl = ""
    }
    
    init(id : Int , title : String , urlString : String , imageUrl : String) {
        self.id = id
        self.title = title
        self.urlString = urlString
        self.imageUrl = imageUrl
    }
}
