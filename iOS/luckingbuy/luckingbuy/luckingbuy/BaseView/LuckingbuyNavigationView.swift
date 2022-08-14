import Foundation
class LuckingbuyNavigationView: UIView {
    let titleLabel: UILabel! = UILabel()
    let backButton: UIButton! = UIButton()
    let rightButton: UIButton! = UIButton()
    let splitLine: CALayer! = CALayer()
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: (navigationFrame.height + statusBarHeight))))
        self.layer.zPosition = 0.75
        let bgLayer1 = CAGradientLayer()
        bgLayer1.colors = [UIColor(red: 134/255, green: 40/255, blue: 250/255, alpha: 1).cgColor, UIColor(red: 81/255, green: 47/255, blue: 253/255, alpha: 1).cgColor]
        bgLayer1.locations = [0, 1]
        bgLayer1.frame = self.bounds
        bgLayer1.startPoint = CGPoint(x: 0.5, y: 0)
        bgLayer1.endPoint = CGPoint(x: 1, y: 1)
        self.layer.addSublayer(bgLayer1)
        titleLabel.frame = CGRect(x: 70 * screenScale, y: (navigationFrame.height + statusBarHeight) - navigationFrame.height + 5 * screenScale, width: self.frame.width - 140 * screenScale, height: 30 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.fontPFMedium(size: UIFont.FontNumberXL() * screenScale)
        titleLabel.textColor = UIColor.white
        self.addSubview(titleLabel)
        backButton.frame = CGRect(x: 0, y: titleLabel.frame.origin.y, width: 50 * screenScale, height: titleLabel.frame.height)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 6 * screenScale, width: 10 * screenScale, height: 18 * screenScale))
        backIconView.image = UIImage(named: "luckingbuy_back_white")
        backButton.addSubview(backIconView)
        self.addSubview(backButton)
        rightButton.frame = CGRect(x: navigationFrame.width - backButton.frame.width - 10 * screenScale, y: backButton.frame.origin.y, width: backButton.frame.width, height: backButton.frame.height)
        self.addSubview(rightButton)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
