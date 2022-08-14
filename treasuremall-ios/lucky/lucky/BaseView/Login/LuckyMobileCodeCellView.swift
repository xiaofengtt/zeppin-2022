//
//  LuckyMobileCodeCellView.swift
//  lucky
//  验证码数字框
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMobileCodeCellView: UILabel{
    var bottomLine: CALayer!
    var lightLine: CALayer!
    
    let fontSize = 24 * screenScale
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.textColor = UIColor.fontBlack()
        self.font = UIFont.mediumFont(size: fontSize)
        self.textAlignment = NSTextAlignment.center
        
        //底部线
        bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: self.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.fontGray().cgColor
        self.layer.addSublayer(bottomLine)
        
        //光标
        lightLine = CALayer()
        lightLine.frame = CGRect(x: self.frame.width/2 - 1, y: self.frame.height/4, width: 2, height: self.frame.height/2)
        lightLine.isHidden = true
        lightLine.backgroundColor = UIColor.mainYellow().cgColor
        self.layer.addSublayer(lightLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
