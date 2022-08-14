import Foundation
class SportsMatchTitleView: UIView {
    let paddingLeft: CGFloat = 15 * screenScale
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: frame.width - paddingLeft * 2, height: frame.height - 10 * screenScale))
        titleLabel.text = title
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
