//
//  LuckyFrontUserCommentModel.swift
//  lucky
//  用户评论
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserCommentModel : NSObject{
    var uuid: String
    var frontUser: String
    var frontUserShowId: Int
    var orderId: String
    var goodsIssue: String
    var image: String
    var video: String
    var detail: String
    var status: String
    var createtime: Int
    
    var operators: String
    var operattime: Int
    var reason: String
    var orderNum: String
    
    //评价人
    var nickName: String
    var imageUrl: String
    
    //奖品
    var title: String
    var issueNum: Int
    var coverImg: String
    
    //资源列表
    var imageList: Array<NSDictionary>
    var videoList: Array<NSDictionary>
    
    //投注数
    var buyCount: Int
    //获奖时间
    var winningTime: Int
    //幸运号
    var luckynum: Int
    
    //动态cell高度
    var cellHeight: CGFloat
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.frontUserShowId = Int.valueOf(any: data.value(forKey: "frontUserShowId"))
        self.orderId = String.valueOf(any: data.value(forKey: "orderId"))
        self.goodsIssue = String.valueOf(any: data.value(forKey: "goodsIssue"))
        self.image = String.valueOf(any: data.value(forKey: "image"))
        self.video = String.valueOf(any: data.value(forKey: "video"))
        self.detail = String.valueOf(any: data.value(forKey: "detail"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
        self.createtime = Int.valueOf(any: data.value(forKey: "createtime"))
        
        self.operators = String.valueOf(any: data.value(forKey: "operators"))
        self.operattime = Int.valueOf(any: data.value(forKey: "operattime"))
        self.reason = String.valueOf(any: data.value(forKey: "reason"))
        
        self.orderNum = String.valueOf(any: data.value(forKey: "orderNum"))
        self.nickName = String.valueOf(any: data.value(forKey: "nickName"))
        self.imageUrl = String.valueOf(any: data.value(forKey: "imageUrl"))
        self.title = String.valueOf(any: data.value(forKey: "title"))
        self.issueNum = Int.valueOf(any: data.value(forKey: "issueNum"))
        self.coverImg = String.valueOf(any: data.value(forKey: "coverImg"))
        
        if(data.value(forKey: "imageList") != nil && data.value(forKey: "imageList").debugDescription != "Optional(<null>)"){
            self.imageList = data.value(forKey: "imageList") as! Array<NSDictionary>
        }else{
            self.imageList = []
        }
        if(data.value(forKey: "videoList") != nil && data.value(forKey: "videoList").debugDescription != "Optional(<null>)"){
            self.videoList = data.value(forKey: "videoList") as! Array<NSDictionary>
        }else{
            self.videoList = []
        }
        
        self.buyCount = Int.valueOf(any: data.value(forKey: "buyCount"))
        self.winningTime = Int.valueOf(any: data.value(forKey: "winningTime"))
        self.luckynum = Int.valueOf(any: data.value(forKey: "luckynum"))
        
        self.cellHeight = 0
    }
}
