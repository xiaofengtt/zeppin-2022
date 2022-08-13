//
//  JudgeAnswer.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController {
    func judgeAnswer(index : Int){
        var item = globalItemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        //题型 来源 Label
        var tagView = TagViewShow(item , true)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item ,self)
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
            optionIcon.setImage(UIImage(named: "judge_\(optionIndex + 1)"), forState: UIControlState.Normal)
            optionIcon.tag = ItemViewTag.optionIconTagBase() + optionIndex
            optionButtons.append(optionIcon)
            
            var optionLabel = UILabel()
            optionLabel.font = UIFont(name: contentFontName, size: fontSize)
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
            optionButton.addTarget(self, action: "JudgeButton:", forControlEvents: UIControlEvents.TouchUpInside)
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
        itemScrollView.addSubview(childScrollView)
        allOptionButtons.append(optionButtons)
    }

    func JudgeButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        
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
            globalItemList[page].children![childPage].answer.userResults = [globalItemList[page].children![childPage].answer.options[optionIndex]]
        }else{
            globalItemList[page].answer.userResults = [globalItemList[page].answer.options[optionIndex]]
        }
        for i in 0 ..< allOptionButtons[index].count{
            allOptionButtons[index][i].setImage(UIImage(named: "judge_\(i + 1)"), forState: UIControlState.Normal)
            allOptionButtons[index][i].layer.borderWidth = 1
            allOptionButtons[index][i].backgroundColor = UIColor.whiteColor()
        }
        progressViews[index].backgroundColor = UIColor.correctColor()
        answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
        answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
        answerCardButtons[index].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        answerCardButtons[index].layer.borderWidth = 0
        allOptionButtons[index][optionIndex].backgroundColor = UIColor.correctColor()
        allOptionButtons[index][optionIndex].setImage(UIImage(named: "judge_\(optionIndex + 1)_selected"), forState: UIControlState.Normal)
        allOptionButtons[index][optionIndex].layer.borderWidth = 0
        if globalItemList[page].isGroup{
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            if childPage != globalItemList[page].children!.count - 1{
                childrenScrollView.scrollRectToVisible(CGRectMake(childrenScrollView.contentOffset.x + screenWidth, 0, screenWidth, childrenScrollView.frame.height), animated: true)
            }else{
                itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.contentOffset.x + screenWidth, 0, screenWidth, itemScrollViewHeight), animated: true)
            }
        }else{
            itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.contentOffset.x + screenWidth, 0, screenWidth, itemScrollViewHeight), animated: true)
        }
    }
}