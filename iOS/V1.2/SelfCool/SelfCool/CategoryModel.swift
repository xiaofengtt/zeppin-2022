//
//  CategoryModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class CategoryModel : NSObject{
    var id : Int
    var name : String
    var iconUrl : String
    
    override init(){
        self.id = 0
        self.name = ""
        self.iconUrl = ""
    }
    
    init(dictionary: NSDictionary){
        self.id = dictionary.objectForKey("id") as! Int
        self.name = dictionary.objectForKey("name") as! String
        self.iconUrl = "category_icon_" + String(dictionary.objectForKey("id") as! Int)
    }
}