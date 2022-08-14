//
//  UIFont.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/16.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIFont{
    
    class func smallestSize() -> CGFloat{
        return 10
    }
    
    class func smallSize() -> CGFloat{
        return 12
    }
    
    class func middleSize() -> CGFloat{
        return 14
    }
    
    class func bigSize() -> CGFloat{
        return 16
    }
    
    class func biggestSize() -> CGFloat{
        return 18
    }
    
//    class func numFont(size: CGFloat) -> UIFont{
//        return UIFont(name: "HiraKakuPro-W3", size: size)!
//    }
    
    class func mainFont(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Regular", size: size)
        
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    
    class func boldFont(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Semibold", size: size)
        
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    
    class func mediumFont(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Medium", size: size)
        
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    
    class func lightFont(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Light", size: size)
        
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
}
