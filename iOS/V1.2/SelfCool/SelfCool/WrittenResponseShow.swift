//
//  WrittenResponseShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UnstandardItemViewController{
    func writtenResponseShow(index: Int){
        var item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        
        //题型 来源 Label
        var tagView = TagViewShow(item , index)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item , nil)
        contentView.frame = CGRectMake(0, tagView.frame.height , screenWidth, contentView.frame.height)
        childScrollView.addSubview(contentView)
        
        //解析按钮
        var analysisButtonView = UIView(frame: CGRectMake(0, tagView.frame.height  + contentView.frame.height, screenWidth, analysisButtonPaddingTop + analysisButtonFontSize ))
        var analysisButton = UIButton(frame: CGRectMake(paddingLeft, analysisButtonPaddingTop - 6, analysisButtonFontSize * 6, analysisButtonFontSize + 12))
        analysisButton.setImage(UIImage(named: "analysis_button"), forState: UIControlState.Normal)
        analysisButton.setImage(UIImage(named: "analysis_button_highlight"), forState: UIControlState.Highlighted)
        analysisButton.addTarget(self, action: "analysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag = ItemViewTag.analysisButtonBase() + index
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        //解析
        var analysisView = AnalysisViewShow(item, false)
        analysisView.frame = CGRectMake(0,  tagView.frame.height +  contentView.frame.height + analysisButtonView.frame.height, screenWidth, analysisView.frame.height)
        childScrollView.addSubview(analysisView)
        analysisView.hidden = true
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + analysisButtonView.frame.height + paddingBottom)
        if childScrollView.contentSize.height <= itemScrollViewHeight{
            childScrollView.contentSize.height = itemScrollViewHeight + 1
        }
        
        var grayBounceView = UIView(frame: CGRectMake(0, tagView.frame.height + contentView.frame.height + analysisButtonView.frame.height + analysisView.frame.height, screenWidth, 1000))
        grayBounceView.backgroundColor = UIColor.backgroundGray()
        grayBounceView.tag = ItemViewTag.grayBounceViewTag()
        childScrollView.addSubview(grayBounceView)
        grayBounceView.hidden = true
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }
    
    func analysisButton(sender : UIButton){
        var childScrollView = sender.superview?.superview as! UIScrollView
        var analysisView = childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!
        let page = sender.tag - ItemViewTag.analysisButtonBase()
        var item = globalItemList[page]
        sender.hidden = true
        analysisView.hidden = false
        childScrollView.viewWithTag(ItemViewTag.grayBounceViewTag())?.hidden = false

        childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
        if childScrollView.contentSize.height <= itemScrollViewHeight{
            childScrollView.contentSize.height = itemScrollViewHeight + 1
        }
        if childScrollView.frame.height < childScrollView.contentSize.height{
            if analysisView.frame.height > childScrollView.frame.height{
                childScrollView.scrollRectToVisible(CGRectMake(0, analysisView.frame.origin.y
                    , screenWidth, childScrollView.frame.height), animated: true)
            }else{
                childScrollView.scrollRectToVisible(CGRectMake(0, childScrollView.contentSize.height - childScrollView.frame.height, screenWidth, childScrollView.frame.height), animated: true)
            }
        }
        if globalItemList[page].answer.completeType == -1{
            globalItemList[page].answer.completeType = 0
            var itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            
            var infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabelUpdate(infoLabel)
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "flag" : String(0)])
            httpController.getNSDataByParams("unstandardMark", paramsDictionary: itemParams)
        }
    }
}