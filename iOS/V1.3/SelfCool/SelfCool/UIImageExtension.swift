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
        var rect = CGRectMake(0, 0, 1, 1)
        UIGraphicsBeginImageContext(rect.size)
        var context = UIGraphicsGetCurrentContext()
        CGContextSetFillColorWithColor(context, color.CGColor)
        CGContextFillRect(context, rect)
        var image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
}
