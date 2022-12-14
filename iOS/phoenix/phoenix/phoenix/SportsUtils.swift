import Foundation
class SportsUtils{
    static func examinePhome(phone: String) -> Bool{
        let regex = ["^1[123456789]\\d{9}$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: phone)){
            return true
        }else{
            return false
        }
    }
    static func examinePassword(password: String) -> Bool{
        let regex = ["^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: password)){
            return true
        }else{
            return false
        }
    }
    static func examineCode(code: String) -> Bool{
        let regex = ["(\\d){6}"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: code)){
            return true
        }else{
            return false
        }
    }
    static func hexToDec(number num:String) -> Int {
        let str = num.uppercased()
        var sum = 0
        for i in str.utf8 {
            sum = sum * 16 + Int(i) - 48
            if i >= 65 {
                sum -= 7
            }
        }
        return sum
    }
    static func timestampFormat(timestamp: Int, format: String)->String {
        let timeSta: TimeInterval = TimeInterval(timestamp/1000)
        let dfmatter = DateFormatter()
        dfmatter.dateFormat = format
        let date = Date(timeIntervalSince1970: timeSta)
        return dfmatter.string(from: date)
    }
    static func hexToDec(string: String) -> String{
        let a = Scanner(string: string)
        var b:UInt32 = 0
        if withUnsafeMutablePointer(to: &b, {
            a.scanHexInt32($0)
        }){
            return String(b)
        }
        return ""
    }
    static func getStandingColor(category: String, place: Int) -> UIColor{
        let normalColor = UIColor.white
        let redColor = UIColor(red: 255/255, green: 243/255, blue: 242/255, alpha: 1)
        let yellowColor = UIColor(red: 255/255, green: 247/255, blue: 233/255, alpha: 1)
        let greenColor = UIColor(red: 235/255, green: 255/255, blue: 233/255, alpha: 1)
        if(category == CategoryUuid.YAGUAN || category == CategoryUuid.OUGUAN){
            if(place <= 2){
                return redColor
            }
        }else if(category == CategoryUuid.ZHONGCHAO){
            if(place <= 3){
                return redColor
            }else if(place >= 15){
                return greenColor
            }
        }else if(category == CategoryUuid.YINGCHAO){
            if(place <= 4){
                return redColor
            }else if(place <= 5){
                return yellowColor
            }else if(place >= 18){
                return greenColor
            }
        }else if(category == CategoryUuid.XIJIA || category == CategoryUuid.YIJIA){
            if(place <= 4){
                return redColor
            }else if(place <= 6){
                return yellowColor
            }else if(place >= 18){
                return greenColor
            }
        }else if(category == CategoryUuid.DEJIA){
            if(place <= 4){
                return redColor
            }else if(place <= 6){
                return yellowColor
            }else if(place >= 16){
                return greenColor
            }
        }
        return normalColor
    }
    static func getStandingFootHeight(category: String) -> CGFloat{
        let footerView = SportsStandingFooterView(category: category)
        return footerView.frame.height
    }
    static func getWeekDay(timestamp: Int) -> String{
        let weekdayEn = timestampFormat(timestamp: timestamp, format: "EEEE")
        return weekdayEnToCn(weekdayEn: weekdayEn)
    }
    static func getChineseStat(stat: String) -> String{
        switch stat {
        case "Ball Possession":
            return "?????????"
        case "Goal Attempts":
            return "??????"
        case "Shots on Goal":
            return "??????"
        case "Shots off Goal":
            return "??????"
        case "Blocked Shots":
            return "???????????????"
        case "Corner Kicks":
            return "??????"
        case "Free Kicks":
            return "?????????"
        case "Offsides":
            return "??????"
        case "Goalkeeper Saves":
            return "??????"
        case "Fouls":
            return "??????"
        case "Yellow Cards":
            return "??????"
        case "Red Cards":
            return "??????"
        case "Total Passes":
            return "??????"
        case "Completed Passes":
            return "????????????"
        case "Tackles":
            return "??????"
        case "Attacks":
            return "??????"
        case "Dangerous Attacks":
            return "????????????"
        default:
            return stat
        }
    }
    static func weekdayEnToCn(weekdayEn: String) -> String{
        switch weekdayEn {
        case "Monday":
            return "?????????"
        case "Tuesday":
            return "?????????"
        case "Wednesday":
            return "?????????"
        case "Thursday":
            return "?????????"
        case "Friday":
            return "?????????"
        case "Saturday":
            return "?????????"
        case "Sunday":
            return "?????????"
        default:
            return "?????????"
        }
    }
    
    static func getMatchResult(finalresult: String) -> NSDictionary{
        let result : NSMutableDictionary = NSMutableDictionary()
        result["homeScore"] = "0"
        result["awayScore"] = "0"
        result["result"] = "0"
        
        if(finalresult.firstIndex(of: "(") == nil){
            let sorces = finalresult.split(separator: "-")
            if(sorces.count == 2){
                result["homeScore"] = "\(sorces[0].replacingOccurrences(of: " ", with: ""))"
                result["awayScore"] = "\(sorces[1].replacingOccurrences(of: " ", with: ""))"
            }else{
                result["homeScore"] = ""
                result["awayScore"] = ""
            }
        }else{
            let finalStr = finalresult.substring(to: finalresult.firstIndex(of: "(")!)
            let halfStr = finalresult.replacingOccurrences(of: finalStr, with: "")
            let sorces = halfStr.split(separator: "-")
            if(sorces.count >= 2){
                result["homeScore"] = "\(sorces[0].replacingOccurrences(of: " ", with: ""))"
                result["awayScore"] = "\(sorces[1].replacingOccurrences(of: " ", with: ""))"
            }else{
                result["homeScore"] = ""
                result["awayScore"] = ""
            }
        }
        
        let homeScore = result["homeScore"] as! String
        let awayScore = result["awayScore"] as! String
        if(homeScore.length > awayScore.length){
            result["result"] = "1"
        }else if(homeScore.length < awayScore.length){
            result["result"] = "-1"
        }else{
            if(homeScore > awayScore){
                result["result"] = "1"
            }else if(homeScore < awayScore){
                result["result"] = "-1"
            }
        }
        return result
    }
}
