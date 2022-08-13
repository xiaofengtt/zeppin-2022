//
//  MultipleChoiceAnswer.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/1.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit


extension StandardItemAnswerViewController {
    
    func multipleChoiceButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
        
        var index = 0
        for i in 0 ..< page{
            if globalItemList[i].isGroup{
                index += globalItemList[i].children!.count
            }else{
                index++
            }
        }
        if globalItemList[page].isGroup{
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            index += childPage
        }
        
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        
        if allOptionButtons[index][optionIndex].selected{
            allOptionButtons[index][optionIndex].selected = false
            allOptionButtons[index][optionIndex].layer.borderWidth = 1
            allOptionButtons[index][optionIndex].backgroundColor = UIColor.whiteColor()
        }else{
            allOptionButtons[index][optionIndex].selected = true
            allOptionButtons[index][optionIndex].layer.borderWidth = 0
            allOptionButtons[index][optionIndex].backgroundColor = UIColor.correctColor()
        }
        
        if globalItemList[page].isGroup{
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            var groupChildScrollView = childrenScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page + ItemViewTag.groupViewTagBase() * (childPage + 1)) as! UIScrollView
            var nextButton = groupChildScrollView.viewWithTag(ItemViewTag.submitButtonTag()) as! UIButton
            globalItemList[page].children![childPage].answer.userResults.removeAll(keepCapacity: false)
            for i in 0 ..< allOptionButtons[index].count{
                if allOptionButtons[index][i].selected{
                    globalItemList[page].children![childPage].answer.userResults.append(globalItemList[page].children![childPage].answer.options[i])
                }
            }
            
            if globalItemList[page].children![childPage].answer.userResults.count == 0{
                nextButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
                nextButton.backgroundColor = UIColor.buttonGray()
                nextButton.userInteractionEnabled = false
                progressViews[index].backgroundColor = UIColor.backgroundGray()
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor()), forState: UIControlState.Normal)
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
                answerCardButtons[index].setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
                answerCardButtons[index].layer.borderWidth = 2
            }else{
                nextButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                nextButton.backgroundColor = UIColor.mainColor()
                nextButton.userInteractionEnabled = true
                progressViews[index].backgroundColor = UIColor.correctColor()
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                answerCardButtons[index].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                answerCardButtons[index].layer.borderWidth = 0
            }
        }else{
            globalItemList[page].answer.userResults.removeAll(keepCapacity: false)
            for i in 0 ..< allOptionButtons[index].count{
                if allOptionButtons[index][i].selected{
                    globalItemList[page].answer.userResults.append(globalItemList[page].answer.options[i])
                }
            }
            var nextButton = childScrollView.viewWithTag(ItemViewTag.submitButtonTag()) as! UIButton
            if globalItemList[page].answer.userResults.count == 0{
                nextButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
                nextButton.backgroundColor = UIColor.backgroundGray()
                nextButton.userInteractionEnabled = false
                progressViews[index].backgroundColor = UIColor.backgroundGray()
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor()), forState: UIControlState.Normal)
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
                answerCardButtons[index].setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
                answerCardButtons[index].layer.borderWidth = 2
            }else{
                nextButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                nextButton.backgroundColor = UIColor.mainColor()
                nextButton.userInteractionEnabled = true
                progressViews[index].backgroundColor = UIColor.correctColor()
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
                answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                answerCardButtons[index].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                answerCardButtons[index].layer.borderWidth = 0
            }
        }
    }
    
    func multipleChoiceAnswer(index: Int){
        var item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childView.tag = ItemViewTag.childScrollViewTagBase() + index
        var childScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth , itemScrollViewHeight - submitButtonHeight))
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        //题型 来源 Label
        var tagView = TagViewShow(item , true)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item,self)
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
            optionIcon.backgroundColor = UIColor.whiteColor()
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = 4
            optionIcon.setTitle( NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Selected)
            optionIcon.selected = false
            optionIcon.tag = ItemViewTag.optionIconTagBase() + optionIndex
            optionButtons.append(optionIcon)
            
            var optionLabel = UILabel()
            optionLabel.font = UIFont(name: contentFontName, size: fontSize)
            optionLabel.numberOfLines = 0
            var optionString = NSMutableAttributedString(string: item.answer.options[optionIndex].content)
            optionString.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString(string: item.answer.options[optionIndex].content).length))
            optionLabel.attributedText = optionString
            let optionSize = NSString(string: item.answer.options[optionIndex].content).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft - optionLabelPaddingIcon - optionIconSize, height: CGFloat.max), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : optionLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
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
        optionsView.frame = CGRectMake(0 ,tagView.frame.height + contentView.frame.height , screenWidth , optionViewHeight + optionViewPaddingBottom)
        childScrollView.addSubview(optionsView)
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height +  contentView.frame.height + optionsView.frame.height)
        childView.addSubview(childScrollView)
        
        //下一题按钮
        var nextButton = UIButton(frame: CGRectMake(0, itemScrollViewHeight - submitButtonHeight, screenWidth, submitButtonHeight))
        nextButton.setTitle("下一题", forState: UIControlState.Normal)
        nextButton.userInteractionEnabled = false
        nextButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        nextButton.backgroundColor = UIColor.backgroundGray()
        nextButton.addTarget(self, action: "nextAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
        nextButton.tag = ItemViewTag.submitButtonTag()
        childView.addSubview(nextButton)
        
        itemScrollView.addSubview(childView)
        allOptionButtons.append(optionButtons)
    }
    
    func nextAnswer(sender: UIButton){
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        if globalItemList[page].isGroup{
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            if globalItemList[page].children!.count == childPage + 1{
                itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.contentOffset.x + screenWidth, 0, screenWidth, itemScrollViewHeight), animated: true)
            }else{
                childrenScrollView.scrollRectToVisible(CGRectMake(childrenScrollView.contentOffset.x + screenWidth, 0, screenWidth, childrenScrollView.frame.height), animated: true)
            }
        }else{
            itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.contentOffset.x + screenWidth, 0, screenWidth, itemScrollViewHeight), animated: true)
        }
    }
    
}