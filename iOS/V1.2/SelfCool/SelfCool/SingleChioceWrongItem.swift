//
//  SingleChioceWrongItem.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/25.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    
    func analysisButton(sender : UIButton){
        var childScrollView = sender.superview?.superview as! UIScrollView
        let item = globalItemList[sender.tag - ItemViewTag.analysisButtonBase()]
        let correctIndex = item.answer.results[0].inx.toInt()! - 1
        var correctOption = childScrollView.viewWithTag(ItemViewTag.optionIconTagBase() + correctIndex) as! UIButton

        sender.hidden = true
        correctOption.layer.borderWidth = 0
        correctOption.backgroundColor = UIColor.mainColor()
        correctOption.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        
        //解析
        var analysisView = AnalysisViewShow(item, true)
        analysisView.frame = CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height, screenWidth, analysisView.frame.height)
        childScrollView.addSubview(analysisView)
        
        var grayBounceView = UIView(frame: CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height + analysisView.frame.height, screenWidth, 1000))
        grayBounceView.backgroundColor = UIColor.backgroundGray()
        grayBounceView.tag = ItemViewTag.grayBounceViewTag()
        childScrollView.addSubview(grayBounceView)
        
        for i in 0 ..< item.answer.options.count{
            var button = childScrollView.viewWithTag(ItemViewTag.optionButtonTagBase() + i) as! UIButton
            button.enabled = false
        }
        childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
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
    }
    
    func singleChioceButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let item = globalItemList[page]
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        var optionView = sender.superview as UIView?
        var childScrollView = optionView!.superview?.superview as! UIScrollView
        var itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
        item.answer.userResults = []
        item.answer.userResults.append(item.answer.options[optionIndex])
        if optionIndex + 1 == item.answer.results[0].inx.toInt()!{
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            item.answer.completeType = 1
            item.userCorrectCount++
        }else{
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.redColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.redColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            item.answer.completeType = 0
            item.userWrongCount++
        }
        
        var infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
        infoLabelUpdate(infoLabel)
        
        var optionButton = optionView!.viewWithTag(ItemViewTag.optionIconTagBase() + optionIndex) as! UIButton
        optionButton.layer.borderWidth = 0
        optionButton.backgroundColor = UIColor.redColor()
        optionButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        var analysisButton = childScrollView.viewWithTag(ItemViewTag.analysisButtonBase() + page) as! UIButton
        analysisButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
        
        let submitParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "item.blankInx" : String(item.blankInx)  , "isAnswered" : String(1) , "completeType" : String(item.answer.completeType) , "reference" : item.answer.userResults[0].inx , "content" : item.answer.userResults[0].content])
        httpController.getNSDataByParams("wrongBookAnswer", paramsDictionary: submitParams)
    }
    
    func singleChioceShow(index: Int){
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
        
        //选项
        var optionsView = UIView()
        var optionsHeight : [CGFloat] = []
        var optionButtons : [UIButton] = []
        for optionIndex in 0 ..< item.answer.options.count{
            var optionView = UIView()
            var optionIcon = UIButton(frame: CGRectMake(paddingLeft, optionPadding / 2, optionIconSize, optionIconSize))
            optionIcon.userInteractionEnabled = false
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = optionIconSize / 2
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle( NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            optionIcon.tag = ItemViewTag.optionIconTagBase() + optionIndex
            optionButtons.append(optionIcon)
            
            var optionLabel = UILabel()
            optionLabel.font = UIFont(name: contentFontName, size: fontSize)
            optionLabel.tag = ItemViewTag.optionLabelTagBase() + optionIndex
            optionLabel.numberOfLines = 0
            var optionString = HtmlToString(item.answer.options[optionIndex].content)
            var optionStringAttributed = NSMutableAttributedString(string: optionString)
            optionStringAttributed.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString(string: optionString).length))
            optionLabel.attributedText = optionStringAttributed
            let optionSize = NSString(string: optionString).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft - optionLabelPaddingIcon - optionIconSize, height: CGFloat.max), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : optionLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
            let optionHeight = optionSize.height > optionIconSize ? optionSize.height : optionIconSize
            optionLabel.frame = CGRectMake(paddingLeft + optionIconSize + optionLabelPaddingIcon, optionPadding / 2, screenWidth - 2 * paddingLeft - optionLabelPaddingIcon - optionIconSize, optionHeight)
            var optionViewY :CGFloat = 0
            for i in 0 ..< optionsHeight.count{
                optionViewY += optionsHeight[i]
                optionViewY += optionPadding
            }
            optionsHeight.append(optionHeight)
            optionView.frame = CGRectMake(0, optionViewY, screenWidth , optionHeight + optionPadding)
            var optionButton = UIButton(frame: CGRectMake(0,0,optionView.frame.width,optionView.frame.height))
            optionButton.addTarget(self, action: "singleChioceButton:", forControlEvents: UIControlEvents.TouchUpInside)
            optionButton.setBackgroundImage(UIImage(named: "background_gray_highlight"), forState: UIControlState.Highlighted)
            optionButton.tag = ItemViewTag.optionButtonTagBase() + optionIndex
            
            optionView.addSubview(optionButton)
            optionView.addSubview(optionIcon)
            optionView.addSubview(optionLabel)
            
            optionsView.addSubview(optionView)
        }
        var optionViewHeight : CGFloat = 0
        for i in 0 ..< optionsHeight.count{
            optionViewHeight += optionsHeight[i]
            optionViewHeight += optionPadding
        }
        optionsView.frame = CGRectMake(0 ,tagView.frame.height + contentView.frame.height, screenWidth , optionViewHeight)
        optionsView.tag = ItemViewTag.optionsViewTag()
        childScrollView.addSubview(optionsView)
        
        //解析按钮
        var analysisButtonView = UIView(frame: CGRectMake(0, tagView.frame.height + contentView.frame.height + optionsView.frame.height, screenWidth, analysisButtonPaddingTop + analysisButtonFontSize ))
        var analysisButton = UIButton(frame: CGRectMake(paddingLeft, analysisButtonPaddingTop - 6, analysisButtonFontSize * 6, analysisButtonFontSize + 12))
        analysisButton.setImage(UIImage(named: "analysis_button"), forState: UIControlState.Normal)
        analysisButton.setImage(UIImage(named: "analysis_button_highlight"), forState: UIControlState.Highlighted)
        analysisButton.addTarget(self, action: "analysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag =  ItemViewTag.analysisButtonBase() + index
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + optionsView.frame.height + analysisButtonView.frame.height + paddingBottom)
        if childScrollView.contentSize.height <= itemScrollViewHeight{
            childScrollView.contentSize.height = itemScrollViewHeight + 1
        }
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }

}