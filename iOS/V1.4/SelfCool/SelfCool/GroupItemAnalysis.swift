//
//  GroupItemAnalysis.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnalysisViewController{
    func groupItemAnalysis(index: Int ,itemList: [ItemModel], scrollView: UIScrollView){
        let contentPaddingTop : CGFloat = 10
        let scrollButtonHeight : CGFloat = 25
        
        let item = itemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        let childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , scrollView.frame.height))
        childScrollView.contentSize = CGSize(width: screenWidth, height: scrollView.frame.height)
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        
        let contentScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth, scrollView.frame.height / 3 - scrollButtonHeight))
        contentScrollView.tag = ItemViewTag.groupContentScrollViewTag()
        contentScrollView.showsHorizontalScrollIndicator = false
        contentScrollView.showsVerticalScrollIndicator = false
        //题干
        let contentView = ContentViewShow(item,viewController: self)
        contentView.frame = CGRectMake(0, contentPaddingTop , screenWidth, contentView.frame.height)
        contentScrollView.addSubview(contentView)
        
        contentScrollView.contentSize = contentView.frame.size
        childScrollView.addSubview(contentScrollView)
        
        let childrenScrollView = ChildrenScrollView(frame: CGRectMake(0, scrollView.frame.height / 3, screenWidth, scrollView.frame.height / 3 * 2))
        childrenScrollView.delegate = self
        childScrollView.addSubview(childrenScrollView)
        if item.children!.count > 0{
            for i in 0 ..< item.children!.count{
                 if item.children![i].modelType == 1{
                    childSingleChoiceAnalysis(i,item: item.children![i] , parent: childrenScrollView)
                }else if item.children![i].modelType == 3{
                    childJudgeAnalysis(i, item: item.children![i], parent: childrenScrollView)
                }else if item.children![i].modelType == 5{
                    childMultipleChoiceAnalysis(i, item: item.children![i], parent: childrenScrollView)
                }
            }
        }
        childrenScrollView.contentSize = CGSize(width: screenWidth * CGFloat(item.children!.count), height:  childrenScrollView.frame.height)
        
        let scrollButton = UIButton(frame: CGRect(x: 0, y: childrenScrollView.frame.origin.y - scrollButtonHeight, width: screenWidth, height: scrollButtonHeight))
        scrollButton.tag = ItemViewTag.groupScrollButtonTag()
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Normal)
        scrollButton.setBackgroundImage(UIImage(named: "group_scroll_button"), forState: UIControlState.Highlighted)
        if scrollView == analysisScrollView{
            let panGesture = UIPanGestureRecognizer(target: self, action: "allScrollButton:")
            scrollButton.addGestureRecognizer(panGesture)
        }else{
            let panGesture = UIPanGestureRecognizer(target: self, action: "failScrollButton:")
            scrollButton.addGestureRecognizer(panGesture)
        }
        childScrollView.addSubview(scrollButton)
        
        scrollView.addSubview(childScrollView)
    }
    
    func allScrollButton(sender: UIPanGestureRecognizer){
        if sender.numberOfTouches() > 0{
            let translation : CGPoint = sender.locationOfTouch(0, inView: analysisScrollView)
            let page = Int(floor((analysisScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childScrollView = analysisScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let scrollButton = childScrollView.viewWithTag(ItemViewTag.groupScrollButtonTag()) as! UIButton
            if translation.y > 0 && translation.y < analysisScrollView.frame.height - scrollButton.frame.height{
                scrollButton.frame = CGRectMake(0, translation.y, screenWidth, scrollButton.frame.height)
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let contentScrollView = childScrollView.viewWithTag(ItemViewTag.groupContentScrollViewTag()) as! UIScrollView
                contentScrollView.frame.size = CGSize(width: screenWidth, height: translation.y)
                childrenScrollView.frame = CGRectMake(0, translation.y + scrollButton.frame.height, screenWidth, analysisScrollView.frame.height - (translation.y + scrollButton.frame.height))
                childrenScrollView.contentSize = CGSize(width: childrenScrollView.contentSize.width, height: childrenScrollView.frame.height)
                for i in 0 ..< globalItemList[page].children!.count{
                    let groupChildScrollView = childrenScrollView.viewWithTag((i+1) * ItemViewTag.groupViewTagBase() + childScrollView.tag) as! UIScrollView
                    groupChildScrollView.frame.size = childrenScrollView.frame.size
                }
            }
        }
    }
    
    func failScrollButton(sender: UIPanGestureRecognizer){
        if sender.numberOfTouches() > 0{
            let translation : CGPoint = sender.locationOfTouch(0, inView: analysisFailScrollView)
            let page = Int(floor((analysisFailScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childScrollView = analysisFailScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let scrollButton = childScrollView.viewWithTag(ItemViewTag.groupScrollButtonTag()) as! UIButton
            if translation.y > 0 && translation.y < analysisFailScrollView.frame.height - scrollButton.frame.height{
                scrollButton.frame = CGRectMake(0, translation.y, screenWidth, scrollButton.frame.height)
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let contentScrollView = childScrollView.viewWithTag(ItemViewTag.groupContentScrollViewTag()) as! UIScrollView
                contentScrollView.frame.size = CGSize(width: screenWidth, height: translation.y)
                childrenScrollView.frame = CGRectMake(0, translation.y + scrollButton.frame.height, screenWidth, analysisFailScrollView.frame.height - (translation.y + scrollButton.frame.height))
                childrenScrollView.contentSize = CGSize(width: childrenScrollView.contentSize.width, height: childrenScrollView.frame.height)
                for i in 0 ..< wrongItemList[page].children!.count{
                    let groupChildScrollView = childrenScrollView.viewWithTag((i+1) * ItemViewTag.groupViewTagBase() + childScrollView.tag) as! UIScrollView
                    groupChildScrollView.frame.size = childrenScrollView.frame.size
                }
            }
        }
    }
}
