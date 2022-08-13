//
//  ChildWrittenResponseShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/26.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UnstandardItemViewController{
    func childWrittenResponseShow(item: ItemModel , parent: UIScrollView){
        let index = item.index
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index - 1), 0, screenWidth , parent.frame.height))
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        childScrollView.tag = parent.superview!.tag + index * ItemViewTag.groupViewTagBase()
        
        //题型 来源 Label
        var tagView = TagViewShow(item , true)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item,self)
        contentView.frame = CGRectMake(0, tagView.frame.height , screenWidth, contentView.frame.height)
        childScrollView.addSubview(contentView)
        
        //解析按钮
        var analysisButtonView = UIView(frame: CGRectMake(0, tagView.frame.height  + contentView.frame.height, screenWidth, analysisButtonPaddingTop + analysisButtonFontSize ))
        var analysisButton = UIButton(frame: CGRectMake(paddingLeft, analysisButtonPaddingTop - 6, analysisButtonFontSize * 6, analysisButtonFontSize + 12))
        analysisButton.setImage(UIImage(named: "analysis_button"), forState: UIControlState.Normal)
        analysisButton.setImage(UIImage(named: "analysis_button_highlight"), forState: UIControlState.Highlighted)
        analysisButton.addTarget(self, action: "childAnalysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag = ItemViewTag.analysisButtonTag()
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + analysisButtonView.frame.height + paddingBottom)
        parent.addSubview(childScrollView)
    }
    
    func childAnalysisButton(sender : UIButton){
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var childScrollView = sender.superview?.superview as! UIScrollView
        let childPage = childScrollView.tag / ItemViewTag.groupViewTagBase()
        var item = globalItemList[page].children![childPage-1]
        sender.hidden = true
        
        //解析
        var analysisView = AnalysisViewShow(item, false, self)
        analysisView.frame = CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height, screenWidth, analysisView.frame.height)
        childScrollView.addSubview(analysisView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
        AnalysisScrollToCenter(analysisView , childScrollView)
        if item.answer.completeType == -1{
            item.answer.completeType = 0
            var itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page + childPage * ItemViewTag.groupViewTagBase()) as! UIButton
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            
            var infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabelUpdate(infoLabel)
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "flag" : String(0)])
            httpController.getNSDataByParams("unstandardMark", paramsDictionary: itemParams)
        }
    }
}