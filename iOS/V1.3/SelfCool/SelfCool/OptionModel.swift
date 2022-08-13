//
//  OptionModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class OptionModel :NSObject{
    var content: String
    var inx: String
    
    override init(){
        self.content = ""
        self.inx = ""
    }
    
    init(content : String , inx : String) {
        self.content = content
        self.inx = inx
    }
}
