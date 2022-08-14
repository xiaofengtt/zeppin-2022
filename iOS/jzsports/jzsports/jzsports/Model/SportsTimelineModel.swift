import Foundation
class SportsTimelineModel : NSObject{
    var time: String
    var type: String
    var side: String
    var playerIn: String
    var playerOut: String
    var score: String
    var iconUrl: String
    var teamName: String
    override init() {
        self.time = ""
        self.type = ""
        self.side = ""
        self.playerIn = ""
        self.playerOut = ""
        self.score = ""
        self.iconUrl = ""
        self.teamName = ""
    }
}
