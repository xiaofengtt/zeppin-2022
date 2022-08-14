import Foundation
class SportsMatchCellView: UIView {
    var match: SportsMatchModel!
    let padding: CGFloat = 6 * screenScale
    let paddingLeft: CGFloat = 15 * screenScale
    
    init(frame: CGRect, match: SportsMatchModel) {
        super.init(frame: frame)
        self.match = match
        self.backgroundColor = UIColor.white
        
        let mainView = UIView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        mainView.layer.masksToBounds = true
        self.addSubview(mainView)
        
        let leftView = UIView()
        let timeLabel = UILabel(frame: CGRect(x: 0 , y: 0, width: frame.width/4 - paddingLeft, height: UIFont.FontSizeSmaller() * screenScale))
        timeLabel.text = SportsUtils.timestampFormat(timestamp: match.time, format: "HH:mm")
        timeLabel.textColor = UIColor.colorFontDarkGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        leftView.addSubview(timeLabel)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 0, width: timeLabel.frame.width, height: timeLabel.frame.height))
        titleLabel.text = "\(match.categoryName)\(match.roundName)"
        titleLabel.textColor = timeLabel.textColor
        titleLabel.font = timeLabel.font
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(x: 0, y: timeLabel.frame.origin.y + timeLabel.frame.height, width: timeLabel.frame.width, height: titleLabel.frame.height)
        leftView.addSubview(titleLabel)
        
        leftView.frame = CGRect(x: paddingLeft, y: (mainView.frame.height - (timeLabel.frame.height + titleLabel.frame.height))/2, width: frame.width/4 - paddingLeft, height: timeLabel.frame.height + titleLabel.frame.height)
        mainView.addSubview(leftView)
        
        let teamView = UIView()
        let homeIconView = UIImageView(frame: CGRect(x: 20 * screenScale, y: 0, width: 25 * screenScale, height: 25 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "image_team_default")
            }
        }
        teamView.addSubview(homeIconView)
        
        let awayIconView = UIImageView(frame: CGRect(x: homeIconView.frame.origin.x, y: homeIconView.frame.origin.y + homeIconView.frame.height + 6 * screenScale, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "image_team_default")
            }
        }
        teamView.addSubview(awayIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x + homeIconView.frame.width + 10 * screenScale, y: homeIconView.frame.origin.y, width: frame.width/2 - (homeIconView.frame.origin.x + homeIconView.frame.width + 10 * screenScale) - 30 * screenScale, height: homeIconView.frame.height))
        homeNameLabel.text = match.hometeamName
        homeNameLabel.textColor = UIColor.colorFontBlack()
        homeNameLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        teamView.addSubview(homeNameLabel)
        
        let awayNameLabel = UILabel(frame: CGRect(x: homeNameLabel.frame.origin.x, y: awayIconView.frame.origin.y, width: homeNameLabel.frame.width, height: awayIconView.frame.height))
        awayNameLabel.text = match.awayteamName
        awayNameLabel.textColor = homeNameLabel.textColor
        awayNameLabel.font = homeNameLabel.font
        teamView.addSubview(awayNameLabel)
        
        teamView.frame = CGRect(x: frame.width/4, y: (frame.height - (awayIconView.frame.origin.y + awayIconView.frame.height))/2 , width: frame.width/2 - 30 * screenScale, height: awayIconView.frame.origin.y + awayIconView.frame.height)
        mainView.addSubview(teamView)
        
        let scoreView = UIView(frame: CGRect(x: frame.width/4 * 3 - 30 * screenScale, y: teamView.frame.origin.y, width: 30 * screenScale, height: teamView.frame.height))
        let homeScoreLabel = UILabel(frame: CGRect(x: 0, y: homeIconView.frame.origin.y, width: 30 * screenScale, height: homeIconView.frame.height))
        homeScoreLabel.textColor = UIColor.colorFontBlack()
        homeScoreLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        homeScoreLabel.textAlignment = NSTextAlignment.center
        scoreView.addSubview(homeScoreLabel)
        
        let awayScoreLabel = UILabel(frame: CGRect(x: 0, y: awayIconView.frame.origin.y, width: 30 * screenScale, height: homeIconView.frame.height))
        awayScoreLabel.textColor = homeScoreLabel.textColor
        awayScoreLabel.font = homeScoreLabel.font
        awayScoreLabel.textAlignment = homeScoreLabel.textAlignment
        scoreView.addSubview(awayScoreLabel)
        
        if(match.finalresult.firstIndex(of: "(") == nil){
            if(match.status == "finished" || match.status == "living"){
                let sorces = match.finalresult.split(separator: "-")
                if(sorces.count == 2){
                    let homeScore = sorces[0].replacingOccurrences(of: " ", with: "")
                    let awayScore = sorces[1].replacingOccurrences(of: " ", with: "")
                    homeScoreLabel.text = homeScore
                    awayScoreLabel.text = awayScore
                    if(Int(homeScore) ?? 0 > Int(awayScore) ?? 0){
                        awayScoreLabel.textColor = UIColor.colorFontGray()
                    }else if(Int(homeScore) ?? 0 < Int(awayScore) ?? 0){
                        homeScoreLabel.textColor = UIColor.colorFontGray()
                    }
                }else{
                    homeScoreLabel.text = "-"
                    awayScoreLabel.text = "-"
                }
            }else{
                homeScoreLabel.text = "-"
                awayScoreLabel.text = "-"
            }
        }else{
            let finalStr = match.finalresult.substring(to: match.finalresult.firstIndex(of: "(")!)
            let halfStr = match.finalresult.replacingOccurrences(of: finalStr, with: "")
            
            let sorces = halfStr.split(separator: "-")
            if(sorces.count == 2){
                let homeScore = sorces[0].replacingOccurrences(of: " ", with: "")
                let awayScore = sorces[1].replacingOccurrences(of: " ", with: "")
                homeScoreLabel.text = homeScore
                awayScoreLabel.text = awayScore
                if(Int(homeScore) ?? 0 > Int(awayScore) ?? 0){
                    awayScoreLabel.textColor = UIColor.colorFontGray()
                }else if(Int(homeScore) ?? 0 < Int(awayScore) ?? 0){
                    homeScoreLabel.textColor = UIColor.colorFontGray()
                }
            }else{
                homeScoreLabel.text = "-"
                awayScoreLabel.text = "-"
            }
        }
        mainView.addSubview(scoreView)
        
        
        let statusLabel = UILabel()
        statusLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        switch match.status {
        case "finished":
            statusLabel.text = "完赛"
            statusLabel.textColor = UIColor.colorMainColor()
        case "living":
            statusLabel.text = "进行中"
            statusLabel.textColor = UIColor(red: 255/255, green: 58/255, blue: 2/255, alpha: 1)
        case "postponed":
            statusLabel.text = "延期"
            statusLabel.textColor = UIColor.colorFontDarkGray()
        default:
            statusLabel.text = "未开赛"
            statusLabel.textColor = UIColor.colorFontDarkGray()
        }
        statusLabel.sizeToFit()
        statusLabel.frame = CGRect(x: frame.width - paddingLeft - statusLabel.frame.width, y: 0, width: statusLabel.frame.width, height: frame.height)
        mainView.addSubview(statusLabel)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: frame.height - 1 * screenScale, width: frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        mainView.layer.addSublayer(splitLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
