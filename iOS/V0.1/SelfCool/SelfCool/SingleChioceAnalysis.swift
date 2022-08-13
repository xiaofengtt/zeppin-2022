//
//  SingleChioceAnalysis.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/23.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension AnalysisViewController {
    func singleChioceAnalysis(index: Int ,itemList: [ItemModel] , scrollView: UIScrollView){
        var item = itemList[index]
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , analysisScrollViewHeight))
        //题型 来源 Label
        var tagView = UIView(frame: CGRectMake(0, tagViewPaddingTop, screenWidth, labelViewHeight))
        var itemTypeLabel = UILabel()
        var itemTypeString = " \(item.itemType)   |"
        itemTypeLabel.text = itemTypeString
        itemTypeLabel.font = UIFont.systemFontOfSize(tagFontSize)
        let itemTypeSize = NSString(string: itemTypeString).sizeWithAttributes([NSFontAttributeName : itemTypeLabel.font])
        itemTypeLabel.frame = CGRectMake(paddingLeft, (labelViewHeight - itemTypeSize.height) / 2, itemTypeSize.width, itemTypeSize.height)
        tagView.addSubview(itemTypeLabel)
        
        var sourceTypeLabel = UILabel()
        var sourceTypeString = "   \(item.sourceType) "
        sourceTypeLabel.text = sourceTypeString
        sourceTypeLabel.font = UIFont.systemFontOfSize(tagFontSize)
        sourceTypeLabel.frame = CGRectMake(paddingLeft + itemTypeSize.width, (labelViewHeight - itemTypeSize.height) / 2, screenWidth - 2 * paddingLeft - itemTypeSize.width, itemTypeSize.height)
        tagView.addSubview(sourceTypeLabel)
        childScrollView.addSubview(tagView)
        
        //题干
        var contentView = UIView()
        var contentLabel = UILabel()
        contentLabel.font = UIFont(name: contentFontName, size: fontSize)
        contentLabel.numberOfLines = 0
        var contentString = "\(itemList[index].index + 1). " + HtmlToString(item.answer.stem)
        var contentAttributedString = NSMutableAttributedString(string: contentString)
        paragraphStyle.lineSpacing = lineSpacing
        contentAttributedString.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString (string: contentString).length ))
        contentLabel.attributedText = contentAttributedString
        
        let contentSize = NSString(string: contentString).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft, height: 1000), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : contentLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
        contentLabel.frame = CGRectMake(paddingLeft, 0 , screenWidth - 2 * paddingLeft , contentSize.height)
        contentView.addSubview(contentLabel)
        
        var imageUrls : [String] = HtmlGetImagesUrl(item.answer.stem)
        var contentImageView = UIView()
        if imageUrls.count > 0{
            for imageIndex in 0 ..< imageUrls.count{
                var imageData = NSData(contentsOfURL: NSURL(string: imageUrls[imageIndex])!)
                var image = UIImage(data: imageData!)
                var imageView = UIImageView(image: image)
                imageView.sizeToFit()
                if imageView.frame.width > screenWidth - 2 * paddingLeft {
                    imageView.frame.size = CGSize(width: screenWidth - 2 * paddingLeft, height: (screenWidth - 2 * paddingLeft) * imageView.frame.height / imageView.frame.width )
                }
                imageView.frame = CGRectMake(paddingLeft, contentImageView.frame.height,
                    imageView.frame.width , imageView.frame.height)
                contentImageView.addSubview(imageView)
                contentImageView.frame = CGRectMake(0, contentSize.height, screenWidth, contentImageView.frame.height + imageView.frame.height)
            }
        }
        contentView.addSubview(contentImageView)
        
        contentView.frame = CGRectMake(0, tagViewPaddingTop + lineSpacing + labelViewHeight , screenWidth, contentSize.height + contentImageView.frame.height)
        childScrollView.addSubview(contentView)
        
        //选项
        var optionsView = UIView()
        var optionsHeight : [CGFloat] = []
        for optionIndex in 0 ..< item.answer.options.count{
            var optionView = UIView()
            var optionIcon = UIButton(frame: CGRectMake(paddingLeft, optionPadding / 2, optionIconSize, optionIconSize))
            optionIcon.userInteractionEnabled = false
            optionIcon.layer.masksToBounds = true
            optionIcon.layer.cornerRadius = optionIconSize / 2
            optionIcon.layer.borderWidth = 1
            optionIcon.layer.borderColor = UIColor.buttonGray().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle(NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
            
            var optionLabel = UILabel()
            optionLabel.font = UIFont(name: contentFontName, size: fontSize)
            optionLabel.numberOfLines = 0
            var optionString = HtmlToString(item.answer.options[optionIndex].content)
            var optionStringAttributed = NSMutableAttributedString(string: optionString)
            optionStringAttributed.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString(string: optionString).length))
            optionLabel.attributedText = optionStringAttributed
            let optionSize = NSString(string: optionString).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft - optionLabelPaddingIcon - optionIconSize, height: 1000), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : optionLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
            let optionHeight = optionSize.height > optionIconSize ? optionSize.height : optionIconSize
            optionLabel.frame = CGRectMake(paddingLeft + optionIconSize + optionLabelPaddingIcon, optionPadding / 2, screenWidth - 2 * paddingLeft - optionLabelPaddingIcon - optionIconSize, optionHeight)
            var optionViewY :CGFloat = 0
            for i in 0 ..< optionsHeight.count{
                optionViewY += optionsHeight[i]
                optionViewY += optionPadding
            }
            optionsHeight.append(optionHeight)
            optionView.frame = CGRectMake(0, optionViewY, screenWidth , optionHeight + optionPadding)
            if item.answer.userResults.count != 0 && item.answer.userResults[0].inx.toInt()! - 1 == optionIndex{
                optionIcon.layer.borderWidth = 0
                optionIcon.backgroundColor = UIColor.redColor()
                optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
            }
            if item.answer.results[0].inx.toInt()! - 1 == optionIndex{
                optionIcon.layer.borderWidth = 0
                optionIcon.backgroundColor = UIColor.mainColor()
                optionIcon.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                optionLabel.textColor = UIColor.mainColor()
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
        optionsView.frame = CGRectMake(0 ,tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height, screenWidth , optionViewHeight)
        childScrollView.addSubview(optionsView)
        
        //解析
        var analysisView = UIView()
        analysisView.backgroundColor = UIColor.backgroundGray()
        
        var resultLabal = UILabel(frame: CGRectMake(paddingLeft, analysisPaddingTop, screenWidth - 2 * paddingLeft, labelViewHeight))
        resultLabal.font = UIFont(name: contentFontName, size: fontSize)
        resultLabal.textColor = UIColor.blackColor()
        resultLabal.text = " 正确答案:  \(NumberToABC(item.answer.results[0].inx.toInt()!))"
        analysisView.addSubview(resultLabal)
        
        var  analysisContentLabel = UILabel()
        analysisContentLabel.font = UIFont(name: contentFontName, size: fontSize)
        analysisContentLabel.numberOfLines = 0
        var analysisContentString = "答案解析: \(HtmlToString(item.analysis))"
        var analysisContentAttributedString = NSMutableAttributedString(string: analysisContentString)
        analysisContentAttributedString.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString (string: analysisContentString).length ))
        analysisContentLabel.attributedText = analysisContentAttributedString
        let analysisContentSize = NSString(string: analysisContentString).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft, height: 1000), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : analysisContentLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
        analysisContentLabel.frame = CGRectMake(paddingLeft, 2 * analysisPaddingTop + labelViewHeight , screenWidth - 2 * paddingLeft , analysisContentSize.height)
        analysisView.addSubview(analysisContentLabel)

        analysisView.frame = CGRectMake(0, tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height + optionViewHeight, screenWidth,2 * analysisPaddingTop + labelViewHeight + analysisContentLabel.frame.height + paddingButtom)
        if tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height + optionViewHeight + analysisView.frame.height < analysisScrollViewHeight{
            analysisView.frame = CGRectMake(0, tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height + optionViewHeight, screenWidth, analysisScrollViewHeight - (tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height + optionViewHeight))
        }
        childScrollView.addSubview(analysisView)
        
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height + optionsView.frame.height + analysisView.frame.height)
        var grayBounceView = UIView(frame: CGRectMake(0, childScrollView.contentSize.height, screenWidth, 500))
        grayBounceView.backgroundColor = UIColor.backgroundGray()
        childScrollView.addSubview(grayBounceView)
        scrollView.addSubview(childScrollView)
    }
}