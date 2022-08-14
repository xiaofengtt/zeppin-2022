import Foundation
extension UIFont{
    class func FontNumberXS() -> CGFloat{
        return 10
    }
    class func FontNumberS() -> CGFloat{
        return 12
    }
    class func FontNumberM() -> CGFloat{
        return 14
    }
    class func FontNumberL() -> CGFloat{
        return 16
    }
    class func FontNumberXL() -> CGFloat{
        return 18
    }
    class func fontPFRegular(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Regular", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontPFSemibold(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Semibold", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
    class func fontPFMedium(size: CGFloat) -> UIFont{
        var font = UIFont(name: "PingFangSC-Medium", size: size)
        if(font == nil){
            font = UIFont.systemFont(ofSize: size)
        }
        return font!
    }
}
