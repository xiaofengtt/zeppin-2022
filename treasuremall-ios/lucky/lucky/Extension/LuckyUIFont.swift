import Foundation
extension UIFont{
    //通用字号
    class func fontSizeSmallest() -> CGFloat{
        return 10
    }
    class func fontSizeSmaller() -> CGFloat{
        return 12
    }
    class func fontSizeMiddle() -> CGFloat{
        return 14
    }
    class func fontSizeBigger() -> CGFloat{
        return 16
    }
    class func fontSizeBiggest() -> CGFloat{
        return 18
    }
    //通用字体
    class func mainFont(size: CGFloat) -> UIFont{
        let font = UIFont.systemFont(ofSize: size, weight: Weight.regular)
        return font
    }
    class func boldFont(size: CGFloat) -> UIFont{
        let font = UIFont.systemFont(ofSize: size, weight: Weight.bold)
        return font
    }
    class func mediumFont(size: CGFloat) -> UIFont{
        let font = UIFont.systemFont(ofSize: size, weight: Weight.medium)
        return font
    }
    
    class func heavyFont(size: CGFloat) -> UIFont{
        let font = UIFont.systemFont(ofSize: size, weight: Weight.heavy)
        return font
    }
}
