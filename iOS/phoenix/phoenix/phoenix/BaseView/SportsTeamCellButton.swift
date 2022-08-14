import Foundation
class SportsTeamCellButton: UIButton {
    var team: SportsTeamModel!
    var iconView: UIView!
    init(frame: CGRect, team: SportsTeamModel) {
        super.init(frame: frame)
        self.team = team
        iconView = UIView(frame: CGRect(x: frame.width * 0.15, y: 0, width: frame.width * 0.7, height: frame.width * 0.7))
        iconView.isUserInteractionEnabled = false
        let iconImageView = UIImageView(frame: CGRect(x: iconView.frame.height * 0.15, y: iconView.frame.height * 0.15, width: iconView.frame.height * 0.7, height: iconView.frame.height * 0.7))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconImageView.image = SDImage
            }else{
                iconImageView.image = UIImage(named: "common_team_default")
            }
        }
        iconView.addSubview(iconImageView)
        iconView.layer.cornerRadius = iconView.frame.width/2
        iconView.layer.borderWidth = 1 * screenScale
        self.addSubview(iconView)
        
        let nameLabel = UILabel(frame: CGRect(x: 0, y: frame.width * 0.8, width: frame.width, height: frame.width * 0.2))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = UIFont.fontBold(size: UIFont.FontSizeSmaller() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.center
        self.addSubview(nameLabel)
        
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
        iconView.backgroundColor = UIColor.colorMainColor().withAlphaComponent(0.2)
        iconView.layer.borderColor = UIColor.colorMainColor().cgColor
    }
    
    func cancelConcren(){
        self.team.isConcren = false
        self.team.concren = ""
        iconView.backgroundColor = UIColor.colorBgGray()
        iconView.layer.borderColor = UIColor.white.cgColor
    }
}
