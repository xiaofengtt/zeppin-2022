import Foundation
class SportsMatchFixtrueView: UIView {
    let winColor: UIColor = UIColor(red: 250/255, green: 40/255, blue: 0/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 172/255, green: 172/255, blue: 172/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 58/255, green: 196/255, blue: 97/255, alpha: 1)
    let paddingLeft: CGFloat = 10 * screenScale
    init(frame: CGRect, matchList: Array<SportsMatchModel>, hometeam: String, hometeamName: String, hometeamIconUrl: String) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        var winCount: Int = 0
        var loseCount: Int = 0
        var drawCount: Int = 0
        for match in matchList{
            let finalresult = match.finalresult.components(separatedBy: "-")
            let homeScoreStr = (finalresult.count >= 2 && finalresult[0] != "" ? finalresult[0] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            let awayScoreStr = (finalresult.count >= 2 && finalresult[1] != "" ? finalresult[1] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            if(homeScoreStr == "-" || awayScoreStr == "-"){
                continue
            }
            if(match.hometeam == hometeam){
                if(homeScoreStr.length == awayScoreStr.length){
                    if(homeScoreStr > awayScoreStr){
                        winCount = winCount + 1
                    }else if(homeScoreStr < awayScoreStr){
                        loseCount = loseCount + 1
                    }else{
                        drawCount = drawCount + 1
                    }
                }else{
                    if(homeScoreStr.length > awayScoreStr.length){
                        winCount = winCount + 1
                    }else{
                        loseCount = loseCount + 1
                    }
                }
            }else{
                if(homeScoreStr.length == awayScoreStr.length){
                    if(homeScoreStr > awayScoreStr){
                        loseCount = loseCount + 1
                    }else if(homeScoreStr < awayScoreStr){
                        winCount = winCount + 1
                    }else{
                        drawCount = drawCount + 1
                    }
                }else{
                    if(homeScoreStr.length > awayScoreStr.length){
                        loseCount = loseCount + 1
                    }else{
                        winCount = winCount + 1
                    }
                }
            }
        }
        let titleView = UIView(frame: CGRect(x: 0, y: 15 * screenScale, width: frame.width, height: 65 * screenScale))
        let iconView = UIImageView(frame: CGRect(x: paddingLeft, y: 0, width: 30 * screenScale, height: 30 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconView.image = SDImage
            }else{
                iconView.image = UIImage(named: "image_team_default")
            }
        }
        let titleLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 5 * screenScale, y: iconView.frame.origin.y, width: titleView.frame.width - (iconView.frame.origin.x + iconView.frame.width + 5 * screenScale), height: iconView.frame.height))
        titleLabel.text = hometeamName
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        titleLabel.textColor = UIColor.colorFontBlack()
        titleView.addSubview(titleLabel)
        titleView.addSubview(iconView)
        self.addSubview(titleView)
        let resultLabel = UILabel()
        resultLabel.numberOfLines = 1
        resultLabel.text = "近\(matchList.count)场比赛"
        resultLabel.textColor = UIColor.colorFontBlack()
        resultLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        resultLabel.sizeToFit()
        resultLabel.frame = CGRect(x: iconView.frame.origin.x, y: iconView.frame.origin.y + iconView.frame.height + 5 * screenScale, width: resultLabel.frame.width, height: titleView.frame.height - (iconView.frame.origin.y + iconView.frame.height + 15 * screenScale))
        titleView.addSubview(resultLabel)
        
        let loseNumLabel = UILabel(frame: CGRect(x: titleView.frame.width - paddingLeft - 25 * screenScale, y: resultLabel.frame.origin.y, width: 25 * screenScale, height: resultLabel.frame.height))
        loseNumLabel.text = "\(loseCount)负"
        loseNumLabel.textColor = loseColor
        loseNumLabel.font = resultLabel.font
        titleView.addSubview(loseNumLabel)
        let drawNumLabel = UILabel(frame: CGRect(x: loseNumLabel.frame.origin.x - loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseNumLabel.frame.width, height: resultLabel.frame.height))
        drawNumLabel.text = "\(drawCount)平"
        drawNumLabel.textColor = drawColor
        drawNumLabel.font = resultLabel.font
        titleView.addSubview(drawNumLabel)
        let winNumLabel = UILabel(frame: CGRect(x: drawNumLabel.frame.origin.x - loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseNumLabel.frame.width, height: resultLabel.frame.height))
        winNumLabel.text = "\(winCount)胜"
        winNumLabel.textColor = winColor
        winNumLabel.font = resultLabel.font
        titleView.addSubview(winNumLabel)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: titleView.frame.height - 1 * screenScale, width: titleView.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        titleView.layer.addSublayer(splitLine)
        self.frame.size = CGSize(width: frame.width, height: titleView.frame.origin.y + titleView.frame.height)
        if(matchList.count > 0){
            let matchListView = SportsMatchListView(frame: CGRect(x: paddingLeft, y: titleView.frame.origin.y + titleView.frame.height + 5 * screenScale, width: frame.width - paddingLeft * 2, height: 0), matchList: matchList, hometeam: hometeam)
            self.addSubview(matchListView)
            self.frame.size = CGSize(width: frame.width, height: matchListView.frame.origin.y + matchListView.frame.height)
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
