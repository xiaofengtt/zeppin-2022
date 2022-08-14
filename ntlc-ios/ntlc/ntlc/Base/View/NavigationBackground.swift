//
//  NavigationBackground.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/12.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class NavigationBackground: UIView {
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: navigationFrame.origin.y + navigationFrame.height)))
        
        self.backgroundColor = UIColor.mainBlue()
        self.layer.zPosition = 0.75
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    init() {
        super.init(frame: CGRect.zero)
    }
}
