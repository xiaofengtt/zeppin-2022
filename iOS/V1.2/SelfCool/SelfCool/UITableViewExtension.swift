//
//  UITableViewExtension.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/6/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UITableView{
    func remoteBorder(){
        if self.respondsToSelector(Selector("separatorInset")){
            self.separatorInset = UIEdgeInsetsZero
        }
        if self.respondsToSelector(Selector("layoutMargins")){
            self.layoutMargins = UIEdgeInsetsZero
        }
    }
}

extension UITableViewCell{
    func remoteBorder(){
        if self.respondsToSelector(Selector("separatorInset")){
            self.separatorInset = UIEdgeInsetsZero
        }
        if self.respondsToSelector(Selector("layoutMargins")){
            self.layoutMargins = UIEdgeInsetsZero
        }
    }
}