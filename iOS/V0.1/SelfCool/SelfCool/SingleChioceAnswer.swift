//
//  singleChioceAnswer.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension AnswerViewController {
    func singleChioceShow(index: Int){
        var item = globalItemList[index]
        var childScrollView = UIScrollView(frame: CGRectMake(screenWidth * CGFloat(index), 0, screenWidth , itemScrollViewHeight))
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
        var contentString = "\(index + 1). " + HtmlToString(item.answer.stem)
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
                if imageData != nil{
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
        }
        contentView.addSubview(contentImageView)
        
        contentView.frame = CGRectMake(0, tagViewPaddingTop + lineSpacing + labelViewHeight , screenWidth, contentSize.height + contentImageView.frame.height)
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
            optionIcon.layer.borderColor = UIColor.buttonGray().CGColor
            optionIcon.titleLabel!.font = UIFont.systemFontOfSize(fontSize)
            optionIcon.setTitle( NumberToABC(optionIndex + 1) , forState: UIControlState.Normal)
            optionIcon.setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
            optionButtons.append(optionIcon)
            
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
            
            var optionButton = UIButton(frame: CGRectMake(0,0,optionView.frame.width,optionView.frame.height))
            optionButton.addTarget(self, action: "singleChioceButton:", forControlEvents: UIControlEvents.TouchUpInside)
            optionButton.setBackgroundImage(UIImage(named: "background_gray_highlight"), forState: UIControlState.Highlighted)
            optionButton.tag = 300 + optionIndex
            
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
        optionsView.frame = CGRectMake(0 ,tagViewPaddingTop + lineSpacing + optionViewPadding + labelViewHeight + contentView.frame.height, screenWidth , optionViewHeight)
        childScrollView.addSubview(optionsView)
        childScrollView.contentSize = CGSize(width: screenWidth, height: tagViewPaddingTop + lineSpacing + optionPadding + labelViewHeight + contentView.frame.height + optionsView.frame.height + paddingButtom)
        itemScrollView.addSubview(childScrollView)
        allOptionButtons.append(optionButtons)
    }
    
    func singleChioceButton(sender: UIButton) {
        itemScrollView.userInteractionEnabled = false
        let index = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        for i in 0 ..< allOptionButtons[index].count{
            allOptionButtons[index][i].setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
            allOptionButtons[index][i].layer.borderWidth = 1
            allOptionButtons[index][i].backgroundColor = UIColor.whiteColor()
        }
        let optionIndex = sender.tag - 300
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