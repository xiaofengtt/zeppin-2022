import Foundation
class DateModel: NSObject{
    var date: String
    var weekday: String
    var matchList: Array<SportsMatchModel>
    override init() {
        self.date = ""
        self.weekday = ""
        self.matchList = []
    }
}
