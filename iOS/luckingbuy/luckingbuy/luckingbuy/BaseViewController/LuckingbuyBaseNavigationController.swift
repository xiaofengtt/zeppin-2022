import UIKit
class LuckingbuyBaseNavigationController: UINavigationController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = true
        self.navigationBar.setBackgroundImage(UIImage.singleColorImage(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.singleColorImage(UIColor.clear)
        self.navigationBar.backIndicatorImage = UIImage.singleColorImage(UIColor.clear)
        self.navigationBar.backIndicatorTransitionMaskImage = UIImage.singleColorImage(UIColor.clear)
    }
}
