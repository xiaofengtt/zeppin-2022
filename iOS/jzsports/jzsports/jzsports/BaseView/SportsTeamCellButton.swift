import Foundation
class SportsTeamCellButton: UIButton {
    var team: SportsTeamModel!
    var selectedImageView: UIImageView!
    init(frame: CGRect, team: SportsTeamModel) {
        super.init(frame: frame)
        self.team = team
        let iconImageView = UIImageView(frame: CGRect(x: 20 * screenScale, y: (frame.height - 32 * screenScale ) / 2, width: 32 * screenScale, height: 32 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconImageView.image = SDImage
            }else{
                iconImageView.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(iconImageView)
        
        selectedImageView = UIImageView(frame: CGRect(x: frame.width - 35 * screenScale, y: (frame.height - 20 * screenScale ) / 2, width: 20 * screenScale, height: 20 * screenScale))
        self.addSubview(selectedImageView)
        
        let nameLabel = UILabel(frame: CGRect(x: iconImageView.frame.origin.x + iconImageView.frame.width + 10 * screenScale, y: iconImageView.frame.origin.y, width: selectedImageView.frame.origin.x - (iconImageView.frame.origin.x + iconImageView.frame.width + 16 * screenScale), height: iconImageView.frame.height))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.colorFontBlack()
        nameLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
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
        selectedImageView.image = UIImage(named: "image_concern_selected")
    }
    
    func cancelConcren(){
        self.team.isConcren = false
        self.team.concren = ""
        selectedImageView.image = UIImage(named: "image_concern_unselected")
    }
}
