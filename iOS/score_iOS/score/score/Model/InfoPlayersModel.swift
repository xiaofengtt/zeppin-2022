//
//  InfoPlayersModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class InfoPlayersModel : NSObject{
    var uuid: String
    var name: String
    var cnname: String
    var number: String
    var country: String
    var type: String
    var age: String
    var matchplayed: String
    var goals: String
    var yellowcards: String
    var redcards: String
    var status: String
    
    
    override init() {
        self.uuid = ""
        self.name = ""
        self.cnname = ""
        self.number = ""
        self.country = ""
        self.type = ""
        self.age = ""
        self.matchplayed = ""
        self.goals = ""
        self.yellowcards = ""
        self.redcards = ""
        self.status = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.cnname = String.valueOf(any: data.value(forKey: "cnname"))
        self.number = String.valueOf(any: data.value(forKey: "number"))
        self.country = String.valueOf(any: data.value(forKey: "country"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.age = String.valueOf(any: data.value(forKey: "age"))
        self.matchplayed = String.valueOf(any: data.value(forKey: "matchplayed"))
        self.goals = String.valueOf(any: data.value(forKey: "goals"))
        self.yellowcards = String.valueOf(any: data.value(forKey: "yellowcards"))
        self.redcards = String.valueOf(any: data.value(forKey: "redcards"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
}
