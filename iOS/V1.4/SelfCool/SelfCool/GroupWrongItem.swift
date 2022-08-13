//
//  GroupWrongItem.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/6.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController{
    func groupItemShow(index: Int){
        let contentPaddingTop : CGFloat = 10
        let scrollButtonHeight : CGFloat = 25
        
        let item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        let childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.contentSize = CGSize(width: screenWidth, height: itemScrollViewHeight)
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        
        let contentScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth, itemScrollViewHeight / 3 - scrollButtonHeight))
        contentScrollView.tag = ItemViewTag.groupContentScrollViewTag()
        contentScrollView.showsHorizontalScrollIndicator = false
        contentScrollView.showsVerticalScrollIndicator = false
        //题干
        let contentView = ContentViewShow(item,viewController: self)
        contentView.frame = CGRectMake(0, contentPaddingTop , screenWidth, contentView.frame.height)
        contentScrollView.addSubview(contentView)
        
        contentScrollView.contentSize = contentView.frame.size
        childScrollView.addSubview(contentScrollView)
        
        let childrenScrollView = ChildrenScrollView(frame: CGRectMake(0, itemScrollViewHeight / 3, screenWidth, itemScrollViewHeight / 3 * 2))
        childrenScrollView.delegate = self
        childScrollView.addSubview(childrenScrollView)
        if item.children!.count > 0{
            for i in 0 ..< item.children!.count{
                if item.children![i].modelType == 1{
                    childSingleChoiceWrongItem(item.children![i] , parent: childrenScrollView)
                }else if item.children![i].modelType == 3{
                    childJudgeWrongItem(item.children![i] , parent: childrenScrollView)
                }else if item.children![i].modelType == 5{
                    childMultipleChoiceWrongItem(item.children![i], parent: childrenScrollView)
                }
            }
        }
        childrenScrollView.contentSize = CGSize(width: screenWidth * CGFloat(item.children!.count), height:  childrenScrollView.frame.height)
        
        let scrollButton = UIButton(frame: CGRect(x: 0, y: childrenScrollView.frame.origin.y - scrollButtonHeight, width: screenWidth, height: scrollButtonHeight))
        scrollButton.tag = ItemViewTag.groupScrollButtonTag()
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Normal)
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Highlighted)
        let panGesture = UIPanGestureRecognizer(target: self, action: "scrollButton:")
        scrollButton.addGestureRecognizer(panGesture)
        childScrollView.addSubview(scrollButton)
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }
    
    func scrollButton(sender: UIPanGestureRecognizer){
        if sender.numberOfTouches() > 0{
            let translation : CGPoint = sender.locationOfTouch(0, inView: itemScrollView)
            let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let scrollButton = childScrollView.viewWithTag(ItemViewTag.groupScrollButtonTag()) as! UIButton
            if translation.y > 0 && translation.y < itemScrollViewHeight - scrollButton.frame.height{
                scrollButton.frame = CGRectMake(0, translation.y, screenWidth, scrollButton.frame.height)
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let contentScrollView = childScrollView.viewWithTag(ItemViewTag.groupContentScrollViewTag()) as! UIScrollView
                contentScrollView.frame.size = CGSize(width: screenWidth, height: translation.y)
                childrenScrollView.frame = CGRectMake(0, translation.y + scrollButton.frame.height, screenWidth, itemScrollViewHeight - (translation.y + scrollButton.frame.height))
                childrenScrollView.contentSize = CGSize(width: childrenScrollView.contentSize.width, height: childrenScrollView.frame.height)
                for i in 0 ..< globalItemList[page].children!.count{
                    let groupChildScrollView = childrenScrollView.viewWithTag((i+1) * ItemViewTag.groupViewTagBase() + childScrollView.tag) as! UIScrollView
                    if globalItemList[page].children![i].modelType == 5{
                        let submitButton = groupChildScrollView.viewWithTag(ItemViewTag.submitButtonTag()) as! UIButton
                        let groupMultipleScrollView = groupChildScrollView.viewWithTag(ItemViewTag.groupMultipleScrollViewTag()) as! UIScrollView
                        
                        groupChildScrollView.frame.size = childrenScrollView.frame.size
                        groupChildScrollView.contentSize = groupChildScrollView.frame.size
                        if submitButton.hidden{
                            groupMultipleScrollView.frame = CGRectMake(0, 0, screenWidth, groupChildScrollView.frame.height)
                        }else{
                            submitButton.frame = CGRectMake(0, groupChildScrollView.frame.height - submitButtonHeight, screenWidth, submitButtonHeight)
                            groupMultipleScrollView.frame = CGRectMake(0, 0, screenWidth, groupChildScrollView.frame.height - submitButtonHeight)
                        }
                    }else{
                        groupChildScrollView.frame.size = childrenScrollView.frame.size
                    }
                }
            }
        }
    }
}