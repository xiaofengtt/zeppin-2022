import Foundation
class SportsMatchTimelineView: UIView {
    let paddingLeft: CGFloat = 15 * screenScale
    let iconHeight: CGFloat = 16 * screenScale
    let splitLineColor: CGColor = UIColor.colorFontBlack().cgColor
    let timeLineFont: UIFont = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
    init(frame: CGRect, timeline: SportsTimelineModel, isFinal: Bool) {
        super.init(frame: frame)
        if(isFinal){
            self.frame.size = CGSize(width: frame.width, height: frame.height - 10 * screenScale)
        }
        let timeLabel = UILabel(frame: CGRect(x: (self.frame.width - 50 * screenScale)/2, y: 0, width: 50 * screenScale, height: iconHeight))
        timeLabel.text = "\(timeline.time)'"
        timeLabel.textColor = UIColor.colorFontBlack()
        timeLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        timeLabel.textAlignment = NSTextAlignment.center
        self.addSubview(timeLabel)
        let splitLine = SportsDottedLine(frame: CGRect(x: (self.frame.width - 2 * screenScale) / 2, y: iconHeight + 5 * screenScale, width: 2 * screenScale, height: self.frame.height - iconHeight - 10 * screenScale), color: UIColor.colorFontGray(), direction: SportsDottedLineDirection.vertical)
        self.addSubview(splitLine)
        if(timeline.type == "subs"){
            if(timeline.side == "home"){
                let subsOutIcon = UIImageView(frame: CGRect(x: paddingLeft, y: 0, width: iconHeight, height: iconHeight))
                subsOutIcon.image = UIImage(named: "image_subs_out")
                self.addSubview(subsOutIcon)
                
                let subsOutLabel = UILabel(frame: CGRect(x: subsOutIcon.frame.origin.x + subsOutIcon.frame.width, y: subsOutIcon.frame.origin.y, width: timeLabel.frame.origin.x - (subsOutIcon.frame.origin.x + subsOutIcon.frame.width), height: subsOutIcon.frame.height))
                subsOutLabel.text = "换下 \(timeline.playerOut)"
                subsOutLabel.textColor = UIColor.colorFontDarkGray()
                subsOutLabel.font = timeLineFont
                subsOutLabel.textAlignment = NSTextAlignment.right
                self.addSubview(subsOutLabel)
                
                let subsInIcon = UIImageView(frame: CGRect(x: subsOutIcon.frame.origin.x, y: subsOutIcon.frame.origin.y + subsOutIcon.frame.height + 10 * screenScale, width: subsOutIcon.frame.width, height: subsOutIcon.frame.height))
                subsInIcon.image = UIImage(named: "image_subs_in")
                self.addSubview(subsInIcon)
                
                let subsInLabel = UILabel(frame: CGRect(x: subsOutLabel.frame.origin.x, y: subsInIcon.frame.origin.y, width: subsOutLabel.frame.width, height: subsInIcon.frame.height))
                subsInLabel.text = "换上 \(timeline.playerIn)"
                subsInLabel.textColor = subsOutLabel.textColor
                subsInLabel.font = subsOutLabel.font
                subsInLabel.textAlignment = subsOutLabel.textAlignment
                self.addSubview(subsInLabel)
            }else{
                let subsOutIcon = UIImageView(frame: CGRect(x: self.frame.width - paddingLeft - iconHeight, y: 0, width: iconHeight, height: iconHeight))
                subsOutIcon.image = UIImage(named: "image_subs_out")
                self.addSubview(subsOutIcon)
                
                let subsOutLabel = UILabel(frame: CGRect(x: timeLabel.frame.origin.x + timeLabel.frame.width, y: subsOutIcon.frame.origin.y, width: subsOutIcon.frame.origin.x - (timeLabel.frame.origin.x + timeLabel.frame.width), height: subsOutIcon.frame.height))
                subsOutLabel.text = "\(timeline.playerOut) 换下"
                subsOutLabel.textColor = UIColor.colorFontDarkGray()
                subsOutLabel.font = timeLineFont
                subsOutLabel.textAlignment = NSTextAlignment.left
                self.addSubview(subsOutLabel)
                
                let subsInIcon = UIImageView(frame: CGRect(x: subsOutIcon.frame.origin.x, y: subsOutIcon.frame.origin.y + subsOutIcon.frame.height + 10 * screenScale, width: subsOutIcon.frame.width, height: subsOutIcon.frame.height))
                subsInIcon.image = UIImage(named: "image_subs_in")
                self.addSubview(subsInIcon)
                
                let subsInLabel = UILabel(frame: CGRect(x: subsOutLabel.frame.origin.x, y: subsInIcon.frame.origin.y, width: subsOutLabel.frame.width, height: subsInIcon.frame.height))
                subsInLabel.text = "\(timeline.playerIn) 换上"
                subsInLabel.textColor = subsOutLabel.textColor
                subsInLabel.font = subsOutLabel.font
                subsInLabel.textAlignment = subsOutLabel.textAlignment
                self.addSubview(subsInLabel)
            }
        }else{
            if(timeline.side == "home"){
                let timelineIcon = UIImageView(frame: CGRect(x: paddingLeft, y: 0, width: iconHeight, height: iconHeight))
                if(timeline.type == "goal"){
                    timelineIcon.image = UIImage(named: "image_goal")
                }else if(timeline.type == "yellow card"){
                    timelineIcon.image = UIImage(named: "image_yellowcard")
                }else if(timeline.type == "red card"){
                    timelineIcon.image = UIImage(named: "image_redcard")
                }
                self.addSubview(timelineIcon)
                
                let timelineLabel = UILabel(frame: CGRect(x: timelineIcon.frame.origin.x + timelineIcon.frame.width, y: timelineIcon.frame.origin.y, width: timeLabel.frame.origin.x - (timelineIcon.frame.origin.x + timelineIcon.frame.width), height: timelineIcon.frame.height))
                timelineLabel.text = "\(getTypeCN(type: timeline.type)) \(timeline.playerIn)"
                timelineLabel.textColor = UIColor.colorFontDarkGray()
                timelineLabel.font = timeLineFont
                timelineLabel.textAlignment = NSTextAlignment.right
                self.addSubview(timelineLabel)
                
                if(timeline.type == "goal"){
                    let scoreLabel = UILabel(frame: CGRect(x: timelineLabel.frame.origin.x, y: timelineLabel.frame.origin.y + timelineLabel.frame.height + 10 * screenScale, width: timelineLabel.frame.width, height: timelineLabel.frame.height))
                    scoreLabel.text = timeline.score.replacingOccurrences(of: "-", with: ":").replacingOccurrences(of: " ", with: "")
                    scoreLabel.textColor = UIColor.colorMainColor()
                    scoreLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
                    scoreLabel.textAlignment = timelineLabel.textAlignment
                    self.addSubview(scoreLabel)
                }
            }else{
                let timelineIcon = UIImageView(frame: CGRect(x: self.frame.width - paddingLeft - iconHeight, y: 0, width: iconHeight, height: iconHeight))
                if(timeline.type == "goal"){
                    timelineIcon.image = UIImage(named: "image_goal")
                }else if(timeline.type == "yellow card"){
                    timelineIcon.image = UIImage(named: "image_yellowcard")
                }else if(timeline.type == "red card"){
                    timelineIcon.image = UIImage(named: "image_redcard")
                }
                self.addSubview(timelineIcon)
                
                let timelineLabel = UILabel(frame: CGRect(x: timeLabel.frame.origin.x + timeLabel.frame.width, y: timelineIcon.frame.origin.y, width: timelineIcon.frame.origin.x - (timeLabel.frame.origin.x + timeLabel.frame.width), height: timelineIcon.frame.height))
                timelineLabel.text = "\(timeline.playerIn) \(getTypeCN(type: timeline.type))"
                timelineLabel.textColor = UIColor.colorFontDarkGray()
                timelineLabel.font = timeLineFont
                timelineLabel.textAlignment = NSTextAlignment.left
                self.addSubview(timelineLabel)
                
                if(timeline.type == "goal"){
                    let scoreLabel = UILabel(frame: CGRect(x: timelineLabel.frame.origin.x, y: timelineLabel.frame.origin.y + timelineLabel.frame.height + 10 * screenScale, width: timelineLabel.frame.width, height: timelineLabel.frame.height))
                    scoreLabel.text = timeline.score.replacingOccurrences(of: "-", with: ":").replacingOccurrences(of: " ", with: "")
                    scoreLabel.textColor = UIColor.colorMainColor()
                    scoreLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
                    scoreLabel.textAlignment = timelineLabel.textAlignment
                    self.addSubview(scoreLabel)
                }
            }
        }
    }
    func getTypeCN(type: String) -> String{
        if(type == "goal"){
            return "进球"
        }else if(type == "yellow card"){
            return "黄牌"
        }else if(type == "red card"){
            return "红牌"
        }else{
            return ""
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
