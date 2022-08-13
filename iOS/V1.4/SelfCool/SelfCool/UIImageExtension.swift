//
//  ImageViewExtension.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/14.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
extension UIImage{
    class func imageWithColor(color : UIColor) -> UIImage {
        let rect = CGRectMake(0, 0, 1, 1)
        UIGraphicsBeginImageContext(rect.size)
        let context = UIGraphicsGetCurrentContext()
        CGContextSetFillColorWithColor(context, color.CGColor)
        CGContextFillRect(context, rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
    
    func imageWithScale(scale : CGFloat) -> UIImage{
        UIGraphicsBeginImageContext(CGSizeMake(self.size.width * scale , self.size.height * scale))
        self.drawInRect(CGRectMake(0, 0, self.size.width * scale, self.size.height * scale))
        let image = UIGraphicsGetImageFromCurrentImageContext()
        return image
    }
    
}
