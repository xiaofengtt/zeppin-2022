//
//  CodeSendButton.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/28.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class CodeSendButton: UIButton {
    var codeTime : Int?
    var nsTimer : Timer?
    
    let normalColor = UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.adjustsImageWhenDisabled = false
        self.adjustsImageWhenHighlighted = false
        self.setBackgroundImage(UIImage.imageWithColor(UIColor.backgroundGray()), for: UIControlState.normal)
        self.layer.cornerRadius = frame.height / 2
        self.layer.masksToBounds = true
        self.setTitle("获取验证码", for: UIControlState.normal)
        self.setTitleColor(normalColor, for: UIControlState.normal)
        self.setTitleColor(UIColor.fontDarkGray(), for: UIControlState.disabled)
        self.titleLabel?.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func startTimer(){
        codeTime = 60
        self.isEnabled = false
        self.backgroundColor = UIColor.darkGray.withAlphaComponent(0.6)
        self.setTitle("重新获取(\(codeTime!))", for: UIControlState.disabled)
        nsTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    
    @objc func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime! > 0{
                self.setTitle("重新获取(\(codeTime!))", for: UIControlState.disabled)
            }else{
                self.isEnabled = true
                self.setTitle("重新获取", for: UIControlState.normal)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
}
