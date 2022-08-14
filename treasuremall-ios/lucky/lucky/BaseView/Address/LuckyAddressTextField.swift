//
//  LuckyAddressTextField.swift
//  lucky
//  地址输入行
//  Created by Farmer Zhu on 2020/9/11.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAddressTextField: UITextField{
    
    var bottomLine: CALayer!
    
    init(frame: CGRect, placeholder: String) {
        super.init(frame: frame)
        self.leftViewMode = UITextField.ViewMode.always
        self.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: frame.height))
        self.tintColor = UIColor.mainYellow()
        self.keyboardType = UIKeyboardType.default
        self.clearButtonMode = UITextField.ViewMode.whileEditing
        self.textColor = UIColor.fontBlack()
        self.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.attributedPlaceholder = NSAttributedString(string: placeholder, attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        
        //底部分割线
        bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    //设为错误 标红
    func setError(){
        bottomLine.backgroundColor = UIColor.mainRed().cgColor
    }
    
    //设为正常
    func setNormal(){
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
