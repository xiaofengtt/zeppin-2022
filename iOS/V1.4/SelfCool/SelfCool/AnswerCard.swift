//
//  AnswerCard.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController {
    func showAnswerCard(){
        var pageNum = 0
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].isGroup{
                pageNum += globalItemList[i].children!.count
            }else{
                pageNum++
            }
        }
        
        //答题卡
        let answerSubmitView = UIView(frame: CGRectMake(screenWidth * CGFloat(globalItemList.count), 0, screenWidth, itemScrollViewHeight))
        
        //提交按钮
        answerCardSubmitButton = UIButton(frame: CGRectMake(0, itemScrollViewHeight - submitButtonHeight, screenWidth, submitButtonHeight))
        answerCardSubmitButton!.setTitle("提 交", forState: UIControlState.Normal)
        answerCardSubmitButton!.titleLabel!.font = UIFont.systemFontOfSize(18)
        answerCardSubmitButton!.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        answerCardSubmitButton!.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        answerCardSubmitButton!.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        answerCardSubmitButton!.addTarget(self, action: "submitAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
        
        //题目列表
        let optionScrollView = UIScrollView(frame: CGRectMake(0, answerCardPaddingTop, screenWidth, itemScrollViewHeight - answerCardPaddingTop - submitButtonHeight))
        let conlumns = 5
        let rows: Int = (pageNum + conlumns - 1 ) / conlumns
        let itemViewSize = screenWidth / CGFloat(conlumns)
        
        optionScrollView.contentSize = CGSize(width: screenWidth, height: itemViewSize * CGFloat(rows))
        var buttonIndex = 0
        for index in 0 ..< globalItemList.count{
            let item = globalItemList[index]
            if item.isGroup{
                for i in 0 ..< item.children!.count{
                    let row :Int = buttonIndex / conlumns
                    let num :Int = buttonIndex % conlumns
                    let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
                    itemButton.setTitle(String(buttonIndex+1), forState: UIControlState.Normal)
                    itemButton.addTarget(self, action: "backToAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
                    itemButton.layer.masksToBounds = true
                    itemButton.layer.borderWidth = 2
                    itemButton.layer.borderColor = UIColor.buttonGray().CGColor
                    itemButton.layer.cornerRadius = itemViewSize / 3
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
                    itemButton.setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
                    itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Highlighted)
                    itemButton.tag = ItemViewTag.answerCardItemButtonTagBase() + index + ItemViewTag.groupViewTagBase() * (i + 1)
                    answerCardButtons.append(itemButton)
                    optionScrollView.addSubview(itemButton)
                    buttonIndex++
                }
            }else{
                let row :Int = buttonIndex / conlumns
                let num :Int = buttonIndex % conlumns
                
                let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
                itemButton.setTitle(String(buttonIndex+1), forState: UIControlState.Normal)
                itemButton.addTarget(self, action: "backToAnswer:", forControlEvents: UIControlEvents.TouchUpInside)
                itemButton.layer.masksToBounds = true
                itemButton.layer.borderWidth = 2
                itemButton.layer.borderColor = UIColor.buttonGray().CGColor
                itemButton.layer.cornerRadius = itemViewSize / 3
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
                itemButton.setTitleColor(UIColor.buttonGray(), forState: UIControlState.Normal)
                itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Highlighted)
                itemButton.tag = ItemViewTag.answerCardItemButtonTagBase() + index
                answerCardButtons.append(itemButton)
                optionScrollView.addSubview(itemButton)
                buttonIndex++
            }
        }
        answerSubmitView.addSubview(answerCardSubmitButton!)
        answerSubmitView.addSubview(optionScrollView)
        itemScrollView.addSubview(answerSubmitView)
    }
    
    func backToAnswer(sender:UIButton) {
        if sender.tag < ItemViewTag.groupViewTagBase(){
            let page = sender.tag - ItemViewTag.answerCardItemButtonTagBase()
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
        }else{
            let page = (sender.tag % ItemViewTag.groupViewTagBase()) - ItemViewTag.answerCardItemButtonTagBase()
            let childPage = sender.tag / ItemViewTag.groupViewTagBase() - 1
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
            let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            childrenScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(childPage), 0, screenWidth, childrenScrollView.frame.height), animated: false)
        }
    }
    
    func submitAnswer(sender:UIButton) {
        sender.userInteractionEnabled = false
        self.view.addSubview(loadingView!)
        judgeIsCorrect()
        let answers = getUserAnswer()
        let AnswerParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "ssoUserTest.id" :String(ssoUserTestId) , "subject.id" : String(selectSubject!.id) , "paper.id" : String(paperId) , "answers" : answers])
        httpController.postNSDataByParams("submitAnswer", paramsDictionary: AnswerParams)
    }
    
}