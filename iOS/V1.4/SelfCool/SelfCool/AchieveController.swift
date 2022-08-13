//
//  AchieveController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/18.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AchieveController{
    static func answerResultControl(viewController : UIViewController , rightCount : Int , totalCount : Int) -> Bool{
        if rightCount == totalCount{
            ShowAchieveView(viewController, imageType: AchieveView.imageType.allRight)
            return true
        }else if rightCount == 0{
            ShowAchieveView(viewController, imageType: AchieveView.imageType.allFail)
            return true
        }else{
            return false
        }
    }
    
    static func progressControl(viewController : UIViewController , oldProgress : Double , newProgress : Double) -> Bool{
        if newProgress >= 100 && oldProgress < 100{
            ShowAchieveView(viewController , imageType: AchieveView.imageType.progress100)
            return true
        }else if newProgress >= 60 && oldProgress < 60{
            ShowAchieveView(viewController, imageType: AchieveView.imageType.progress60)
            return true
        }else if newProgress >= 30 && oldProgress < 30{
            ShowAchieveView(viewController, imageType: AchieveView.imageType.progress30)
            return true
        }else if newProgress >= 10 && oldProgress < 10{
            ShowAchieveView(viewController, imageType: AchieveView.imageType.progress10)
            return true
        }else{
            return false
        }
    }
}
