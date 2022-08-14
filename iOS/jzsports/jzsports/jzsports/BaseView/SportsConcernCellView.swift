import Foundation
class SportsConcernCellView: UIView {
    var selectedImageView: UIImageView!
    init(frame: CGRect, team: SportsTeamModel) {
        super.init(frame: frame)
        let iconImageView = UIImageView(frame: CGRect(x: 15 * screenScale, y: (frame.height - 24 * screenScale ) / 2, width: 24 * screenScale, height: 24 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconImageView.image = SDImage
            }else{
                iconImageView.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(iconImageView)
        
        selectedImageView = UIImageView(frame: CGRect(x: frame.width - 35 * screenScale, y: (frame.height - 20 * screenScale ) / 2, width: 20 * screenScale, height: 20 * screenScale))
        selectedImageView.image = UIImage(named: team.isConcren ? "image_concern_selected" : "image_concern_unselected")
        self.addSubview(selectedImageView)
        
        let nameLabel = UILabel(frame: CGRect(x: iconImageView.frame.origin.x + iconImageView.frame.width + 8 * screenScale, y: iconImageView.frame.origin.y, width: selectedImageView.frame.origin.x - (iconImageView.frame.origin.x + iconImageView.frame.width + 16 * screenScale), height: iconImageView.frame.height))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
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
