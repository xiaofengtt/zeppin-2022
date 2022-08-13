//
//  UIScrollViewExtension.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/27.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
extension UIScrollView{
    func makeVerticalScrollEnable(){
        if self.contentSize.height <= self.frame.height{
            self.contentSize.height = self.frame.height + 1
        }
    }
    
    func addGrayBottomBounceView(){
        var grayBounceView = UIView(frame: CGRectMake(0, self.contentSize.height, screenWidth, 1000))
        grayBounceView.tag = ItemViewTag.grayBounceViewTag()
        grayBounceView.backgroundColor = UIColor.backgroundGray()
        self.addSubview(grayBounceView)
    }
}