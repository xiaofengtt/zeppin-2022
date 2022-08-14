//
//  UITabBarExtension.swift
//  nmgs
//
//  Created by zeppin on 2016/11/28.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

extension UITabBar{
    
    func showBadgePoint(index: Int){
        let itemNumber: CGFloat = 5
        let pointSize: CGFloat = 10
        
        self.hideBadgePoint(index: index)
        let badgeView = UIView()
        badgeView.tag = TagController.baseTag().badgePoint + index
        badgeView.layer.cornerRadius = pointSize/2
        badgeView.backgroundColor = UIColor.red
        
        let percentX = (CGFloat(index) + 0.6) / itemNumber
        let x = percentX * self.frame.size.width
        let y = 0.1 * self.frame.size.height
        badgeView.frame = CGRect(x: x, y: y, width: pointSize, height: pointSize)
        self.addSubview(badgeView)
    }
    
    func hideBadgePoint(index: Int){
        self.viewWithTag(TagController.baseTag().badgePoint + index)?.removeFromSuperview()
    }
}
