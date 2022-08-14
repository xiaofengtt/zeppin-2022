import Foundation
class SportsMatchCellView: UIView {
    var match: SportsMatchModel!
    let padding: CGFloat = 6 * screenScale
    let paddingLeft: CGFloat = 15 * screenScale
    init(frame: CGRect, match: SportsMatchModel) {
        super.init(frame: frame)
        self.match = match
        self.backgroundColor = UIColor.white
        
        let matchResult = SportsUtils.getMatchResult(finalresult: match.finalresult)
        let homeScore = matchResult["homeScore"] as! String
        let awayScore = matchResult["awayScore"] as! String
        let result = matchResult["result"] as! String
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 10 * screenScale, width: frame.width, height: 20 * screenScale))
        titleLabel.text = "\(SportsUtils.timestampFormat(timestamp: match.time, format: "HH:mm"))  \(match.categoryName)\(match.roundName)"
        titleLabel.textColor = UIColor.colorFontDarkGray()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        self.addSubview(titleLabel)
        
        let scoreView = UIView(frame: CGRect(x: (frame.width - 70 * screenScale)/2, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale, width: 70 * screenScale, height: 30 * screenScale))
        self.addSubview(scoreView)
        if(match.status != "living" && match.status != "finished"){
            let scoreLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: scoreView.frame.size))
            scoreLabel.text = "VS"
            scoreLabel.textColor = UIColor.colorFontGray()
            scoreLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
            scoreLabel.textAlignment = NSTextAlignment.center
            scoreView.addSubview(scoreLabel)
        }else{
            let splitLabel = UILabel(frame: CGRect(x: (scoreView.frame.width - 20 * screenScale)/2, y: 0, width: 20 * screenScale, height: scoreView.frame.height))
            splitLabel.text = "-"
            splitLabel.textColor = UIColor.colorFontGray()
            splitLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
            splitLabel.textAlignment = NSTextAlignment.center
            scoreView.addSubview(splitLabel)
            
            let homeScoreLabel = UILabel(frame: CGRect(x: splitLabel.frame.origin.x - 25 * screenScale, y: 0, width: 25 * screenScale, height: scoreView.frame.height))
            homeScoreLabel.text = homeScore
            if(match.status == "living"){
                homeScoreLabel.textColor = UIColor.colorMainColor()
            }else{
                if(result == "-1"){
                    homeScoreLabel.textColor = UIColor.colorFontGray()
                }else{
                    homeScoreLabel.textColor = UIColor.colorFontBlack()
                }
            }
            homeScoreLabel.font = splitLabel.font
            homeScoreLabel.textAlignment = NSTextAlignment.right
            scoreView.addSubview(homeScoreLabel)
            
            let awayScoreLabel = UILabel(frame: CGRect(x: splitLabel.frame.origin.x + splitLabel.frame.width, y: homeScoreLabel.frame.origin.y, width: homeScoreLabel.frame.width, height: homeScoreLabel.frame.height))
            awayScoreLabel.text = awayScore
            if(match.status == "living"){
                awayScoreLabel.textColor = UIColor.colorMainColor()
            }else{
                if(result == "1"){
                    awayScoreLabel.textColor = UIColor.colorFontGray()
                }else{
                    awayScoreLabel.textColor = UIColor.colorFontBlack()
                }
            }
            awayScoreLabel.font = splitLabel.font
            awayScoreLabel.textAlignment = NSTextAlignment.left
            scoreView.addSubview(awayScoreLabel)
            
        }
        
        let homeIconView = UIImageView(frame: CGRect(x: scoreView.frame.origin.x - scoreView.frame.height, y: scoreView.frame.origin.y, width: scoreView.frame.height, height: scoreView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(homeIconView)
        
        let awayIconView = UIImageView(frame: CGRect(x: scoreView.frame.origin.x + scoreView.frame.width, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(awayIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: 0, y: scoreView.frame.origin.y, width: homeIconView.frame.origin.x - 10 * screenScale, height: scoreView.frame.height))
        homeNameLabel.text = match.hometeamName
        if(result == "-1"){
            homeNameLabel.textColor = UIColor.colorFontGray()
        }else{
            homeNameLabel.textColor = UIColor.colorFontBlack()
        }
        homeNameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        homeNameLabel.textAlignment = NSTextAlignment.right
        self.addSubview(homeNameLabel)
        
        let awayNameLabel = UILabel(frame: CGRect(x: awayIconView.frame.origin.x + awayIconView.frame.width + 10 * screenScale, y: homeNameLabel.frame.origin.y, width: frame.width - (awayIconView.frame.origin.x + awayIconView.frame.width + 10 * screenScale), height: homeNameLabel.frame.height))
        awayNameLabel.text = match.awayteamName
        if(result == "1"){
            awayNameLabel.textColor = UIColor.colorFontGray()
        }else{
            awayNameLabel.textColor = UIColor.colorFontBlack()
        }
        awayNameLabel.font = homeNameLabel.font
        awayNameLabel.textAlignment = NSTextAlignment.left
        self.addSubview(awayNameLabel)
        
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1 * screenScale, width: frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
