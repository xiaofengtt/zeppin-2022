//
//  UnstandardItemGeneralMethod.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UnstandardItemViewController{
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
                if globalItemList[page + i].modelType == 6{
                    writtenResponseShow(page + i)
                }else{
                    groupItemShow(page + i)
                }
            }
        }
    }
    
    func infoLabelUpdate(infoLabel: UILabel){
        var looked = 0
        var count = 0
        for index in 0 ..< globalItemList.count{
            if globalItemList[index].isGroup{
                for i in 0 ..< globalItemList[index].children!.count{
                    if globalItemList[index].children![i].answer.completeType > -1{
                        looked++
                    }
                    count++
                }
            }else{
                if globalItemList[index].answer.completeType > -1{
                    looked++
                }
                count++
            }
        }
        infoLabel.text = "已看\(looked)道, 未看\(count - looked)道, 共\(count)道"
    }
}