import Foundation
extension UIView{
    func addBaseShadow(){
        self.layer.shadowPath = CGPath(rect: CGRect.init(x: 4 * screenScale, y: 4 * screenScale, width: self.frame.width - 8 * screenScale, height: self.frame.height - 8 * screenScale), transform: nil)
        self.layer.shadowColor = UIColor.lightGray.cgColor
        self.layer.shadowOpacity = 0.5
        self.layer.shadowOffset = CGSize(width: 0, height: 2 * screenScale)
        self.layer.shadowRadius = 4 * screenScale
    }
}
