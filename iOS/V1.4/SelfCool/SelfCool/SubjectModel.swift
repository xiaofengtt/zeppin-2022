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
    var preparingProgress : Double
    var countDown : Int
    var doneItemCount : Int
    var correctRate : Double
    var rankingRate : Double
    var isSelect : Bool
    
    override init(){
        self.id = 0
        self.name = ""
        self.iconUrl = ""
        self.preparingProgress = 0
        self.countDown = 0
        self.doneItemCount = 0
        self.correctRate = 0.0
        self.rankingRate = 0.0
        self.isSelect = false
    }
    
    init(dictionary: NSDictionary){
        let path = dictionary.objectForKey("resourcePath") as! String
        self.id = dictionary.objectForKey("subjectid") as! Int
        self.name = dictionary.objectForKey("subjectname") as! String
        self.iconUrl = UrlBase + path
        self.preparingProgress = dictionary.objectForKey("progress") as! Double
        self.countDown = dictionary.objectForKey("nextTestdayCount") as! Int
        self.doneItemCount = dictionary.objectForKey("brushItemCount") as! Int
        self.correctRate = dictionary.objectForKey("correctRate") as! Double
        self.rankingRate = dictionary.objectForKey("rankingRate") as! Double
        self.isSelect = false
    }
}