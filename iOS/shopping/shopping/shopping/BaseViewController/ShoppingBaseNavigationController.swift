import UIKit
class ShoppingBaseNavigationController: UINavigationController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = true
        self.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clear)
        self.navigationBar.backIndicatorImage = UIImage.imageWithColor(UIColor.clear)
        self.navigationBar.backIndicatorTransitionMaskImage = UIImage.imageWithColor(UIColor.clear)
    }
}
