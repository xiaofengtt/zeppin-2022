import Foundation
class SportsMatchListView: UIView {
    let winColor: UIColor = UIColor(red: 247/255, green: 49/255, blue: 82/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 190/255, green: 190/255, blue: 190/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 59/255, green: 104/255, blue: 243/255, alpha: 1)
    init(frame: CGRect, matchList: Array<SportsMatchModel>, hometeam: String) {
        super.init(frame: frame)
        
        let titleView = UIView(frame: CGRect(x: 0, y: 0, width: frame.width, height: 40 * screenScale))
        let dateLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 70 * screenScale, height: titleView.frame.height))
        dateLabel.text = "日期"
        dateLabel.textColor = UIColor.colorFontGray()
        dateLabel.font = UIFont.fontMedium(size: UIFont.FontSizeMiddle() * screenScale)
        dateLabel.textAlignment = NSTextAlignment.left
        titleView.addSubview(dateLabel)
        
        let leagueLabel = UILabel(frame: CGRect(x: frame.width - 70 * screenScale, y: 0, width: 70 * screenScale, height: titleView.frame.height))
        leagueLabel.text = "赛事"
        leagueLabel.textColor = dateLabel.textColor
        leagueLabel.font = dateLabel.font
        leagueLabel.textAlignment = NSTextAlignment.right
        titleView.addSubview(leagueLabel)
        
        let scoreLabel = UILabel(frame: CGRect(x: (frame.width - 60 * screenScale)/2, y: 0, width: 60 * screenScale, height: titleView.frame.height))
        scoreLabel.text = "比分"
        scoreLabel.textColor = dateLabel.textColor
        scoreLabel.font = dateLabel.font
        scoreLabel.textAlignment = NSTextAlignment.center
        titleView.addSubview(scoreLabel)
        
        let hometeamLabel = UILabel(frame: CGRect(x: dateLabel.frame.width, y: 0, width: scoreLabel.frame.origin.x - dateLabel.frame.width, height: titleView.frame.height))
        hometeamLabel.text = "主队"
        hometeamLabel.textColor = dateLabel.textColor
        hometeamLabel.font = dateLabel.font
        hometeamLabel.textAlignment = NSTextAlignment.right
        titleView.addSubview(hometeamLabel)
        
        let awayteamLabel = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x + scoreLabel.frame.width, y: 0, width: leagueLabel.frame.origin.x - (scoreLabel.frame.origin.x + scoreLabel.frame.width), height: titleView.frame.height))
        awayteamLabel.text = "客队"
        awayteamLabel.textColor = dateLabel.textColor
        awayteamLabel.font = dateLabel.font
        awayteamLabel.textAlignment = NSTextAlignment.left
        titleView.addSubview(awayteamLabel)
        
        let titleSplitline = CALayer()
        titleSplitline.frame = CGRect(x: 0, y: titleView.frame.height - 1 * screenScale, width: titleView.frame.width, height: 1 * screenScale)
        titleSplitline.backgroundColor = UIColor.colorBgGray().cgColor
        titleView.layer.addSublayer(titleSplitline)
        self.addSubview(titleView)
        
        let tableView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height + 5 * screenScale, width: frame.width, height: 0))
        for i in 0 ..< matchList.count{
            let match = matchList[i]
            var scoreColor = UIColor.colorBgGray()
            let finalresult = match.finalresult.components(separatedBy: "-")
            let homeScoreStr = (finalresult.count >= 2 && finalresult[0] != "" ? finalresult[0] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            let awayScoreStr = (finalresult.count >= 2 && finalresult[1] != "" ? finalresult[1] : "-").trimmingCharacters(in: CharacterSet.whitespaces)
            if(homeScoreStr == "-" || awayScoreStr == "-"){
                continue
            }
            if(hometeam == match.hometeam){
                if(homeScoreStr.length == awayScoreStr.length){
                    if(homeScoreStr > awayScoreStr){
                        scoreColor = winColor
                    }else if(homeScoreStr < awayScoreStr){
                        scoreColor = loseColor
                    }else{
                        scoreColor = drawColor
                    }
                }else{
                    if(homeScoreStr.length > awayScoreStr.length){
                        scoreColor = winColor
                    }else{
                        scoreColor = loseColor
                    }
                }
            }else{
                if(homeScoreStr.length == awayScoreStr.length){
                    if(homeScoreStr > awayScoreStr){
                        scoreColor = loseColor
                    }else if(homeScoreStr < awayScoreStr){
                        scoreColor = winColor
                    }else{
                        scoreColor = drawColor
                    }
                }else{
                    if(homeScoreStr.length > awayScoreStr.length){
                        scoreColor = loseColor
                    }else{
                        scoreColor = winColor
                    }
                }
            }
            
            let rowView = UIView(frame: CGRect(x: 0, y: tableView.frame.height, width: frame.width, height: 40 * screenScale))
            
            let dateRowLabel = UILabel(frame: CGRect(x: dateLabel.frame.origin.x, y: 0, width: dateLabel.frame.width, height: rowView.frame.height))
            dateRowLabel.numberOfLines = 2
            let attString = NSMutableAttributedString(string: "\(SportsUtils.timestampFormat(timestamp: match.time, format: "yyyy MM-dd"))")
            let style = NSMutableParagraphStyle()
            style.maximumLineHeight = 14 * screenScale
            attString.addAttribute(NSAttributedString.Key.paragraphStyle, value: style, range: NSRange(location: 0, length: attString.length))
            dateRowLabel.attributedText = attString
            dateRowLabel.textColor = UIColor.colorFontDarkGray()
            dateRowLabel.font = UIFont.fontMedium(size: UIFont.FontSizeSmaller() * screenScale)
            dateRowLabel.textAlignment = dateLabel.textAlignment
            rowView.addSubview(dateRowLabel)
            
            let leagueRowLabel = UILabel(frame: CGRect(x: leagueLabel.frame.origin.x, y: 0, width: leagueLabel.frame.width, height: rowView.frame.height))
            leagueRowLabel.text = "\(match.categoryName)\(match.roundName)"
            leagueRowLabel.textColor = dateRowLabel.textColor
            leagueRowLabel.font = dateRowLabel.font
            leagueRowLabel.textAlignment = leagueLabel.textAlignment
            rowView.addSubview(leagueRowLabel)
            
            let scoreRowLabel = UILabel(frame: CGRect(x: (frame.width - 35 * screenScale)/2, y: (rowView.frame.height - 18 * screenScale)/2, width: 35 * screenScale, height: 18 * screenScale))
            scoreRowLabel.layer.masksToBounds = true
            scoreRowLabel.layer.cornerRadius = 2 * screenScale
            scoreRowLabel.backgroundColor = scoreColor
            scoreRowLabel.text = match.finalresult.replacingOccurrences(of: "-", with: ":")
            scoreRowLabel.textColor = UIColor.white
            scoreRowLabel.font = dateRowLabel.font
            scoreRowLabel.textAlignment = scoreLabel.textAlignment
            rowView.addSubview(scoreRowLabel)
            
            let hometeamRowLabel = UILabel(frame: CGRect(x: hometeamLabel.frame.origin.x, y: 0, width: hometeamLabel.frame.width, height: rowView.frame.height))
            hometeamRowLabel.text = match.hometeamName
            hometeamRowLabel.textColor = UIColor.colorFontBlack()
            hometeamRowLabel.font = dateRowLabel.font
            hometeamRowLabel.textAlignment = hometeamLabel.textAlignment
            rowView.addSubview(hometeamRowLabel)
            
            let awayteamRowLabel = UILabel(frame: CGRect(x: awayteamLabel.frame.origin.x, y: 0, width: awayteamLabel.frame.width, height: rowView.frame.height))
            awayteamRowLabel.text = match.awayteamName
            awayteamRowLabel.textColor = hometeamRowLabel.textColor
            awayteamRowLabel.font = hometeamRowLabel.font
            awayteamRowLabel.textAlignment = awayteamLabel.textAlignment
            rowView.addSubview(awayteamRowLabel)
            
            tableView.addSubview(rowView)
            tableView.frame.size = CGSize(width: tableView.frame.width, height: rowView.frame.origin.y + rowView.frame.height)
        }
        self.addSubview(tableView)
        self.frame.size = CGSize(width: frame.width, height: tableView.frame.origin.y + tableView.frame.height)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
