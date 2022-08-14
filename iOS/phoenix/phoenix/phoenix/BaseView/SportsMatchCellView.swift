import Foundation
class SportsMatchCellView: UIView {
    var match: SportsMatchModel!
    
    let padding: CGFloat = 15 * screenScale
    
    init(frame: CGRect, match: SportsMatchModel) {
        super.init(frame: frame)
        self.match = match
        self.backgroundColor = UIColor.white
        self.layer.cornerRadius = 5 * screenScale
        
        let mainView = UIView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        mainView.layer.masksToBounds = true
        self.addSubview(mainView)
        
        let timeLabel = UILabel(frame: CGRect(x: padding, y: 10 * screenScale, width: 100 * screenScale, height: 10 * screenScale))
        timeLabel.text = SportsUtils.timestampFormat(timestamp: match.time, format: "HH:mm")
        timeLabel.textColor = UIColor.colorFontDarkGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        mainView.addSubview(timeLabel)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: timeLabel.frame.origin.y, width: frame.width, height: timeLabel.frame.height))
        titleLabel.text = "\(match.categoryName)\(match.roundName)"
        titleLabel.textColor = timeLabel.textColor
        titleLabel.font = timeLabel.font
        titleLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(titleLabel)
        
        let statusLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + 25 * screenScale, width: titleLabel.frame.width, height: 20 * screenScale))
        statusLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.center
        switch match.status {
        case "finished":
            statusLabel.text = "完赛"
            statusLabel.textColor = UIColor.colorMainColor()
        case "living":
            statusLabel.text = "进行中"
            statusLabel.textColor = UIColor(red: 255/255, green: 63/255, blue: 55/255, alpha: 1)
        case "postponed":
            statusLabel.text = "延期"
            statusLabel.textColor = titleLabel.textColor
        default:
            statusLabel.text = "未开赛"
            statusLabel.textColor = titleLabel.textColor
        }
        mainView.addSubview(statusLabel)
        
        let homeIconView = UIImageView(frame: CGRect(x: frame.width/6, y: 30 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "phoenix_team_default")
            }
        }
        mainView.addSubview(homeIconView)
        
        let awayIconView = UIImageView(frame: CGRect(x: frame.width - homeIconView.frame.origin.x - homeIconView.frame.width, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "phoenix_team_default")
            }
        }
        mainView.addSubview(awayIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height + 4 * screenScale, width: homeIconView.frame.width + 100 * screenScale, height: 20 * screenScale))
        homeNameLabel.text = match.hometeamName
        homeNameLabel.textColor = UIColor.colorFontBlack()
        homeNameLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        homeNameLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(homeNameLabel)
        
        let awayNameLabel = UILabel(frame: CGRect(x: awayIconView.frame.origin.x - 50 * screenScale, y: homeNameLabel.frame.origin.y, width: homeNameLabel.frame.width, height: homeNameLabel.frame.height))
        awayNameLabel.text = match.awayteamName
        awayNameLabel.textColor = homeNameLabel.textColor
        awayNameLabel.font = homeNameLabel.font
        awayNameLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(awayNameLabel)
        
        if(match.finalresult.firstIndex(of: "(") != nil){
            match.finalresult = match.finalresult.substring(to: match.finalresult.firstIndex(of: "(")!)
        }
        let scoreLabel = UILabel(frame: CGRect(x: 0, y: homeNameLabel.frame.origin.y, width: frame.width, height: homeNameLabel.frame.height))
        if(match.status == "finished" || match.status == "living"){
           scoreLabel.text = match.finalresult
        }else{
            scoreLabel.text = "-"
        }
        scoreLabel.textColor = UIColor.colorFontBlack()
        scoreLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        scoreLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(scoreLabel)
        
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 2 * screenScale, width: frame.width, height: 2 * screenScale)
        bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
