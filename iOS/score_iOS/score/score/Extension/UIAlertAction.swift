//
//  UIAlertAction.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/17.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIAlertAction{
    func setCancleStyle(){
        if #available(iOS 10.0, *) {
            self.setValue(UIColor.alertCancleColor(), forKey: "titleTextColor")
        }
    }
    
    func setSureStyle(){
        if #available(iOS 10.0, *) {
            self.setValue(UIColor.alertSureColor(), forKey: "titleTextColor")
        }
    }
}
