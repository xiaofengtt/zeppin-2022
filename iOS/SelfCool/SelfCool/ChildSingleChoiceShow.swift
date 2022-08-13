//
//  ChildSingleChoiceShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController{
    func childSingleChoiceShow(item: ItemModel , parent: UIScrollView){
        let index = item.index
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index - 1), 0, screenWidth , parent.frame.height))
        childScrollView.tag = parent.superview!.tag + index * ItemViewTag.groupViewTagBase()
        
        //题型 来源 Label
        var tagView = TagViewShow(item , true)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item , nil)
        contentView.frame = CGRectMake(0, tagView.frame.height , screenWidth, contentView.frame.height)
        childScrollView.addSubview(contentView)
        
        
        parent.addSubview(childScrollView)
    }
}