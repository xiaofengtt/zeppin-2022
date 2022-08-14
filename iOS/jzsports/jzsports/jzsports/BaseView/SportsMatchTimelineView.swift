import Foundation
class SportsMatchTimelineView: UIView {
    let paddingLeft: CGFloat = 20 * screenScale
    let iconHeight: CGFloat = 20 * screenScale
    let splitLineColor: CGColor = UIColor.colorFontBlack().cgColor
    let timeLineFont: UIFont = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
    init(frame: CGRect, timeline: SportsTimelineModel, isFinal: Bool) {
        super.init(frame: frame)
        let timeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 100 * screenScale, height: iconHeight))
        timeLabel.text = "\(timeline.time)'"
        timeLabel.textColor = UIColor.colorFontBlack()
        timeLabel.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
        timeLabel.sizeToFit()
        timeLabel.frame = CGRect(x: 25 * screenScale - timeLabel.frame.width / 2, y: 0, width: timeLabel.frame.width, height: iconHeight)
        self.addSubview(timeLabel)
        let splitLine = SportsDottedLine(frame: CGRect(x: paddingLeft + 4 * screenScale, y: iconHeight + 5 * screenScale, width: 2 * screenScale, height: self.frame.height - iconHeight - 10 * screenScale), color: UIColor.colorFontGray(), direction: SportsDottedLineDirection.vertical)
        self.addSubview(splitLine)
        
        let timelineIcon = UIImageView(frame: CGRect(x: 50 * screenScale, y: 0, width: iconHeight, height: iconHeight))
        if(timeline.type == "goal"){
            timelineIcon.image = UIImage(named: "image_goal")
        }else if(timeline.type == "yellow card"){
            timelineIcon.image = UIImage(named: "image_yellowcard")
        }else if(timeline.type == "red card"){
            timelineIcon.image = UIImage(named: "image_redcard")
        }else{
            timelineIcon.image = UIImage(named: "image_subs_turn")
        }
        self.addSubview(timelineIcon)
        let teamIcon = UIImageView(frame: CGRect(x: timelineIcon.frame.origin.x + timelineIcon.frame.width + 15 * screenScale, y: timelineIcon.frame.origin.y, width: iconHeight, height: iconHeight))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + timeline.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                teamIcon.image = SDImage
            }else{
                teamIcon.image = UIImage(named: "image_team_default")
            }
        }
        self.addSubview(teamIcon)
        let timeLineLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x + teamIcon.frame.width + 5 * screenScale, y: timelineIcon.frame.origin.y, width: frame.width - (teamIcon.frame.origin.x + teamIcon.frame.width + 5 * screenScale), height: timelineIcon.frame.height))
        timeLineLabel.text = "\(timeline.teamName) \(getTypeCN(type: timeline.type))"
        timeLineLabel.textColor = UIColor.colorFontGray()
        timeLineLabel.font = UIFont.fontMedium(size: UIFont.FontSizeMiddle() * screenScale)
        timeLineLabel.sizeToFit()
        timeLineLabel.frame = CGRect(x: teamIcon.frame.origin.x + teamIcon.frame.width + 5 * screenScale, y: timelineIcon.frame.origin.y, width: timeLineLabel.frame.width, height: timelineIcon.frame.height)
        self.addSubview(timeLineLabel)
        if(timeline.type == "goal"){
            let scoreLabel = UILabel(frame: CGRect(x: timeLineLabel.frame.origin.x + timeLineLabel.frame.width + 5 * screenScale, y: timelineIcon.frame.origin.y, width: 50 * screenScale, height: timelineIcon.frame.height))
            scoreLabel.text = timeline.score.replacingOccurrences(of: "-", with: ":").replacingOccurrences(of: " ", with: "")
            scoreLabel.textColor = UIColor.colorFontBlack()
            scoreLabel.font = timeLineLabel.font
            self.addSubview(scoreLabel)
        }
        if(timeline.type == "subs"){
            let subsOutLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x, y: timelineIcon.frame.origin.y + timelineIcon.frame.height + 8 * screenScale, width: 200 * screenScale, height: iconHeight))
            subsOutLabel.text = "\(timeline.playerOut) 换下"
            subsOutLabel.textColor = UIColor.colorFontBlack()
            subsOutLabel.font = timeLineLabel.font
            subsOutLabel.sizeToFit()
            subsOutLabel.frame = CGRect(x: teamIcon.frame.origin.x, y: timelineIcon.frame.origin.y + timelineIcon.frame.height + 8 * screenScale, width: subsOutLabel.frame.width, height: iconHeight)
            self.addSubview(subsOutLabel)
            let subsOutIcon = UIImageView(frame: CGRect(x: subsOutLabel.frame.origin.x + subsOutLabel.frame.width + 10 * screenScale, y: subsOutLabel.frame.origin.y, width: iconHeight, height: iconHeight))
            subsOutIcon.image = UIImage(named: "image_subs_out")
            self.addSubview(subsOutIcon)
            let subsInLabel = UILabel(frame: CGRect(x: subsOutLabel.frame.origin.x, y: subsOutLabel.frame.origin.y + subsOutLabel.frame.height + 8 * screenScale, width: 200 * screenScale, height: iconHeight))
            subsInLabel.text = "\(timeline.playerIn) 换上"
            subsInLabel.textColor = subsOutLabel.textColor
            subsInLabel.font = subsOutLabel.font
            subsInLabel.sizeToFit()
            subsInLabel.frame = CGRect(x: subsOutLabel.frame.origin.x, y: subsOutLabel.frame.origin.y + subsOutLabel.frame.height + 8 * screenScale, width: subsInLabel.frame.width, height: iconHeight)
            self.addSubview(subsInLabel)
            let subsInIcon = UIImageView(frame: CGRect(x: subsInLabel.frame.origin.x + subsInLabel.frame.width + 10 * screenScale, y: subsInLabel.frame.origin.y, width: iconHeight, height: iconHeight))
            subsInIcon.image = UIImage(named: "image_subs_in")
            self.addSubview(subsInIcon)
        }else{
            let infoLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x, y: timelineIcon.frame.origin.y + timelineIcon.frame.height + 8 * screenScale, width: frame.width - teamIcon.frame.origin.x, height: iconHeight))
            infoLabel.text = "\(timeline.playerIn) \(getTypeCN(type: timeline.type))"
            infoLabel.textColor = UIColor.colorFontBlack()
            infoLabel.font = timeLineLabel.font
            self.addSubview(infoLabel)
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
            return "换人"
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
