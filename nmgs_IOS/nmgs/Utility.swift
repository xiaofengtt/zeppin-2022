//
//  Utility.swift
//  nmgs
//
//  Created by zeppin on 2016/11/2.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

func < <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
    switch (lhs, rhs) {
    case let (l?, r?):
        return l < r
    case (nil, _?):
        return true
    default:
        return false
    }
}

func > <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
    switch (lhs, rhs) {
    case let (l?, r?):
        return l > r
    default:
        return rhs < lhs
    }
}

func validatePhone(phoneNumber: String) -> Bool {
    return validate(validString: phoneNumber, patternString: "^1[0-9]{10}$")}

func validateCode(codeString: String) -> Bool {
    return validate(validString: codeString, patternString: "^[0-9]{6}$")
}

func validate(validString: String, patternString: String) -> Bool{
    do {
        let regex: NSRegularExpression = try NSRegularExpression(pattern: patternString, options: NSRegularExpression.Options.caseInsensitive)
        let matches = regex.matches(in: validString, options: NSRegularExpression.MatchingOptions.reportProgress, range: NSMakeRange(0, validString.characters.count))
        return matches.count > 0
    }
    catch {
        return false
    }
}

func secondsToTimeString(_ seconds:Double) ->String{
    let secondInt = Int(seconds)
    let second = secondInt%60
    let minute = secondInt/60
    let secondString = second >= 10 ? "\(second)" : "0\(second)"
    let minuteString = minute >= 10 ? "\(minute)" : "0\(minute)"
    let result = "\(minuteString):\(secondString)"
    return result
}

func htmlEscape(string: String) -> String{
    var result = string
    result = result.replacingOccurrences(of: "&quot;", with: "\"", options: [], range: nil)
    result = result.replacingOccurrences(of: "&#39;", with: "'", options: [], range: nil)
    result = result.replacingOccurrences(of: "&lt;", with: "<", options: [], range: nil)
    result = result.replacingOccurrences(of: "&gt;", with: ">", options: [], range: nil)
    result = result.replacingOccurrences(of: "&ldquo;", with: "\"", options: [], range: nil)
    result = result.replacingOccurrences(of: "&rdquo;", with: "\"", options: [], range: nil)
    result = result.replacingOccurrences(of: "&mdash;", with: "-", options: [], range: nil)
    result = result.replacingOccurrences(of: "&bull;", with: "·", options: [], range: nil)
    result = result.replacingOccurrences(of: "&middot;", with: "·", options: [], range: nil)
    result = result.replacingOccurrences(of: "&hellip;", with: "…", options: [], range: nil)
    result = result.replacingOccurrences(of: "&lsquo;", with: "‘", options: [], range: nil)
    result = result.replacingOccurrences(of: "&rsquo;", with: "’", options: [], range: nil)
    result = result.replacingOccurrences(of: "&rarr;", with: "→", options: [], range: nil)
    result = result.replacingOccurrences(of: "&larr;", with: "←", options: [], range: nil)
    result = result.replacingOccurrences(of: "&plusmn;", with: "±", options: [], range: nil)
    return result
}

func businessUrl() -> String{
    let timestamp = Int(NSDate().timeIntervalSince1970)
    let str = "?cid=\(BusinessChanelID)&timestamp=\(timestamp)&svcNum=\(nmgsUser.phone)&acctNm=\(nmgsUser.phone)_15&sign=\(EncodeController.getBusinessSign(timestamp))"
    return str
}
