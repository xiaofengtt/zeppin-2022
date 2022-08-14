import UIKit
class SportsBaseNavigationController: UINavigationController{
    var statusBarStyle: UIStatusBarStyle = UIStatusBarStyle.darkContent
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = true
        self.navigationBar.titleTextAttributes = [NSAttributedString.Key.font: UIFont.fontNormal(size: 18) as Any, NSAttributedString.Key.foregroundColor: UIColor.white]
        self.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clear)
        self.navigationBar.backIndicatorImage = UIImage.imageWithColor(UIColor.clear)
        self.navigationBar.backIndicatorTransitionMaskImage = UIImage.imageWithColor(UIColor.clear)
    }
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return statusBarStyle
    }
}
