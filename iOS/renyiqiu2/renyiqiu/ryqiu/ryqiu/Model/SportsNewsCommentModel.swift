import Foundation
class SportsNewsCommentModel : NSObject{
    var uuid: String
    var news: String
    var parent: SportsNewsCommentModel?
    var content: String
    var status: String
    var creator: String
    var creatorName: String
    var createtime: Int
    var cellHeight: CGFloat
    override init() {
        self.uuid = ""
        self.news = ""
        self.content = ""
        self.status = ""
        self.creator = ""
        self.creatorName = ""
        self.createtime = 0
        self.cellHeight = 0
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.news = String.valueOf(any: data.value(forKey: "newspublish"))
        if(data.value(forKey: "parent").debugDescription != "Optional(<null>)"){
            self.parent = SportsNewsCommentModel(data: data.value(forKey: "parent") as! NSDictionary)
        }
        self.content = String.valueOf(any: data.value(forKey: "content"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.creator = String.valueOf(any: data.value(forKey: "creator"))
        self.creatorName = String.valueOf(any: data.value(forKey: "creatorName"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.cellHeight = 0
    }
}
