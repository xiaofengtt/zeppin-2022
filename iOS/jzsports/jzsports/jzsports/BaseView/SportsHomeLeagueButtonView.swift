import Foundation
class SportsHomeLeagueButtonView: UIView {
    var titleLabel: UILabel!
    var imageView: UIImageView!
    var button: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)
        let imageBgView = UIView(frame: CGRect(x: (frame.width - 50 * screenScale)/2, y: 0, width: 50 * screenScale, height: 50 * screenScale))
        imageView = UIImageView(frame: CGRect(x: 6 * screenScale, y: 6 * screenScale, width: 38 * screenScale, height: 38 * screenScale))
        imageBgView.addSubview(imageView)
        imageBgView.backgroundColor = UIColor.white
        imageBgView.layer.cornerRadius = imageBgView.frame.width/2
        imageBgView.addBaseShadow()
        self.addSubview(imageBgView)
        
        titleLabel = UILabel(frame: CGRect(x: 0, y: imageBgView.frame.origin.y + imageBgView.frame.height + 10 * screenScale, width: frame.width, height: 20 * screenScale))
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        self.addSubview(titleLabel)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
