//
//  LuckyMobileCodeSendButton.swift
//  lucky
//  验证码按钮
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit
class LuckyMobileCodeSendButton: UIButton {
    var codeTime : Int?
    var nsTimer : Timer?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.layer.masksToBounds = true
        self.setTitle(NSLocalizedString("Resend", comment: ""), for: UIControl.State.normal)
        self.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.disabled)
        self.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //开始间隔倒计时
    func startTimer(){
        codeTime = 60
        self.isEnabled = false
        self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
        //开启计时器
        nsTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    
    //更新显示时间
    @objc func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime! > 0{
                //大于0显示时间
                self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
            }else{
                //倒计时结束 变为可用状态
                self.isEnabled = true
                self.setTitle(NSLocalizedString("Resend", comment: ""), for: UIControl.State.disabled)
                self.setTitle(NSLocalizedString("Resend", comment: ""), for: UIControl.State.normal)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
}
