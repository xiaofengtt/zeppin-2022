import Foundation
import UIKit
extension UIImage{
    //根据颜色绘制1X1色点
    class func getImageByColor(_ color : UIColor) -> UIImage {
        let rect = CGRect(x: 0, y: 0, width: 1, height: 1)
        UIGraphicsBeginImageContext(rect.size)
        let context = UIGraphicsGetCurrentContext()
        context?.setFillColor(color.cgColor)
        context?.fill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image!
    }
    //设置图片透明度
    func setImageAlpha(_ alpha: CGFloat) -> UIImage{
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
    //修改图片尺寸
    func getImageBySize(size: CGSize) -> UIImage? {
        if self.size.height > size.height {
            //原始高度大于要求高度
            let width = size.height / self.size.height * self.size.width
            let newImgSize = CGSize(width: width, height: size.height)
            UIGraphicsBeginImageContext(newImgSize)
            self.draw(in: CGRect(x: 0, y: 0, width: newImgSize.width, height: newImgSize.height))
            
            let theImage = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()
            guard let newImg = theImage else { return  nil}
            return newImg
        } else {
            //原始高度不大于要求高度
            let newImgSize = CGSize(width: size.width, height: size.height)
            UIGraphicsBeginImageContext(newImgSize)
            self.draw(in: CGRect(x: 0, y: 0, width: newImgSize.width, height: newImgSize.height))
            
            let theImage = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()
            guard let newImg = theImage else { return  nil}
            return newImg
        }
    }
}
