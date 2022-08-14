import Foundation
class SportsNewsModel : NSObject{
    var uuid: String
    var news: String
    var category: String
    var categoryList: [SportsLeagueModel]
    var team: String
    var teamList: [SportsTeamModel]
    var title: String
    var content: String
    var coverUrl: String
    var author: String
    var newstime: String
    var status: String
    override init() {
        self.uuid = ""
        self.news = ""
        self.category = ""
        self.categoryList = []
        self.team = ""
        self.teamList = []
        self.title = ""
        self.content = ""
        self.coverUrl = ""
        self.author = ""
        self.newstime = ""
        self.status = ""
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.news = String.valueOf(any: data.value(forKey: "news"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.categoryList = []
        for data in data.value(forKey: "categoryList") as! NSArray{
            self.categoryList.append(SportsLeagueModel(data: data as! NSDictionary))
        }
        self.team = String.valueOf(any: data.value(forKey: "team"))
        self.teamList = []
        for data in data.value(forKey: "teamList") as! NSArray{
            self.teamList.append(SportsTeamModel(data: data as! NSDictionary))
        }
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.content = String.valueOf(any: data.value(forKey: "content"))
        self.coverUrl = String.valueOf(any: data.value(forKey: "coverUrl"))
        self.author = String.valueOf(any: data.value(forKey: "author"))
        self.newstime = String.valueOf(any: data.value(forKey: "newstime"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
}
