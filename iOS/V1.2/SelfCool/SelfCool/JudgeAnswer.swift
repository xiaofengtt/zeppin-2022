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
        
        //题型 来源 Label
        var tagView = TagViewShow(item , nil)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item , index)
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
        optionsView.frame = CGRectMake(0 ,tagView.frame.height + contentView.frame.height , screenWidth , optionViewHeight + optionViewPaddingBottom)
        childScrollView.addSubview(optionsView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height +  contentView.frame.height + optionsView.frame.height)
        itemScrollView.addSubview(childScrollView)
        allOptionButtons.append(optionButtons)
    }

    func JudgeButton(sender: UIButton) {
        itemScrollView.userInteractionEnabled = false
        let index = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        for i in 0 ..< allOptionButtons[index].count{
            allOptionButtons[index][i].setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            allOptionButtons[index][i].layer.borderWidth = 1
            allOptionButtons[index][i].backgroundColor = UIColor.whiteColor()
        }
        let optionIndex = sender.tag - ItemViewTag.optionButtonTagBase()
        globalItemList[index].answer.userResults = [globalItemList[index].answer.options[optionIndex]]
        progressViews[index].backgroundColor = UIColor.mainColor()
        
        answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
        answerCardButtons[index].setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
        answerCardButtons[index].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        answerCardButtons[index].layer.borderWidth = 0
        allOptionButtons[index][optionIndex].backgroundColor = UIColor.mainColor()
        allOptionButtons[index][optionIndex].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        allOptionButtons[index][optionIndex].layer.borderWidth = 0
        itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.contentOffset.x + screenWidth, 0, screenWidth, itemScrollViewHeight), animated: true)
    }
}