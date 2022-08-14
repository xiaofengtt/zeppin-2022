import UIKit
extension UIButton{
    func showNumber(number: String) {
        let numberLabel = UILabel(frame: CGRect(x: self.frame.width - 10 * screenScale, y: 0, width: 20 * screenScale, height: 10 * screenScale))
        numberLabel.backgroundColor = UIColor.white
        numberLabel.text = number
        numberLabel.textColor = UIColor.colorMainColor()
        numberLabel.textAlignment = NSTextAlignment.center
        numberLabel.font = UIFont.fontBold(size: 10 * screenScale)
        self.addSubview(numberLabel)
    }
}
