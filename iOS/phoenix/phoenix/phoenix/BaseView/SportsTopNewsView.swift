import Foundation
class SportsTopNewsView: UIView {
    var label: UILabel!
    var imageView: UIImageView!
    var button: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        imageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: (frame.height - 20 * screenScale) / 3 * 4, height: frame.height - 20 * screenScale))
        self.addSubview(imageView)
        label = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: imageView.frame.origin.y, width: frame.width - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale) - 10 * screenScale, height: imageView.frame.height))
        label.numberOfLines = 2
        label.textColor = UIColor.colorFontBlack()
        label.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(label)
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
