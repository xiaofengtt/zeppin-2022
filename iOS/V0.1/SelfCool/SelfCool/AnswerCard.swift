//
//  AnswerCard.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension AnswerViewController {
    func showAnswerCard(){
        //答题卡
        var answerSubmitView = UIView(frame: CGRectMake(screenWidth * CGFloat(globalItemList.count), 0, screenWidth, itemScrollViewHeight))
        
        //提交按钮
        var submitButton = UIButton(frame: CGRectMake(0, itemScrollViewHeight - submitButtonHeight, screenWidth, submitButtonHeight))
        submitButton.setTitle("提 交", forState: UIControlState.Normal)
        submitButton.titleLabel!.font = UIFont.systemFontOfSize(14)
        submitButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        submitButton.backgroundColor = UIColor.mainColor()
        submitButton.addTarget(self, action: "submitAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
        
        //题目列表
        var optionScrollView = UIScrollView(frame: CGRectMake(0, answerCardPaddingTop, screenWidth, itemScrollViewHeight - answerCardPaddingTop - submitButtonHeight))
        let conlumns = 5
        let rows: Int = (globalItemList.count + conlumns - 1 ) / conlumns
        let itemViewSize = screenWidth / CGFloat(conlumns)
        
        optionScrollView.contentSize = CGSize(width: screenWidth, height: itemViewSize * CGFloat(rows))
        for index in 0 ..< globalItemList.count{
            let row :Int = index / conlumns
            let num :Int = index % conlumns
            var item = globalItemList[index]
            
            var itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
            itemButton.setTitle(String(index+1), forState: UIControlState.Normal)
            itemButton.addTarget(self, action: "backToAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
            itemButton.tag = 200 + index
            
            itemButton.layer.masksToBounds = true
            itemButton.layer.borderWidth = 2
            itemButton.layer.borderColor = UIColor.buttonGray().CGColor
            itemButton.layer.cornerRadius = itemViewSize / 3
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
            itemButton.setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
            itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Highlighted)
            answerCardButtons.append(itemButton)
            optionScrollView.addSubview(itemButton)
        }
        answerSubmitView.addSubview(submitButton)
        answerSubmitView.addSubview(optionScrollView)
        itemScrollView.addSubview(answerSubmitView)
    }
    
    func backToAnswer(sender:UIButton) {
        let index = sender.tag - 200
        itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.bounds.width * CGFloat(index), 0, itemScrollView.bounds.width, itemScrollView.bounds.height), animated: false)
    }
    
    func submitAnswer(sender:UIButton) {
        sender.userInteractionEnabled = false
        self.view.addSubview(loadingView!)
        judgeIsCorrect()
        var answers = getUserAnswer()
        var AnswerParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "ssoUserTest.id" :String(ssoUserTestId) , "subject.id" : String(selectSubject!.id) , "paper.id" : String(paperId) , "answers" : answers])
        httpController.postNSDataByParams("submitAnswer", paramsDictionary: AnswerParams)
    }
    
    func judgeIsCorrect(){
        correctCount = 0
        for index in 0 ..< globalItemList.count{
            var item = globalItemList[index]
            switch item.modelType{
            case 24 :
                if item.answer.userResults.count > 0 {
                    if item.answer.userResults[0].inx == item.answer.results[0].inx{
                        item.answer.completeType = 1
                        correctCount++
                    }
                }
            default :
                if item.answer.userResults.count > 0 {
                    if item.answer.userResults[0].inx == item.answer.results[0].inx{
                        item.answer.completeType = 1
                        correctCount++
                    }
                }
            }            
        }
    }
    
    func getUserAnswer() ->String{
        var result = "["
        for index in 0 ..< globalItemList.count{
            var item = globalItemList[index]
            result += "{\"ssoUserTestItem.id\":"
            result += String(item.ssoUserTestItemId)
            if item.modelType == 24{
                result += ",\"completeType\":"
                result += String(item.answer.completeType)
                if item.answer.userResults.count != 0{
                    result += ",\"isAnswered\":"
                    result += "1"
                    result += ",\"reference\":\""
                    result += item.answer.userResults[0].inx
                    result += "\""
                }else{
                    result += ",\"isAnswered\":"
                    result += "0"
                    result += ",\"reference\":\"\""
                }
                result += "}"
            }
            if index != globalItemList.count - 1 {
                result += ","
            }
        }
        result += "]"
        return result
    }
}