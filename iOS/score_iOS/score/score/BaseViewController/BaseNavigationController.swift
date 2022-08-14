//
//  BaseNavigationController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BaseNavigationController: UINavigationController{
    
    var statusBarStyle: UIStatusBarStyle = UIStatusBarStyle.default
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = true
        self.navigationBar.titleTextAttributes = [NSAttributedString.Key.font: UIFont.mainFont(size: 18) as Any, NSAttributedString.Key.foregroundColor: UIColor.white]
        self.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clear)
        
        self.navigationBar.backIndicatorImage = UIImage.imageWithColor(UIColor.clear)
        self.navigationBar.backIndicatorTransitionMaskImage = UIImage.imageWithColor(UIColor.clear)
    }
    
    
    
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return statusBarStyle
    }
}
