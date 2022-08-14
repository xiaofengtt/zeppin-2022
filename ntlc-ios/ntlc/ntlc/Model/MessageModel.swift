//
//  MessageModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/18.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class MessageModel : NSObject{
    var uuid: String
    var infoTitle: String
    var infoText: String
    var status: String
    var flagRead: Bool
    var createtime: Int
    var createtimeCN: String
    
    override init() {
        self.uuid = ""
        self.infoTitle = ""
        self.infoText = ""
        self.status = ""
        self.flagRead = false
        self.createtime = 0
        self.createtimeCN = ""
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.infoTitle = String.valueOf(any: data.value(forKey: "infoTitle"))
        self.infoText = String.valueOf(any: data.value(forKey: "infoText"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.flagRead = Bool.valueOf(any: data.value(forKey: "flagRead"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.createtimeCN = MessageModel.timeStampToMessageTime(timeStamp: createtime)
    }
    
    static func timeStampToMessageTime(timeStamp: Int) -> String {
        let timeSta: TimeInterval = NSString(string: "\(timeStamp/1000)").doubleValue
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy/MM/dd hh:mm"
        
        let date = Date(timeIntervalSince1970: timeSta)
        
        return dfmatter.string(from: date)
    }
}
