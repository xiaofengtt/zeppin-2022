//
//  CategoryStandingModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class CategoryStandingModel : NSObject{
    var uuid: String
    var category: String
    var team: String
    var teamName: String
    var teamIconUrl: String
    var season: String
    var round: String
    var place: Int
    var played: String
    var won: String
    var drawn: String
    var lost: String
    var scored: String
    var against: String
    var difference: String
    var point: String
    
    
    override init() {
        self.uuid = ""
        self.category = ""
        self.team = ""
        self.teamName = ""
        self.teamIconUrl = ""
        self.season = ""
        self.round = ""
        self.place = 0
        self.played = ""
        self.won = ""
        self.drawn = ""
        self.lost = ""
        self.scored = ""
        self.against = ""
        self.difference = ""
        self.point = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.team = String.valueOf(any: data.value(forKey: "team"))
        self.teamName = String.valueOf(any: data.value(forKey: "teamName"))
        self.teamIconUrl = String.valueOf(any: data.value(forKey: "teamIconUrl"))
        self.season = String.valueOf(any: data.value(forKey: "season"))
        self.round = String.valueOf(any: data.value(forKey: "round"))
        self.place = Int.valueOf(any: data.value(forKey: "place"))
        self.played = String.valueOf(any: data.value(forKey: "played"))
        self.won = String.valueOf(any: data.value(forKey: "won"))
        self.drawn = String.valueOf(any: data.value(forKey: "drawn"))
        self.lost = String.valueOf(any: data.value(forKey: "lost"))
        self.scored = String.valueOf(any: data.value(forKey: "scored"))
        self.against = String.valueOf(any: data.value(forKey: "against"))
        self.difference = String.valueOf(any: data.value(forKey: "difference"))
        self.point = String.valueOf(any: data.value(forKey: "point"))
    }
}
