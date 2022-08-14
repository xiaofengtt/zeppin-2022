import Foundation
extension Date{
    var timestamp: Int {
        let timeInterval: TimeInterval = self.timeIntervalSince1970
        return Int(timeInterval * 1000)
    }
}
