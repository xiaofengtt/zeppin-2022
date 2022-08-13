//
//  KnowledgeModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class KnowledgeModel : NSObject{
    var id : Int
    var name : String
    var level :Int
    var progress : Double
    var haschild : Bool
    var itemCount : Int
    var correctCount : Int
    var isopen : Bool
    var children : [KnowledgeModel]?
    
    override init() {
        self.id = 0
        self.name = ""
        self.level = 0
        self.progress = 0
        self.haschild = false
        self.itemCount = 0
        self.correctCount = 0
        self.isopen = false
    }
    
}

func DictionaryToKnowledge(dictionary: NSDictionary) -> KnowledgeModel{
    var knowledge : KnowledgeModel = KnowledgeModel()
    knowledge.id = dictionary.objectForKey("id") as! Int
    knowledge.name = dictionary.objectForKey("name") as! String
    knowledge.level = dictionary.objectForKey("level") as! Int
    knowledge.correctCount = dictionary.objectForKey("rightCount") as! Int
    knowledge.itemCount = dictionary.objectForKey("itemCount") as! Int
    var haschild = dictionary.objectForKey("hasChild") as! Bool
    if haschild {
        var childKnowledgeList : [KnowledgeModel] = []
        knowledge.haschild = true
        var childKnowledgeArray = dictionary.objectForKey("data") as! NSArray
        for index in 0 ..< childKnowledgeArray.count{
            var childKnowledge = childKnowledgeArray[index] as! NSDictionary
            var childKnowledgeModel = DictionaryToKnowledge(childKnowledge)
            childKnowledgeList.append(childKnowledgeModel)
        }
        knowledge.children = childKnowledgeList
    }
    return knowledge
}