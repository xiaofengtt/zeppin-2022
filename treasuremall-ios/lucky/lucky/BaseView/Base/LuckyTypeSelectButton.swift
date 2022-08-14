//
//  LuckyTypeSelectButton.swift
//  lucky
//  泛用分类按钮
//  Created by Farmer Zhu on 2020/8/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTypeSelectButton: UIButton{
    var selectedLine: CALayer!
    var type: String!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.setTitleColor(UIColor.fontBlack(), for: UIControl.State.selected)
        self.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        self.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        
        //选中标线
        selectedLine = CALayer()
        selectedLine.isHidden = true
        selectedLine.frame = CGRect(x: frame.width/3, y: frame.height - 2, width: frame.width/3, height: 2)
        selectedLine.backgroundColor = UIColor.fontBlack().cgColor
        self.layer.addSublayer(selectedLine)
    }
    
    //选中状态 选中线控制
    override var isSelected: Bool{
        willSet{
            selectedLine.isHidden = !newValue
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
