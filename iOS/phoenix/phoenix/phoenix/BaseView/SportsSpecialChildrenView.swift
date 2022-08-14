import Foundation
class SportsSpecialChildrenView: UIView {
    var button: UIButton!
    var data: [Substring]!
    var bgName: String!
    init(frame: CGRect, data: [Substring], index: Int) {
        super.init(frame: frame)
        self.data = data
        self.bgName = "phoenix_special_bg_\(index % 10)"
        let title = data[1]
        let content = data[2]
        
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 4 * screenScale
        self.backgroundColor = UIColor.colorMainColor()
        
        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        bgImageView.image = UIImage(named: bgName)
        self.addSubview(bgImageView)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: frame.height * 0.25, width: frame.width, height: 20 * screenScale))
        titleLabel.text = "\(title)"
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        self.addSubview(titleLabel)
        
        let contentLabel = UILabel(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 5 * screenScale, width: frame.width, height: 20 * screenScale))
        contentLabel.text = "\(content)"
        contentLabel.textColor = titleLabel.textColor
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        self.addSubview(contentLabel)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
