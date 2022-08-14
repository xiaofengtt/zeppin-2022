//
//  UIColor.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/16.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIColor{
    class func mainYellow() -> UIColor{
        return UIColor(red: 255/255, green: 196/255, blue: 0/255, alpha: 1)
    }
    
    class func fontBlack() -> UIColor{
        return UIColor(red: 40/255, green: 40/255, blue: 40/255, alpha: 1)
    }
    
    class func fontDarkGray() -> UIColor{
        return UIColor(red: 110/255, green: 110/255, blue: 110/255, alpha: 1)
    }
    
    class func fontGray() -> UIColor{
        return UIColor(red: 144/255, green: 144/255, blue: 144/255, alpha: 1)
    }
    
    class func backgroundGray() -> UIColor{
        return UIColor(red: 249/255, green: 248/255, blue: 246/255, alpha: 1)
    }
    
    class func unselectedColor() -> UIColor{
        return UIColor(red: 73/255, green: 144/255, blue: 226/255, alpha: 1)
    }
    
    class func alertCancleColor() -> UIColor{
        return UIColor(red: 123/255, green: 123/255, blue: 123/255, alpha: 0.5)
    }
    
    class func alertSureColor() -> UIColor{
        return UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
    }
    
    class func homeMatchColor() -> UIColor{
        return UIColor(red: 173/255, green: 128/255, blue: 0/255, alpha: 1)
    }
    
    class func awayMatchColor() -> UIColor{
        return UIColor(red: 255/255, green: 190/255, blue: 10/255, alpha: 1)
    }
    
    class func mainPlaceholder() -> UIColor{
        return UIColor.black.withAlphaComponent(0.34)
    }
}
