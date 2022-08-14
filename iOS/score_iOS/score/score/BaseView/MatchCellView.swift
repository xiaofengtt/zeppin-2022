//
//  MatchCellView.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/6.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MatchCellView: UIView {
    
    var match: MatchModel!
    
    let padding: CGFloat = 6 * screenScale
    
    init(frame: CGRect, match: MatchModel) {
        super.init(frame: frame)
        self.match = match
        self.backgroundColor = UIColor.white
        self.layer.cornerRadius = 5 * screenScale
        self.addBaseShadow()
        
        let mainView = UIView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        mainView.layer.masksToBounds = true
        self.addSubview(mainView)
        
        let timeLabel = UILabel(frame: CGRect(x: padding, y: padding, width: 100 * screenScale, height: UIFont.smallSize() * screenScale))
        timeLabel.text = Utils.timestampFormat(timestamp: match.time, format: "HH:mm")
        timeLabel.textColor = UIColor.fontGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        mainView.addSubview(timeLabel)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: timeLabel.frame.origin.y, width: frame.width, height: timeLabel.frame.height))
        titleLabel.text = "\(match.categoryName)\(match.roundName)"
        titleLabel.textColor = timeLabel.textColor
        titleLabel.font = timeLabel.font
        titleLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(titleLabel)
        
        let statusView = UIView(frame: CGRect(x: frame.width - 42 * screenScale, y: -5 * screenScale, width: 47 * screenScale, height: 27 * screenScale))
        statusView.layer.cornerRadius = 5 * screenScale
        let statusLabel = UILabel(frame: CGRect(x: 0, y: 5 * screenScale, width: 42 * screenScale, height: 22 * screenScale))
        statusLabel.textColor = UIColor.white
        statusLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.center
        switch match.status {
        case "finished":
            statusLabel.text = "完赛"
            statusView.backgroundColor = UIColor(red: 10/255, green: 157/255, blue: 255/255, alpha: 1)
        case "living":
            statusLabel.text = "进行中"
            statusView.backgroundColor = UIColor.mainYellow()
        case "postponed":
            statusLabel.text = "延期"
            statusView.backgroundColor = UIColor.lightGray
        default:
            statusLabel.text = "未开赛"
            statusView.backgroundColor = UIColor.gray
        }
        statusView.addSubview(statusLabel)
        mainView.addSubview(statusView)
        
        let homeIconView = UIImageView(frame: CGRect(x: frame.width/6, y: 30 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "common_team_default")
            }
        }
        mainView.addSubview(homeIconView)
        
        let awayIconView = UIImageView(frame: CGRect(x: frame.width - homeIconView.frame.origin.x - homeIconView.frame.width, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "common_team_default")
            }
        }
        mainView.addSubview(awayIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height + 6 * screenScale, width: homeIconView.frame.width + 100 * screenScale, height: 20 * screenScale))
        homeNameLabel.text = match.hometeamName
        homeNameLabel.textColor = UIColor.fontBlack()
        homeNameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        homeNameLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(homeNameLabel)
        
        let awayNameLabel = UILabel(frame: CGRect(x: awayIconView.frame.origin.x - 50 * screenScale, y: homeNameLabel.frame.origin.y, width: homeNameLabel.frame.width, height: homeNameLabel.frame.height))
        awayNameLabel.text = match.awayteamName
        awayNameLabel.textColor = homeNameLabel.textColor
        awayNameLabel.font = homeNameLabel.font
        awayNameLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(awayNameLabel)
        
        if(match.finalresult.firstIndex(of: "(") == nil){
            let scoreLabel = UILabel(frame: CGRect(x: 0, y: frame.height * 0.45, width: frame.width, height: 20 * screenScale))
            if(match.status == "finished" || match.status == "living"){
               scoreLabel.text = match.finalresult
            }else{
                scoreLabel.text = "VS"
            }
            scoreLabel.textColor = UIColor.fontBlack()
            scoreLabel.font = UIFont.boldFont(size: UIFont.biggestSize() * screenScale)
            scoreLabel.textAlignment = NSTextAlignment.center
            mainView.addSubview(scoreLabel)
        }else{
            let finalStr = match.finalresult.substring(to: match.finalresult.firstIndex(of: "(")!)
            let halfStr = match.finalresult.replacingOccurrences(of: finalStr, with: "")
            
            let finalLabel = UILabel(frame: CGRect(x: 0, y: frame.height * 0.4, width: frame.width, height: 20 * screenScale))
            finalLabel.text = finalStr
            finalLabel.textColor = UIColor.fontBlack()
            finalLabel.font = UIFont.boldFont(size: UIFont.biggestSize() * screenScale)
            finalLabel.textAlignment = NSTextAlignment.center
            mainView.addSubview(finalLabel)
            
            let halfLabel = UILabel(frame: CGRect(x: 0, y: finalLabel.frame.origin.y + finalLabel.frame.height, width: frame.width, height: 20 * screenScale))
            halfLabel.text = halfStr
            halfLabel.textColor = finalLabel.textColor
            halfLabel.font = UIFont.boldFont(size: UIFont.middleSize() * screenScale)
            halfLabel.textAlignment = NSTextAlignment.center
            mainView.addSubview(halfLabel)
            
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
