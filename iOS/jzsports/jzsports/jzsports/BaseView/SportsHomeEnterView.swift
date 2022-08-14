import Foundation
class SportsHomeEnterView: UIView {
    var titleLabel: UILabel!
    var contentLabel: UILabel!
    var iconImageView: UIImageView!
    var button: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)
        iconImageView = UIImageView(frame: CGRect(x: 15 * screenScale, y: frame.height/4, width: frame.height/2, height: frame.height/2))
        self.addSubview(iconImageView)
        
        titleLabel = UILabel(frame: CGRect(x: iconImageView.frame.origin.x + iconImageView.frame.width + 10 * screenScale, y: iconImageView.frame.origin.y, width: 100 * screenScale, height: 20 * screenScale))
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
        
        contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height, width: titleLabel.frame.width, height: 20 * screenScale))
        contentLabel.textColor = UIColor.colorFontGray()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        self.addSubview(contentLabel)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
