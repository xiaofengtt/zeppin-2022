import UIKit
class SportsMainHeaderView: UIView {
    let paddingLeft: CGFloat = 15 * screenScale
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        let cellView = UIView(frame: CGRect(x: paddingLeft, y: 0, width: 100 * screenScale, height: self.frame.height))
        cellView.backgroundColor = UIColor.clear
        let nameLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
        nameLabel.text = title
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 4) * screenScale)
        nameLabel.sizeToFit()
        nameLabel.frame = CGRect(x: 0, y: 0, width: nameLabel.frame.width, height: self.frame.height)
        cellView.addSubview(nameLabel)
        let selectImageView = UIImageView(frame: CGRect(x: nameLabel.frame.width/2 - 3 * screenScale, y: self.frame.height - 6 * screenScale, width: 6 * screenScale, height: 6 * screenScale))
        selectImageView.backgroundColor = UIColor.colorMainColor()
        selectImageView.layer.cornerRadius = 3 * screenScale
        cellView.addSubview(selectImageView)
        
        self.addSubview(cellView)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
