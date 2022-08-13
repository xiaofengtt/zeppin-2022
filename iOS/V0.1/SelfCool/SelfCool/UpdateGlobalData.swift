//
//  UpdateGlobalData.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/11.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension AnswerResultViewController {
    func UpdateGlobalData(data: NSDictionary){
        selectSubject!.preparingProgress = data.objectForKey("progress") as! Double
        selectSubject!.doneItemCount = data.objectForKey("brushItemCount") as! Int
        selectSubject!.correctRate = data.objectForKey("correctRate") as! Double
        selectSubject!.rankingRate = data.objectForKey("rankingRate") as! Double
        selectSubject!.countDown = data.objectForKey("nextTestdayCount") as! Int
        for index in 0 ..< globalUserSubjectList.count{
            if globalUserSubjectList[index].id == selectSubject!.id{
                globalUserSubjectList[index] == selectSubject!
            }
        }
        let changeKnowledgeList = data.objectForKey("changeKnowledges") as! NSArray
        KnowledgeChange(changeKnowledgeList, knowledgeList: globalKnowledgeList)
    }
    
    func KnowledgeChange(changeKnowledgeList : NSArray , knowledgeList: [KnowledgeModel]){
        for index in 0 ..< changeKnowledgeList.count{
            let changeKnowledgeData = changeKnowledgeList[index] as! NSDictionary
            for i in 0 ..< knowledgeList.count{
                if knowledgeList[i].id == changeKnowledgeData.objectForKey("id") as! Int{
                    knowledgeList[i].correctCount = changeKnowledgeData.objectForKey("rightCount") as! Int
                    if changeKnowledgeData.objectForKey("hasChild") as! Bool {
                        let childChangeKnowledgeList = changeKnowledgeData.objectForKey("data") as! NSArray
                        var childKnowledgeList = knowledgeList[i].children!
                        KnowledgeChange(childChangeKnowledgeList, knowledgeList: childKnowledgeList)
                    }
                }
            }
        }
    }
}