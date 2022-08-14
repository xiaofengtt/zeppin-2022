//
//  BusinessModuleTitleView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/10.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class BusinessModuleTitleView: UIView{
    
    let paddingEdge :CGFloat = 10
    let spaceLineWidth : CGFloat = 3
    
    init(frame: CGRect ,name: String) {
        super.init(frame: frame)
        self.backgroundColor =  UIColor.white
        let lineView = UIView(frame: CGRect(x: paddingEdge, y: self.frame.height/3, width: spaceLineWidth, height: self.frame.height/3))
        lineView.backgroundColor = UIColor.mobileBlue()
        self.addSubview(lineView)
        let titleLabel = UILabel()
        titleLabel.font = UIFont.systemFont(ofSize: 14)
        titleLabel.textAlignment = NSTextAlignment.left
        titleLabel.text = name
        titleLabel.sizeToFit()
        titleLabel.frame.origin = CGPoint(x: lineView.frame.width + paddingEdge*2, y: (frame.height - titleLabel.frame.height)/2)
        self.addSubview(titleLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
