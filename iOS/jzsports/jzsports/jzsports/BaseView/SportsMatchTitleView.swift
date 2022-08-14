import Foundation
class SportsMatchTitleView: UIView {
    let paddingLeft: CGFloat = 15 * screenScale
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 0, width: frame.width, height: frame.height))
        titleLabel.text = title
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(x: paddingLeft , y: (frame.height - titleLabel.frame.height)/2, width: titleLabel.frame.width, height: titleLabel.frame.height)
        let titleBgImageView = UIImageView(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height/3*2 - 2 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height/3))
        titleBgImageView.image = UIImage(named: "image_title_bg")
        self.addSubview(titleBgImageView)
        self.addSubview(titleLabel)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
