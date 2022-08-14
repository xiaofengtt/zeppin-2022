import Foundation
class SportsLeagueModel : NSObject{
    var uuid: String
    var name: String
    var shortname: String
    var level: Int
    var parent: String
    var scode: String
    var icon: String
    var iconUrl: String
    var isTag: Bool
    var status: String
    var children: [SportsLeagueModel]
    override init() {
        self.uuid = ""
        self.name = ""
        self.shortname = ""
        self.level = 0
        self.parent = ""
        self.scode = ""
        self.icon = ""
        self.iconUrl = ""
        self.isTag = false
        self.status = ""
        self.children = []
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortname = String.valueOf(any: data.value(forKey: "shortname"))
        self.level = Int.valueOf(any: data.value(forKey: "level"))
        self.parent = String.valueOf(any: data.value(forKey: "parent"))
        self.scode = String.valueOf(any: data.value(forKey: "scode"))
        self.icon = String.valueOf(any: data.value(forKey: "icon"))
        self.iconUrl = String.valueOf(any: data.value(forKey: "iconUrl"))
        self.isTag = Bool.valueOf(any: data.value(forKey: "isTag"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.children = []
        for data in data.value(forKey: "children") as! NSArray{
            self.children.append(SportsLeagueModel(data: data as! NSDictionary))
        }
    }
}
