//
//  MatchModel.swift
//  ryqiu
//
//  Created by worker on 2019/5/24.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class MatchModel : NSObject{
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
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
}
