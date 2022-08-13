//
//  ContentViewShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/28.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func ContentViewShow(item : ItemModel , viewController : UIViewController) -> UIView{
    let contentFontName = "STHeitiSC-Medium"
    let fontSize :CGFloat = 16
    let contentPaddingTop : CGFloat = 10
    let contentPaddingBottom : CGFloat = 30
    let contentPaddingLeft : CGFloat = 25
    let lineSpacing : CGFloat = 10
    var paragraphStyle = NSMutableParagraphStyle()
    
    var contentView = UIView()
    var contentLabel = UILabel()
    contentLabel.font = UIFont(name: contentFontName, size: fontSize)
    contentLabel.numberOfLines = 0
    var contentString = ""
    contentString = HtmlToString(item.answer.stem)
    var contentAttributedString = NSMutableAttributedString(string: contentString)
    paragraphStyle.lineSpacing = lineSpacing
    contentAttributedString.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString (string: contentString).length ))
    contentLabel.attributedText = contentAttributedString
    
    let contentSize = NSString(string: contentString).boundingRectWithSize(CGSize(width: screenWidth - 2 * contentPaddingLeft, height: CGFloat.max), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : contentLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
    contentLabel.frame = CGRectMake(contentPaddingLeft, contentPaddingTop , screenWidth - 2 * contentPaddingLeft , contentSize.height)
    contentView.addSubview(contentLabel)
    
    var imageUrls : [String] = HtmlGetImagesUrl(item.answer.stem)
    var contentImageView = UIView()
    if imageUrls.count > 0{
        for imageIndex in 0 ..< imageUrls.count{
            var imageData = NSData(contentsOfURL: NSURL(string: imageUrls[imageIndex].stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)!)
            if imageData != nil{
                var image = UIImage(data: imageData!)
                var imageView = UIImageView(image: image)
                imageView.tag = ItemViewTag.imageTagBase() + imageIndex
                imageView.sizeToFit()
                if imageView.frame.width > screenWidth - 2 * contentPaddingLeft {
                    imageView.frame.size = CGSize(width: screenWidth - 2 * contentPaddingLeft, height: (screenWidth - 2 * contentPaddingLeft) * imageView.frame.height / imageView.frame.width )
                }
                imageView.frame = CGRectMake(contentPaddingLeft, contentImageView.frame.height, imageView.frame.width , imageView.frame.height)
                var imageButton = UIButton(frame: imageView.frame)
                imageButton.tag = ItemViewTag.imageButtonTagBase() + imageIndex
                imageButton.addTarget(viewController, action: "imageButton:", forControlEvents: UIControlEvents.TouchUpInside)
                contentImageView.addSubview(imageView)
                contentImageView.addSubview(imageButton)
                contentImageView.frame = CGRectMake(0,contentPaddingTop + contentSize.height, screenWidth, contentImageView.frame.height + imageView.frame.height)
            }
        }
    }
    contentView.addSubview(contentImageView)
    
    contentView.frame.size = CGSize(width: screenWidth, height: contentPaddingTop + contentSize.height + contentImageView.frame.height + contentPaddingBottom)
    contentView.tag = ItemViewTag.contentViewTag()
    return contentView
}


