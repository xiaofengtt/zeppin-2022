import Foundation
class SportsTeamCellButton: UIButton {
    var team: SportsTeamModel!
    var unselectedView: UIView!
    var selectedView: UIView!
    let paddingLeft = 25 * screenScale
    init(frame: CGRect, team: SportsTeamModel) {
        super.init(frame: frame)
        self.team = team
        let iconView = UIImageView(frame: CGRect(x: paddingLeft, y: (frame.height - 25 * screenScale)/2, width: 25 * screenScale, height: 25 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconView.image = SDImage
            }else{
                iconView.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(iconView)
        let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: 200 * screenScale, height: iconView.frame.height))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(nameLabel)
        
        let unconcernLabel = UILabel()
        unconcernLabel.text = "关注"
        unconcernLabel.textColor = UIColor.colorMainColor()
        unconcernLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        unconcernLabel.textAlignment = NSTextAlignment.center
        unconcernLabel.sizeToFit()
        unconcernLabel.frame = CGRect(x: 0, y: 0, width: unconcernLabel.frame.width + 20 * screenScale, height: 26 * screenScale)
        unselectedView = UIView(frame: CGRect(x: frame.width - 15 * screenScale - unconcernLabel.frame.width, y: (frame.height - unconcernLabel.frame.height)/2, width: unconcernLabel.frame.width, height: unconcernLabel.frame.height))
        unselectedView.layer.borderColor = UIColor.colorMainColor().cgColor
        unselectedView.layer.borderWidth = 1 * screenScale
        unselectedView.layer.cornerRadius = 2 * screenScale
        unselectedView.isUserInteractionEnabled = false
        unselectedView.addSubview(unconcernLabel)
        self.addSubview(unselectedView)
        
        let concernIcon = UIImageView(frame: CGRect(x: 6 * screenScale, y: (unselectedView.frame.height - 12 * screenScale)/2, width: 16 * screenScale, height: 12 * screenScale))
        concernIcon.image = UIImage(named: "image_concern_tick")
        let concernLabel = UILabel()
        concernLabel.text = "已关注"
        concernLabel.textColor = UIColor.colorFontGray()
        concernLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        concernLabel.textAlignment = NSTextAlignment.center
        concernLabel.sizeToFit()
        concernLabel.frame = CGRect(x: concernIcon.frame.origin.x + concernIcon.frame.width + 2 * screenScale, y: unconcernLabel.frame.origin.y, width: concernLabel.frame.width, height: unconcernLabel.frame.height)
        selectedView = UIView(frame: CGRect(x: frame.width - 15 * screenScale - (concernLabel.frame.origin.x + concernLabel.frame.width + 10 * screenScale), y: unselectedView.frame.origin.y, width: concernLabel.frame.origin.x + concernLabel.frame.width + 10 * screenScale, height: unselectedView.frame.height))
        selectedView.layer.borderColor = UIColor.colorFontGray().cgColor
        selectedView.layer.borderWidth = unselectedView.layer.borderWidth
        selectedView.layer.cornerRadius = unselectedView.layer.cornerRadius
        selectedView.isUserInteractionEnabled = false
        selectedView.addSubview(concernIcon)
        selectedView.addSubview(concernLabel)
        self.addSubview(selectedView)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: paddingLeft, y: self.frame.height - 1 * screenScale, width: self.frame.width - paddingLeft, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(splitLine)
        
        if(team.isConcren){
            setConcren(concren: team.concren)
        }else{
            cancelConcren()
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    func setConcren(concren: String){
        self.team.isConcren = true
        self.team.concren = concren
        self.selectedView.isHidden = false
        self.unselectedView.isHidden = true
    }
    func cancelConcren(){
        self.team.isConcren = false
        self.team.concren = ""
        self.selectedView.isHidden = true
        self.unselectedView.isHidden = false
    }
}
