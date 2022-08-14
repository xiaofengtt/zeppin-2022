//
//  UIColor.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/16.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension UIColor{
     static func getColorByHex(hexString: String) -> UIColor{
        let string = hexString.replacingOccurrences(of: "#", with: "")
        if(string.count != 6){
            return UIColor.mainBlue()
        }else{
            let r = String(string[..<String.Index.init(encodedOffset: 2)])
            let g = String(string[String.Index.init(encodedOffset: 2)..<String.Index.init(encodedOffset: 4)])
            let b = String(string[String.Index.init(encodedOffset: 4)..<String.Index.init(encodedOffset: 6)])
            
            return UIColor(red: CGFloat(Utils.hexToDec(number: r))/255, green: CGFloat(Utils.hexToDec(number: g))/255, blue: CGFloat(Utils.hexToDec(number: b))/255, alpha: 1)
        }
    }
    
    class func mainBlue() -> UIColor{
        return UIColor(red: 0/255, green: 105/255, blue: 229/255, alpha: 1)
    }
    
    class func mainGold() -> UIColor{
        return UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
    }
    
    class func mainPlaceholder() -> UIColor{
        return UIColor.black.withAlphaComponent(0.34)
    }
    
    class func mainRed() -> UIColor{
        return UIColor(red: 244/255, green: 51/255, blue: 60/255, alpha: 1)
    }
    
    class func moneyInputColor() -> UIColor{
        return UIColor(red: 47/255, green: 83/255, blue: 126/255, alpha: 1)
    }
    
    class func fontBlack() -> UIColor{
        return UIColor(red: 30/255, green: 30/255, blue: 30/255, alpha: 1)
    }
    
    class func fontDarkGray() -> UIColor{
        return UIColor(red: 86/255, green: 86/255, blue: 86/255, alpha: 1)
    }
    
    class func fontGray() -> UIColor{
        return UIColor(red: 144/255, green: 144/255, blue: 144/255, alpha: 1)
    }
    
    class func backgroundGray() -> UIColor{
        return UIColor(red: 240/255, green: 240/255, blue: 240/255, alpha: 1)
    }
    
    class func unselectedColor() -> UIColor{
        return UIColor(red: 73/255, green: 144/255, blue: 226/255, alpha: 1)
    }
    
    class func selectedColor() -> UIColor{
        return UIColor(red: 91/255, green: 162/255, blue: 65/255, alpha: 1)
    }
    
    class func alertCancleColor() -> UIColor{
        return UIColor(red: 123/255, green: 123/255, blue: 123/255, alpha: 0.5)
    }
    
    class func alertSureColor() -> UIColor{
        return UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
    }
    
    class func incomeColor() -> UIColor{
        return UIColor(red: 225/255, green: 111/255, blue: 110/255, alpha: 1)
    }
    
    class func expendColor() -> UIColor{
        return UIColor(red: 108/255, green: 172/255, blue: 75/255, alpha: 1)
    }
}
