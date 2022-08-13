//
//  BaseNavigationBar.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class BaseNavigationBar :UINavigationController{
    override func preferredStatusBarStyle() -> UIStatusBarStyle {
        return UIStatusBarStyle.LightContent
    }
    override func childViewControllerForStatusBarStyle() -> UIViewController? {
        return self.childViewControllers[0] as? UIViewController
    }
}
