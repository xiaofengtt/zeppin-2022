//
//  AlertCodeView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/5.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class AlertCodeView: UIView {
    
    var codeButton: CodeSendButton!
    var cancleButton: UIButton!
    var sureButton: UIButton!
    var codeInput: UITextField!
    
    init() {
        super.init(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
        self.layer.zPosition = 0.8
        self.isHidden = true
        self.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        
        let sendFrame = UIView(frame: CGRect(x: (self.frame.width - 270 * screenScale)/2, y: self.frame.height/3, width: 270 * screenScale, height: 185 * screenScale))
        sendFrame.backgroundColor = UIColor.white
        sendFrame.layer.cornerRadius = 5 * screenScale
        
        let label = UILabel(frame: CGRect(x: 0, y: 34 * screenScale, width: sendFrame.frame.width, height: 17 * screenScale))
        label.text = "发送至\(user!.phone)的手机"
        label.textColor = UIColor.fontGray()
        label.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        label.textAlignment = NSTextAlignment.center
        sendFrame.addSubview(label)
        
        let inputView = UIView(frame: CGRect(x: 15 * screenScale, y: label.frame.origin.y + label.frame.height + 23 * screenScale, width: sendFrame.frame.width - 15 * 2 * screenScale, height: 45 * screenScale))
        inputView.backgroundColor = UIColor.backgroundGray()
        inputView.layer.cornerRadius = 5 * screenScale
        codeInput = UITextField(frame: CGRect(x: 12 * screenScale, y: 0, width: inputView.frame.width - 12 * 2 * screenScale, height: inputView.frame.height))
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.numFont(size: UIFont.biggestSize() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: "请输入验证码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        inputView.addSubview(codeInput)
        codeButton = CodeSendButton(frame: CGRect(x: inputView.frame.width - (80 + 7) * screenScale, y: 0, width: 80 * screenScale, height: inputView.frame.height))
        inputView.addSubview(codeButton)
        sendFrame.addSubview(inputView)
        
        let buttonView = UIView(frame: CGRect(x: 0, y: sendFrame.frame.height - 48 * screenScale, width: sendFrame.frame.width, height: 48 * screenScale))
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: sendFrame.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        buttonView.layer.addSublayer(topLine)
        let middleLine = CALayer()
        middleLine.frame = CGRect(x: sendFrame.frame.width/2, y: 0, width: 1, height: buttonView.frame.height)
        middleLine.backgroundColor = UIColor.backgroundGray().cgColor
        buttonView.layer.addSublayer(middleLine)
        cancleButton = UIButton(frame: CGRect(x: 0, y: 0, width: buttonView.frame.width/2, height: buttonView.frame.height))
        cancleButton.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIControlState.normal)
        cancleButton.setTitle("取消", for: UIControlState.normal)
        cancleButton.setTitleColor(UIColor.alertCancleColor(), for: UIControlState.normal)
        cancleButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        cancleButton.addTarget(self, action: #selector(cancle(_:)), for: UIControlEvents.touchUpInside)
        buttonView.addSubview(cancleButton)
        sureButton = UIButton(frame: CGRect(x: buttonView.frame.width/2, y: 0, width: buttonView.frame.width/2, height: buttonView.frame.height))
        sureButton.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIControlState.normal)
        sureButton.setTitle("确认", for: UIControlState.normal)
        sureButton.setTitleColor(UIColor.alertSureColor(), for: UIControlState.normal)
        sureButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        buttonView.addSubview(sureButton)
        sendFrame.addSubview(buttonView)
        
        self.addSubview(sendFrame)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.codeInput.endEditing(true)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func cancle(_ sender: UIButton) {
        self.codeInput.endEditing(true)
        self.isHidden = true
    }
}
