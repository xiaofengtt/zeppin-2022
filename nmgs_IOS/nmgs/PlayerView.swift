//
//  PlayerView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/7.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit
import AVFoundation

public enum PlayViewModel : Int {
    case normal
    case fullScreen
}

public protocol PlayerViewDelegate{
    func commodityButtonClick(_ sender:UIButton)
    func popViewController()
    func fullScreenButtonClick(_ sender:UIButton)
    func normalScreenButtonClick(_ sender:UIButton)
}

protocol CommodityViewDelegate{
    func commodityClick(_ sender:UIButton)
}

var playerList: [PlayerView] = []

class PlayerView: UIView ,CommodityViewDelegate{
    
    var delegate : PlayerViewDelegate?
    
    var video : VideoModel!
    var rect : CGRect!
    var model : PlayViewModel!
    var player: AVPlayer!
    var videoView = UIView()
    var commodityViewList : Array<CommodityView> = Array()
    var screenButton = UIButton()
    var playButton = UIButton()
    var videoTimeSlider = UISlider()
    var videoTopView = UIView()
    var videoBarView = UIView()
    var commodityView = UIView()
    var timeLabel = UILabel()
    var clarityButton = UIButton()
    var clarityView = UIView()
    var playItems : [AVPlayerItem]!
    var nsTimer : Timer?
    var playTimer : Timer?
    var touchPoint = CGPoint.zero
    
    let paddingEdge : CGFloat = 10
    let commodityViewPaddingEdge : CGFloat = 20
    var commodityViewHeight : CGFloat = 40
    var commodityViewWidth : CGFloat = 100
    let commodityPointWidth : CGFloat = 5
    let contentViewPadding : CGFloat = 15
    let videoBarViewHeight : CGFloat = 40
    let videoBarPlayButtonPaddingEdge : CGFloat = 5
    let videoBarScreenButtonPadding : CGFloat = 10
    let clarityViewButtonHeight : CGFloat = 30
    let nsTimerInterval: TimeInterval = 5
    
    init(frame: CGRect, video: VideoModel, player: AVPlayer, playItems: [AVPlayerItem], model: PlayViewModel) {
        super.init(frame: frame)
        self.clipsToBounds = true
        self.rect = frame
        self.video = video
        self.model = model
        self.player = player
        self.playItems = playItems
        
        if self.model == PlayViewModel.fullScreen{
            commodityViewHeight = 80
            commodityViewWidth = frame.width / 3
        }
        
        videoView = UIView(frame: CGRect(x: 0, y: 0, width: rect.width, height: rect.height))
        let layer = AVPlayerLayer(player: player)
        layer.frame = videoView.frame
        layer.backgroundColor = UIColor.black.cgColor
        videoView.layer.addSublayer(layer)
        self.addSubview(videoView)
        
        
        loadVideoTopView(CGRect(x: 0, y: 0, width: rect.width, height: videoBarViewHeight))
        loadVideoBarView(CGRect(x: 0, y: videoView.frame.height - videoBarViewHeight, width: rect.width, height: videoBarViewHeight))
        
        clarityView = UIView(frame: CGRect(x: clarityButton.frame.origin.x - clarityButton.frame.width/4, y: videoView.frame.height - videoBarViewHeight - clarityViewButtonHeight*3, width: clarityButton.frame.width*1.5, height: clarityViewButtonHeight*3))
        clarityView.addSubview(loadClarityViewButton(CGRect(x: 0, y: 0, width: clarityView.frame.width, height: clarityViewButtonHeight), title: "高清", tag: TagController.playVideoTags().highClarity))
        clarityView.addSubview(loadClarityViewButton(CGRect(x: 0, y: clarityViewButtonHeight, width: clarityView.frame.width, height: clarityViewButtonHeight), title: "标清", tag: TagController.playVideoTags().middleClarity))
        clarityView.addSubview(loadClarityViewButton(CGRect(x: 0, y: 2*clarityViewButtonHeight, width: clarityView.frame.width, height: clarityViewButtonHeight), title: "极速", tag: TagController.playVideoTags().lowClarity))
        self.addSubview(clarityView)
        clarityView.isHidden = true
        playTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
        nsTimer = Timer.scheduledTimer(timeInterval: nsTimerInterval, target: self, selector: #selector(self.hiddenBarView), userInfo: nil, repeats: false)
        playerList.append(self)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func loadVideoTopView(_ frame : CGRect){
        videoTopView = UIView(frame: frame)
        videoTopView.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        
        let backButton = UIButton(frame: CGRect(x: 0, y: 0, width: videoTopView.frame.height, height: videoTopView.frame.height))
        backButton.setImage(UIImage(named: "back_white_button"), for: UIControlState.normal)
        backButton.addTarget(self, action: #selector(popViewController(_:)), for: UIControlEvents.touchUpInside)
        videoTopView.addSubview(backButton)
        
        if model == PlayViewModel.fullScreen{
            let titleLabel = UILabel(frame: CGRect(x: backButton.frame.width, y: 0, width: videoTopView.frame.width - backButton.frame.width*2, height: videoTopView.frame.height))
            titleLabel.textColor = UIColor.white
            titleLabel.textAlignment = NSTextAlignment.center
            titleLabel.font = UIFont.systemFont(ofSize: 16)
            titleLabel.text = video.title
            videoTopView.addSubview(titleLabel)
        }
        
        self.addSubview(videoTopView)
    }
    
    func loadVideoBarView(_ frame : CGRect){
        videoBarView = UIView(frame : frame)
        videoBarView.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        
        let videoBarHeight = videoBarView.frame.height
        playButton = UIButton(frame: CGRect(x: videoBarPlayButtonPaddingEdge, y:videoBarPlayButtonPaddingEdge, width: videoBarHeight - 2*videoBarPlayButtonPaddingEdge, height: videoBarHeight - 2*videoBarPlayButtonPaddingEdge))
        playButton.setBackgroundImage(UIImage(named: "play_bar_play"), for: UIControlState())
        playButton.setBackgroundImage(UIImage(named: "play_bar_pause"), for: UIControlState.selected)
        playButton.addTarget(self, action: #selector(self.clickPlayButton(_:)), for: UIControlEvents.touchUpInside)
        videoBarView.addSubview(playButton)
        
        let screenButton = UIButton(frame: CGRect(x: videoBarView.frame.width - videoBarScreenButtonPadding - videoBarHeight/2,y: videoBarHeight/4, width: videoBarHeight/2, height: videoBarHeight/2))
        screenButton.setBackgroundImage(UIImage(named: "play_bar_screen"), for: UIControlState())
        screenButton.addTarget(self, action: #selector(clickScreenButton(_:)), for: UIControlEvents.touchUpInside)
        videoBarView.addSubview(screenButton)
        
        clarityButton = UIButton(frame: CGRect(x: screenButton.frame.origin.x - videoBarHeight, y: 0, width: videoBarHeight, height: videoBarHeight))
        clarityButton.setTitle("标清", for: UIControlState())
        clarityButton.tag = TagController.playVideoTags().middleClarityMain
        clarityButton.addTarget(self, action: #selector(self.clickClarityButton(_:)), for: UIControlEvents.touchUpInside)
        clarityButton.titleLabel?.font = UIFont.systemFont(ofSize: 14)
        clarityButton.isSelected = false
        videoBarView.addSubview(clarityButton)
        
        let totalTimeLabel = UILabel()
        totalTimeLabel.text = "/\(video.timeLength)"
        totalTimeLabel.font = UIFont.systemFont(ofSize: 14)
        totalTimeLabel.textColor = UIColor.gray
        totalTimeLabel.sizeToFit()
        totalTimeLabel.frame = CGRect(origin: CGPoint(x: clarityButton.frame.origin.x - totalTimeLabel.frame.width,y: playButton.frame.origin.y), size: CGSize(width: totalTimeLabel.frame.width, height: playButton.frame.height))
        videoBarView.addSubview(totalTimeLabel)
        
        timeLabel.text = "000:00"
        timeLabel.font = UIFont.systemFont(ofSize: 14)
        timeLabel.textColor = UIColor.white
        timeLabel.sizeToFit()
        timeLabel.frame = CGRect(origin: CGPoint(x: totalTimeLabel.frame.origin.x - timeLabel.frame.width,y: playButton.frame.origin.y), size: CGSize(width: timeLabel.frame.width, height: playButton.frame.height))
        timeLabel.textAlignment = NSTextAlignment.right
        timeLabel.text = "00:00"
        videoBarView.addSubview(timeLabel)
        
        let videoTimeView = UIView(frame: CGRect(x: playButton.frame.origin.x + playButton.frame.width + paddingEdge , y: 0, width: timeLabel.frame.origin.x - (playButton.frame.origin.x + playButton.frame.width) - paddingEdge*2, height: videoBarView.frame.height))
        videoTimeSlider.setThumbImage(UIImage.getRoundImage(CGSize(width: commodityPointWidth*2,height: commodityPointWidth*2), color: UIColor.white), for: UIControlState.normal)
        videoTimeSlider.minimumValue = 0.0
        videoTimeSlider.maximumValue = 1.0
        videoTimeSlider.value = 0.0
        videoTimeSlider.addTarget(self, action: #selector(self.timeSliderChanged), for: UIControlEvents.valueChanged)
        videoTimeSlider.frame = CGRect(origin: CGPoint.zero, size: videoTimeView.frame.size)
        videoTimeView.addSubview(videoTimeSlider)
        
        for i in 0 ..< video.videoPointList.count{
            let videoPoint = video.videoPointList[i]
            let timeRate = videoPoint.timepointSecond / video.timeLengthSeconds
            let pointView = UIView(frame: CGRect(x: videoTimeView.frame.width * CGFloat(timeRate) + commodityPointWidth/2, y: (videoTimeView.frame.height - commodityPointWidth)/2, width: commodityPointWidth, height: commodityPointWidth))
            let pointImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: pointView.frame.width, height: pointView.frame.height))
            pointImageView.image = UIImage(named: "play_bar_point")
            pointView.addSubview(pointImageView)
            videoTimeView.addSubview(pointView)
        }
        videoBarView.addSubview(videoTimeView)
        self.addSubview(videoBarView)
    }
    
    func loadClarityViewButton(_ frame : CGRect, title: String, tag:Int) -> UIButton{
        let button = UIButton(frame: frame)
        button.tag = tag
        button.layer.borderColor = UIColor.clear.cgColor
        button.layer.borderWidth = 1
        button.setTitle(title, for: UIControlState())
        button.titleLabel?.font = UIFont.systemFont(ofSize: 14)
        button.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        button.addTarget(self, action: #selector(self.changeClarity(_:)), for: UIControlEvents.touchUpInside)
        return button
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        nsTimerReload()
        videoBarView.isHidden = false
        videoTopView.isHidden = false
        
        let touch : UITouch = touches.first! as UITouch
        touchPoint = touch.location(in: self)
        playTimer?.invalidate()
        playTimer = nil
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch : UITouch = touches.first! as UITouch
        let currentPoint = touch.location(in: self)
        
        let moveX = currentPoint.x - touchPoint.x
        videoTimeSlider.setValue(videoTimeSlider.value + Float(moveX/self.frame.width), animated: false)
        let rate = videoTimeSlider.value
        let timeSeconds = Float(video.timeLengthSeconds) * rate
        let time = CMTime(seconds: Double(timeSeconds), preferredTimescale: player.currentTime().timescale)
        player.seek(to: time, toleranceBefore: kCMTimeZero, toleranceAfter: kCMTimeZero)
        touchPoint = currentPoint
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        playTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        playTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    
    func timeSliderChanged(_ sender: UISlider){
        nsTimerReload()
        let rate = sender.value
        let timeSeconds = Float(video.timeLengthSeconds) * rate
        let time = CMTime(seconds: Double(timeSeconds), preferredTimescale: player.currentTime().timescale)
        player.seek(to: time, toleranceBefore: kCMTimeZero, toleranceAfter: kCMTimeZero)
    }
    
    func nsTimerReload(){
        nsTimer?.invalidate()
        nsTimer = nil
        nsTimer = Timer.scheduledTimer(timeInterval: nsTimerInterval, target: self, selector: #selector(self.hiddenBarView), userInfo: nil, repeats: false)
    }
    
    func popViewController(_ sender:UIButton){
        self.delegate!.popViewController()
    }
    
    func clickScreenButton(_ sender:UIButton){
        nsTimerReload()
        if(model == PlayViewModel.normal){
            self.delegate!.fullScreenButtonClick(sender)
        }else{
            self.delegate!.normalScreenButtonClick(sender)
        }
    }
    
    func changeClarity(_ sender:UIButton){
        nsTimerReload()
        let playRate = player.rate
        player.pause()
        let time = player.currentTime()
        if(sender.tag == TagController.playVideoTags().highClarity){
            clarityButton.tag = TagController.playVideoTags().highClarityMain
            clarityButton.setTitle("高清", for: UIControlState())
            player.replaceCurrentItem(with: playItems![2])
        }else if(sender.tag == TagController.playVideoTags().middleClarity){
            clarityButton.tag = TagController.playVideoTags().middleClarityMain
            clarityButton.setTitle("标清", for: UIControlState())
            player.replaceCurrentItem(with: playItems![1])
        }else{
            clarityButton.tag = TagController.playVideoTags().lowClarityMain
            clarityButton.setTitle("极速", for: UIControlState())
            player.replaceCurrentItem(with: playItems![0])
        }
        player.seek(to: time, toleranceBefore: kCMTimeZero, toleranceAfter: kCMTimeZero)
        if(playRate > 0){
            player.play()
        }
        clarityView.isHidden = true
        clarityButton.isSelected = false
    }
    
    func clickClarityButton(_ sender:UIButton){
        nsTimerReload()
        clarityView.isHidden = !clarityView.isHidden
    }
    
    func clickPlayButton(_ sender:UIButton){
        nsTimerReload()
        if(sender.isSelected){
            playButton.isSelected = false
            screenButton.isSelected = false
            player.pause()
        }else{
            playButton.isSelected = true
            screenButton.isSelected = true
            player.play()
        }
    }
    
    func removeCommodityView(){
        let commodityView = commodityViewList.first
        commodityViewList.removeFirst()
        commodityView?.removeFromSuperview()
    }
    
    internal func commodityClick(_ sender: UIButton) {
        self.clickCommodityButton(sender)
    }
    
    func clickCommodityButton(_ sender:UIButton){
        playButton.isSelected = false
        screenButton.isSelected = false
        player.pause()
        self.delegate?.commodityButtonClick(sender)
    }
    
    func hiddenBarView(){
        videoTopView.isHidden = true
        videoBarView.isHidden = true
        clarityView.isHidden = true
    }
    
    func updateTime(){
        let playSeconds = player.currentTime().seconds
        let playRate = Float(playSeconds / video.timeLengthSeconds)
        videoTimeSlider.value = playRate
        timeLabel.text = secondsToTimeString(playSeconds)
        if((Int(playSeconds) == Int(video.timeLengthSeconds) || Int(playSeconds) == Int(video.timeLengthSeconds) - 1) && player.rate == 0){
            player.seek(to: CMTimeMake(0, player.currentTime().timescale), toleranceBefore: kCMTimeZero, toleranceAfter: kCMTimeZero)
            player.pause()
            playButton.isSelected = false
            
            nsTimerReload()
            videoBarView.isHidden = false
            videoTopView.isHidden = false
        }
        
        if(player.rate > 0){
            for i in 0 ..< video.videoPointList.count{
                if(Int(playSeconds) == Int(video.videoPointList[i].timepointSecond)){
                    var flag = true
                    for index in 0 ..< commodityViewList.count{
                        let oldCommodityView = commodityViewList[index]
                        if oldCommodityView.commodity.commodityId == video.videoPointList[i].commodityId{
                            flag = false
                            break
                        }
                    }
                    if flag{
                        for index in 0 ..< commodityViewList.count{
                            let oldCommodityView = commodityViewList[index]
                            oldCommodityView.center = CGPoint(x: oldCommodityView.center.x, y: oldCommodityView.center.y - commodityViewHeight)
                        }
                        
                        let commodity = video.videoPointList[i]
                        let commodityView = CommodityView(frame: CGRect(x: videoView.frame.width - commodityViewWidth - commodityViewPaddingEdge,y: videoView.frame.height - commodityViewHeight - videoBarViewHeight,width: commodityViewWidth,height: commodityViewHeight), commodity: commodity, index: i)
                        commodityViewList.append(commodityView)
                        commodityView.delegate = self
                        Timer.scheduledTimer(timeInterval: 10, target: self, selector: #selector(removeCommodityView), userInfo: nil, repeats: false)
                        videoView.addSubview(commodityView)
                    }
                }
            }
        }
    }
    
    class func playerListPause(){
        for i in 0 ..< playerList.count{
            playerList[i].player.pause()
        }
    }
    
    class func playerListDelete(){
        for _ in 0 ..< playerList.count{
            playerList[0].nsTimer?.invalidate()
            playerList[0].nsTimer = nil
            playerList[0].playTimer?.invalidate()
            playerList[0].playTimer = nil
            playerList[0].player.replaceCurrentItem(with: nil)
            playerList[0].removeFromSuperview()
            playerList.remove(at: 0)
        }
    }
    
    class CommodityView: UIView{
        
        var delegate : CommodityViewDelegate?
        
        var commodity : VideoPointModel!
        
        init(frame: CGRect, commodity: VideoPointModel, index: Int) {
            super.init(frame: frame)
            self.commodity = commodity
            self.backgroundColor = UIColor.clear
            
            let commodityBackgroundView = ProgressCurcuitView(frame: CGRect(x: 0, y: 0, width: self.frame.height, height: self.frame.height))
            commodityBackgroundView.backgroundColor = UIColor.clear
            commodityBackgroundView.layer.masksToBounds = true
            commodityBackgroundView.layer.cornerRadius = self.frame.height/2
            let commodityImageView = UIImageView(frame: CGRect(x: commodityBackgroundView.frame.width/6,   y: commodityBackgroundView.frame.width/6, width: commodityBackgroundView.frame.width/3*2, height: commodityBackgroundView.frame.height/3*2))
            commodityImageView.layer.masksToBounds = true
            commodityImageView.layer.cornerRadius = commodityImageView.frame.width/2
            SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + commodity.commodityCover), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                if result{
                    commodityImageView.image = SDImage
                }
            }
            self.addSubview(commodityBackgroundView)
            self.addSubview(commodityImageView)
            
            let titleLabel = UILabel(frame: CGRect(x: commodityBackgroundView.frame.width,y: self.frame.height/2,width: self.frame.width - commodityBackgroundView.frame.width,height: self.frame.height/2))
            titleLabel.font = UIFont.systemFont(ofSize: 10)
            titleLabel.numberOfLines = 1
            titleLabel.textColor = UIColor.white
            titleLabel.textAlignment = NSTextAlignment.left
            titleLabel.text = "  \(commodity.showMessage)  "
            titleLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
            titleLabel.backgroundColor = UIColor.black.withAlphaComponent(0.8)
            titleLabel.layer.masksToBounds = true
            titleLabel.layer.cornerRadius = titleLabel.frame.height/2
            titleLabel.sizeToFit()
            if(titleLabel.frame.width > self.frame.width - commodityBackgroundView.frame.width){
                titleLabel.frame.size = CGSize(width: self.frame.width - commodityBackgroundView.frame.width, height: self.frame.height/2)
            }else{
                titleLabel.frame.size = CGSize(width: titleLabel.frame.width, height: self.frame.height/2)
            }
            self.addSubview(titleLabel)
            
            let commodityButton = UIButton()
            commodityButton.frame.origin = CGPoint.zero
            commodityButton.frame.size = self.frame.size
            commodityButton.tag = TagController.playVideoTags().commodityButton + index
            commodityButton.addTarget(self, action: #selector(clickButton(_:)), for:  UIControlEvents.touchUpInside)
            self.addSubview(commodityButton)
        }
        
        func clickButton(_ sender:UIButton){
            self.delegate?.commodityClick(sender)
        }
        
        required init(coder aDecoder: NSCoder) {
            super.init(coder: aDecoder)!
        }
    }
}
