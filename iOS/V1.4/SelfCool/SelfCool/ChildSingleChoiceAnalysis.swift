//
//  ChildSingleChoiceAnalysis.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnalysisViewController{
    func childSingleChoiceAnalysis(index : Int , item: ItemModel , parent: UIScrollView){
        paragraphStyle.lineSpacing = lineSpacing
        
        let childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , parent.frame.height))
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        childScrollView.tag = parent.superview!.tag + (index + 1) * ItemViewTag.groupViewTagBase()
        
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
        for optionIndex in 0 ..< item.answer.options.count{
            let optionView = UIView()
            let optionIcon = UIButton(frame: CGRectMake(paddingLeft, optionPadding / 2, optionIconSize, optionIconSize))
            optionIcon.userInteractionEnabled = false
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = optionIconSize / 2
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle(NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            
            let optionLabel = UILabel()
            optionLabel.font = UIFont.systemFontOfSize(fontSize)
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
            if item.answer.userResults.count != 0 && Int(item.answer.userResults[0].inx)! - 1 == optionIndex{
                optionIcon.layer.borderWidth = 0
                optionIcon.backgroundColor = UIColor.wrongColor()
                optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
            }
            if Int(item.answer.results[0].inx)! - 1 == optionIndex{
                optionIcon.layer.borderWidth = 0
                optionIcon.backgroundColor = UIColor.correctColor()
                optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
            }
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
        
        //解析
        let analysisView = AnalysisViewShow(item, isStandard: true, viewController: self)
        analysisView.frame = CGRectMake(0, tagView.frame.height + contentView.frame.height + optionViewHeight, screenWidth , analysisView.frame.height)
        childScrollView.addSubview(analysisView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + optionsView.frame.height + analysisView.frame.height)
        childScrollView.addGrayBottomBounceView()
        parent.addSubview(childScrollView)
    }
}