import Foundation
extension UIFont{
    class func FontSizeSmallest() -> CGFloat{
        return 10
    }
    class func FontSizeSmaller() -> CGFloat{
        return 12
    }
    class func FontSizeMiddle() -> CGFloat{
        return 14
    }
    class func FontSizeBigger() -> CGFloat{
        return 16
    }
    class func FontSizeBiggest() -> CGFloat{
        return 18
    }
    class func fontNormal(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Regular", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontBold(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Semibold", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontMedium(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Medium", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontLight(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Light", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontCode(size: CGFloat) -> UIFont{
        var font = UIFont(name: "Apple Color Emoji", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
}
