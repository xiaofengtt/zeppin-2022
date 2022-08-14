//
//  LuckyPasswordTextField.swift
//  lucky
//  密码框
//  Created by Farmer Zhu on 2020/8/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPasswordTextField: UITextField{
    var bottomLine: CALayer!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        self.clearButtonMode = UITextField.ViewMode.whileEditing
        self.leftView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 10 * screenScale, height: self.frame.height)))
        self.tintColor = UIColor.mainYellow()
        self.keyboardType = UIKeyboardType.asciiCapable
        self.autocapitalizationType = UITextAutocapitalizationType.none
        self.isSecureTextEntry = true
        self.textColor = UIColor.fontBlack()
        
//        let clearButton = self.value(forKey: "clearButton") as! UIButton
//        clearButton.setImage(UIImage(named: "image_close_grey"), for: UIControl.State.normal)
//        clearButton.setImage(UIImage(named: "image_close_grey"), for: UIControl.State.highlighted)
        
        //底部提示线
        bottomLine = CALayer()
        bottomLine.isHidden = true
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.fontRed().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    //选中状态 有错误时选中 显示提示线
    override var isSelected: Bool{
        willSet{
            bottomLine.isHidden = !newValue
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
