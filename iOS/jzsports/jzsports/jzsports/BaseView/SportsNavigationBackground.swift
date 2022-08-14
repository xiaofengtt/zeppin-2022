import UIKit
class SportsNavigationBackground: UIView {
    let titleLabel: UILabel! = UILabel()
    let backButton: UIButton! = UIButton()
    let rightButton: UIButton! = UIButton()
    let splitLine: CALayer! = CALayer()
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: (navigationFrame.height + statusBarHeight))))
        self.backgroundColor = UIColor.white
        self.layer.zPosition = 0.75
        self.layer.shadowPath = CGPath(rect: CGRect.init(x: 4, y: 4, width: self.frame.width - 8, height: self.frame.height - 8), transform: nil)
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.3
        self.layer.shadowOffset = CGSize(width: 0, height: 2)
        self.layer.shadowRadius = 4
        titleLabel.frame = CGRect(x: 70 * screenScale, y: (navigationFrame.height + statusBarHeight) - navigationFrame.height + 5 * screenScale, width: self.frame.width - 140 * screenScale, height: 30 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.textColor = UIColor.colorFontBlack()
        self.addSubview(titleLabel)
        backButton.frame = CGRect(x: 0, y: titleLabel.frame.origin.y, width: 50 * screenScale, height: titleLabel.frame.height)
        let backIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_black")
        backButton.addSubview(backIconView)
        self.addSubview(backButton)
        rightButton.frame = CGRect(x: navigationFrame.width - backButton.frame.width - 10 * screenScale, y: backButton.frame.origin.y, width: backButton.frame.width, height: backButton.frame.height)
        self.addSubview(rightButton)
        splitLine.frame = CGRect(x: 0, y: self.frame.height - 1 * screenScale, width: self.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(splitLine)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    init() {
        super.init(frame: CGRect.zero)
    }
}
