import Foundation
class SportsHomeSpecialView: UIView {
    var titleLabel: UILabel!
    var imageView: UIImageView!
    var button: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)
        imageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(imageView)
        
        let imageHideView = UIImageView(frame: imageView.frame)
        imageHideView.image = UIImage(named: "phoenix_home_special_hide")
        self.addSubview(imageHideView)
        
        titleLabel = UILabel(frame: imageView.frame)
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        self.addSubview(titleLabel)
        titleLabel.textAlignment = NSTextAlignment.center
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
