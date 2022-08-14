import Foundation
class SportsSpecialChildrenView: UIView {
    var button: UIButton!
    var data: [Substring]!
    var bgName: String!
    init(frame: CGRect, data: [Substring], index: Int) {
        super.init(frame: frame)
        self.data = data
        self.bgName = "image_main_special_\(index+1)"
        let title = data[1]
        let content = data[2]

        let mainView = UIView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        mainView.backgroundColor = UIColor.white
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 10 * screenScale
        self.addSubview(mainView)

        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: frame.width, height: 190 * screenScale)))
        bgImageView.image = UIImage(named: bgName)
        mainView.addSubview(bgImageView)

        let titleLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: bgImageView.frame.origin.y + bgImageView.frame.height + 25 * screenScale, width: frame.width - 30 * screenScale, height: 30 * screenScale))
        titleLabel.text = "#\(title)"
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        mainView.addSubview(titleLabel)

        let contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 5 * screenScale, width: titleLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = "\(content)"
        contentLabel.textColor = titleLabel.textColor
        contentLabel.font = UIFont.fontMedium(size: UIFont.FontSizeMiddle() * screenScale)
        mainView.addSubview(contentLabel)

        let infoLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: contentLabel.frame.origin.y + contentLabel.frame.height + 15 * screenScale, width: 100 * screenScale, height: 26 * screenScale))
        infoLabel.text = ""
        infoLabel.textColor = UIColor.colorFontGray()
        infoLabel.font = contentLabel.font
        mainView.addSubview(infoLabel)

        let labelView = UILabel(frame: CGRect(x: frame.width - 95 * screenScale, y: infoLabel.frame.origin.y, width: 80 * screenScale, height: infoLabel.frame.height))
        labelView.backgroundColor = UIColor.colorMainColor()
        labelView.text = "查看全部"
        labelView.textColor = UIColor.white
        labelView.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        labelView.textAlignment = NSTextAlignment.center
        labelView.layer.masksToBounds = true
        labelView.layer.cornerRadius = labelView.frame.height/2
        mainView.addSubview(labelView)

        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
