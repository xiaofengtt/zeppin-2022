import Foundation
class SportsMatchFixtrueView: UIView {
    let winColor: UIColor = UIColor(red: 247/255, green: 49/255, blue: 82/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 190/255, green: 190/255, blue: 190/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 59/255, green: 104/255, blue: 243/255, alpha: 1)
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
        
        let titleView = UIView(frame: CGRect(x: paddingLeft, y: 20 * screenScale, width: frame.width - 2 * paddingLeft, height: 20 * screenScale))
        let iconView = UIImageView(frame: CGRect(x: 0, y: 0, width: titleView.frame.height, height: titleView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconView.image = SDImage
            }else{
                iconView.image = UIImage(named: "image_team_default")
            }
        }
        titleView.addSubview(iconView)
        
        let resultLabel = UILabel()
        resultLabel.numberOfLines = 1
        resultLabel.text = "\(hometeamName) — "
        resultLabel.textColor = UIColor.colorFontBlack()
        resultLabel.font = UIFont.fontMedium(size: UIFont.FontSizeMiddle() * screenScale)
        resultLabel.sizeToFit()
        resultLabel.frame = CGRect(x: iconView.frame.origin.x + iconView.frame.width + 5 * screenScale, y: 0, width: resultLabel.frame.width, height: titleView.frame.height)
        titleView.addSubview(resultLabel)
        
        let winNumLabel = UILabel(frame: CGRect(x: resultLabel.frame.origin.x + resultLabel.frame.width, y: resultLabel.frame.origin.y, width: 10 * screenScale, height: resultLabel.frame.height))
        winNumLabel.text = "\(winCount)"
        winNumLabel.textColor = winColor
        winNumLabel.font = resultLabel.font
        titleView.addSubview(winNumLabel)
        
        let winTextLabel = UILabel()
        winTextLabel.numberOfLines = 1
        winTextLabel.text = "胜 "
        winTextLabel.textColor = resultLabel.textColor
        winTextLabel.font = resultLabel.font
        winTextLabel.sizeToFit()
        winTextLabel.frame = CGRect(x: winNumLabel.frame.origin.x + winNumLabel.frame.width, y: resultLabel.frame.origin.y, width: winTextLabel.frame.width, height: resultLabel.frame.height)
        titleView.addSubview(winTextLabel)
        
        let drawNumLabel = UILabel(frame: CGRect(x: winTextLabel.frame.origin.x + winTextLabel.frame.width, y: resultLabel.frame.origin.y, width: winNumLabel.frame.width, height: resultLabel.frame.height))
        drawNumLabel.text = "\(drawCount)"
        drawNumLabel.textColor = drawColor
        drawNumLabel.font = resultLabel.font
        titleView.addSubview(drawNumLabel)
        
        let drawTextLabel = UILabel()
        drawTextLabel.numberOfLines = 1
        drawTextLabel.text = "平 "
        drawTextLabel.textColor = resultLabel.textColor
        drawTextLabel.font = resultLabel.font
        drawTextLabel.sizeToFit()
        drawTextLabel.frame = CGRect(x: drawNumLabel.frame.origin.x + drawNumLabel.frame.width, y: resultLabel.frame.origin.y, width: drawTextLabel.frame.width, height: resultLabel.frame.height)
        titleView.addSubview(drawTextLabel)
        
        let loseNumLabel = UILabel(frame: CGRect(x: drawTextLabel.frame.origin.x + drawTextLabel.frame.width, y: resultLabel.frame.origin.y, width: winNumLabel.frame.width, height: resultLabel.frame.height))
        loseNumLabel.text = "\(loseCount)"
        loseNumLabel.textColor = loseColor
        loseNumLabel.font = resultLabel.font
        titleView.addSubview(loseNumLabel)
        
        let loseTextLabel = UILabel()
        loseTextLabel.numberOfLines = 1
        loseTextLabel.text = "负"
        loseTextLabel.textColor = resultLabel.textColor
        loseTextLabel.font = resultLabel.font
        loseTextLabel.sizeToFit()
        loseTextLabel.frame = CGRect(x: loseNumLabel.frame.origin.x + loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseTextLabel.frame.width, height: resultLabel.frame.height)
        titleView.addSubview(loseTextLabel)
        
        self.frame.size = CGSize(width: frame.width, height: titleView.frame.origin.y + titleView.frame.height)
        self.addSubview(titleView)
        
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
