import Foundation
extension Date{
    //获取当前13位时间戳
    var timestamp: Int {
        let timeInterval: TimeInterval = self.timeIntervalSince1970
        return Int(timeInterval * 1000)
    }
}
