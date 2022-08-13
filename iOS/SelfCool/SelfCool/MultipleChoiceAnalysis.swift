//
//  MultipleChoiceAnalysis.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/19.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandradItemAnalysisViewController {
    func multipleChoiceAnalysis(index: Int ,itemList: [ItemModel] , scrollView: UIScrollView){
        var item = itemList[index]
        paragraphStyle.lineSpacing = lineSpacing
        
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , analysisScrollViewHeight))
        childScrollView.tag = ItemViewTag.childScrollViewTagBase() + index
        childScrollView.showsHorizontalScrollIndicator = false
        childScrollView.showsVerticalScrollIndicator = false
        //题型 来源 Label
        var tagView = TagViewShow(item , false)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = ContentViewShow(item , index)
        contentView.frame = CGRectMake(0, tagView.frame.height , screenWidth, contentView.frame.height)
        childScrollView.addSubview(contentView)
        
        //选项
        var optionsView = UIView()
        var optionsHeight : [CGFloat] = []
        for optionIndex in 0 ..< item.answer.options.count{
            var optionView = UIView()
            var optionIcon = UIButton(frame: CGRectMake(paddingLeft, optionPadding / 2, optionIconSize, optionIconSize))
            optionIcon.userInteractionEnabled = false
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = 4
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.blackColor().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle(NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
            
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
            if item.answer.completeType == 1{
                for i in 0 ..< item.answer.results.count{
                    if item.answer.results[i].inx.toInt()! - 1 == optionIndex{
                        optionIcon.layer.borderWidth = 0
                        optionIcon.backgroundColor = UIColor.correctColor()
                        optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                    }
                }
            }else{
                if item.answer.userResults.count != 0{
                    for i in 0 ..< item.answer.userResults.count{
                        if item.answer.userResults[i].inx.toInt()! - 1 == optionIndex{
                            optionIcon.layer.borderWidth = 0
                            optionIcon.backgroundColor = UIColor.wrongColor()
                            optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                        }
                    }
                }
                for i in 0 ..< item.answer.results.count{
                    if item.answer.results[i].inx.toInt()! - 1 == optionIndex{
                        optionIcon.layer.borderWidth = 0
                        if optionIcon.backgroundColor == UIColor.wrongColor(){
                            optionIcon.setBackgroundImage(UIImage(named: "multiple_half"), forState: UIControlState.Normal)
                        }else{
                            optionIcon.backgroundColor = UIColor.correctColor()
                        }
                        optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                    }
                }
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
        optionsView.frame = CGRectMake(0 ,tagView.frame.height + contentView.frame.height, screenWidth , optionViewHeight)
        childScrollView.addSubview(optionsView)
        
        //解析
        var analysisView = AnalysisViewShow(item, true)
        
        analysisView.frame = CGRectMake(0, tagView.frame.height + contentView.frame.height + optionViewHeight, screenWidth , analysisView.frame.height)
        childScrollView.addSubview(analysisView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagView.frame.height + contentView.frame.height + optionsView.frame.height + analysisView.frame.height)
        var grayBounceView = UIView(frame: CGRectMake(0, childScrollView.contentSize.height, screenWidth, 1000))
        grayBounceView.backgroundColor = UIColor.backgroundGray()
        childScrollView.addSubview(grayBounceView)
        scrollView.addSubview(childScrollView)
    }
}