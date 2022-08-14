//
//  UIView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/22.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIView{
    func addBaseShadow(){
        self.layer.shadowPath = CGPath(rect: CGRect.init(x: 4, y: 4, width: self.frame.width - 8, height: self.frame.height - 8), transform: nil)
        self.layer.shadowColor = UIColor.darkGray.cgColor
        self.layer.shadowOpacity = 0.5
        self.layer.shadowOffset = CGSize(width: 0, height: 2)
        self.layer.shadowRadius = 4
    }
}
