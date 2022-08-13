//
//  SubjectModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class SubjectModel : NSObject {
    var id: Int
    var name: String
    var iconUrl: String
    //var count: Int
    var preparingProgress : Double
    //var examTime : String
    var countDown : Int
    var doneItemCount : Int
    var correctRate : Double
    var rankingRate : Double
    var isSelect : Bool
    
    override init(){
        self.id = 0
        self.name = ""
        self.iconUrl = ""
        //self.count = 0
        self.preparingProgress = 0
        //self.examTime = ""
        self.countDown = 0
        self.doneItemCount = 0
        self.correctRate = 0.0
        self.rankingRate = 0.0
        self.isSelect = false
    }
}

func DictionaryToUserSubject(dictionary: NSDictionary) -> SubjectModel{
    var subject = SubjectModel()
    subject.id = dictionary.objectForKey("subjectid") as! Int
    subject.name = dictionary.objectForKey("subjectname") as! String
    subject.iconUrl = "category_icon_50"
        //+ String(dictionary.objectForKey("category.id") as! Int)
    //subject.count = dictionary.objectForKey("count") as! Int
    subject.preparingProgress = dictionary.objectForKey("progress") as! Double
    //subject.examTime = dictionary.objectForKey("examtime") as! String
    subject.countDown = dictionary.objectForKey("nextTestdayCount") as! Int
    subject.doneItemCount = dictionary.objectForKey("brushItemCount") as! Int
    subject.correctRate = dictionary.objectForKey("correctRate") as! Double
    subject.rankingRate = dictionary.objectForKey("rankingRate") as! Double
    return subject
}