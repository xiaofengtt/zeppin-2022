import Foundation
import UIKit
extension UIImage{
    class func singleColorImage(_ color : UIColor) -> UIImage {
        let rect = CGRect(x: 0, y: 0, width: 1, height: 1)
        UIGraphicsBeginImageContext(rect.size)
        let context = UIGraphicsGetCurrentContext()
        context?.setFillColor(color.cgColor)
        context?.fill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
    
    func setAlpha(_ alpha: CGFloat) -> UIImage{
        UIGraphicsBeginImageContextWithOptions(self.size, false, UIScreen.main.scale);
        let context = UIGraphicsGetCurrentContext();
        let area = CGRect(origin: CGPoint.zero, size: self.size)
        context?.scaleBy(x: 1, y: -1)
        context?.translateBy(x: 0, y: area.size.height * (-1))
        context?.setBlendMode(CGBlendMode.multiply)
        context?.setAlpha(alpha)
        context?.draw(self.cgImage!, in: area)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
}
