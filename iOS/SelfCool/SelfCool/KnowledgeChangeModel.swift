//
//  KnowledgeChangeModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class KnowledgeChangeModel : NSObject {
    var id : Int
    var name : String
    var changeValue : Int
    
    override init(){
        self.id = 0
        self.name = ""
        self.changeValue = 0
    }
    
    init(dictionary: NSDictionary){
        self.id = dictionary.objectForKey("id") as! Int
        self.name = dictionary.objectForKey("name") as! String
        self.changeValue = dictionary.objectForKey("rightChangeCount") as! Int
    }
}