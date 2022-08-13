//
//  UIButtonExtension.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import Foundation

extension UIButton{
    func hasRedPoint(has : Bool){
        if has{
            if self.viewWithTag(998) == nil{
                let redPointView = UIImageView(frame: CGRect(x: self.frame.width * 0.85, y: self.frame.height * 0.15, width: 4, height: 4))
                redPointView.tag = 998
                redPointView.image = UIImage.imageWithColor(UIColor.redColor())
                redPointView.layer.masksToBounds = true
                redPointView.layer.cornerRadius = 2
                self.addSubview(redPointView)
                self.layoutSubviews()
            }
        }else{
            self.viewWithTag(998)?.removeFromSuperview()
            self.layoutSubviews()
        }
    }
}