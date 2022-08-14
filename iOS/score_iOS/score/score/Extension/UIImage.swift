//
//  UIImage.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

import UIKit
extension UIImage{
    class func imageWithColor(_ color : UIColor) -> UIImage {
        let rect = CGRect(x: 0, y: 0, width: 1, height: 1)
        UIGraphicsBeginImageContext(rect.size)
        let context = UIGraphicsGetCurrentContext()
        context?.setFillColor(color.cgColor)
        context?.fill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
    
    func imageWithScale(_ scale : CGFloat) -> UIImage{
        UIGraphicsBeginImageContextWithOptions(CGSize(width: self.size.width * scale , height: self.size.height * scale),false,UIScreen.main.scale)
        self.draw(in: CGRect(x: 0, y: 0, width: self.size.width * scale, height: self.size.height * scale))
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
    
    func imageWithAlpha(_ alpha: CGFloat) -> UIImage{
        UIGraphicsBeginImageContextWithOptions(self.size, false, UIScreen.main.scale);
        let context = UIGraphicsGetCurrentContext();
        let area = CGRect(origin: CGPoint.zero, size: self.size)
        context?.scaleBy(x: 1, y: -1)
        context?.translateBy(x: 0, y: area.size.height * (-1))
        context?.setBlendMode(CGBlendMode.multiply)
        context?.setAlpha(alpha)
        context?.draw(self.cgImage!, in: area)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
    
    class func getRoundImage(_ size: CGSize, color: UIColor) -> UIImage {
        let rect = CGRect(origin: CGPoint.zero, size: size)
        UIGraphicsBeginImageContextWithOptions(size, false, UIScreen.main.scale)
        let context = UIGraphicsGetCurrentContext()
        context?.addEllipse(in: rect)
        context?.setFillColor(color.cgColor)
        context?.fillPath()
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
}
