import Foundation
class SportsMatchListView: UIView {
    let winColor: UIColor = UIColor(red: 250/255, green: 40/255, blue: 0/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 172/255, green: 172/255, blue: 172/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 58/255, green: 196/255, blue: 97/255, alpha: 1)
    init(frame: CGRect, matchList: Array<SportsMatchModel>, hometeam: String) {
        super.init(frame: frame)
        let titleView = UIView(frame: CGRect(x: 0, y: 0, width: frame.width, height: 40 * screenScale))
        let dateLabel = UILabel(frame: CGRect(x: 0, y: 0, width: titleView.frame.width/4, height: titleView.frame.height))
        dateLabel.text = "时间"
        dateLabel.textColor = UIColor.colorFontGray()
        dateLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        dateLabel.textAlignment = NSTextAlignment.center
        titleView.addSubview(dateLabel)
        let leagueLabel = UILabel(frame: CGRect(x: frame.width/4, y: 0, width: frame.width/4, height: titleView.frame.height))
        leagueLabel.text = "赛事"
        leagueLabel.textColor = dateLabel.textColor
        leagueLabel.font = dateLabel.font
        leagueLabel.textAlignment = NSTextAlignment.center
        titleView.addSubview(leagueLabel)
        let scoreLabel = UILabel(frame: CGRect(x: frame.width/2, y: 0, width: frame.width/2, height: titleView.frame.height))
        scoreLabel.text = "对阵"
        scoreLabel.textColor = dateLabel.textColor
        scoreLabel.font = dateLabel.font
        scoreLabel.textAlignment = NSTextAlignment.center
        titleView.addSubview(scoreLabel)
        self.addSubview(titleView)
        let tableView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: frame.width, height: 0))
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
            let rowView = UIView(frame: CGRect(x: 0, y: tableView.frame.height, width: frame.width, height: 30 * screenScale))
            let dateRowLabel = UILabel(frame: CGRect(x: dateLabel.frame.origin.x, y: 0, width: dateLabel.frame.width, height: rowView.frame.height))
            dateRowLabel.numberOfLines = 2
            let attString = NSMutableAttributedString(string: "\(SportsUtils.timestampFormat(timestamp: match.time, format: "yy-MM-dd"))")
            let style = NSMutableParagraphStyle()
            style.maximumLineHeight = 14 * screenScale
            attString.addAttribute(NSAttributedString.Key.paragraphStyle, value: style, range: NSRange(location: 0, length: attString.length))
            dateRowLabel.attributedText = attString
            dateRowLabel.textColor = UIColor.colorFontDarkGray()
            dateRowLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
            dateRowLabel.textAlignment = dateLabel.textAlignment
            rowView.addSubview(dateRowLabel)
            let leagueRowLabel = UILabel(frame: CGRect(x: leagueLabel.frame.origin.x, y: 0, width: leagueLabel.frame.width, height: rowView.frame.height))
            leagueRowLabel.text = "\(match.categoryName)\(match.roundName)"
            leagueRowLabel.textColor = dateRowLabel.textColor
            leagueRowLabel.font = dateRowLabel.font
            leagueRowLabel.textAlignment = leagueLabel.textAlignment
            rowView.addSubview(leagueRowLabel)
            let scoreRowLabel = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x + scoreLabel.frame.width/2 - 20 * screenScale, y: (rowView.frame.height - 18 * screenScale)/2, width: 40 * screenScale, height: 18 * screenScale))
            scoreRowLabel.text = match.finalresult.replacingOccurrences(of: "-", with: ":")
            scoreRowLabel.textColor = scoreColor
            scoreRowLabel.font = dateRowLabel.font
            scoreRowLabel.textAlignment = scoreLabel.textAlignment
            rowView.addSubview(scoreRowLabel)
            let hometeamRowLabel = UILabel(frame: CGRect(x: frame.width/2, y: 0, width: scoreRowLabel.frame.origin.x - frame.width/2, height: rowView.frame.height))
            hometeamRowLabel.text = match.hometeamName
            hometeamRowLabel.textColor = dateRowLabel.textColor
            hometeamRowLabel.font = dateRowLabel.font
            hometeamRowLabel.textAlignment = NSTextAlignment.right
            rowView.addSubview(hometeamRowLabel)
            let awayteamRowLabel = UILabel(frame: CGRect(x: scoreRowLabel.frame.origin.x + scoreRowLabel.frame.width, y: 0, width: frame.width - (scoreRowLabel.frame.origin.x + scoreRowLabel.frame.width), height: rowView.frame.height))
            awayteamRowLabel.text = match.awayteamName
            awayteamRowLabel.textColor = dateRowLabel.textColor
            awayteamRowLabel.font = dateRowLabel.font
            awayteamRowLabel.textAlignment = NSTextAlignment.left
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
