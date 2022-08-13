//
//  UIImageExtension.swift
//  nmgs
//
//  Created by zeppin on 2016/10/25.
//  Copyright © 2016年 zeppin. All rights reserved.
//

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
        UIGraphicsBeginImageContext(CGSize(width: self.size.width * scale , height: self.size.height * scale))
        self.draw(in: CGRect(x: 0, y: 0, width: self.size.width * scale, height: self.size.height * scale))
        let image = UIGraphicsGetImageFromCurrentImageContext()
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
