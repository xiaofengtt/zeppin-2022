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
        var resultLabel_2_width : CGFloat = 0
        var resultLabel_2_string = ""
        if item.modelType == 3{
            var resultImage_2 = UIImageView()
            resultImage_2.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width, analysisPaddingTop, fontSize, fontSize)
            resultImage_2.image = UIImage(named: "judge_true_\(item.answer.results[0].inx)")
            resultLabel_2_width = fontSize
            analysisView.addSubview(resultImage_2)
        }else{
            var resultLabel_2 = UILabel()
            resultLabel_2.font = UIFont(name: contentFontName, size: fontSize)
            resultLabel_2.textColor = UIColor.correctColor()
            for i in 0 ..< item.answer.results.count{
                resultLabel_2_string += (NumberToABC(item.answer.results[i].inx.toInt()!))
            }
            resultLabel_2.text = resultLabel_2_string
            resultLabel_2.sizeToFit()
            resultLabel_2.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width, analysisPaddingTop, resultLabel_2.frame.width, fontSize)
            resultLabel_2_width = resultLabel_2.frame.width
            analysisView.addSubview(resultLabel_2)
        }
        if item.answer.userResults.count > 0{
            var resultLabel_3 = UILabel()
            resultLabel_3.font = UIFont(name: contentFontName, size: fontSize)
            resultLabel_3.text = "  您的选择为 "
            resultLabel_3.sizeToFit()
            resultLabel_3.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width + resultLabel_2_width, analysisPaddingTop, resultLabel_3.frame.width, fontSize)
            analysisView.addSubview(resultLabel_3)
            
            if item.modelType == 3{
                var resultImage_4 = UIImageView()
                resultImage_4.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width + resultLabel_2_width + resultLabel_3.frame.width, analysisPaddingTop, fontSize, fontSize)
                if item.answer.userResults[0].inx != item.answer.results[0].inx{
                    resultImage_4.image = UIImage(named: "judge_false_\(item.answer.userResults[0].inx)")
                }else{
                    resultImage_4.image = UIImage(named: "judge_true_\(item.answer.userResults[0].inx)")
                }
                analysisView.addSubview(resultImage_4)
            }else{
                var resultLabel_4 = UILabel()
                var resultLabel_4_string = ""
                resultLabel_4.font = UIFont(name: contentFontName, size: fontSize)
                for i in 0 ..< item.answer.userResults.count{
                    resultLabel_4_string += (NumberToABC(item.answer.userResults[i].inx.toInt()!))
                }
                resultLabel_4.text = resultLabel_4_string
                if resultLabel_2_string != resultLabel_4_string{
                    resultLabel_4.textColor = UIColor.wrongColor()
                }else{
                    resultLabel_4.textColor = UIColor.correctColor()
                }
                resultLabel_4.sizeToFit()
                resultLabel_4.frame = CGRectMake(paddingLeft + resultLabel_1.frame.width + resultLabel_2_width + resultLabel_3.frame.width, analysisPaddingTop, resultLabel_4.frame.width, fontSize)
                analysisView.addSubview(resultLabel_4)
            }
        }
        
        var answerResultLabel = UILabel()
        answerResultLabel.font = UIFont(name: contentFontName, size: fontSize)
        answerResultLabel.text = "答案解析:  "
        answerResultLabel.sizeToFit()
        answerResultLabel.frame = CGRectMake(paddingLeft, 2 * analysisPaddingTop + fontSize, answerResultLabel.frame.width, fontSize)
        analysisView.addSubview(answerResultLabel)
        
        var userCorrectLabel = UIButton()
        userCorrectLabel.userInteractionEnabled = false
        userCorrectLabel.titleLabel?.font = UIFont(name: contentFontName, size: userResultSize)
        userCorrectLabel.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        userCorrectLabel.setBackgroundImage(UIImage(named: "analysis_user_result"), forState: UIControlState.Normal)
        if item.userCorrectCount != 0{
            userCorrectLabel.setTitle("做对\(item.userCorrectCount)次", forState: UIControlState.Normal)
            userCorrectLabel.sizeToFit()
            userCorrectLabel.frame = CGRectMake(paddingLeft + answerResultLabel.frame.width ,2 * analysisPaddingTop + fontSize , userResultSize * 5 , fontSize)
        }
        analysisView.addSubview(userCorrectLabel)
        
        var userWrongLabel = UIButton()
        userWrongLabel.userInteractionEnabled = false
        userWrongLabel.titleLabel?.font = UIFont(name: contentFontName, size: userResultSize)
        userWrongLabel.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        userWrongLabel.setBackgroundImage(UIImage(named: "analysis_user_result"), forState: UIControlState.Normal)
        if item.userWrongCount != 0{
            userWrongLabel.setTitle("做错\(item.userWrongCount)次", forState: UIControlState.Normal)
            userWrongLabel.sizeToFit()
            userWrongLabel.frame = CGRectMake(paddingLeft + answerResultLabel.frame.width + userCorrectLabel.frame.width ,2 * analysisPaddingTop + fontSize , userResultSize * 5, fontSize)
        }
        analysisView.addSubview(userWrongLabel)
        
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
