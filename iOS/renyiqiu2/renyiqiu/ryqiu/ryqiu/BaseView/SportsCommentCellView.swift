import Foundation
class SportsCommentCellView: UIView {
    init(frame: CGRect, comment: SportsNewsCommentModel) {
        super.init(frame: frame)
        let iconView = UIImageView(frame: CGRect(x: 20 * screenScale, y: 10 * screenScale, width: 40 * screenScale, height: 40 * screenScale))
        iconView.image = UIImage(named: "image_user_login")
        self.addSubview(iconView)
        let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 20 * screenScale, y: 8 * screenScale, width: 100 * screenScale, height: 20 * screenScale))
        nameLabel.text = "热心球迷"
        nameLabel.textColor = UIColor.colorFontGray()
        nameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        self.addSubview(nameLabel)
        let timeLabel = UILabel(frame: CGRect(x: frame.width - 20 * screenScale - 100 * screenScale, y: nameLabel.frame.origin.y, width: 100 * screenScale, height: nameLabel.frame.height))
        timeLabel.text = "\(SportsUtils.timestampFormat(timestamp: comment.createtime, format: "MM-dd HH:mm"))"
        timeLabel.textColor = UIColor.colorFontGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        timeLabel.textAlignment = NSTextAlignment.right
        self.addSubview(timeLabel)
        let contentLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.frame.width - nameLabel.frame.origin.x - 4 * screenScale, height: 0))
        contentLabel.numberOfLines = 0
        contentLabel.text = comment.content.removingPercentEncoding
        contentLabel.textColor = UIColor.colorFontBlack()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        contentLabel.sizeToFit()
        contentLabel.frame.origin = CGPoint(x: nameLabel.frame.origin.x, y: timeLabel.frame.origin.y + timeLabel.frame.height + 5 * screenScale)
        self.addSubview(contentLabel)
        self.frame.size = CGSize(width: frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 10 * screenScale)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 10 * screenScale, y: self.frame.height - 1 * screenScale, width: self.frame.width - 20 * screenScale, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor(red: 235/255, green: 235/255, blue: 235/255, alpha: 1).cgColor
        self.layer.addSublayer(splitLine)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
