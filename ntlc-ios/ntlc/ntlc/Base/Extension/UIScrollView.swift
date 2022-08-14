//
//  UIScrollView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/24.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIScrollView{
    
    func addRefreshView(){
        let refreshView = ScrollRefreshView(parent: self)
        refreshView.tag = 99999
        self.addSubview(refreshView)
    }
    
    func getRefreshView() -> ScrollRefreshView{
        let refreshView = self.viewWithTag(99999) as! ScrollRefreshView
        return refreshView
    }
    
    func refreshToAble(){
        let refreshView = self.viewWithTag(99999) as! ScrollRefreshView
        refreshView.setAble()
    }
    
    func refreshToNormal(){
        let refreshView = self.viewWithTag(99999) as! ScrollRefreshView
        refreshView.setNormal()
    }
}

