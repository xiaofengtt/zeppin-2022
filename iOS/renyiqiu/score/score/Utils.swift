//
//  Utils.swift
//  ntlc
//
//  Created byfarmer on 2017/11/28.
//  Copyright © 2017年 farmer. All rights reserved.
//

import Foundation

class Utils{
    static func checkPhone(phone: String) -> Bool{
        let regex = ["^1[123456789]\\d{9}$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: phone)){
            return true
        }else{
            return false
        }
    }
    
    static func checkPassword(password: String) -> Bool{
        let regex = ["^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: password)){
            return true
        }else{
            return false
        }
    }
    
    static func checkCode(code: String) -> Bool{
        let regex = ["(\\d){6}"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: code)){
            return true
        }else{
            return false
        }
    }
    
    static func checkMoney(money: String) -> Bool{
        let regex = ["^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: money)){
            return true
        }else{
            return false
        }
    }
    
    static func checkIdcard(idcard: String) -> Bool{
        let regex = ["^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"]
        if(NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: idcard)){
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
        let redColor = UIColor(red: 237/255, green: 50/255, blue: 36/255, alpha: 1)
        let yellowColor = UIColor(red: 255/255, green: 235/255, blue: 50/255, alpha: 1)
        let greenColor = UIColor(red: 66/255, green: 189/255, blue: 86/255, alpha: 1)
        
        if(category == CategoryUuid.AFCCL || category == CategoryUuid.UCL){
            if(place <= 2){
                return redColor
            }
        }else if(category == CategoryUuid.CSL){
            if(place <= 3){
                return redColor
            }else if(place >= 15){
                return greenColor
            }
        }else if(category == CategoryUuid.PREMIERLEAGUE){
            if(place <= 4){
                return redColor
            }else if(place <= 5){
                return yellowColor
            }else if(place >= 18){
                return greenColor
            }
        }else if(category == CategoryUuid.LALIGA || category == CategoryUuid.SERIEA){
            if(place <= 4){
                return redColor
            }else if(place <= 6){
                return yellowColor
            }else if(place >= 18){
                return greenColor
            }
        }else if(category == CategoryUuid.BUNDESLIGA){
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
        let footerView = StandingFooterView(category: category)
        return footerView.frame.height
    }
    
    static func getWeekDay(timestamp: Int) -> String{
        let weekdayEn = timestampFormat(timestamp: timestamp, format: "EEEE")
        return weekdayEnToCn(weekdayEn: weekdayEn)
    }
    
    static func getChineseStat(stat: String) -> String{
        switch stat {
        case "Ball Possession":
            return "控球率"
        case "Goal Attempts":
            return "射门"
        case "Shots on Goal":
            return "射正"
        case "Shots off Goal":
            return "射偏"
        case "Blocked Shots":
            return "射门被封堵"
        case "Corner Kicks":
            return "角球"
        case "Free Kicks":
            return "任意球"
        case "Offsides":
            return "越位"
        case "Goalkeeper Saves":
            return "扑救"
        case "Fouls":
            return "犯规"
        case "Yellow Cards":
            return "黄牌"
        case "Red Cards":
            return "红牌"
        case "Total Passes":
            return "传球"
        case "Completed Passes":
            return "传球成功"
        case "Tackles":
            return "铲断"
        case "Attacks":
            return "进攻"
        case "Dangerous Attacks":
            return "危险进攻"
        default:
            return stat
        }
    }
    
    static func weekdayEnToCn(weekdayEn: String) -> String{
        switch weekdayEn {
        case "Monday":
            return "星期一"
        case "Tuesday":
            return "星期二"
        case "Wednesday":
            return "星期三"
        case "Thursday":
            return "星期四"
        case "Friday":
            return "星期五"
        case "Saturday":
            return "星期六"
        case "Sunday":
            return "星期日"
        default:
            return "星期日"
        }
    }
}
