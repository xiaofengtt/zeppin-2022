//
//  TeamModel.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class TeamModel : NSObject{
    var uuid: String
    var category: String
    var name: String
    var shortname: String
    var icon: String
    var iconUrl: String
    var status: String
    var isConcren: Bool
    var concren: String
    
    override init() {
        self.uuid = ""
        self.category = ""
        self.name = ""
        self.shortname = ""
        self.icon = ""
        self.iconUrl = ""
        self.status = ""
        self.isConcren = false
        self.concren = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortname = String.valueOf(any: data.value(forKey: "shortname"))
        self.icon = String.valueOf(any: data.value(forKey: "icon"))
        self.iconUrl = String.valueOf(any: data.value(forKey: "iconUrl"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.isConcren = false
        self.concren = ""
    }
}
