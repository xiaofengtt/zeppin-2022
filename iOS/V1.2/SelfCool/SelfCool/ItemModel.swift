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
    var blankInx : Int
    var index : Int
    var itemType: String
    var modelType: Int
    var sourceType: String
    var isGroup: Bool
    var userCorrectCount : Int
    var userWrongCount : Int
    var children : [ItemModel]?
    var analysis: String
    var answer: AnswerModel
    var isShow : Bool
    
    override init() {
        self.id = 0
        self.ssoUserTestItemId = 0
        self.blankInx = 0
        self.index = 0
        self.itemType = ""
        self.modelType = 0
        self.sourceType = ""
        self.isGroup = false
        self.userCorrectCount = 0
        self.userWrongCount = 0
        self.analysis = ""
        self.answer = AnswerModel()
        self.isShow = false
    }
    
    init(dictionary: NSDictionary) {
        self.id = dictionary.objectForKey("item.id") as! Int
        self.ssoUserTestItemId = dictionary.objectForKey("id") as! Int
        self.blankInx = 0
        self.index = 0
        self.modelType = dictionary.objectForKey("modelType") as! Int
        self.itemType = dictionary.objectForKey("itemType.name") as! String
        self.sourceType = dictionary.objectForKey("source") as! String
        self.userWrongCount = 0
        self.userCorrectCount = 0
        self.isGroup = dictionary.objectForKey("isgroup") as! Bool
        if modelType == 4{
            var answer = AnswerModel()
            answer.stem = dictionary.objectForKey("data") as! String
            self.answer = answer
            self.analysis = "暂不支持此题型,请下载最新版本."
        }else{
            self.analysis = dictionary.objectForKey("analysis") as! String
            var answer = dictionary.objectForKey("data") as! NSDictionary
            var answerModel = AnswerModel(dictionary: answer , modelType: modelType)
            if modelType == 6{
                answerModel.completeType = dictionary.objectForKey("completeType") as! Int
            }
            self.answer = answerModel
        }
        self.isShow = false
    }
}