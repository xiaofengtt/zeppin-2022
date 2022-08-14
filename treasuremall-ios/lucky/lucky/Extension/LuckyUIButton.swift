//
//  LuckyUIButton.swift
//  lucky
//
//  Created by Farmer Zhu on 2020/8/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

extension UIButton{
    //增加按钮右上红色数字标
    func addNumberPoint(orign: CGPoint, number: Int){
        removeNumberPoint()
        
        let label = UILabel()
        label.tag = LuckyTagManager.globalTags.redNumberPoint
        label.backgroundColor = UIColor.mainRed()
        label.text = number > 99 ? "99+" : "\(number)"
        label.textColor = UIColor.white
        label.font = UIFont.mediumFont(size: 10 * screenScale)
        label.textAlignment = NSTextAlignment.center
        label.sizeToFit()
        label.frame = CGRect(x: self.frame.width - orign.x - (label.frame.width + 8 * screenScale), y: orign.y, width: label.frame.width + 8 * screenScale, height: 16 * screenScale)
        label.layer.masksToBounds = true
        label.layer.cornerRadius = label.frame.height/2
        self.addSubview(label)
    }
    
    //删除按钮右上红色数字标
    func removeNumberPoint(){
        self.viewWithTag(LuckyTagManager.globalTags.redNumberPoint)?.removeFromSuperview()
    }
}
