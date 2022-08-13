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
    var enable : Bool
    var descriptionWord : String
    
    override init(){
        self.id = 0
        self.name = ""
        self.iconUrl = ""
        self.descriptionWord = ""
        self.enable = false
    }
    
     init(id: Int , name : String , descriptionWord : String){
        self.id = id
        self.name = name
        self.iconUrl = "category_icon_\(id)"
        self.descriptionWord = descriptionWord
        self.enable = false
    }
    
    static func getEnable(id : Int){
        for i in 0 ..< globalCategoryList.count{
            if id == globalCategoryList[i].id{
                globalCategoryList[i].enable = true
            }
        }
    }
}