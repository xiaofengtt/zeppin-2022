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
        let item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        let childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        //题型 来源 Label
        let tagView = TagViewShow(item , hasPage: true)
        childScrollView.addSubview(tagView)
        
        //题干
        let contentView = ContentViewShow(item,viewController: self)
        contentView.frame = CGRectMake(0, tagView.frame.height , screenWidth, contentView.frame.height)
        childScrollView.addSubview(contentView)
        
        //解析按钮
        let analysisButtonView = UIView(frame: CGRectMake(0, tagView.frame.height  + contentView.frame.height, screenWidth, analysisButtonPaddingTop + analysisButtonFontSize ))
        let analysisButton = UIButton(frame: CGRectMake(paddingLeft, analysisButtonPaddingTop - 6, analysisButtonFontSize * 6, analysisButtonFontSize + 12))
        analysisButton.setImage(UIImage(named: "analysis_button"), forState: UIControlState.Normal)
        analysisButton.setImage(UIImage(named: "analysis_button_highlight"), forState: UIControlState.Highlighted)
        analysisButton.addTarget(self, action: "analysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag = ItemViewTag.analysisButtonTag()
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + analysisButtonView.frame.height + paddingBottom)
        childScrollView.makeVerticalScrollEnable()
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }
    
    func analysisButton(sender : UIButton){
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let item = globalItemList[page]
        let childScrollView = sender.superview?.superview as! UIScrollView
        sender.hidden = true
        
        MobClick.event("UnstandardAnalysis")
        
        //解析
        let analysisView = AnalysisViewShow(item, isStandard: false, viewController: self)
        analysisView.frame = CGRectMake(0,  childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height, screenWidth, analysisView.frame.height)
        childScrollView.addSubview(analysisView)

        childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
        childScrollView.addGrayBottomBounceView()
        childScrollView.makeVerticalScrollEnable()
        AnalysisScrollToCenter(analysisView , parentView: childScrollView)
        
        if globalItemList[page].answer.completeType == -1{
            globalItemList[page].answer.completeType = 0
            let itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            
            let infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabelUpdate(infoLabel)
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "flag" : String(0)])
            httpController.getNSDataByParams("unstandardMark", paramsDictionary: itemParams)
        }
    }
}