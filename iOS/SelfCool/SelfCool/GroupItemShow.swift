//
//  GroupItemShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/24.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UnstandardItemViewController{
    func groupItemShow(index: Int){
        let contentPaddingTop : CGFloat = 10
        let scrollButtonHeight : CGFloat = 15
        
        var item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.contentSize = CGSize(width: screenWidth, height: itemScrollViewHeight)
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        
        var contentScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth, itemScrollViewHeight / 3 * 2 - scrollButtonHeight))
        contentScrollView.tag = ItemViewTag.groupContentScrollViewTag()
        contentScrollView.showsHorizontalScrollIndicator = false
        contentScrollView.showsVerticalScrollIndicator = false
        //题干
        var contentView = ContentViewShow(item , nil)
        contentView.frame = CGRectMake(0, contentPaddingTop , screenWidth, contentView.frame.height)
        contentScrollView.addSubview(contentView)
        
        contentScrollView.contentSize = contentView.frame.size
        childScrollView.addSubview(contentScrollView)
        
        var childrenScrollView = UIScrollView(frame: CGRectMake(0, itemScrollViewHeight / 3 * 2, screenWidth, itemScrollViewHeight / 3))
        childrenScrollView.tag = ItemViewTag.groupChildrenScrollViewTag()
        childrenScrollView.showsHorizontalScrollIndicator = false
        childrenScrollView.showsVerticalScrollIndicator = false
        childrenScrollView.pagingEnabled = true
        childrenScrollView.backgroundColor = UIColor.backgroundGray()
        childrenScrollView.delegate = self
        childScrollView.addSubview(childrenScrollView)
        if item.children!.count > 0{
            for i in 0 ..< item.children!.count{
                childWrittenResponseShow(item.children![i] , parent: childrenScrollView)
            }
        }
        childrenScrollView.contentSize = CGSize(width: screenWidth * CGFloat(item.children!.count), height:  childrenScrollView.frame.height)
        
        var scrollButton = UIButton(frame: CGRect(x: 0, y: childrenScrollView.frame.origin.y - scrollButtonHeight, width: screenWidth, height: scrollButtonHeight))
        scrollButton.tag = ItemViewTag.groupScrollButtonTag()
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Normal)
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Highlighted)
        var panGesture = UIPanGestureRecognizer(target: self, action: "handlePanGesture:")
        scrollButton.addGestureRecognizer(panGesture)
        childScrollView.addSubview(scrollButton)
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }
    
    func handlePanGesture(sender: UIPanGestureRecognizer){
        if sender.numberOfTouches() > 0{
            var translation : CGPoint = sender.locationOfTouch(0, inView: itemScrollView)
            let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var scrollButton = childScrollView.viewWithTag(ItemViewTag.groupScrollButtonTag()) as! UIButton
            if translation.y > 0 && translation.y < itemScrollViewHeight - scrollButton.frame.height{
                scrollButton.frame = CGRectMake(0, translation.y, screenWidth, scrollButton.frame.height)
                var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! UIScrollView
                var contentScrollView = childScrollView.viewWithTag(ItemViewTag.groupContentScrollViewTag()) as! UIScrollView
                contentScrollView.frame.size = CGSize(width: screenWidth, height: translation.y)
                childrenScrollView.frame = CGRectMake(0, translation.y + scrollButton.frame.height, screenWidth, itemScrollViewHeight - (translation.y + scrollButton.frame.height))
                childrenScrollView.contentSize = CGSize(width: childrenScrollView.contentSize.width, height: childrenScrollView.frame.height)
                for i in 0 ..< globalItemList[page].children!.count{
                    var groupChildScrollView = childrenScrollView.viewWithTag((i+1) * ItemViewTag.groupViewTagBase() + childScrollView.tag) as! UIScrollView
                    groupChildScrollView.frame.size = childrenScrollView.frame.size
                }
            }
        }
    }
}