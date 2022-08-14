//
//  CategoryTopscore.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class CategoryTopscoreModel : NSObject{
    var uuid: String
    var category: String
    var team: String
    var teamName: String
    var player: String
    var playerName: String
    var place: Int
    var goals: String
    
    
    override init() {
        self.uuid = ""
        self.category = ""
        self.team = ""
        self.teamName = ""
        self.player = ""
        self.playerName = ""
        self.place = 0
        self.goals = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.team = String.valueOf(any: data.value(forKey: "team"))
        self.teamName = String.valueOf(any: data.value(forKey: "teamName"))
        self.player = String.valueOf(any: data.value(forKey: "player"))
        self.playerName = String.valueOf(any: data.value(forKey: "playerName"))
        self.place = Int.valueOf(any: data.value(forKey: "place"))
        self.goals = String.valueOf(any: data.value(forKey: "goals"))
    }
}
