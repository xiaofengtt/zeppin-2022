//
//  ItemTypeModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ItemTypeModel : NSObject{
    var id : Int
    var name : String
    var itemCount : Int
    var flagCount : Int
    var progressColor : UIColor
    
    static func getProgressColor(index: Int) -> UIColor{
        let colorList : [UIColor] =
            [UIColor(red: 95/255, green: 205/255, blue: 126/255, alpha: 1),
            UIColor(red: 159/255, green: 217/255, blue: 247/255, alpha: 1),
            UIColor(red: 240/255, green: 131/255, blue: 0, alpha: 1),
            UIColor(red: 239/255, green: 133/255, blue: 125/255, alpha: 1),
            UIColor(red: 207/255, green: 167/255, blue: 205/255, alpha: 1),
            UIColor(red: 80/255, green: 161/255, blue: 63/255, alpha: 1),
            UIColor(red: 35/255, green: 153/255, blue: 200/255, alpha: 1),
            UIColor(red: 246/255, green: 175/255, blue: 106/255, alpha: 1),
            UIColor(red: 231/255, green: 33/255, blue: 71/255, alpha: 1),
            UIColor(red: 105/255, green: 70/255, blue: 154/255, alpha: 1)]
        return colorList[index%10]
    }
    
    override init(){
        self.id = 0
        self.name = ""
        self.itemCount = 0
        self.flagCount = 0
        self.progressColor = UIColor.whiteColor()
    }
    
    init(dictionary: NSDictionary){
        self.id = dictionary.objectForKey("itemType.id") as! Int
        self.name = dictionary.objectForKey("itemType.name") as! String
        self.itemCount = dictionary.objectForKey("releasedItemCount") as! Int
        self.flagCount = dictionary.objectForKey("lastRightItemCount") as! Int
        self.progressColor = UIColor.whiteColor()
    }
    
    init(id:Int,name:String,itemCount:Int,flagCount:Int,progressColor:UIColor){
        self.id = id
        self.name = name
        self.itemCount = itemCount
        self.flagCount = flagCount
        self.progressColor = progressColor
    }
    
}