//
//  AddLoginView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/5.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func AddLoginView(scrollview : UIScrollView , owner : UIViewController){
    scrollview.contentSize = CGSize(width: screenWidth * 4, height: scrollview.frame.height)
    for i in 0 ..< 4{
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoginView_\(i)", owner: owner, options: nil)
        let loginView = nibs.lastObject as? UIView
        loginView!.frame = CGRectMake(screenWidth * CGFloat(i), 0, scrollview.frame.width, scrollview.frame.height)
        scrollview.addSubview(loginView!)
    }
}