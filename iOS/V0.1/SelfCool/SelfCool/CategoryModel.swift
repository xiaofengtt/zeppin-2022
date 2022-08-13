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
}

func DictionaryToCategory(dictionary: NSDictionary) -> CategoryModel{
    var category = CategoryModel()
    category.id = dictionary.objectForKey("id") as! Int
    category.name = dictionary.objectForKey("name") as! String
    category.iconUrl = "category_icon_" + String(dictionary.objectForKey("id") as! Int)
    return category
}