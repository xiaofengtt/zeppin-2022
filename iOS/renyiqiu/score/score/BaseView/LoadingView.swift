//
//  LoadingView.swift
//  ntlc
//
//  Created byfarmer on 2017/11/24.
//  Copyright © 2017年 farmer. All rights reserved.
//

import UIKit

class LoadingView: MBProgressHUD {
    
    init() {
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        self.layer.zPosition = 0.9
        self.removeFromSuperViewOnHide = true
        self.label.text = "加载中..."
        self.minShowTime = 0.5
        UIApplication.shared.keyWindow?.addSubview(self)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
