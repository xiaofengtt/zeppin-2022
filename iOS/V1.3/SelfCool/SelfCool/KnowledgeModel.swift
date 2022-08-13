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
    var haschild : Bool
    var itemCount : Int
    var correctCount : Int
    var isopen : Bool
    var children : [KnowledgeModel]?
    
    override init() {
        self.id = 0
        self.name = ""
        self.level = 0
        self.haschild = false
        self.itemCount = 0
        self.correctCount = 0
        self.isopen = false
    }
    
    init(dictionary: NSDictionary){
        self.id = dictionary.objectForKey("id") as! Int
        self.name = dictionary.objectForKey("name") as! String
        self.level = dictionary.objectForKey("level") as! Int
        self.correctCount = dictionary.objectForKey("rightCount") as! Int
        self.itemCount = dictionary.objectForKey("itemCount") as! Int
        self.isopen = false
        var haschild = dictionary.objectForKey("hasChild") as! Bool
        if haschild {
            var childKnowledgeList : [KnowledgeModel] = []
            self.haschild = true
            var childKnowledgeArray = dictionary.objectForKey("data") as! NSArray
            for index in 0 ..< childKnowledgeArray.count{
                var childKnowledge = childKnowledgeArray[index] as! NSDictionary
                var childKnowledgeModel = KnowledgeModel(dictionary: childKnowledge)
                childKnowledgeList.append(childKnowledgeModel)
            }
            self.children = childKnowledgeList
        }else{
            self.haschild = false
        }
    }
}