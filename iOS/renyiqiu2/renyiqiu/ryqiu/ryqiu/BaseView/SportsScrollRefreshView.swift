import UIKit
enum UIScrollRefreshStatus {
    case normal
    case able
}
class SportsScrollRefreshView: UIView {
    var label: UILabel!
    var status: UIScrollRefreshStatus!
    static let height: CGFloat = 40 * screenScale
    init(parent: UIScrollView){
        super.init(frame: CGRect(x: 0, y: SportsScrollRefreshView.height * -1, width: parent.frame.width, height: SportsScrollRefreshView.height))
        label = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: self.frame.width, height: self.frame.height / 2)))
        label.textColor = UIColor.colorFontGray()
        label.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        label.textAlignment = NSTextAlignment.center
        self.addSubview(label)
        self.setNormal()
    }
    func setNormal(){
        self.status = UIScrollRefreshStatus.normal
        label.text = "下拉刷新"
    }
    func setAble(){
        self.status = UIScrollRefreshStatus.able
        label.text = "松开刷新"
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
