//
//  ChildMultipleChoiceAnswer.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/22.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController{
    func childMultipleChoiceAnswer(item: ItemModel , parent: UIScrollView){
        let index = item.index
        paragraphStyle.lineSpacing = lineSpacing
        
        var childView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index - 1), 0, screenWidth , parent.frame.height))
        childView.tag = parent.superview!.tag + index * ItemViewTag.groupViewTagBase()
        childView.contentSize = childView.frame.size
        
        var childScrollView = UIScrollView(frame: CGRectMake(0, 0, screenWidth , parent.frame.height - submitButtonHeight))
        childScrollView.tag = ItemViewTag.groupMultipleScrollViewTag()
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
        var nextButton = UIButton(frame: CGRectMake(0, parent.frame.height - submitButtonHeight, screenWidth, submitButtonHeight))
        nextButton.setTitle("下一题", forState: UIControlState.Normal)
        nextButton.userInteractionEnabled = false
        nextButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        nextButton.backgroundColor = UIColor.buttonGray()
        nextButton.addTarget(self, action: "nextAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
        nextButton.tag = ItemViewTag.submitButtonTag()
        childView.addSubview(nextButton)
        
        parent.addSubview(childView)
        allOptionButtons.append(optionButtons)
    }
}