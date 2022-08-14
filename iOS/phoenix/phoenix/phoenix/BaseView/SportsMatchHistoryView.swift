import Foundation
class SportsMatchHistoryView: UIView {
    let winColor: UIColor = UIColor(red: 250/255, green: 40/255, blue: 0/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 172/255, green: 172/255, blue: 172/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 3/255, green: 175/255, blue: 152/255, alpha: 1)
    let paddingLeft: CGFloat = 15 * screenScale
    init(frame: CGRect, historyList: Array<SportsMatchModel>, match: SportsMatchDetailModel) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        var winCount: Int = 0
        var loseCount: Int = 0
        var drawCount: Int = 0
        for history in historyList{
            let finalresult = history.finalresult.components(separatedBy: "-")
            let homeScoreStr = (finalresult.count >= 2 && finalresult[0] != "" ? finalresult[0] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            let awayScoreStr = (finalresult.count >= 2 && finalresult[1] != "" ? finalresult[1] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            if(homeScoreStr == "-" || awayScoreStr == "-"){
                continue
            }
            if(history.hometeam == match.hometeam){
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
        let titleView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: frame.width, height: 40 * screenScale), title: "历史交锋")
        self.addSubview(titleView)
        
        let resultLabel = UILabel()
        resultLabel.numberOfLines = 1
        resultLabel.text = "\(match.hometeamName) vs \(match.awayteamName)"
        resultLabel.textColor = UIColor.colorFontBlack()
        resultLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        resultLabel.sizeToFit()
        resultLabel.frame = CGRect(x: paddingLeft, y: titleView.frame.origin.y + titleView.frame.height + 10 * screenScale, width: resultLabel.frame.width, height: 20 * screenScale)
        self.addSubview(resultLabel)
        
        let loseNumLabel = UILabel(frame: CGRect(x: frame.width - paddingLeft - 25 * screenScale, y: resultLabel.frame.origin.y, width: 25 * screenScale, height: resultLabel.frame.height))
        loseNumLabel.text = "\(loseCount)负"
        loseNumLabel.textColor = loseColor
        loseNumLabel.font = resultLabel.font
        self.addSubview(loseNumLabel)
        let drawNumLabel = UILabel(frame: CGRect(x: loseNumLabel.frame.origin.x - loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseNumLabel.frame.width, height: resultLabel.frame.height))
        drawNumLabel.text = "\(drawCount)平"
        drawNumLabel.textColor = drawColor
        drawNumLabel.font = resultLabel.font
        self.addSubview(drawNumLabel)
        let winNumLabel = UILabel(frame: CGRect(x: drawNumLabel.frame.origin.x - loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseNumLabel.frame.width, height: resultLabel.frame.height))
        winNumLabel.text = "\(winCount)胜"
        winNumLabel.textColor = winColor
        winNumLabel.font = resultLabel.font
        self.addSubview(winNumLabel)
        
        self.frame.size = CGSize(width: frame.width, height: resultLabel.frame.origin.y + resultLabel.frame.height + 10 * screenScale)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: self.frame.height - 1 * screenScale, width: frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(splitLine)
        if(historyList.count > 0){
            let matchListView = SportsMatchListView(frame: CGRect(x: paddingLeft, y: resultLabel.frame.origin.y + resultLabel.frame.height + 10 * screenScale, width: frame.width - paddingLeft * 2, height: 0), matchList: historyList, hometeam: match.hometeam)
            self.addSubview(matchListView)
            self.frame.size = CGSize(width: frame.width, height: matchListView.frame.origin.y + matchListView.frame.height + 10 * screenScale)
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
