//
//  MatchHistoryView.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/27.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MatchHistoryView: UIView {
    
    let winColor: UIColor = UIColor(red: 237/255, green: 50/255, blue: 36/255, alpha: 1)
    let drawColor: UIColor = UIColor(red: 175/255, green: 166/255, blue: 142/255, alpha: 1)
    let loseColor: UIColor = UIColor(red: 38/255, green: 123/255, blue: 230/255, alpha: 1)
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, historyList: Array<MatchModel>, match: MatchDetailModel) {
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
        
        
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 15 * screenScale, width: 72 * screenScale, height: 20 * screenScale))
        titleLabel.text = "历史交锋"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        let titleBackground = UIImageView(frame: CGRect(x: 0, y: titleLabel.frame.height/5*3, width: titleLabel.frame.width, height: 8 * screenScale))
        titleBackground.image = UIImage(named: "common_selected")
        titleLabel.addSubview(titleBackground)
        self.addSubview(titleLabel)
        
        let resultLabel = UILabel()
        resultLabel.numberOfLines = 1
        resultLabel.text = "\(match.hometeamName) VS \(match.awayteamName) — "
        resultLabel.textColor = UIColor.fontBlack()
        resultLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        resultLabel.sizeToFit()
        resultLabel.frame = CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 20 * screenScale, width: resultLabel.frame.width, height: 20 * screenScale)
        self.addSubview(resultLabel)
        
        let winNumLabel = UILabel(frame: CGRect(x: resultLabel.frame.origin.x + resultLabel.frame.width, y: resultLabel.frame.origin.y, width: 10 * screenScale, height: resultLabel.frame.height))
        winNumLabel.text = "\(winCount)"
        winNumLabel.textColor = winColor
        winNumLabel.font = resultLabel.font
        self.addSubview(winNumLabel)
        
        let winTextLabel = UILabel()
        winTextLabel.numberOfLines = 1
        winTextLabel.text = "胜 "
        winTextLabel.textColor = resultLabel.textColor
        winTextLabel.font = resultLabel.font
        winTextLabel.sizeToFit()
        winTextLabel.frame = CGRect(x: winNumLabel.frame.origin.x + winNumLabel.frame.width, y: resultLabel.frame.origin.y, width: winTextLabel.frame.width, height: resultLabel.frame.height)
        self.addSubview(winTextLabel)
        
        let drawNumLabel = UILabel(frame: CGRect(x: winTextLabel.frame.origin.x + winTextLabel.frame.width, y: resultLabel.frame.origin.y, width: winNumLabel.frame.width, height: resultLabel.frame.height))
        drawNumLabel.text = "\(drawCount)"
        drawNumLabel.textColor = drawColor
        drawNumLabel.font = resultLabel.font
        self.addSubview(drawNumLabel)
        
        let drawTextLabel = UILabel()
        drawTextLabel.numberOfLines = 1
        drawTextLabel.text = "平 "
        drawTextLabel.textColor = resultLabel.textColor
        drawTextLabel.font = resultLabel.font
        drawTextLabel.sizeToFit()
        drawTextLabel.frame = CGRect(x: drawNumLabel.frame.origin.x + drawNumLabel.frame.width, y: resultLabel.frame.origin.y, width: drawTextLabel.frame.width, height: resultLabel.frame.height)
        self.addSubview(drawTextLabel)
        
        let loseNumLabel = UILabel(frame: CGRect(x: drawTextLabel.frame.origin.x + drawTextLabel.frame.width, y: resultLabel.frame.origin.y, width: winNumLabel.frame.width, height: resultLabel.frame.height))
        loseNumLabel.text = "\(loseCount)"
        loseNumLabel.textColor = loseColor
        loseNumLabel.font = resultLabel.font
        self.addSubview(loseNumLabel)
        
        let loseTextLabel = UILabel()
        loseTextLabel.numberOfLines = 1
        loseTextLabel.text = "负"
        loseTextLabel.textColor = resultLabel.textColor
        loseTextLabel.font = resultLabel.font
        loseTextLabel.sizeToFit()
        loseTextLabel.frame = CGRect(x: loseNumLabel.frame.origin.x + loseNumLabel.frame.width, y: resultLabel.frame.origin.y, width: loseTextLabel.frame.width, height: resultLabel.frame.height)
        self.addSubview(loseTextLabel)
        
        let resultLineView = UIView(frame: CGRect(x: paddingLeft, y: resultLabel.frame.origin.y + resultLabel.frame.height + 5 * screenScale, width: frame.width - paddingLeft * 2, height: 30 * screenScale))
        let winLineLabel = UILabel(frame: CGRect(x: 0, y: 0, width: resultLineView.frame.width/3, height: resultLineView.frame.height))
        winLineLabel.backgroundColor = winColor
        winLineLabel.text = "\(winCount)胜"
        winLineLabel.textColor = UIColor.white
        winLineLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        winLineLabel.textAlignment = NSTextAlignment.center
        resultLineView.addSubview(winLineLabel)
        let drawLineLabel = UILabel(frame: CGRect(x: resultLineView.frame.width/3, y: 0, width: resultLineView.frame.width/3, height: resultLineView.frame.height))
        drawLineLabel.backgroundColor = drawColor
        drawLineLabel.text = "\(drawCount)平"
        drawLineLabel.textColor = winLineLabel.textColor
        drawLineLabel.font = winLineLabel.font
        drawLineLabel.textAlignment = winLineLabel.textAlignment
        resultLineView.addSubview(drawLineLabel)
        let loseLineLabel = UILabel(frame: CGRect(x: resultLineView.frame.width/3*2, y: 0, width: resultLineView.frame.width/3, height: resultLineView.frame.height))
        loseLineLabel.backgroundColor = loseColor
        loseLineLabel.text = "\(loseCount)负"
        loseLineLabel.textColor = winLineLabel.textColor
        loseLineLabel.font = winLineLabel.font
        loseLineLabel.textAlignment = winLineLabel.textAlignment
        resultLineView.addSubview(loseLineLabel)
        self.addSubview(resultLineView)
        self.frame.size = CGSize(width: frame.width, height: resultLineView.frame.origin.y + resultLineView.frame.height + 20 * screenScale)
        
        if(historyList.count > 0){
            let matchListView = MatchListView(frame: CGRect(x: paddingLeft, y: resultLineView.frame.origin.y + resultLineView.frame.height + 5 * screenScale, width: frame.width - paddingLeft * 2, height: 0), matchList: historyList, hometeam: match.hometeam)
            self.addSubview(matchListView)
            self.frame.size = CGSize(width: frame.width, height: matchListView.frame.origin.y + matchListView.frame.height + 20 * screenScale)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
