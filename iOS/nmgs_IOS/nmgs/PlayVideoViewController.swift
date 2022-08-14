//
//  PlayVideoViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/10/28.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit
import AVFoundation

class PlayVideoViewController: UIViewController, UIScrollViewDelegate, PlayerViewDelegate{
    
    @IBOutlet weak var topView: UIView!
    @IBOutlet weak var baseScrollView: UIScrollView!
    
    var publish = PublishModel()
    var category = CategoryModel()
    var video = VideoModel()
    var aboutListView = UIView()
    var aboutPublishList : [PublishModel] = []
    var loadAble = false
    var aboutVideoShowRows = 0
    var player = AVPlayer()
    var playItems : [AVPlayerItem]?
    
    var baseScrollViewContentHeight : CGFloat = 0
    let paddingEdge : CGFloat = 10
    let contentViewPadding : CGFloat = 15
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        baseScrollView.delegate = self
        
        if (publish.id == ""){
            let vcList = self.navigationController!.viewControllers
            _ = self.navigationController?.popToViewController(vcList.first!, animated: true)
        }else{
            let videoParams = NSDictionary(dictionary: ["id" : publish.videoId])
            HttpController.getNSDataByParams("videoInfo", paramsDictionary: videoParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    self.video.title = data.object(forKey: "videoTitle") as! String
                    self.video.context = data.object(forKey: "videoContext") as! String
                    self.video.thumbanil = data.object(forKey: "videoThumbanil") as! String
                    self.video.timeLengthSeconds = Double(data.object(forKey: "timepointSecond") as! String)!
                    self.video.timeLength = secondsToTimeString(self.video.timeLengthSeconds)
                    self.video.urls = data.object(forKey: "videoURLs") as! [String]
                    
                    let pointDataList = data.object(forKey: "webVideoPoints") as! NSArray
                    var pointList : [VideoPointModel] = []
                    for i in 0 ..< pointDataList.count{
                        let pointData = pointDataList[i] as! NSDictionary
                        let point = VideoPointModel()
                        point.commodityId = pointData.object(forKey: "commodity") as! String
                        point.commodityCover = pointData.object(forKey: "commodityCover") as! String
                        point.showMessage = pointData.object(forKey: "showMessage") as! String
                        point.showBannerUrl = pointData.object(forKey: "showBannerURL") as! String
                        point.showTime = pointData.object(forKey: "showTime") as! Double
                        point.timepoint = pointData.object(forKey: "timepoint") as! String
                        point.timepointSecond = Double(pointData.object(forKey: "timepointSecond") as! String)!
                        pointList.append(point)
                    }
                    self.video.videoPointList = pointList
                    var orgin_y : CGFloat = 0
                    orgin_y = self.loadVideoView()
                    orgin_y = self.loadContentView(orgin_y)
                    orgin_y = self.loadAboutLabelView(orgin_y)
                    self.aboutListView = UIView(frame: CGRect(x: 0, y: orgin_y, width: self.baseScrollView.frame.width,height: 0))
                    self.baseScrollView.addSubview(self.aboutListView)
                    self.baseScrollViewContentHeight = orgin_y
                    self.baseScrollView.contentSize = CGSize(width: self.baseScrollView.frame.width, height: self.baseScrollViewContentHeight)
                    
                    if(self.category.id != "all"){
                        let publishParams = NSDictionary(dictionary: ["category" : self.category.id , "except" : self.publish.id])
                        HttpController.getNSDataByParams("publishList", paramsDictionary: publishParams, data: { (data) in
                            let dataDictionary = data as! NSDictionary
                            let status = dataDictionary.object(forKey: "status") as! String
                            if status == "success" {
                                let dataList = dataDictionary.object(forKey: "data") as! NSArray
                                self.aboutPublishList = PublishModel.getPublishListByDataList(dataList: dataList)
                                self.loadAboutVideoView(0)
                                self.loadAboutVideoView(1)
                                self.aboutVideoShowRows = 2
                                self.loadAble = true
                            }
                        }, errors: { (error) in
                            
                        })
                    }else{
                        let totalPublishParams = NSDictionary(dictionary: ["component" : componentId ,"province" : provinceId, "except" : self.publish.id])
                        HttpController.getNSDataByParams("totalPublishList", paramsDictionary: totalPublishParams, data: { (data) in
                            let dataDictionary = data as! NSDictionary
                            let status = dataDictionary.object(forKey: "status") as! String
                            if status == "success" {
                                let dataList = dataDictionary.object(forKey: "data") as! NSArray
                                self.aboutPublishList = PublishModel.getPublishListByDataList(dataList: dataList)
                                self.loadAboutVideoView(0)
                                self.loadAboutVideoView(1)
                                self.aboutVideoShowRows = 2
                                self.loadAble = true
                            }
                        }, errors: { (error) in
                            
                        })
                    }
                }else{
                    let alert = UIAlertController(title: "加载视频失败", message: "", preferredStyle: UIAlertControllerStyle.alert)
                    let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                    alert.addAction(acCancel)
                    self.present(alert, animated: true, completion: nil)
                }
            }, errors: { (error) in
                
            })
        }
    }
    
    override var shouldAutorotate : Bool {
        return true
    }
    
    func loadAboutVideoView(_ row: Int){
        if aboutPublishList.count > row*2 {
            let childPublishViewWidth = (baseScrollView.frame.width - paddingEdge*3)/2
            let childPublishView = UIView(frame: CGRect(x: 0, y: baseScrollView.contentSize.height, width: baseScrollView.frame.width, height: 0))
            
            let leftPublishView = loadPublishChildView(row*2,frame: CGRect(x: paddingEdge, y: 0, width: childPublishViewWidth, height: 0))
            childPublishView.addSubview(leftPublishView)
            childPublishView.frame.size = CGSize(width: childPublishView.frame.width, height: leftPublishView.frame.height)
            
            if aboutPublishList.count > row*2+1{
                let rightPublishView = loadPublishChildView(row*2+1, frame: CGRect(x: childPublishViewWidth + paddingEdge*2, y: 0, width: childPublishViewWidth, height: 0))
                childPublishView.addSubview(rightPublishView)
                if (rightPublishView.frame.height > leftPublishView.frame.height) {
                    childPublishView.frame.size = CGSize(width: childPublishView.frame.width, height: rightPublishView.frame.height)
                }
            }
            
            baseScrollView.addSubview(childPublishView)
            baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: baseScrollView.contentSize.height + childPublishView.frame.height)
        }
    }
    
    func loadPublishChildView(_ index: Int, frame:CGRect) -> UIView{
        let publish = aboutPublishList[index]
        let publishView = UIView(frame: frame)
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: publishView.frame.width, height: publishView.frame.width/16*9))
        imageView.contentMode = UIViewContentMode.scaleAspectFill
        imageView.clipsToBounds = true
        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + publish.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
            if result{
                imageView.image = SDImage
            }
        }
        publishView.addSubview(imageView)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.height + paddingEdge, width: publishView.frame.width, height: 0))
        titleLabel.numberOfLines = 2
        titleLabel.text = htmlEscape(string: publish.title)
        titleLabel.font = UIFont.systemFont(ofSize: 14)
        titleLabel.sizeToFit()
        titleLabel.frame.size = CGSize(width: titleLabel.frame.width, height: titleLabel.frame.height+paddingEdge)
        publishView.addSubview(titleLabel)
        publishView.frame.size = CGSize(width: publishView.frame.width, height: titleLabel.frame.origin.y + titleLabel.frame.height + paddingEdge)
        
        let publishButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: publishView.frame.size))
        publishButton.tag = TagController.playVideoTags().aboutPublishButton + index
        publishButton.addTarget(self, action: #selector(publishButtonClick(_:)), for: UIControlEvents.touchUpInside)
        publishView.addSubview(publishButton)
        return publishView
    }
    
    func loadVideoView() -> CGFloat{
        playItems = []
        for i in 0 ..< video.urls.count{
            playItems?.append(AVPlayerItem(url: URL(string: (SourceBase + video.urls[i]).addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!)!))
        }
        player = AVPlayer(playerItem: playItems![1])
        
        let normalVideoView = PlayerView(frame: CGRect(x: 0, y: 0, width: baseScrollView.frame.width, height: baseScrollView.frame.width*0.6),video: video, player: player, playItems: playItems!, model:PlayViewModel.normal)
        normalVideoView.delegate = self
        normalVideoView.tag = TagController.playVideoTags().normalVideoView
        baseScrollView.addSubview(normalVideoView)
        
        let fullViewView = PlayerView(frame: CGRect(x: 0, y: 0, width: self.view.frame.height, height: self.view.frame.width),video: video, player: player, playItems: playItems!, model:PlayViewModel.fullScreen)
        fullViewView.delegate = self
        fullViewView.tag = TagController.playVideoTags().fullVideoView
        fullViewView.isHidden = true
        fullViewView.playTimer?.invalidate()
        fullViewView.playTimer = nil
        self.view.addSubview(fullViewView)
        
        player.play()
        normalVideoView.playButton.isSelected = true
        return normalVideoView.frame.origin.y + normalVideoView.frame.height
    }
    
    func loadContentView(_ orgin_y : CGFloat) -> CGFloat{
        let contentView = UIView(frame: CGRect(x: 0, y: orgin_y, width: baseScrollView.frame.width, height: 0))
        let titleLabel = UILabel(frame: CGRect(x: paddingEdge, y: contentViewPadding, width: contentView.frame.width - paddingEdge*2, height: 0))
        titleLabel.numberOfLines = 0
        titleLabel.textColor = UIColor.black
        titleLabel.text = htmlEscape(string: "简介")
        titleLabel.textAlignment = NSTextAlignment.left
        titleLabel.font = UIFont.systemFont(ofSize: 16)
        titleLabel.sizeToFit()
        contentView.addSubview(titleLabel)
        
        let contentLabel = UILabel(frame: CGRect(x: paddingEdge, y: titleLabel.frame.origin.y + titleLabel.frame.height + contentViewPadding, width: contentView.frame.width - paddingEdge*2, height: 0))
        contentLabel.numberOfLines = 0
        contentLabel.textColor = UIColor.gray
        contentLabel.text = htmlEscape(string: video.context)
        contentLabel.textAlignment = NSTextAlignment.left
        contentLabel.font = UIFont.systemFont(ofSize: 14)
        contentLabel.sizeToFit()
        contentView.addSubview(contentLabel)
        
        contentView.frame.size = CGSize(width: contentView.frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + contentViewPadding)
        let spaceLineView = UIView(frame: CGRect(x: paddingEdge, y: contentView.frame.height - 1, width: contentView.frame.width - paddingEdge*2, height: 1))
        spaceLineView.backgroundColor = UIColor.lightGray
        contentView.addSubview(spaceLineView)
        
        baseScrollView.addSubview(contentView)
        return contentView.frame.origin.y + contentView.frame.height
    }
    
    func loadAboutLabelView(_ orgin_y : CGFloat) -> CGFloat{
        let aboutLabelView = UIView(frame: CGRect(x: 0, y: orgin_y, width: baseScrollView.frame.width - paddingEdge*2, height: 0))
        let aboutLabel = UILabel(frame: CGRect(x: paddingEdge, y: contentViewPadding, width: aboutLabelView.frame.width - paddingEdge*2, height: 0))
        aboutLabel.textColor = UIColor.black
        aboutLabel.text = "相关视频"
        aboutLabel.textAlignment = NSTextAlignment.left
        aboutLabel.font = UIFont.systemFont(ofSize: 16)
        aboutLabel.sizeToFit()
        aboutLabelView.addSubview(aboutLabel)
        aboutLabelView.frame.size = CGSize(width: aboutLabel.frame.width, height: aboutLabel.frame.height + contentViewPadding*2)
        
        baseScrollView.addSubview(aboutLabelView)
        return aboutLabelView.frame.origin.y + aboutLabelView.frame.height
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(loadAble){
            let offsetHeight: CGFloat = scrollView.contentOffset.y + scrollView.frame.height
            if(offsetHeight >= scrollView.contentSize.height - 5){
                loadAble = false
                self.loadAboutVideoView(aboutVideoShowRows)
                aboutVideoShowRows += 1
                if aboutPublishList.count > aboutVideoShowRows*2 {
                    loadAble = true
                }
            }
        }
    }
    
    func fullScreenButtonClick(_ sender: UIButton) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.isForceLandscape = true
        _ = appDelegate.application(UIApplication.shared, supportedInterfaceOrientationsFor: appDelegate.window)
        self.tabBarController?.tabBar.isHidden = true
        UIDevice.current.setValue(UIInterfaceOrientation.landscapeLeft.rawValue, forKey: "orientation")
        
        let fullScreenVideoView = self.view.viewWithTag(TagController.playVideoTags().fullVideoView)! as! PlayerView
        let normalViewView = self.view.viewWithTag(TagController.playVideoTags().normalVideoView)! as! PlayerView
        
        normalViewView.playTimer?.invalidate()
        normalViewView.playTimer = nil
        fullScreenVideoView.updateTime()
        fullScreenVideoView.playTimer = Timer.scheduledTimer(timeInterval: 0.5, target: fullScreenVideoView, selector: #selector(fullScreenVideoView.updateTime), userInfo: nil, repeats: true)
        
        let playRate = player.rate
        normalViewView.playButton.isSelected = false
        normalViewView.screenButton.isSelected = false
        fullScreenVideoView.isHidden = false
        let clarityTag = normalViewView.clarityButton.tag
        if clarityTag == TagController.playVideoTags().highClarityMain {
            fullScreenVideoView.clarityButton.tag = TagController.playVideoTags().highClarityMain
            fullScreenVideoView.clarityButton.setTitle("高清", for: UIControlState())
        }else if clarityTag == TagController.playVideoTags().middleClarityMain{
            fullScreenVideoView.clarityButton.tag = TagController.playVideoTags().middleClarityMain
            fullScreenVideoView.clarityButton.setTitle("标清", for: UIControlState())
        }else{
            fullScreenVideoView.clarityButton.tag = TagController.playVideoTags().lowClarityMain
            fullScreenVideoView.clarityButton.setTitle("极速", for: UIControlState())
        }
        if(playRate>0){
            fullScreenVideoView.playButton.isSelected = true
            fullScreenVideoView.screenButton.isSelected = true
        }
    }
    
    func normalScreenButtonClick(_ sender: UIButton) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.isForceLandscape = false
        _ = appDelegate.application(UIApplication.shared, supportedInterfaceOrientationsFor: appDelegate.window)
        self.tabBarController?.tabBar.isHidden = false
        UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
        
        let fullScreenVideoView = self.view.viewWithTag(TagController.playVideoTags().fullVideoView)! as! PlayerView
        let normalViewView = self.view.viewWithTag(TagController.playVideoTags().normalVideoView)! as! PlayerView
        
        fullScreenVideoView.playTimer?.invalidate()
        fullScreenVideoView.playTimer = nil
        normalViewView.updateTime()
        normalViewView.playTimer = Timer.scheduledTimer(timeInterval: 0.5, target: normalViewView, selector: #selector(normalViewView.updateTime), userInfo: nil, repeats: true)
        
        let playRate = player.rate
        fullScreenVideoView.playButton.isSelected = false
        fullScreenVideoView.screenButton.isSelected = false
        fullScreenVideoView.isHidden = true
        let clarityTag = fullScreenVideoView.clarityButton.tag
        if clarityTag == TagController.playVideoTags().highClarityMain {
            normalViewView.clarityButton.tag = TagController.playVideoTags().highClarityMain
            normalViewView.clarityButton.setTitle("高清", for: UIControlState())
        }else if clarityTag == TagController.playVideoTags().middleClarityMain{
            normalViewView.clarityButton.tag = TagController.playVideoTags().middleClarityMain
            normalViewView.clarityButton.setTitle("标清", for: UIControlState())
        }else{
            normalViewView.clarityButton.tag = TagController.playVideoTags().lowClarityMain
            normalViewView.clarityButton.setTitle("极速", for: UIControlState())
        }
        if(playRate>0){
            normalViewView.playButton.isSelected = true
            normalViewView.screenButton.isSelected = true
        }
    }
    
    func popViewController() {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.isForceLandscape = false
        _ = appDelegate.application(UIApplication.shared, supportedInterfaceOrientationsFor: appDelegate.window)
        self.tabBarController?.tabBar.isHidden = false
        UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
        
        PlayerView.playerListDelete()
        let vcList = self.navigationController!.viewControllers
        _ = self.navigationController?.popToViewController(vcList.first!, animated: true)
    }
    
    func publishButtonClick(_ sender: UIButton){
        
        PlayerView.playerListDelete()
        let index = sender.tag - TagController.playVideoTags().aboutPublishButton
        let publish = aboutPublishList[index]
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "playVideoViewController") as! PlayVideoViewController
        vc.publish = publish
        vc.category = category
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func commodityButtonClick(_ sender: UIButton) {
        let fullScreenVideoView = self.view.viewWithTag(TagController.playVideoTags().fullVideoView)! as! PlayerView
        let normalViewView = self.view.viewWithTag(TagController.playVideoTags().normalVideoView)! as! PlayerView
        fullScreenVideoView.playButton.isSelected = false
        fullScreenVideoView.screenButton.isSelected = false
        fullScreenVideoView.isHidden = true
        let clarityTag = fullScreenVideoView.clarityButton.tag
        if clarityTag == TagController.playVideoTags().highClarityMain {
            normalViewView.clarityButton.tag = TagController.playVideoTags().highClarityMain
            normalViewView.clarityButton.setTitle("高清", for: UIControlState())
        }else if clarityTag == TagController.playVideoTags().middleClarityMain{
            normalViewView.clarityButton.tag = TagController.playVideoTags().middleClarityMain
            normalViewView.clarityButton.setTitle("标清", for: UIControlState())
        }else{
            normalViewView.clarityButton.tag = TagController.playVideoTags().lowClarityMain
            normalViewView.clarityButton.setTitle("极速", for: UIControlState())
        }
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.isForceLandscape = false
        _ = appDelegate.application(UIApplication.shared, supportedInterfaceOrientationsFor: appDelegate.window)
        self.tabBarController?.tabBar.isHidden = false
        UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
        
        let index = sender.tag - TagController.playVideoTags().commodityButton
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "display360ViewController") as! Display360ViewController
        vc.commodity = video.videoPointList[index]
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
