//
//  AnalysisViewShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/28.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func AnalysisViewShow(item : ItemModel , isStandard : Bool) -> UIView{
    let analysisPaddingTop : CGFloat = 10
    let paddingLeft : CGFloat = 25
    let userResultLabelPaddingLeft : CGFloat = 10
    var labelViewHeight :CGFloat = 20
    let fontSize :CGFloat = 16
    let userResultSize :CGFloat = 11
    let analysisPaddingBottom : CGFloat = 30
    let contentFontName = "STHeitiSC-Medium"
    let lineSpacing : CGFloat = 10
    var paragraphStyle = NSMutableParagraphStyle()
    
    var analysisView = UIView()
    analysisView.backgroundColor = UIColor.backgroundGray()
    
    if isStandard{
        labelViewHeight = 40
        var resultLabel_1 = UILabel()
        resultLabel_1.font = UIFont(name: contentFontName, size: fontSize)
        resultLabel_1.text = "正确答案为 "
        resultLabel_1.sizeToFit()
        resultLabel_1.frame = CGRectMake(paddingLeft, analysisPaddingTop, resultLabel_1.frame.width, fontSize)
        analysisView.addSubview(resultLabel_1)

        var resultLabel_2 = UILabel()
        resultLabel_2.font = UIFont(name: contentFontName, size: fontSize)
        resultLabel_2.textColor = UIColor.mainColor()
        resultLabel_2.text = "\(NumberToABC(item.answer.results[0].inx.toInt()!))"
        resultLabel_2.sizeToFit()
        resultLabel_2.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width, analysisPaddingTop, resultLabel_2.frame.width, fontSize)
        analysisView.addSubview(resultLabel_2)
        
        if item.answer.userResults.count > 0{
            var resultLabel_3 = UILabel()
            resultLabel_3.font = UIFont(name: contentFontName, size: fontSize)
            resultLabel_3.text = "  您的选择为 "
            resultLabel_3.sizeToFit()
            resultLabel_3.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width + resultLabel_2.frame.width, analysisPaddingTop, resultLabel_3.frame.width, fontSize)
            analysisView.addSubview(resultLabel_3)
            
            var resultLabel_4 = UILabel()
            resultLabel_4.font = UIFont(name: contentFontName, size: fontSize)
            if item.answer.userResults[0].inx != item.answer.results[0].inx{
                resultLabel_4.textColor = UIColor.redColor()
            }else{
                resultLabel_4.textColor = UIColor.mainColor()
            }
            resultLabel_4.text = "\(NumberToABC(item.answer.userResults[0].inx.toInt()!))"
            resultLabel_4.sizeToFit()
            resultLabel_4.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width + resultLabel_2.frame.width + resultLabel_3.frame.width, analysisPaddingTop, resultLabel_3.frame.width, fontSize)
            analysisView.addSubview(resultLabel_4)
        }
        
        var answerResultLabel = UILabel()
        answerResultLabel.font = UIFont(name: contentFontName, size: fontSize)
        answerResultLabel.text = "答案解析:  "
        answerResultLabel.sizeToFit()
        answerResultLabel.frame = CGRectMake(paddingLeft, 2 * analysisPaddingTop + fontSize, answerResultLabel.frame.width, fontSize)
        analysisView.addSubview(answerResultLabel)
        
        var userResultLabel = UILabel()
        userResultLabel.font = UIFont(name: contentFontName, size: userResultSize)
        userResultLabel.textColor = UIColor.blackColor()
        userResultLabel.layer.masksToBounds = true
        userResultLabel.layer.cornerRadius = fontSize / 2
        userResultLabel.layer.borderWidth = 1
        userResultLabel.layer.borderColor = UIColor.buttonGray().CGColor
        if item.userCorrectCount != 0 || item.userWrongCount != 0{
            var userResultText = ""
            if item.userCorrectCount != 0{
                userResultText +=  "   做对\(item.userCorrectCount)次"
            }
            if item.userWrongCount != 0{
                userResultText += "   做错\(item.userWrongCount)次"
            }
            userResultText += "   "
            userResultLabel.text = userResultText
        }
        userResultLabel.sizeToFit()
        userResultLabel.frame = CGRectMake(paddingLeft + answerResultLabel.frame.width ,2 * analysisPaddingTop + fontSize , userResultLabel.frame.width, fontSize)
        userResultLabel.tag = ItemViewTag.userResultLabelTag()
        analysisView.addSubview(userResultLabel)
    }else{
        var resultLabel = UILabel()
        resultLabel.font = UIFont(name: contentFontName, size: fontSize)
        resultLabel.textColor = UIColor.blackColor()
        resultLabel.text = "答案解析"
        resultLabel.sizeToFit()
        resultLabel.frame = CGRectMake(paddingLeft, analysisPaddingTop, resultLabel.frame.width, labelViewHeight)
        analysisView.addSubview(resultLabel)
    }
    
    var  analysisContentLabel = UILabel()
    analysisContentLabel.font = UIFont(name: contentFontName, size: fontSize)
    analysisContentLabel.numberOfLines = 0
    var analysisContentString = ""
    analysisContentString = HtmlToString(item.analysis)
    var analysisContentAttributedString = NSMutableAttributedString(string: analysisContentString)
    paragraphStyle.lineSpacing = lineSpacing
    analysisContentAttributedString.addAttribute(NSParagraphStyleAttributeName, value: paragraphStyle, range: NSMakeRange(0, NSString (string: analysisContentString).length ))
    analysisContentLabel.attributedText = analysisContentAttributedString
    let analysisContentSize = NSString(string: analysisContentString).boundingRectWithSize(CGSize(width: screenWidth - 2 * paddingLeft, height: CGFloat.max), options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: [NSFontAttributeName : analysisContentLabel.font , NSParagraphStyleAttributeName : paragraphStyle], context: nil)
    analysisContentLabel.frame = CGRectMake(paddingLeft, 2 * analysisPaddingTop + labelViewHeight , screenWidth - 2 * paddingLeft , analysisContentSize.height)
    analysisView.addSubview(analysisContentLabel)
    
    analysisView.frame.size = CGSize(width: screenWidth, height: 2 * analysisPaddingTop + labelViewHeight + analysisContentLabel.frame.height + analysisPaddingBottom)
    analysisView.tag = ItemViewTag.analysisViewTag()
    return analysisView
}
