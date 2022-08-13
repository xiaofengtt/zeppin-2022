//
//  MultipleChoiceWrongItem.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/19.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    func  submitButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let childView = sender.superview as! UIScrollView
        var item = globalItemList[page]
        var itemButton : UIButton
        var reference = ""
        
        MobClick.event("WrongBookAnswer")
        
        if item.isGroup{
            let childrenScrollView = childView.superview as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            item = item.children![childPage]
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page + ItemViewTag.groupViewTagBase() * (childPage + 1)) as! UIButton
        }else{
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
        }
        if item.answer.userResults.count > 0{
            if item.answer.userResults.count == item.answer.results.count{
                var flag = true
                for i in 0 ..< item.answer.userResults.count{
                    if item.answer.userResults[i] != item.answer.results[i]{
                        flag = false
                    }
                }
                if flag {
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
            }else{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                item.answer.completeType = 0
                item.userWrongCount++
            }
            
            for i in 0 ..< item.answer.userResults.count{
                let inx = item.answer.userResults[i].inx
                reference += item.answer.userResults[i].inx
                if i != item.answer.userResults.count - 1{
                    reference += ","
                }
                let optionButton = childView.viewWithTag(ItemViewTag.optionIconTagBase() + Int(inx)! - 1) as! UIButton
                optionButton.backgroundColor = UIColor.wrongColor()
            }
        }
        let infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
        infoLabelUpdate(infoLabel)
        
        let analysisButton = childView.viewWithTag(ItemViewTag.analysisButtonTag()) as! UIButton
        analysisButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
        
        let submitParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "item.blankInx" : String(item.blankInx)  , "isAnswered" : String(1) , "completeType" : String(item.answer.completeType) , "reference" : reference , "content" : reference])
        httpController.getNSDataByParams("wrongBookAnswer", paramsDictionary: submitParams)
    }
    
    func multipleChoiceButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var item = globalItemList[page]
        let childView = sender.superview?.superview?.superview?.superview as! UIScrollView
        let submitButton = childView.viewWithTag(ItemViewTag.submitButtonTag()) as! UIButton
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        let optionView = sender.superview as UIView?
        let optionButton = optionView!.viewWithTag(ItemViewTag.optionIconTagBase() + optionIndex) as! UIButton
        if optionButton.selected{
            optionButton.selected = false
            optionButton.layer.borderWidth = 1
            optionButton.backgroundColor = UIColor.whiteColor()
        }else{
            optionButton.selected = true
            optionButton.layer.borderWidth = 0
            optionButton.backgroundColor = UIColor.correctColor()
        }
        
        if item.isGroup{
            let childrenScrollView = childView.superview as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            item = item.children![childPage]
        }
        
        item.answer.userResults.removeAll(keepCapacity: false)
        for i in 0 ..< item.answer.options.count{
            let button = childView.viewWithTag(ItemViewTag.optionIconTagBase() + i) as! UIButton
            if button.selected{
                item.answer.userResults.append(item.answer.options[i])
            }
        }
        
        if item.answer.userResults.count == 0{
            submitButton.enabled = false
        }else{
            submitButton.enabled = true
        }
    }
    
    func multipleChoiceAnalysisButton(sender : UIButton){
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let childScrollView = sender.superview?.superview as! UIScrollView
        let childView = childScrollView.superview as! UIScrollView
        let submitButton = childView.viewWithTag(ItemViewTag.submitButtonTag()) as! UIButton
        submitButton.hidden = true
        let item = globalItemList[page]
        sender.hidden = true
        
        if item.isGroup{
            let childrenScrollView = childView.superview as! ChildrenScrollView
            childScrollView.frame = CGRectMake(0, 0, screenWidth, childrenScrollView.frame.height)
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childItem = item.children![childPage]
            if childItem.answer.completeType == -1{
                childItem.answer.userResults.removeAll(keepCapacity: false)
                for i in 0 ..< childItem.answer.options.count{
                    let button = childView.viewWithTag(ItemViewTag.optionIconTagBase() + i) as! UIButton
                    button.selected = false
                    button.layer.borderWidth = 1
                    button.backgroundColor = UIColor.whiteColor()
                }
            }
            for i in 0 ..< childItem.answer.results.count{
                let correctIndex = Int(childItem.answer.results[i].inx)! - 1
                let correctOption = childScrollView.viewWithTag(ItemViewTag.optionIconTagBase() + correctIndex) as! UIButton
                if correctOption.selected && childItem.answer.completeType != 1{
                    correctOption.layer.borderWidth = 0
                    correctOption.setBackgroundImage(UIImage(named: "multiple_half"), forState: UIControlState.Normal)
                    correctOption.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                }else{
                    correctOption.layer.borderWidth = 0
                    correctOption.backgroundColor = UIColor.correctColor()
                    correctOption.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                }
            }
            
            for i in 0 ..< childItem.answer.options.count{
                let button = childScrollView.viewWithTag(ItemViewTag.optionButtonTagBase() + i) as! UIButton
                button.enabled = false
            }
            
            //解析
            let analysisView = AnalysisViewShow(childItem, isStandard: true, viewController: self)
            analysisView.frame = CGRectMake(0, childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height, screenWidth, analysisView.frame.height)
            childScrollView.addSubview(analysisView)
            
            childScrollView.contentSize = CGSize(width: screenWidth, height: childScrollView.viewWithTag(ItemViewTag.tagViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.contentViewTag())!.frame.height + childScrollView.viewWithTag(ItemViewTag.optionsViewTag())!.frame.height +  childScrollView.viewWithTag(ItemViewTag.analysisViewTag())!.frame.height)
        }else{
            childScrollView.frame = CGRectMake(0, 0, screenWidth, itemScrollViewHeight)
            if item.answer.completeType == -1{
                item.answer.userResults.removeAll(keepCapacity: false)
                for i in 0 ..< item.answer.options.count{
                    let button = childView.viewWithTag(ItemViewTag.optionIconTagBase() + i) as! UIButton
                    button.selected = false
                    button.layer.borderWidth = 1
                    button.backgroundColor = UIColor.whiteColor()
                }
            }
            for i in 0 ..< item.answer.results.count{
                let correctIndex = Int(item.answer.results[i].inx)! - 1
                let correctOption = childScrollView.viewWithTag(ItemViewTag.optionIconTagBase() + correctIndex) as! UIButton
                if correctOption.selected && item.answer.completeType != 1{
                    correctOption.layer.borderWidth = 0
                    correctOption.setBackgroundImage(UIImage(named: "multiple_half"), forState: UIControlState.Normal)
                    correctOption.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                }else{
                    correctOption.layer.borderWidth = 0
                    correctOption.backgroundColor = UIColor.correctColor()
                    correctOption.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                }
            }
            
            for i in 0 ..< item.answer.options.count{
                let button = childScrollView.viewWithTag(ItemViewTag.optionButtonTagBase() + i) as! UIButton
                button.enabled = false
            }
            
            //解析
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
    
    func multipleChoiceShow(index: Int){
        let item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        let childView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        let childScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth , itemScrollViewHeight - submitButtonHeight))
        childView.tag = ItemViewTag.childScrollViewTagBase() + index
        childView.showsHorizontalScrollIndicator = false
        childView.showsVerticalScrollIndicator = false
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
            optionIcon.layer.cornerRadius = 4
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle( NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Selected)
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
            optionButton.addTarget(self, action: "multipleChoiceButton:", forControlEvents: UIControlEvents.TouchUpInside)
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
        analysisButton.addTarget(self, action: "multipleChoiceAnalysisButton:", forControlEvents: UIControlEvents.TouchUpInside)
        analysisButton.tag =  ItemViewTag.analysisButtonTag()
        analysisButtonView.addSubview(analysisButton)
        analysisButtonView.tag = ItemViewTag.analysisButtonViewTag()
        childScrollView.addSubview(analysisButtonView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + optionsView.frame.height + analysisButtonView.frame.height + paddingBottom)
        childScrollView.makeVerticalScrollEnable()
        childView.addSubview(childScrollView)
        
        //提交按钮
        let submitButton = UIButton(frame: CGRectMake(0, itemScrollViewHeight - submitButtonHeight, screenWidth, submitButtonHeight))
        submitButton.enabled = false
        submitButton.setTitle("提交", forState: UIControlState.Normal)
        submitButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        submitButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Disabled)
        submitButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        submitButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Disabled)
        submitButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        submitButton.addTarget(self, action: "submitButton:", forControlEvents: UIControlEvents.TouchUpInside)
        submitButton.tag = ItemViewTag.submitButtonTag()
        childView.addSubview(submitButton)
        
        itemScrollView.addSubview(childView)
        globalItemList[index].isShow = true
    }
    
}