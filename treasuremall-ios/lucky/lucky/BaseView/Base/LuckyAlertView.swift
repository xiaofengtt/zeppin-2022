//
//  LuckyAlertView.swift
//  lucky
//  公用提示框
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit
class LuckyAlertView: MBProgressHUD {
    init(title: String) {
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        self.layer.zPosition = 0.95
        self.removeFromSuperViewOnHide = true
        self.mode = MBProgressHUDMode.text
        //背景
        self.bezelView.style = MBProgressHUDBackgroundStyle.solidColor
        self.bezelView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        //内容
        self.label.text = title
        self.label.textColor = UIColor.white
        self.label.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.label.numberOfLines = 0
        UIApplication.shared.keyWindow?.addSubview(self)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //显示限定时间
    func showByTime(time: TimeInterval){
        self.show(animated: true)
        Timer.scheduledTimer(timeInterval: time, target: self, selector: #selector(hide(animated:)), userInfo: nil, repeats: true)
    }
}
