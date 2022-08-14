//
//  NewsComment.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class NewsCommentModel : NSObject{
    var uuid: String
    var news: String
    var parent: NewsCommentModel?
    var content: String
    var status: String
    var creator: String
    var creatorName: String
    var createtime: Int
    var cellHeight: CGFloat
    
    override init() {
        self.uuid = ""
        self.news = ""
        self.content = ""
        self.status = ""
        self.creator = ""
        self.creatorName = ""
        self.createtime = 0
        self.cellHeight = 0
    }
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.news = String.valueOf(any: data.value(forKey: "newspublish"))
        if(data.value(forKey: "parent").debugDescription != "Optional(<null>)"){
            self.parent = NewsCommentModel(data: data.value(forKey: "parent") as! NSDictionary)
        }
        self.content = String.valueOf(any: data.value(forKey: "content"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.creator = String.valueOf(any: data.value(forKey: "creator"))
        self.creatorName = String.valueOf(any: data.value(forKey: "creatorName"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        self.cellHeight = 0
    }
}
