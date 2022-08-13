//
//  JudgeWrongItem.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/18.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import Foundation

extension WrongItemViewController {
   
    func judgeAnalysisButton(sender : UIButton){
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let childScrollView = sender.superview?.superview as! UIScrollView
        let item = globalItemList[page]
        sender.hidden = true
        
        if item.isGroup{
            let childPage = childScrollView.tag / ItemViewTag.groupViewTagBase() - 1
            let childItem = item.children![childPage]
            let correctIndex = Int(childItem.answer.results[0].inx)! - 1
            let correctOption = childScrollView.viewWithTag(ItemViewTag.optionIconTagBase() + correctIndex) as! UIButton
            correctOption.layer.borderWidth = 0
            correctOption.backgroundColor = UIColor.correctColor()
            correctOption.setImage(UIImage(named: "judge_\(correctIndex + 1)_selected"), forState: UIControlState.Normal)
            for i in 0 ..< childItem.answer.options.count{
                let button = childScrollView.viewWithTag(ItemViewTag.optionButtonTagBase() + i) as! UIButton
                button.enabled = false
            }
            let analysisView = AnalysisViewShow(childItem, isStandard: true, viewController: self)
            analysisView.frame = CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height, screenWidth, analysisView.frame.height)
            childScrollView.addSubview(analysisView)
            
            childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height +  childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
        }else{
            let correctIndex = Int(item.answer.results[0].inx)! - 1
            let correctOption = childScrollView.viewWithTag(ItemViewTag.optionIconTagBase() + correctIndex) as! UIButton
            correctOption.layer.borderWidth = 0
            correctOption.backgroundColor = UIColor.correctColor()
            correctOption.setImage(UIImage(named: "judge_\(correctIndex + 1)_selected"), forState: UIControlState.Normal)
            for i in 0 ..< item.answer.options.count{
                let button = childScrollView.viewWithTag(ItemViewTag.optionButtonTagBase() + i) as! UIButton
                button.enabled = false
            }
            let analysisView = AnalysisViewShow(item, isStandard: true, viewController: self)
            analysisView.frame = CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height, screenWidth, analysisView.frame.height)
            childScrollView.addSubview(analysisView)
            
            childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisButtonViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
            childScrollView.addGrayBottomBounceView()
            childScrollView.makeVerticalScrollEnable()
        }
        let analysisView = childScrollView.viewWithTag(ItemViewTag.analysisViewTag()) as UIView?
        AnalysisScrollToCenter(analysisView!, parentView: childScrollView)
    }
    
    func judgeButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let item = globalItemList[page]
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        let optionView = sender.superview as UIView?
        let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
        var itemButton : UIButton
        
        MobClick.event("WrongBookAnswer")
        
        if item.isGroup{
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page + ItemViewTag.groupViewTagBase() * (childPage + 1)) as! UIButton
            let childItem = item.children![childPage]
            childItem.answer.userResults = [childItem.answer.options[optionIndex]]
            if optionIndex + 1 == Int(childItem.answer.results[0].inx)!{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                childItem.answer.completeType = 1
                childItem.userCorrectCount++
            }else{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                childItem.answer.completeType = 0
                childItem.userWrongCount++
            }
        }else{
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
            item.answer.userResults = [item.answer.options[optionIndex]]
            if optionIndex + 1 == Int(item.answer.results[0].inx)!{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                item.answer.completeType = 1
                item.userCorrectCount++
            }else{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                item.answer.completeType = 0
                item.userWrongCount++
            }
        }
        
        let infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
        infoLabelUpdate(infoLabel)
        
        let optionButton = optionView!.viewWithTag(ItemViewTag.optionIconTagBase() + optionIndex) as! UIButton
        optionButton.layer.borderWidth = 0
        optionButton.backgroundColor = UIColor.wrongColor()
        optionButton.setImage(UIImage(named: "judge_\(optionIndex + 1)_selected"), forState: UIControlState.Normal)
        if item.isGroup{
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childItem = item.children![childPage]
            let groupChildScrollView = childScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page + ItemViewTag.groupViewTagBase() * (childPage + 1)) as! UIScrollView
            let analysisButton = groupChildScrollView.viewWithTag(ItemViewTag.analysisButtonTag()) as! UIButton
            analysisButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            
            let submitParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(childItem.id) , "item.blankInx" : String(childItem.blankInx)  , "isAnswered" : String(1) , "completeType" : String(childItem.answer.completeType) , "reference" : childItem.answer.userResults[0].inx , "content" : childItem.answer.userResults[0].content])
            httpController.getNSDataByParams("wrongBookAnswer", paramsDictionary: submitParams)
        }else{
            let analysisButton = childScrollView.viewWithTag(ItemViewTag.analysisButtonTag()) as! UIButton
            analysisButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            
            let submitParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "item.blankInx" : String(item.blankInx)  , "isAnswered" : String(1) , "completeType" : String(item.answer.completeType) , "reference" : item.answer.userResults[0].inx , "content" : item.answer.userResults[0].content])
            httpController.getNSDataByParams("wrongBookAnswer", paramsDictionary: submitParams)
        }

    }
    
    func judgeShow(index: Int){
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
        
        //选项
        let optionsView = UIView()
        var optionsHeight : [CGFloat] = []
        var optionButtons : [UIButton] = []
        for optionIndex in 0 ..< item.answer.options.count{
            let optionView = UIView()
            let optionIcon = UIButton(frame: CGRectMake(paddingLeft, optionPadding / 2, optionIconSize, optionIconSize))
            optionIcon.userInteractionEnabled = false
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = optionIconSize / 2
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setImage(UIImage(named: "judge_\(optionIndex + 1)"), forState: UIControlState.Normal)
            optionIcon.tag = ItemViewTag.optionIconTagBase() + optionIndex
            optionButtons.append(optionIcon)
            
            let optionLabel = UILabel()
            optionLabel.font = UIFont.systemFontOfSize(fontSize)
            optionLabel.tag = ItemViewTag.optionLabelTagBase() + optionIndex
            optionLabel.numberOfLines = 0
            let optionString = HtmlToString(item.answer.options[optionIndex].content)
            let optionStringAttributed = NSMutableAttributedString(string: optionString)
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
            let optionButton = UIButton(frame: CGRectMake(0,0,optionView.frame.width,optionView.frame.height))
            optionButton.addTarget(self, action: "judgeButton:", forControlEvents: UIControlEvents.TouchUpInside)
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
        let analysisButtonView = UIView(frame: CGRectMake(0, tagView.frame.height + contentView.frame.height + optionsView.frame.height, screenWidth, analysisButtonPaddingTop + analysisButtonFontSize ))
        let analysisButton = UIButton(frame: CGRectMake(paddingLeft, analysisButtonPaddingTop - 6, analysisButtonFontSize * 6, analysisButtonFontSize + 12))
        analysisButton.setImage(UIImage(named: "analysis_button"), forState: UIControlState.Normal)
        analysisButton.setImage(UIImage(named: "analysis_button_highlight"), forState: UIControlState.Highlighted)
        analysisButton.addTarget(self, action: "judgeAnalysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag =  ItemViewTag.analysisButtonTag()
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + optionsView.frame.height + analysisButtonView.frame.height + paddingBottom)
        childScrollView.makeVerticalScrollEnable()
        
        itemScrollView.addSubview(childScrollView)
        globalItemList[index].isShow = true
    }
}