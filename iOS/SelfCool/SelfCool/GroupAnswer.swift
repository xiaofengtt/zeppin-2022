//
//  GroupAnswer.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController{
    func groupAnswer(index: Int){
        let contentPaddingTop : CGFloat = 10
        let scrollButtonHeight : CGFloat = 50
        
        var item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.contentSize = CGSize(width: screenWidth, height: itemScrollViewHeight)
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        
        var contentScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth, itemScrollViewHeight / 3 * 2))
        contentScrollView.tag = ItemViewTag.groupContentScrollViewTag()
        //题干
        var contentView = ContentViewShow(item , nil)
        contentView.frame = CGRectMake(0, contentPaddingTop , screenWidth, contentView.frame.height)
        contentScrollView.addSubview(contentView)
        
        contentScrollView.contentSize = contentView.frame.size
        childScrollView.addSubview(contentScrollView)
        
        var childrenScrollView = UIScrollView(frame: CGRectMake(0, itemScrollViewHeight / 3 * 2, screenWidth, itemScrollViewHeight / 3))
        childrenScrollView.tag = ItemViewTag.groupChildrenScrollViewTag()
        childrenScrollView.pagingEnabled = true
        childrenScrollView.backgroundColor = UIColor.backgroundGray()
        childrenScrollView.delegate = self
        childScrollView.addSubview(childrenScrollView)
        if item.children!.count > 0{
            for i in 0 ..< item.children!.count{
                childSingleChoiceShow(item.children![i] , parent: childrenScrollView)
            }
        }
        childrenScrollView.contentSize = CGSize(width: screenWidth * CGFloat(item.children!.count), height:  childrenScrollView.frame.height)
        
        var scrollButton = UIButton(frame: CGRect(x: 0, y: childrenScrollView.frame.origin.y - scrollButtonHeight, width: screenWidth, height: scrollButtonHeight))
        scrollButton.backgroundColor = UIColor.blackColor().colorWithAlphaComponent(0.25)
        scrollButton.addTarget(self, action: "scrollButton", forControlEvents: UIControlEvents.TouchDragOutside)
        childScrollView.addSubview(scrollButton)
        
        itemScrollView.addSubview(childScrollView)
    }
    
    func submitAnswer(sender:UIButton , withEvent : UIEvent) {
        println(withEvent)
        sender.frame = CGRect(x: 0, y: 0, width: screenWidth, height: sender.frame.height)
    }
}