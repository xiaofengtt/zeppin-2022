//
//  MatchTimelineView.swift
//  ryqiu
//
//  Created by worker on 2019/6/27.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class MatchTimelineView: UIView {
    
    let iconHeight: CGFloat = 18 * screenScale
    let pointHeight: CGFloat = 12 * screenScale
    let splitLineColor: CGColor = UIColor.black.withAlphaComponent(0.1).cgColor
    
    init(frame: CGRect, timeline: TimelineModel, isFinal: Bool) {
        super.init(frame: frame)
        if(isFinal){
            self.frame.size = CGSize(width: frame.width, height: frame.height - 20 * screenScale)
        }
        
        if(timeline.type == "goal"){
            let scoreLabel = UILabel()
            scoreLabel.text = timeline.ryqiu.replacingOccurrences(of: " ", with: "").replacingOccurrences(of: "-", with: ":")
            scoreLabel.textColor = UIColor.white
            scoreLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            scoreLabel.textAlignment = NSTextAlignment.center
            scoreLabel.sizeToFit()
            scoreLabel.layer.cornerRadius = 2 * screenScale
            scoreLabel.layer.masksToBounds = true
            scoreLabel.frame = CGRect(x: (self.frame.width - scoreLabel.frame.width - 10 * screenScale)/2, y: 0, width: scoreLabel.frame.width + 10 * screenScale, height: iconHeight)
            self.addSubview(scoreLabel)
            
            let splitLine = CALayer()
            splitLine.frame = CGRect(x: self.frame.width/2, y: iconHeight, width: 1 * screenScale, height: self.frame.height - iconHeight)
            splitLine.backgroundColor = splitLineColor
            self.layer.addSublayer(splitLine)
            
            let goalIcon = UIImageView(frame: CGRect(x: 0, y: 0, width: iconHeight, height: iconHeight ))
            goalIcon.image = UIImage(named: "match_goal")
            self.addSubview(goalIcon)
            
            let nameLabel = UILabel()
            nameLabel.frame.size = CGSize(width: 0, height: scoreLabel.frame.height)
            nameLabel.text = timeline.playerIn
            nameLabel.textColor = UIColor.fontBlack()
            nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            nameLabel.textAlignment = NSTextAlignment.center
            nameLabel.sizeToFit()
            self.addSubview(nameLabel)
            
            let timeLabel = UILabel()
            timeLabel.frame.size = CGSize(width: 0, height: scoreLabel.frame.height)
            timeLabel.text = "\(timeline.time)'"
            timeLabel.textColor = UIColor.fontBlack()
            timeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            timeLabel.textAlignment = NSTextAlignment.center
            timeLabel.sizeToFit()
            self.addSubview(timeLabel)
            
            if(timeline.side == "home"){
                scoreLabel.backgroundColor = UIColor.homeMatchColor()
                goalIcon.frame.origin = CGPoint(x: scoreLabel.frame.origin.x - goalIcon.frame.width - 5 * screenScale, y: scoreLabel.frame.origin.y)
                nameLabel.frame = CGRect(x: goalIcon.frame.origin.x - nameLabel.frame.width - 5 * screenScale, y: scoreLabel.frame.origin.y, width: nameLabel.frame.width, height: scoreLabel.frame.height)
                timeLabel.frame = CGRect(x: scoreLabel.frame.origin.x + scoreLabel.frame.width + 5 * screenScale, y: scoreLabel.frame.origin.y, width: timeLabel.frame.width, height: scoreLabel.frame.height)
            }else{
                scoreLabel.backgroundColor = UIColor.awayMatchColor()
                goalIcon.frame.origin = CGPoint(x: scoreLabel.frame.origin.x + scoreLabel.frame.width + 5 * screenScale, y: scoreLabel.frame.origin.y)
                nameLabel.frame = CGRect(x: goalIcon.frame.origin.x + goalIcon.frame.width + 5 * screenScale, y: scoreLabel.frame.origin.y, width: nameLabel.frame.width, height: scoreLabel.frame.height)
                timeLabel.frame = CGRect(x: scoreLabel.frame.origin.x - timeLabel.frame.width - 5 * screenScale, y: scoreLabel.frame.origin.y, width: timeLabel.frame.width, height: scoreLabel.frame.height)
            }
        }else{
            let timePoint = UIView(frame: CGRect(x: (self.frame.width - pointHeight)/2, y: (iconHeight - pointHeight)/2, width: pointHeight, height: pointHeight))
            timePoint.backgroundColor = timeline.side == "home" ? UIColor.homeMatchColor() : UIColor.awayMatchColor()
            timePoint.layer.cornerRadius = pointHeight/2
            timePoint.layer.masksToBounds = true
            self.addSubview(timePoint)
            
            let splitLine = CALayer()
            splitLine.frame = CGRect(x: self.frame.width/2, y: iconHeight, width: 1 * screenScale, height: self.frame.height - iconHeight)
            splitLine.backgroundColor = splitLineColor
            self.layer.addSublayer(splitLine)
            
            let timeLabel = UILabel()
            timeLabel.frame.size = CGSize(width: 0, height: iconHeight)
            timeLabel.text = "\(timeline.time)'"
            timeLabel.textColor = UIColor.fontBlack()
            timeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            timeLabel.textAlignment = NSTextAlignment.center
            timeLabel.sizeToFit()
            if(timeline.side == "home"){
                timeLabel.frame = CGRect(x: timePoint.frame.origin.x + timePoint.frame.width + 10 * screenScale, y: 0, width: timeLabel.frame.width, height: iconHeight)
            }else{
                timeLabel.frame = CGRect(x: timePoint.frame.origin.x - timeLabel.frame.width - 10 * screenScale, y: 0, width: timeLabel.frame.width, height: iconHeight)
            }
            self.addSubview(timeLabel)
            
            if(timeline.type == "yellow card" || timeline.type == "red card"){
                let cardIcon = UIImageView(frame: CGRect(x: 0, y: 0, width: iconHeight, height: iconHeight))
                cardIcon.image = UIImage(named: timeline.type == "yellow card" ? "match_yellowcard" : "match_redcard")
                self.addSubview(cardIcon)
                
                let nameLabel = UILabel()
                nameLabel.frame.size = CGSize(width: 0, height: iconHeight)
                nameLabel.text = timeline.playerIn
                nameLabel.textColor = UIColor.fontBlack()
                nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                nameLabel.textAlignment = NSTextAlignment.center
                nameLabel.sizeToFit()
                self.addSubview(nameLabel)
                
                if(timeline.side == "home"){
                    cardIcon.frame.origin = CGPoint(x: timePoint.frame.origin.x - cardIcon.frame.width - 10 * screenScale, y: 0)
                    nameLabel.frame = CGRect(x: cardIcon.frame.origin.x - nameLabel.frame.width - 5 * screenScale, y: 0, width: nameLabel.frame.width, height: iconHeight)
                }else{
                    cardIcon.frame.origin = CGPoint(x: timePoint.frame.origin.x + timePoint.frame.width + 10 * screenScale, y: 0)
                    nameLabel.frame = CGRect(x: cardIcon.frame.origin.x + cardIcon.frame.width + 5 * screenScale, y: 0, width: nameLabel.frame.width, height: iconHeight)
                }
            }
            
            if(timeline.type == "subs"){
                let inIcon = UIImageView(frame: CGRect(x: 0, y: 0, width: iconHeight, height: iconHeight))
                self.addSubview(inIcon)
                
                let outIcon = UIImageView(frame: CGRect(x: 0, y: 0, width: iconHeight, height: iconHeight))
                self.addSubview(outIcon)
                
                let inLabel = UILabel()
                inLabel.frame.size = CGSize(width: 0, height: iconHeight)
                inLabel.text = timeline.playerIn
                inLabel.textColor = UIColor.fontBlack()
                inLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                inLabel.textAlignment = NSTextAlignment.center
                inLabel.sizeToFit()
                self.addSubview(inLabel)
                
                let outLabel = UILabel()
                outLabel.frame.size = CGSize(width: 0, height: iconHeight)
                outLabel.text = timeline.playerOut
                outLabel.textColor = UIColor.fontBlack()
                outLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                outLabel.textAlignment = NSTextAlignment.center
                outLabel.sizeToFit()
                self.addSubview(outLabel)
                
                if(timeline.side == "home"){
                    inIcon.image = UIImage(named: "match_in_home")
                    inLabel.frame = CGRect(x: timePoint.frame.origin.x - inLabel.frame.width - 15 * screenScale, y: 0, width: inLabel.frame.width, height: iconHeight)
                    inIcon.frame.origin = CGPoint(x: inLabel.frame.origin.x - inIcon.frame.width - 5 * screenScale, y: 0)
                    
                    outIcon.image = UIImage(named: "match_out_home")
                    outIcon.frame.origin = CGPoint(x: timePoint.frame.origin.x - outIcon.frame.width - 10 * screenScale, y: iconHeight + 2 * screenScale)
                    outLabel.frame = CGRect(x: outIcon.frame.origin.x - outLabel.frame.width - 5 * screenScale, y: outIcon.frame.origin.y, width: outLabel.frame.width, height: iconHeight)
                }else{
                    inIcon.image = UIImage(named: "match_in_away")
                    inLabel.frame = CGRect(x: timePoint.frame.origin.x + timePoint.frame.width + 15 * screenScale, y: 0, width: inLabel.frame.width, height: iconHeight)
                    inIcon.frame.origin = CGPoint(x: inLabel.frame.origin.x + inLabel.frame.width + 5 * screenScale, y: 0)
                    
                    outIcon.image = UIImage(named: "match_out_away")
                    outIcon.frame.origin = CGPoint(x: timePoint.frame.origin.x + timePoint.frame.width + 10 * screenScale, y: iconHeight + 2 * screenScale)
                    outLabel.frame = CGRect(x: outIcon.frame.origin.x + outIcon.frame.width + 5 * screenScale, y: outIcon.frame.origin.y, width: outLabel.frame.width, height: iconHeight)
                }
            }
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
