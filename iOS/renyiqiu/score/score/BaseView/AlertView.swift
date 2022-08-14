//
//  AlertView.swift
//  ntlc
//
//  Created byfarmer on 2017/11/30.
//  Copyright © 2017年 farmer. All rights reserved.
//

import UIKit

class AlertView: MBProgressHUD {
    
    init(title: String) {
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        self.layer.zPosition = 1
        self.removeFromSuperViewOnHide = true
        self.mode = MBProgressHUDMode.text
        self.bezelView.backgroundColor = UIColor.black
        self.label.text = title
        self.label.textColor = UIColor.white
        self.label.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        self.label.numberOfLines = 0
         UIApplication.shared.keyWindow?.addSubview(self)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func showByTime(time: TimeInterval){
        self.show(animated: true)
        Timer.scheduledTimer(timeInterval: time, target: self, selector: #selector(hide(animated:)), userInfo: nil, repeats: true)
    }
}
