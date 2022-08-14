//
//  LuckyRetrieveCodeSendButton.swift
//  lucky
//  找回密码 验证码发送按钮
//  Created by Farmer Zhu on 2020/8/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit
class LuckyRetrieveCodeSendButton: UIButton {
    var codeTime: Int! = 0
    var nsTimer: Timer?
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.layer.masksToBounds = true
        self.setTitle(NSLocalizedString("Send", comment: ""), for: UIControl.State.normal)
        self.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        self.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 4 * screenScale
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //设置可用
    override var isEnabled: Bool{
        willSet{
            if newValue{
                //可用
                self.backgroundColor = UIColor.clear
                self.layer.borderColor = UIColor.mainYellow().cgColor
                self.layer.borderWidth = 1 * screenScale
            }else{
                //不可用
                self.backgroundColor = UIColor.backgroundGray()
                self.layer.borderColor = UIColor.clear.cgColor
                self.layer.borderWidth = 0
            }
        }
    }
    
    //开始倒计时
    func startTimer(){
        codeTime = 60
        self.isEnabled = false
        self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
        //开启计时器
        nsTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    
    //更新时间
    @objc func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime! > 0{
                //时间大于0 显示时间
                self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
            }else{
                //结束倒计时 按钮变可用
                self.isEnabled = true
                self.setTitle(NSLocalizedString("Resend", comment: ""), for: UIControl.State.disabled)
                self.setTitle(NSLocalizedString("Resend", comment: ""), for: UIControl.State.normal)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
}
