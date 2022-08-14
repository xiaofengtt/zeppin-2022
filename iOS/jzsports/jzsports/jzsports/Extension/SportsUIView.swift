import Foundation
extension UIView{
    func addBaseShadow(){
        self.layer.shadowColor = UIColor.lightGray.cgColor
        self.layer.shadowOpacity = 1
        self.layer.shadowOffset = CGSize(width: 0, height: 2 * screenScale)
        self.layer.shadowRadius = 2 * screenScale
    }
}
