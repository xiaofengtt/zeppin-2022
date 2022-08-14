import Foundation
class ShoppingNavigationView: UIView {
    let titleLabel: UILabel! = UILabel()
    let backButton: UIButton! = UIButton()
    let rightButton: UIButton! = UIButton()
    let splitLine: CALayer! = CALayer()
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: (navigationFrame.height + statusBarHeight))))
        self.backgroundColor = UIColor.colorMainColor()
        self.layer.zPosition = 0.75
        titleLabel.frame = CGRect(x: 70 * screenScale, y: (navigationFrame.height + statusBarHeight) - navigationFrame.height + 5 * screenScale, width: self.frame.width - 140 * screenScale, height: 30 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.fontMedium(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.textColor = UIColor.white
        self.addSubview(titleLabel)
        backButton.frame = CGRect(x: 0, y: titleLabel.frame.origin.y, width: 50 * screenScale, height: titleLabel.frame.height)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 6 * screenScale, width: 10 * screenScale, height: 18 * screenScale))
        backIconView.image = UIImage(named: "shopping_back_white")
        backButton.addSubview(backIconView)
        self.addSubview(backButton)
        rightButton.frame = CGRect(x: navigationFrame.width - backButton.frame.width - 10 * screenScale, y: backButton.frame.origin.y, width: backButton.frame.width, height: backButton.frame.height)
        self.addSubview(rightButton)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
