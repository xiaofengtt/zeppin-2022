//
//  LuckyFaqsModel.swift
//  lucky
//  Faq对象
//  Created by Farmer Zhu on 2021/1/18.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

//结构类型
enum LuckyFaqsType {
    //头
    case header
    //标题
    case title
    //内容 不可收起
    case content
    //问题 可收起
    case openable
}

class LuckyFaqsModel : NSObject{
    var type: LuckyFaqsType
    var titleView: UIView
    var contentView: UIView?
    //可收起的 是否已展开
    var isOpen: Bool = false
    
    init(type: LuckyFaqsType, titleView: UIView, contentView: UIView?) {
        self.type = type
        self.titleView = titleView
        self.contentView = contentView
    }
}
