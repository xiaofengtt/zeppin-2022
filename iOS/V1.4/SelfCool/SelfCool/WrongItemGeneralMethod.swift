//
//  WrongItemGeneralMethod.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/27.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    func imageButton(sender: UIButton){
        let imageScrollView = self.view.viewWithTag(ItemViewTag.imageScrollViewTag()) as?
        UIScrollView
        if imageScrollView == nil{
            let imageIndex = sender.tag - ItemViewTag.imageButtonTagBase()
            let itemImageView = sender.superview!.viewWithTag(ItemViewTag.imageTagBase() + imageIndex) as! UIImageView
            let image = itemImageView.image
            
            let imageScrollView = ImageScrollView(frame: self.view.frame)
            imageScrollView.image = image
            imageScrollView.delegate = imageScrollView
            self.view.addSubview(imageScrollView)
        }
    }
    
    func showPages(page : Int){
        var front = 2 , back = 2
        if page < 2 {
            front = page
        }
        if globalItemList.count - page < 3{
            back = globalItemList.count - page - 1
        }
        
        for i in -front ..< (back + 1) {
            if !globalItemList[page + i].isShow{
                if globalItemList[page + i].modelType == 1{
                    singleChoiceShow(page + i)
                }else if globalItemList[page + i].modelType == 3{
                    judgeShow(page + i)
                }else if globalItemList[page + i].modelType == 5{
                    multipleChoiceShow(page + i)
                }else{
                    groupItemShow(page + i)
                }
            }
        }
    }
    
    func infoLabelUpdate(infoLabel: UILabel){
        var correctCount = 0
        var failCount = 0
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].answer.completeType == 1{
                correctCount++
            }else if globalItemList[i].answer.completeType == 0{
                failCount++
            }
        }
        if correctCount == 0 && failCount == 0{
            infoLabel.text = "亲爱的，快清空你的错题吧!"
        }else{
            infoLabel.text = "共\(globalItemList.count)道错题, 已做对\(correctCount)道, 已做错\(failCount)道"
        }
    }
    
}