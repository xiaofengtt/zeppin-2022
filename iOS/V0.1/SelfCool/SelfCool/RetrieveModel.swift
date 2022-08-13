//
//  RetrieveModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class RetrieveModel : NSObject{
    var id : Int
    var name : String
    var subjects : [SubjectModel]
    
    override init(){
        self.id = 0
        self.name = ""
        self.subjects = []
    }
}

func DictionaryToRetrieve(dictionary: NSDictionary) -> RetrieveModel{
    var retrieve = RetrieveModel()
    retrieve.id = dictionary.objectForKey("id") as! Int
    retrieve.name = dictionary.objectForKey("name") as! String
    var subjectList = dictionary.objectForKey("subjects") as! [NSDictionary]
    var subjects : [SubjectModel] = []
    for i in 0 ..< subjectList.count{
        var subject = subjectList[i]
        var subjectModel = SubjectModel()
        subjectModel.id = subject.objectForKey("id") as! Int
        subjectModel.name = subject.objectForKey("name") as! String
        subjectModel.isSelect = subject.objectForKey("isSelected") as! Bool
        subjects.append(subjectModel)
    }
    retrieve.subjects = subjects
    return retrieve
}
