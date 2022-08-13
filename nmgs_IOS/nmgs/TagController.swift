//
//  ViewTagController.swift
//  nmgs
//
//  Created by zeppin on 2016/10/28.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class TagController {
    class playListTags {
        let categoryButton : Int = 11000
        let categorySelectView : Int = 11100
        let categoryTableView : Int = 12000
        let categoryTableReloadView: Int = 13000
        let categoryTablenoDataLabel: Int = 14000
    }
    
    class playVideoTags {
        let highClarity : Int = 21000
        let middleClarity : Int = 21001
        let lowClarity : Int = 21002
        let highClarityMain : Int = 21004
        let middleClarityMain : Int = 21005
        let lowClarityMain : Int = 21006
        let commodityButton : Int =  22000
        let normalVideoView : Int = 23000
        let fullVideoView : Int = 23001
        let aboutPublishButton: Int = 24000
    }
    
    class businessTags {
        let module1Tag : Int = 31000
        let module2Tag : Int = 32000
        let module3Tag : Int = 33000
        let module4Tag : Int = 34000
        let module5Tag : Int = 35000
        let module6Tag : Int = 36000
    }
    
    class baseTag{
        let badgePoint : Int = 90000
    }
}
