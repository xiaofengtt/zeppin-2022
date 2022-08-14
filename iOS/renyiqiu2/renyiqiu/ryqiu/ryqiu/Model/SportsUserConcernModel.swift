import Foundation
class UserConcrenModel : NSObject{
    var uuid: String
    var user: String
    var team: String
    var teamName: String
    var teamIconUrl: String
    var category: String
    var categoryName: String
    var createtime: Int
    override init() {
        self.uuid = ""
        self.user = ""
        self.team = ""
        self.teamName = ""
        self.teamIconUrl = ""
        self.category = ""
        self.categoryName = ""
        self.createtime = 0
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.user = String.valueOf(any: data.value(forKey: "user"))
        self.team = String.valueOf(any: data.value(forKey: "team"))
        self.teamName = String.valueOf(any: data.value(forKey: "teamName"))
        self.teamIconUrl = String.valueOf(any: data.value(forKey: "teamIconUrl"))
        self.category = String.valueOf(any: data.value(forKey: "category"))
        self.categoryName = String.valueOf(any: data.value(forKey: "categoryName"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
    }
}
