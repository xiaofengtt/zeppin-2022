import Foundation
class SportsMainMatchButton: UIButton {
    var match: SportsMatchModel!
    init(frame: CGRect, match: SportsMatchModel) {
        super.init(frame: frame)
        self.match = match
        self.backgroundColor = UIColor.white
        self.layer.cornerRadius = 5 * screenScale
        self.addBaseShadow()
        if(match.uuid != ""){
            let finalresult = match.finalresult.components(separatedBy: "-")
            let homeScoreStr = finalresult.count == 2 && finalresult[0] != "" ? finalresult[0] : "-"
            let awayScoreStr = finalresult.count == 2 && finalresult[1] != "" ? finalresult[1] : "-"
            let titleLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: self.frame.width/2, height: 20 * screenScale))
            titleLabel.text = "\(match.categoryName)\(match.roundName)"
            titleLabel.textColor = UIColor.colorFontGray()
            titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            self.addSubview(titleLabel)
            let timeLabel = UILabel(frame: CGRect(x: self.frame.width/2, y: titleLabel.frame.origin.y, width: self.frame.width/2 - 10 * screenScale, height: titleLabel.frame.height))
            if(match.status == "finished"){
                timeLabel.text = "完赛"
                timeLabel.textColor = titleLabel.textColor
            }else if(match.status == "living"){
                timeLabel.text = "进行中"
                timeLabel.textColor = UIColor.colorMainColor()
            }else{
                timeLabel.text = SportsUtils.timestampFormat(timestamp: match.time, format: "MM-dd HH:mm")
                timeLabel.textColor = titleLabel.textColor
            }
            timeLabel.font = titleLabel.font
            timeLabel.textAlignment = NSTextAlignment.right
            self.addSubview(timeLabel)
            let homeIcon = UIImageView(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
            SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                if(SDImage != nil){
                    homeIcon.image = SDImage
                }else{
                    homeIcon.image = UIImage(named: "image_team_default")
                }
            }
            self.addSubview(homeIcon)
            let homeName = UILabel(frame: CGRect(x: homeIcon.frame.origin.x + homeIcon.frame.width + 10 * screenScale, y: homeIcon.frame.origin.y, width: self.frame.width/2, height: homeIcon.frame.width))
            homeName.text = match.hometeamName
            homeName.textColor = UIColor.colorFontBlack()
            homeName.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            self.addSubview(homeName)
            let homeScore = UILabel(frame: CGRect(x: self.frame.width - 50 * screenScale, y: homeName.frame.origin.y, width: 40 * screenScale, height: homeName.frame.height))
            homeScore.text = homeScoreStr
            homeScore.textColor = homeName.textColor
            homeScore.font = homeName.font
            homeScore.textAlignment = NSTextAlignment.right
            self.addSubview(homeScore)
            let awayIcon = UIImageView(frame: CGRect(origin: CGPoint(x: homeIcon.frame.origin.x, y: homeIcon.frame.origin.y + homeIcon.frame.height + 6 * screenScale), size: homeIcon.frame.size))
            SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                if(SDImage != nil){
                    awayIcon.image = SDImage
                }else{
                    awayIcon.image = UIImage(named: "image_team_default")
                }
            }
            self.addSubview(awayIcon)
            let awayName = UILabel(frame: CGRect(x: homeName.frame.origin.x, y: awayIcon.frame.origin.y, width: homeName.frame.width, height: awayIcon.frame.height))
            awayName.text = match.awayteamName
            awayName.textColor = homeName.textColor
            awayName.font = homeName.font
            self.addSubview(awayName)
            let awayScore = UILabel(frame: CGRect(x: homeScore.frame.origin.x, y: awayName.frame.origin.y, width: homeScore.frame.width, height: awayName.frame.height))
            awayScore.text = awayScoreStr
            awayScore.textColor = homeScore.textColor
            awayScore.font = homeScore.font
            awayScore.textAlignment = NSTextAlignment.right
            self.addSubview(awayScore)
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
