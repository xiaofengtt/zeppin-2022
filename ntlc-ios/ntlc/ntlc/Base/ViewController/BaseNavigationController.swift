//
//  BaseNavigationController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BaseNavigationController: UINavigationController{
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = true
        self.navigationBar.titleTextAttributes = [NSAttributedStringKey.font: UIFont.mainFont(size: 18) as Any, NSAttributedStringKey.foregroundColor: UIColor.white]
        self.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clear)
    }
    
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return UIStatusBarStyle.lightContent
    }
}
