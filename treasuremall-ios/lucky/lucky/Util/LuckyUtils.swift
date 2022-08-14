//
//  LuckyUtils.swift
//  lucky
//
//  Created by Farmer Zhu on 2020/8/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUtils{
    //校验密码格式
    static func checkPassword(password: String) -> Bool{
        let regex = ["^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$"]
        return NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: password)
    }
    
    //是否为手机号格式
    static func checkMobileCode(code: String) -> Bool{
        let regex = ["(\\d){4}"]
        return NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: code)
    }
    
    //是否为Email格式
    static func checkEmail(email: String) -> Bool{
        let regex = ["[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"]
        return NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: email)
    }
    
    //是否为金额格式
    static func checkMoney(money: String) -> Bool{
        let regex = ["^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"]
        return NSPredicate(format: "SELF MATCHES %@", argumentArray: regex).evaluate(with: money)
    }
    
    //时间戳格式显示
    static func timestampFormat(timestamp: Int, format: String)->String {
        let timeSta: TimeInterval = TimeInterval(timestamp/1000)
        let dfmatter = DateFormatter()
        dfmatter.dateFormat = format
        let date = Date(timeIntervalSince1970: timeSta)
        return dfmatter.string(from: date)
    }
    
    //所需的各种类型格式化
    static func coinFormat(amount: Double) -> String{
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 2
        var string = format.string(from: NSNumber(value: amount))
        if(string == nil || string!.isEmpty){
            string = "0"
        }
        return string!
    }
    
    static func coinFullFormat(amount: Double) -> String {
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 2
        format.usesGroupingSeparator = true
        format.groupingSeparator = ","
        format.groupingSize = 3
        var string = format.string(from: NSNumber(value: amount))
        if(string == nil || string!.isEmpty){
            string = "0"
        }
        return string!
    }
    
    static func moneyFormat(amount: Double) -> String{
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        var string = format.string(from: NSNumber(value: amount))
        if(string == nil || string!.isEmpty){
            string = "0.00"
        }
        return string!
    }
    
    static func moneyFullFormat(amount: Double) -> String{
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        format.usesGroupingSeparator = true
        format.groupingSeparator = ","
        format.groupingSize = 3
        var string = format.string(from: NSNumber(value: amount))
        if(string == nil || string!.isEmpty){
            string = "0.00"
        }
        return string!
    }
    
    static func currencyFormat(amount: Double) -> String{
        return String(Int(round(amount)))
    }
    
    static func currencyFullFormat(amount: Double) -> String{
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 2
        format.usesGroupingSeparator = true
        format.groupingSeparator = ","
        format.groupingSize = 3
        var string = format.string(from: NSNumber(value: amount))
        if(string == nil || string!.isEmpty){
            string = "0"
        }
        return string!
    }
    
    static func localCurrencyFormat(amount: Double)-> String{
        let format = NumberFormatter()
        format.roundingMode = NumberFormatter.RoundingMode.halfUp
        format.maximumFractionDigits = 0
        
        var localAmount: Double = amount
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            localAmount = localAmount * Double.valueOf(any: rate)
            symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
        }
        
        var string = format.string(from: NSNumber(value: localAmount))
        if(string == nil || string!.isEmpty){
            string = "\(String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])) 0"
        }
        return "\(symbol) \(string!)"
    }
    
    //手机号加星
    static func getHideMobile(mobile: String) -> String{
        var mobileStr = mobile
        
        if(mobileStr.count > 6){
            mobileStr.replaceSubrange(Range<String.Index>(NSRange(location: 3, length: mobileStr.count - 6), in: mobileStr)!, with: "****")
        }else if(mobileStr.count > 4){
            mobileStr.replaceSubrange(Range<String.Index>(NSRange(location: 2, length: mobileStr.count - 4), in: mobileStr)!, with: "****")
        }
        
        return mobileStr
    }
    
    //获取月份名
    static func getMonthWord(monthNumber: String) -> String{
        switch (monthNumber){
        case "01":
            return NSLocalizedString("January", comment: "")
        case "02":
            return NSLocalizedString("February", comment: "")
        case "03":
            return NSLocalizedString("March", comment: "")
        case "04":
            return NSLocalizedString("April", comment: "")
        case "05":
            return NSLocalizedString("May", comment: "")
        case "06":
            return NSLocalizedString("June", comment: "")
        case "07":
            return NSLocalizedString("July", comment: "")
        case "08":
            return NSLocalizedString("August", comment: "")
        case "09":
            return NSLocalizedString("September", comment: "")
        case "10":
            return NSLocalizedString("October", comment: "")
        case "11":
            return NSLocalizedString("November", comment: "")
        case "12":
            return NSLocalizedString("December", comment: "")
        default:
            return ""
        }
    }
    
    //获取交易记录类型名称
    static func getHistoryType(type: String) -> String{
        switch (type){
        case "system_add":
            return "Earned"
        case "system_sub":
            return "Deducted"
        case "system_give":
            return "Earned"
        case "user_recharge":
            return "Top Up"
        case "user_withdraw":
            return "Withdraw"
        case "user_payment":
            return "Payment"
        case "user_exchange":
            return "Exchange"
        case "user_register":
            return "Register"
        case "user_scorelottery":
            return "Earned"
        case "user_buyfree":
            return "Earned"
        case "user_checkin":
            return "Earned"
        case "user_delivery":
            return "Deducted"
        case "user_recommend":
            return ""
        default:
            return ""
        }
    }
    
    //获取PK队伍名
    static func getTeamName(value: String) -> String{
        if(value == "lucky"){
            return NSLocalizedString("Red Team", comment: "")
        }else{
            return NSLocalizedString("Blue Team", comment: "")
        }
    }
    
    //进制转换
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
    
    //拼装主包FAQ内容
    static func createFaqsList() -> Array<LuckyFaqsModel>{
        var array: Array<LuckyFaqsModel> = []
        
        let headView = UIImageView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 88 * screenScale))
        headView.image = UIImage(named: "image_fags_header")
        let headBottomLine = CALayer()
        headBottomLine.frame = CGRect(x: 0, y: headView.frame.height - 1, width: headView.frame.width, height: 1)
        headBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        headView.layer.addSublayer(headBottomLine)
        array.append(LuckyFaqsModel(type: LuckyFaqsType.header, titleView: headView, contentView: nil))
        
        let titleView1 = LuckyFaqsTitleView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), title: NSLocalizedString("faqs_title2_1", comment: ""), image: UIImage(named: "image_fags_title1"))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.title, titleView: titleView1, contentView: nil))
        
        let normalView1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_normal_1_1", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.content, titleView: normalView1, contentView: nil))
        
        let conetentTitleView_1_1 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_1_1q", comment: ""))
        let contentAnswerView_1_1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_1_1.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_1_1a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_1_1, contentView: contentAnswerView_1_1))
        
        let conetentTitleView_1_2 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_1_2q", comment: ""))
        let contentAnswerView_1_2 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_1_2.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_1_2a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_1_2, contentView: contentAnswerView_1_2))
        
        let titleView2 = LuckyFaqsTitleView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), title: NSLocalizedString("faqs_title2_2", comment: ""), image: UIImage(named: "image_fags_title2"))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.title, titleView: titleView2, contentView: nil))
        
        let conetentTitleView_2_1 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_2_1q", comment: ""))
        let contentAnswerView_2_1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_2_1.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_2_1a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_2_1, contentView: contentAnswerView_2_1))
        
        let conetentTitleView_2_2 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_2_2q", comment: ""))
        let contentAnswerView_2_2 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_2_2.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_2_2a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_2_2, contentView: contentAnswerView_2_2))
        
        let conetentTitleView_2_3 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_2_3q", comment: ""))
        let contentAnswerView_2_3 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_2_3.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_2_3a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_2_3, contentView: contentAnswerView_2_3))
        
        let conetentTitleView_2_4 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_2_4q", comment: ""))
        let contentAnswerView_2_4 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_2_4.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_2_4a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_2_4, contentView: contentAnswerView_2_4))
        
        let titleView3 = LuckyFaqsTitleView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), title: NSLocalizedString("faqs_title2_3", comment: ""), image: UIImage(named: "image_fags_title3"))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.title, titleView: titleView3, contentView: nil))
        
        let conetentTitleView_3_1 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_1q", comment: ""))
        let contentAnswerView_3_1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_1.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_1a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_1, contentView: contentAnswerView_3_1))
        
        let conetentTitleView_3_2 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_2q", comment: ""))
        let contentAnswerView_3_2 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_2.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_2a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_2, contentView: contentAnswerView_3_2))
        
        let conetentTitleView_3_3 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_3q", comment: ""))
        let contentAnswerView_3_3 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_3.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_3a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_3, contentView: contentAnswerView_3_3))
        
        let conetentTitleView_3_4 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_4q", comment: ""))
        let contentAnswerView_3_4 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_4.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_4a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_4, contentView: contentAnswerView_3_4))
        
        let conetentTitleView_3_5 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_5q", comment: ""))
        let contentAnswerView_3_5 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_5.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_5a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_5, contentView: contentAnswerView_3_5))
        
        let conetentTitleView_3_6 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_6q", comment: ""))
        let contentAnswerView_3_6 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_6.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_6a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_6, contentView: contentAnswerView_3_6))
        
        let conetentTitleView_3_7 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_7q", comment: ""))
        let contentAnswerView_3_7 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_7.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_7a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_7, contentView: contentAnswerView_3_7))
        
        let conetentTitleView_3_8 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_8q", comment: ""))
        let contentAnswerView_3_8 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_8.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_8a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_8, contentView: contentAnswerView_3_8))
        
        let conetentTitleView_3_9 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_9q", comment: ""))
        let contentAnswerView_3_9 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_9.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_9a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_9, contentView: contentAnswerView_3_9))
        
        let conetentTitleView_3_10 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_3_10q", comment: ""))
        let contentAnswerView_3_10 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_3_10.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_3_10a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_3_10, contentView: contentAnswerView_3_10))
        
        let titleView4 = LuckyFaqsTitleView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), title: NSLocalizedString("faqs_title2_4", comment: ""), image: UIImage(named: "image_fags_title4"))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.title, titleView: titleView4, contentView: nil))
        
        let conetentTitleView_4_1 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_4_1q", comment: ""))
        let contentAnswerView_4_1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_4_1.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_4_1a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_4_1, contentView: contentAnswerView_4_1))
        
        let conetentTitleView_4_2 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_4_2q", comment: ""))
        let contentAnswerView_4_2 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_4_2.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_4_2a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_4_2, contentView: contentAnswerView_4_2))
        
        let conetentTitleView_4_3 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_4_3q", comment: ""))
        let contentAnswerView_4_3 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_4_3.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_4_3a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_4_3, contentView: contentAnswerView_4_3))
        
        let conetentTitleView_4_4 = LuckyFaqsQuestionView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 0), question: NSLocalizedString("faqs_content2_4_4q", comment: ""))
        let contentAnswerView_4_4 = LuckyFaqsContentView(frame: CGRect(x: 0, y: conetentTitleView_4_4.frame.height, width: headView.frame.width, height: 0), content: NSLocalizedString("faqs_content2_4_4a", comment: ""))
        array.append(LuckyFaqsModel(type: LuckyFaqsType.openable, titleView: conetentTitleView_4_4, contentView: contentAnswerView_4_4))
        return array
    }
}
