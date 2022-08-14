//
//  Utils.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/28.
//  Copyright © 2017年 teacher zhu. All rights reserved.
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
    
    static func formatMoney(money: String) -> String{
        let format = NumberFormatter()
        format.numberStyle = NumberFormatter.Style.decimal
        return format.string(from: NSNumber(value: Double(money)!)) == nil ? "" : format.string(from: NSNumber(value: Double(money)!))!
    }
    
    static func getMoneyByUnit(money: Double) -> String{
        var string = "\(money)"
        if(money >= 100000000){
            string = "\(doubleToMoney(doubleString: "\(money / 100000000)"))亿"
        }else if(money >= 10000){
            string = "\(doubleToMoney(doubleString: "\(money / 10000)"))万"
        }else{
            string = "\(doubleToMoney(doubleString: string))"
        }
        return string
    }
    
    static func doubleToMoney(doubleString: String) -> String{
        var string = doubleString
        if(String(string[String.Index.init(encodedOffset: string.count - 2) ..< String.Index.init(encodedOffset: string.count)]) == ".0"){
            string = String(string[..<String.Index.init(encodedOffset: string.count - 2)])
        }
        return string
    }
    
    static func getChineseTime() -> String{
        let date = Date()
        let timeFormatter = DateFormatter()
        timeFormatter.dateFormat = "yyyy年MM月dd日 HH:mm:ss"
        return timeFormatter.string(from: date)
    }
    
    static func getColorNumString(string: String, color: UIColor, numFont: UIFont) -> NSAttributedString{
        let goldStrs: Array = ["0","1","2","3","4","5","6","7","8","9",".",","]
        let attributedString = NSMutableAttributedString(string: string)
        for i in 0 ..< string.count {
            if goldStrs.contains(String(string[String.Index.init(encodedOffset: i) ..< String.Index.init(encodedOffset: i+1)])){
                attributedString.setAttributes([NSAttributedStringKey.foregroundColor : color, NSAttributedStringKey.font: numFont], range: NSRange(location: i, length: 1))
            }
        }
        return attributedString
    }
}
