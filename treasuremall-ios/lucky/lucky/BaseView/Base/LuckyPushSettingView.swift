//
//  LuckyPushSettingView.swift
//  lucky
//  引导开启推送
//  Created by Farmer Zhu on 2021/1/19.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyPushSettingView: UIView{
    
    init() {
        super.init(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
        self.backgroundColor = UIColor.white
        self.layer.zPosition = 1
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight, width: 100 * screenScale, height: 40 * screenScale))
        closeButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        let closeIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        closeIconView.image = UIImage(named: "image_close_black")
        closeButton.addSubview(closeIconView)
        self.addSubview(closeButton)
        
        //图
        let imageView = UIImageView(frame: CGRect(x: 0, y: frame.height/6, width: frame.width, height: frame.width / 375 * 200))
        imageView.image = UIImage(named: "image_push_setting")
        self.addSubview(imageView)
        
        //信息
        let label1 = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 35 * screenScale, width: frame.width, height: 22 * screenScale))
        label1.text = NSLocalizedString("Push Notifications Required", comment: "")
        label1.textColor = UIColor.fontRed()
        label1.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        label1.textAlignment = NSTextAlignment.center
        self.addSubview(label1)
        
        let label2 = UILabel(frame: CGRect(x: 0, y: label1.frame.origin.y + label1.frame.height + 20 * screenScale, width: frame.width, height: 18 * screenScale))
        label2.text = NSLocalizedString("Enable notifications to receive winning", comment: "")
        label2.textColor = UIColor.fontBlack()
        label2.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        label2.textAlignment = NSTextAlignment.center
        self.addSubview(label2)
        
        let label3 = UILabel(frame: CGRect(x: 0, y: label2.frame.origin.y + label2.frame.height, width: frame.width, height: 18 * screenScale))
        label3.text = NSLocalizedString("information", comment: "")
        label3.textColor = label2.textColor
        label3.font = label2.font
        label3.textAlignment = NSTextAlignment.center
        self.addSubview(label3)
        
        let label4 = UILabel(frame: CGRect(x: 0, y: label3.frame.origin.y + label3.frame.height + 12 * screenScale, width: frame.width, height: 18 * screenScale))
        label4.text = NSLocalizedString("You can always turn them off later", comment: "")
        label4.textColor = UIColor.fontDarkGray()
        label4.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        label4.textAlignment = NSTextAlignment.center
        self.addSubview(label4)
        
        //拒绝按钮
        let noButton = UIButton(frame: CGRect(x: 38 * screenScale, y: label4.frame.origin.y + label4.frame.height + 54 * screenScale, width: 130 * screenScale, height: 44 * screenScale))
        noButton.layer.borderWidth = 1
        noButton.layer.borderColor = UIColor.mainYellow().cgColor
        noButton.layer.cornerRadius =  6 * screenScale
        noButton.setTitle(NSLocalizedString("No thanks", comment: ""), for: UIControl.State.normal)
        noButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        noButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        noButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        self.addSubview(noButton)
        
        //同意按钮
        let okButton = UIButton(frame: CGRect(x: frame.width - (noButton.frame.origin.x + noButton.frame.width) , y: noButton.frame.origin.y, width: noButton.frame.width, height: noButton.frame.height))
        okButton.backgroundColor = UIColor.mainYellow()
        okButton.layer.cornerRadius = noButton.layer.cornerRadius
        okButton.setTitle("Okay", for: UIControl.State.normal)
        okButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        okButton.titleLabel?.font = noButton.titleLabel?.font
        okButton.addTarget(self, action: #selector(toSetting), for: UIControl.Event.touchUpInside)
        self.addSubview(okButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //关闭
    @objc func close(){
        self.removeFromSuperview()
    }
    
    //去设置
    @objc func toSetting(){
        LuckyRemotePushManager.toSetting()
        self.removeFromSuperview()
    }
}
