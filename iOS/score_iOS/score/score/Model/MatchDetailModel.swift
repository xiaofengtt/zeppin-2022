//
//  MatchDetailModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MatchDetailModel : NSObject{
    var uuid: String
    var category: String
    var categoryName: String
    var sesson: String
    var round: String
    var roundName: String
    var time: Int
    var progress: String
    var hometeam: String
    var hometeamName: String
    var hometeamIconUrl: String
    var awayteam: String
    var awayteamName: String
    var awayteamIconUrl: String
    var finalresult: String
    var penaltyresult: String
    var homestarting: [NSDictionary]
    var awaystarting: [NSDictionary]
    var homesubstitutes: [NSDictionary]
    var awaysubstitutes: [NSDictionary]
    var homecoaches: [NSDictionary]
    var awaycoaches: [NSDictionary]
    var goals: [NSDictionary]
    var cards: [NSDictionary]
    var substitutes: [NSDictionary]
    var statistics: [NSDictionary]
    var status: String
    
    
    override init() {
        self.uuid = ""
        self.category = ""
        self.categoryName = ""
        self.sesson = ""
        self.round = ""
        self.roundName = ""
        self.time = 0
        self.progress = ""
        self.hometeam = ""
        self.hometeamName = ""
        self.hometeamIconUrl = ""
        self.awayteam = ""
        self.awayteamName = ""
        self.awayteamIconUrl = ""
        self.finalresult = ""
        self.penaltyresult = ""
        self.homestarting = []
        self.awaystarting = []
        self.homesubstitutes = []
        self.awaysubstitutes = []
        self.homecoaches = []
        self.awaycoaches = []
        self.goals = []
        self.cards = []
        self.substitutes = []
        self.statistics = []
        self.status = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.categoryName = String.valueOf(any: data.value(forKey: "categoryName"))
        self.sesson = String.valueOf(any: data.value(forKey: "sesson"))
        self.round = String.valueOf(any: data.value(forKey: "round"))
        self.roundName = String.valueOf(any: data.value(forKey: "roundName"))
        self.time = Int.valueOf(any: data.value(forKey: "time"))
        self.progress = String.valueOf(any: data.value(forKey: "progress"))
        self.hometeam = String.valueOf(any: data.value(forKey: "hometeam"))
        self.hometeamName = String.valueOf(any: data.value(forKey: "hometeamName"))
        self.hometeamIconUrl = String.valueOf(any: data.value(forKey: "hometeamIconUrl"))
        self.awayteam = String.valueOf(any: data.value(forKey: "awayteam"))
        self.awayteamName = String.valueOf(any: data.value(forKey: "awayteamName"))
        self.awayteamIconUrl = String.valueOf(any: data.value(forKey: "awayteamIconUrl"))
        self.finalresult = String.valueOf(any: data.value(forKey: "finalresult"))
        self.penaltyresult = String.valueOf(any: data.value(forKey: "penaltyresult"))
        self.homestarting = []
        for data in data.value(forKey: "homestarting") as! NSArray{
            self.homestarting.append(data as! NSDictionary)
        }
        self.awaystarting = []
        for data in data.value(forKey: "awaystarting") as! NSArray{
            self.awaystarting.append(data as! NSDictionary)
        }
        self.homesubstitutes = []
        for data in data.value(forKey: "homesubstitutes") as! NSArray{
            self.homesubstitutes.append(data as! NSDictionary)
        }
        self.awaysubstitutes = []
        for data in data.value(forKey: "awaysubstitutes") as! NSArray{
            self.awaysubstitutes.append(data as! NSDictionary)
        }
        self.homecoaches = []
        for data in data.value(forKey: "homecoaches") as! NSArray{
            self.homecoaches.append(data as! NSDictionary)
        }
        self.awaycoaches = []
        for data in data.value(forKey: "awaycoaches") as! NSArray{
            self.awaycoaches.append(data as! NSDictionary)
        }
        self.goals = []
        for data in data.value(forKey: "goals") as! NSArray{
            self.goals.append(data as! NSDictionary)
        }
        self.cards = []
        for data in data.value(forKey: "cards") as! NSArray{
            self.cards.append(data as! NSDictionary)
        }
        self.substitutes = []
        for data in data.value(forKey: "substitutes") as! NSArray{
            self.substitutes.append(data as! NSDictionary)
        }
        self.statistics = []
        for data in data.value(forKey: "statistics") as! NSArray{
            self.statistics.append(data as! NSDictionary)
        }
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
}
