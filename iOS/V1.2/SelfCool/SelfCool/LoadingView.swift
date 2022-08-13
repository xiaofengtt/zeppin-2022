//
//  LoadingView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func LoadingView(owner: UIViewController) -> UIView{
    let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: owner, options: nil)
    var loadingView = nibs.lastObject as? UIView
    loadingView!.frame = UIScreen.mainScreen().bounds
    return loadingView!
}
