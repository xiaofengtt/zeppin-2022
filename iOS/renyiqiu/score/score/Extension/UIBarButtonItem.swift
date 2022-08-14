//
//  UIBarButton.swift
//  ntlc
//
//  Created byfarmer on 2017/11/17.
//  Copyright © 2017年 farmer. All rights reserved.
//

import Foundation

extension UIBarButtonItem{
    
    func setBackStyle(){
        self.title = ""
        self.tintColor = UIColor.black
    }
    
    func setWhiteStyle(){
        self.title = ""
        self.tintColor = UIColor.white
    }
    
    func showRedPoint(){
        let pointView = self.customView?.viewWithTag(99998)
        
        if(self.customView != nil && pointView == nil){
            let point = UIImageView(frame: CGRect(x: self.customView!.frame.width - 4, y: 0, width: 4, height: 4))
            point.tag = 99998
            point.layer.cornerRadius = point.frame.width / 2
            point.backgroundColor = UIColor.red
            self.customView!.addSubview(point)
        }
    }
    
    func hideRedPoint(){
        let pointView = self.customView?.viewWithTag(99998)
        
        if(pointView != nil){
            pointView?.removeFromSuperview()
        }
    }
}
