import Foundation
class SportsTeamDetailModel : NSObject{
    var uuid: String
    var category: String
    var categoryList: [SportsLeagueModel]
    var name: String
    var shortname: String
    var icon: String
    var iconUrl: String
    var status: String
    var coachesList: [NSDictionary]
    var playersList: [NSDictionary]
    var arrayList: [NSDictionary]
    var isConcren: Bool
    var concren: String
    override init() {
        self.uuid = ""
        self.category = ""
        self.categoryList = []
        self.name = ""
        self.shortname = ""
        self.icon = ""
        self.iconUrl = ""
        self.status = ""
        self.coachesList = []
        self.playersList = []
        self.arrayList = []
        self.isConcren = false
        self.concren = ""
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.categoryList = []
        for data in data.value(forKey: "categoryList") as! NSArray{
            self.categoryList.append(SportsLeagueModel(data: data as! NSDictionary))
        }
        self.name = String.valueOf(any: data.value(forKey: "name"))
        self.shortname = String.valueOf(any: data.value(forKey: "shortname"))
        self.icon = String.valueOf(any: data.value(forKey: "icon"))
        self.iconUrl = String.valueOf(any: data.value(forKey: "iconUrl"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.coachesList = []
        for data in data.value(forKey: "coachesList") as! NSArray{
            self.coachesList.append(data as! NSDictionary)
        }
        self.playersList = []
        for data in data.value(forKey: "playersList") as! NSArray{
            self.playersList.append(data as! NSDictionary)
        }
        self.arrayList = []
        for data in playersList{
            arrayList.append(data)
        }
        self.isConcren = false
        self.concren = ""
    }
}
