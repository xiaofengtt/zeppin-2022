//
//  UIColorExtension.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
extension UIColor{
    class func mainColor() -> UIColor{
        return UIColor(red: 13/255, green: 169/255, blue: 69/255, alpha: 1)
    }
    class func buttonGray() -> UIColor{
        return UIColor(red: 201/255, green: 202/255, blue: 202/255, alpha: 1)
    }
    class func backgroundGray() -> UIColor{
        return UIColor(red: 248/255, green: 248/255, blue: 248/255, alpha: 1)
    }
    class func spaceLineGray() -> UIColor{
        return UIColor(red: 230/255, green: 230/255, blue: 230/255, alpha: 1)
    }
    class func buttonBlue() -> UIColor{
        return UIColor(red: 86/255, green: 194/255, blue: 251/255, alpha: 1)
    }
    class func flagColor() ->UIColor {
        return UIColor(red: 245/255, green: 153/255, blue: 63/255, alpha: 1)
    }
    class func correctColor() ->UIColor {
        return UIColor(red: 123/255, green: 193/255, blue: 124/255, alpha: 1)
    }
    class func wrongColor() ->UIColor {
        return UIColor(red: 255/255, green: 99/255, blue: 99/255, alpha: 1)
    }
}