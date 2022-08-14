import UIKit
class LuckyBaseNavigationController: UINavigationController{
    override init(rootViewController: UIViewController) {
        super.init(rootViewController: rootViewController)
        //隐藏头
        self.navigationBar.isTranslucent = true
        self.navigationBar.setBackgroundImage(UIImage.getImageByColor(UIColor.clear), for: UIBarMetrics.default)
        self.navigationBar.shadowImage = UIImage.getImageByColor(UIColor.clear)
        self.navigationBar.backIndicatorImage = UIImage.getImageByColor(UIColor.clear)
        self.navigationBar.backIndicatorTransitionMaskImage = UIImage.getImageByColor(UIColor.clear)
        self.setNavigationBarHidden(true, animated: false)
        
        //选中文字色
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor(red: 255/255, green: 178/255, blue: 1/255, alpha: 1)], for: UIControl.State.selected)
        //选中文字上调
        self.tabBarItem.titlePositionAdjustment = UIOffset(horizontal: 0, vertical: -3)
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
