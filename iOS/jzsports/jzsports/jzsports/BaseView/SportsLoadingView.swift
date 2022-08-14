import UIKit
class SportsLoadingView: MBProgressHUD {
    init() {
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        self.layer.zPosition = 0.9
        self.removeFromSuperViewOnHide = true
        self.label.text = "加载中..."
        self.minShowTime = 0.5
        UIApplication.shared.windows[0].addSubview(self)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
