//
//  LuckyDetailTypeSelectButton.swift
//  lucky
//  详情页记录类型切换按钮
//  Created by Farmer Zhu on 2020/9/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailTypeSelectButton: UIButton{
    var selectedLine: CALayer!
    var type: String!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.titleLabel?.numberOfLines = 2
        self.setTitleColor(UIColor.mainYellow(), for: UIControl.State.selected)
        self.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        self.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.titleLabel?.textAlignment = NSTextAlignment.center
        
        //选中标线
        selectedLine = CALayer()
        selectedLine.isHidden = true
        selectedLine.frame = CGRect(x: frame.width/8*3, y: frame.height - 3, width: frame.width/4, height: 3)
        selectedLine.backgroundColor = UIColor.mainYellow().cgColor
        self.layer.addSublayer(selectedLine)
    }
    
    //选中状态控制选中标线
    override var isSelected: Bool{
        willSet{
            selectedLine.isHidden = !newValue
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
