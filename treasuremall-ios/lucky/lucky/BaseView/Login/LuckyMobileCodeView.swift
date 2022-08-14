//
//  LuckyMobileCodeView.swift
//  lucky
//  四位验证码框
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
class LuckyMobileCodeView : UIView{
    var codeInput: UITextField!
    var codeSquare1: LuckyMobileCodeCellView!
    var codeSquare2: LuckyMobileCodeCellView!
    var codeSquare3: LuckyMobileCodeCellView!
    var codeSquare4: LuckyMobileCodeCellView!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        let squareWidth = 40 * screenScale
        let spaceWidth = (self.frame.width - squareWidth * 4)/3
        
        //四位 数字框
        codeSquare1 = LuckyMobileCodeCellView(frame: CGRect(x: 0, y: 0, width: squareWidth, height: self.frame.height))
        codeSquare1.lightLine.isHidden = false
        codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
        self.addSubview(codeSquare1)
        codeSquare2 = LuckyMobileCodeCellView(frame: CGRect(x: squareWidth + spaceWidth, y: 0, width: squareWidth, height: self.frame.height))
        self.addSubview(codeSquare2)
        codeSquare3 = LuckyMobileCodeCellView(frame: CGRect(x: squareWidth * 2 + spaceWidth * 2, y: 0, width: squareWidth, height: self.frame.height))
        self.addSubview(codeSquare3)
        codeSquare4 = LuckyMobileCodeCellView(frame: CGRect(x: squareWidth * 3 + spaceWidth * 3, y: 0, width: squareWidth, height: self.frame.height))
        self.addSubview(codeSquare4)
        
        //隐形输入框
        codeInput = UITextField(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height))
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.textColor = UIColor.clear
        codeInput.tintColor = UIColor.clear
        codeInput.clearButtonMode = UITextField.ViewMode.never
        self.addSubview(codeInput)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //赋值
    func setValue(value: String){
        //显示框赋值
        if(value.length > 0){
            codeSquare1.text = value[0]
        }else{
            codeSquare1.text = ""
        }
        if(value.length > 1){
            codeSquare2.text = value[1]
        }else{
            codeSquare2.text = ""
        }
        if(value.length > 2){
            codeSquare3.text = value[2]
        }else{
            codeSquare3.text = ""
        }
        if(value.length > 3){
            codeSquare4.text = value[3]
        }else{
            codeSquare4.text = ""
        }
        
        //当前光标控制
        if(value.length == 0){
            //第一格
            codeSquare1.lightLine.isHidden = false
            codeSquare2.lightLine.isHidden = true
            codeSquare3.lightLine.isHidden = true
            codeSquare4.lightLine.isHidden = true
            codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare2.bottomLine.backgroundColor = UIColor.fontGray().cgColor
            codeSquare3.bottomLine.backgroundColor = UIColor.fontGray().cgColor
            codeSquare4.bottomLine.backgroundColor = UIColor.fontGray().cgColor
        }else if(value.length == 1){
            //第二格
            codeSquare1.lightLine.isHidden = true
            codeSquare2.lightLine.isHidden = false
            codeSquare3.lightLine.isHidden = true
            codeSquare4.lightLine.isHidden = true
            codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare2.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare3.bottomLine.backgroundColor = UIColor.fontGray().cgColor
            codeSquare4.bottomLine.backgroundColor = UIColor.fontGray().cgColor
        }else if(value.length == 2){
            //第三格
            codeSquare1.lightLine.isHidden = true
            codeSquare2.lightLine.isHidden = true
            codeSquare3.lightLine.isHidden = false
            codeSquare4.lightLine.isHidden = true
            codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare2.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare3.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare4.bottomLine.backgroundColor = UIColor.fontGray().cgColor
        }else if(value.length == 3){
            //第四格
            codeSquare1.lightLine.isHidden = true
            codeSquare2.lightLine.isHidden = true
            codeSquare3.lightLine.isHidden = true
            codeSquare4.lightLine.isHidden = false
            codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare2.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare3.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare4.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
        }else if(value.length == 4){
            //输入完毕 无光标
            codeSquare1.lightLine.isHidden = true
            codeSquare2.lightLine.isHidden = true
            codeSquare3.lightLine.isHidden = true
            codeSquare4.lightLine.isHidden = true
            codeSquare1.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare2.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare3.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
            codeSquare4.bottomLine.backgroundColor = UIColor.fontBlack().cgColor
        }
    }
}
