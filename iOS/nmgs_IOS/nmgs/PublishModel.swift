//
//  PublishModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class PublishModel : NSObject{
    var id: String
    var title: String
    var videoId: String
    var coverUrl: String
    var videoUrl: String
    
    override init() {
        self.id = ""
        self.title = ""
        self.videoId = ""
        self.coverUrl = ""
        self.videoUrl = ""
    }
    
    class func getPublishListByDataList(dataList : NSArray) -> [PublishModel]{
        var thisPublishList : [PublishModel] = []
        for i in 0 ..< dataList.count{
            let data = dataList[i] as! NSDictionary
            let publish = PublishModel()
            publish.id = data.object(forKey: "publishId") as! String
            publish.title = data.object(forKey: "title") as! String
            publish.videoId = data.object(forKey: "id") as! String
            publish.videoUrl = data.object(forKey: "videoURL") as! String
            publish.coverUrl = data.object(forKey: "coverURL") as! String
            thisPublishList.append(publish)
        }
        return thisPublishList
    }
}
