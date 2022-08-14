import Foundation
class SportsMatchPlayerView: UIView {
    init(frame: CGRect, player: NSDictionary, color: UIColor) {
        super.init(frame: frame)
        let numberLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: (frame.height - 20 * screenScale) / 2, width: 20 * screenScale, height: 20 * screenScale))
        numberLabel.backgroundColor = color
        numberLabel.text = "\(player["player_number"] as! String)"
        numberLabel.textColor = UIColor.white
        numberLabel.textAlignment = NSTextAlignment.center
        numberLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        numberLabel.layer.cornerRadius = numberLabel.frame.width/2
        numberLabel.layer.masksToBounds = true
        self.addSubview(numberLabel)
        let nameLabel = UILabel(frame: CGRect(x: numberLabel.frame.origin.x + numberLabel.frame.width + 10 * screenScale, y: numberLabel.frame.origin.y, width: screenWidth/2 - (numberLabel.frame.origin.x + numberLabel.frame.width), height: numberLabel.frame.height))
        nameLabel.text = player["player"] as? String
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = numberLabel.font
        self.addSubview(nameLabel)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1 * screenScale, width: frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
