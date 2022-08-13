//
//  ItemModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ItemModel : NSObject {
    var id: Int
    var ssoUserTestItemId: Int
    var index : Int
    var itemType: String
    var modelType: Int
    var sourceType: String
    var isgroup: Bool
    var analysis: String
    var answer: AnswerModel
    
    override init() {
        self.id = 0
        self.ssoUserTestItemId = 0
        self.index = 0
        self.itemType = ""
        self.modelType = 0
        self.sourceType = ""
        self.isgroup = false
        self.analysis = ""
        self.answer = AnswerModel()
    }
    
}
func DictionaryToItem(dictionary: NSDictionary) -> ItemModel{
    var itemModel = ItemModel()
    itemModel.id = dictionary.objectForKey("item.id") as! Int
    itemModel.ssoUserTestItemId = dictionary.objectForKey("id") as! Int
    itemModel.modelType = dictionary.objectForKey("itemType.id") as! Int
    itemModel.itemType = dictionary.objectForKey("itemType.name") as! String
    itemModel.sourceType = dictionary.objectForKey("source") as! String
    itemModel.isgroup = dictionary.objectForKey("isgroup") as! Bool
    itemModel.analysis = dictionary.objectForKey("analysis") as! String
    
    var answer = dictionary.objectForKey("data") as! NSDictionary
    var answerModel = DictionaryToAnswer(answer)
    
    itemModel.answer = answerModel
    return itemModel
}