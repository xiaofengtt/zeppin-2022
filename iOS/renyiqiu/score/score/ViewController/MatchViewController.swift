//
//  MatchViewController.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class MatchViewController: UIViewController, UIScrollViewDelegate{
    
    var headView: UIView!
    var bodyView: UIScrollView!
    var dataView: UIView!
    var infoView: UIView!
    var arrayView: UIView!
    
    var uuid: String = ""
    var match: MatchDetailModel = MatchDetailModel()
    var selectTag: Int = TagController.MatchTags.dataButton
    var aboutList: Array<NewsModel> = []
    var timelineList: Array<TimelineModel> = []
    var teamToTeamList: Array<MatchModel> = []
    var homeMatchList: Array<MatchModel> = []
    var awayMatchList: Array<MatchModel> = []
    
    let aboutCellHeight: CGFloat = 110 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let leftItem = UIBarButtonItem(image: UIImage(named: "common_back_white")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        leftItem.setWhiteStyle()
        self.navigationItem.leftBarButtonItem = leftItem
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        headView = UIView()
        self.view.addSubview(headView)
        bodyView = UIScrollView()
        self.view.addSubview(bodyView)
        getMatch()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.white
        let navigationController = self.navigationController as! BaseNavigationController
        navigationController.statusBarStyle = UIStatusBarStyle.lightContent
        navigationController.setNeedsStatusBarAppearanceUpdate()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        let navigationController = self.navigationController as! BaseNavigationController
        navigationController.statusBarStyle = UIStatusBarStyle.default
        navigationController.setNeedsStatusBarAppearanceUpdate()
    }
    
    func getMatch(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/match/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.match = MatchDetailModel(data: data)
                
                var timelines: Array<TimelineModel> = []
                for goal in self.match.goals{
                    let timeline: TimelineModel = TimelineModel()
                    if(String.valueOf(any: goal.value(forKey: "home_ryqiur")) != ""){
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: goal.value(forKey: "home_ryqiur"))
                    }else if(String.valueOf(any: goal.value(forKey: "away_ryqiur")) != ""){
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: goal.value(forKey: "away_ryqiur"))
                    }else{
                        continue
                    }
                    timeline.time = String.valueOf(any: goal.value(forKey: "time"))
                    timeline.type = "goal"
                    timeline.ryqiu = String.valueOf(any: goal.value(forKey: "ryqiu"))
                    timelines.append(timeline)
                }
                
                for card in self.match.cards{
                    let timeline: TimelineModel = TimelineModel()
                    if(String.valueOf(any: card.value(forKey: "home_fault")) != ""){
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: card.value(forKey: "home_fault"))
                    }else if(String.valueOf(any: card.value(forKey: "away_fault")) != ""){
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: card.value(forKey: "away_fault"))
                    }else{
                        continue
                    }
                    timeline.time = String.valueOf(any: card.value(forKey: "time"))
                    timeline.type = String.valueOf(any: card.value(forKey: "card"))
                    timelines.append(timeline)
                }
                
                for subs in self.match.substitutes{
                    let timeline: TimelineModel = TimelineModel()
                    if(subs.value(forKey: "home_ryqiur") != nil && subs.value(forKey: "home_ryqiur").debugDescription.contains("out")){
                        let dic = subs.value(forKey: "home_ryqiur") as! NSDictionary
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: dic.value(forKey: "in"))
                        timeline.playerOut = String.valueOf(any: dic.value(forKey: "out"))
                        
                    }
                    
                    if(subs.value(forKey: "away_ryqiur") != nil && subs.value(forKey: "away_ryqiur").debugDescription.contains("out")){
                        let dic = subs.value(forKey: "away_ryqiur") as! NSDictionary
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: dic.value(forKey: "in"))
                        timeline.playerOut = String.valueOf(any: dic.value(forKey: "out"))
                    }
                    
                    if(timeline.side == ""){
                        continue
                    }
                    
                    timeline.time = String.valueOf(any: subs.value(forKey: "time"))
                    timeline.type = "subs"
                    timelines.append(timeline)
                }
                
                timelines.sort(by: { (a, b) -> Bool in
                    if(a.time.firstIndex(of: "+") != nil && b.time.firstIndex(of: "+") != nil){
                        let aTimes = a.time.split(separator: "+")
                        let bTimes = b.time.split(separator: "+")
                        
                        if(bTimes[0].count > aTimes[0].count){
                            return true
                        }
                        if(bTimes[0].count < aTimes[0].count){
                            return false
                        }
                        
                        if(aTimes[0] != bTimes[0]){
                            return aTimes[0] < bTimes[0]
                        }
                        
                        if(bTimes[1].count > aTimes[1].count){
                            return true
                        }
                        
                        if(bTimes[1].count < aTimes[1].count){
                            return false
                        }
                        
                        return aTimes[1] < bTimes[1]
                    }else if (a.time.firstIndex(of: "+") != nil){
                        let aTimes = a.time.split(separator: "+")
                        
                        if(b.time.count > aTimes[0].count){
                            return true
                        }
                        if(b.time.count < aTimes[0].count){
                            return false
                        }
                        
                        if(aTimes[0] != b.time){
                            return aTimes[0] < b.time
                        }else{
                            return false
                        }
                    }else if (b.time.firstIndex(of: "+") != nil){
                        let bTimes = b.time.split(separator: "+")
                        if(bTimes[0].count > a.time.count){
                            return true
                        }
                        if(bTimes[0].count < a.time.count){
                            return false
                        }
                        if(a.time != bTimes[0]){
                            return a.time < bTimes[0]
                        }else{
                            return true
                        }
                        
                    }else{
                        if(b.time.length > a.time.length){
                            return true
                        }
                        
                        if(b.time.length < a.time.length){
                            return false
                        }
                        
                        return a.time < b.time
                    }
                })
                
                self.timelineList = timelines
                
                self.createHeadView()
                self.getAbout()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getAbout(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "category":  match.category]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.aboutList = []
                var abouts: [NewsModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    abouts.append(NewsModel(data: data))
                }
                self.aboutList = abouts
                
                
                self.getHistory()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getHistory(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "teamToTeam":  "\(match.hometeam),\(match.awayteam)", "sort": "time desc", "status": "finished" ]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.teamToTeamList = []
                var teamToTeams: [MatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    teamToTeams.append(MatchModel(data: data))
                }
                self.teamToTeamList = teamToTeams
                
                self.getHomeMatchs()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    func getHomeMatchs(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "team": match.hometeam, "sort": "time desc", "status": "finished"]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.homeMatchList = []
                var matchs: [MatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matchs.append(MatchModel(data: data))
                }
                self.homeMatchList = matchs
                
                self.getAwayMatchs()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getAwayMatchs(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "team": match.awayteam, "sort": "time desc", "status": "finished"]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.awayMatchList = []
                var matchs: [MatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matchs.append(MatchModel(data: data))
                }
                self.awayMatchList = matchs
                
                self.createBodyView()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func createHeadView(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.removeFromSuperview()
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: navigationFrame.origin.y +  navigationFrame.height + 170 * screenScale))
        
        let backgroundImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundImageView.image = UIImage(named: "match_header")
        headView.addSubview(backgroundImageView)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: navigationFrame.origin.y, width: headView.frame.width, height: navigationFrame.height))
        titleLabel.text = "\(Utils.timestampFormat(timestamp: match.time, format: "MM-dd HH:mm")) \(match.categoryName)\(match.roundName)"
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(titleLabel)
        
        let homeIconView = UIImageView(frame: CGRect(x: screenWidth/6, y: titleLabel.frame.origin.y + titleLabel.frame.height + 25 * screenScale, width: screenWidth/8, height: screenWidth/8))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "common_team_default")
            }
        }
        headView.addSubview(homeIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height + 8 * screenScale, width: homeIconView.frame.width + 100 * screenScale, height: 22 * screenScale))
        homeNameLabel.text = match.hometeamName
        homeNameLabel.textColor = UIColor.white
        homeNameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        homeNameLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(homeNameLabel)
        
        let hometeamButton = UIButton(frame: CGRect(x: 0, y: homeIconView.frame.origin.y, width: screenWidth/2, height: homeNameLabel.frame.origin.y + homeNameLabel.frame.height - homeIconView.frame.origin.y))
        hometeamButton.addTarget(self, action: #selector(enterHometeam(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(hometeamButton)
        
        let awayIconView = UIImageView(frame: CGRect(x: screenWidth - homeIconView.frame.origin.x - homeIconView.frame.width, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "common_team_default")
            }
        }
        headView.addSubview(awayIconView)
        
        let awayNameLabel = UILabel(frame: CGRect(x: awayIconView.frame.origin.x - 50 * screenScale, y: homeNameLabel.frame.origin.y, width: awayIconView.frame.width + 100 * screenScale, height: homeNameLabel.frame.height))
        awayNameLabel.text = match.awayteamName
        awayNameLabel.textColor = homeNameLabel.textColor
        awayNameLabel.font = homeNameLabel.font
        awayNameLabel.textAlignment = homeNameLabel.textAlignment
        headView.addSubview(awayNameLabel)
        
        let awayteamButton = UIButton(frame: CGRect(x: screenWidth/2, y: homeIconView.frame.origin.y, width: screenWidth/2, height: homeNameLabel.frame.origin.y + homeNameLabel.frame.height - homeIconView.frame.origin.y))
        awayteamButton.addTarget(self, action: #selector(enterAwayteam(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(awayteamButton)
        
        if(match.finalresult.firstIndex(of: "(") == nil){
            let scoreLabel = UILabel(frame: CGRect(x: screenWidth/2 - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height/5, width: 100 * screenScale, height: 30 * screenScale))
            if(match.status == "finished" || match.status == "living"){
                scoreLabel.text = match.finalresult
            }else{
                scoreLabel.text = "VS"
            }
            scoreLabel.textColor = UIColor.white
            scoreLabel.font = UIFont.boldFont(size: (UIFont.biggestSize() + 4 ) * screenScale)
            scoreLabel.textAlignment = NSTextAlignment.center
            headView.addSubview(scoreLabel)
        }else{
            let finalStr = match.finalresult.substring(to: match.finalresult.firstIndex(of: "(")!)
            let halfStr = match.finalresult.replacingOccurrences(of: finalStr, with: "")
            
            let finalLabel = UILabel(frame: CGRect(x: screenWidth/2 - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height/10, width: 100 * screenScale, height: 20 * screenScale))
            finalLabel.text = finalStr
            finalLabel.textColor = UIColor.white
            finalLabel.font = UIFont.boldFont(size: (UIFont.biggestSize() + 2) * screenScale)
            finalLabel.textAlignment = NSTextAlignment.center
            headView.addSubview(finalLabel)
            
            let halfLabel = UILabel(frame: CGRect(x: 0, y: finalLabel.frame.origin.y + finalLabel.frame.height, width: screenWidth, height: 20 * screenScale))
            halfLabel.text = halfStr
            halfLabel.textColor = finalLabel.textColor
            halfLabel.font = UIFont.boldFont(size: UIFont.bigSize() * screenScale)
            halfLabel.textAlignment = NSTextAlignment.center
            headView.addSubview(halfLabel)
        }
        
        let statusLabel = UILabel(frame: CGRect(x: screenWidth/2 - 25 * screenScale, y: homeNameLabel.frame.origin.y, width: 50 * screenScale, height: homeNameLabel.frame.height))
        statusLabel.backgroundColor = UIColor.white.withAlphaComponent(0.3)
        statusLabel.layer.cornerRadius = statusLabel.frame.height/2
        statusLabel.layer.masksToBounds = true
        switch match.status {
        case "finished":
            statusLabel.text = "完赛"
        case "living":
            statusLabel.text = "\(match.progress)'"
        case "postponed":
            statusLabel.text = "延期"
        default:
            statusLabel.text = "未开赛"
        }
        statusLabel.textColor = UIColor.white
        statusLabel.font = UIFont.boldFont(size: UIFont.smallSize() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(statusLabel)
        
        let typeButtonView = UIView(frame: CGRect(x: 0, y: headView.frame.height - 40 * screenScale, width: screenWidth, height: 40 * screenScale))
        typeButtonView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        headView.addSubview(typeButtonView)
        
        let dataButton = MatchTypeButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/3, height: typeButtonView.frame.height), title: "数据")
        dataButton.tag = TagController.MatchTags.dataButton
        dataButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == dataButton.tag){
            dataButton.selectImage.isHidden = false
            dataButton.isSelected = true
        }
        typeButtonView.addSubview(dataButton)
        
        let infoButton = MatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "赛况")
        infoButton.tag = TagController.MatchTags.infoButton
        infoButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == infoButton.tag){
            infoButton.selectImage.isHidden = false
            infoButton.isSelected = true
        }
        typeButtonView.addSubview(infoButton)
        
        let arrayButton = MatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3*2, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "阵容")
        arrayButton.tag = TagController.MatchTags.arrayButton
        arrayButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == arrayButton.tag){
            arrayButton.selectImage.isHidden = false
            arrayButton.isSelected = true
        }
        typeButtonView.addSubview(arrayButton)
        self.view.addSubview(headView)
    }
    
    func createBodyView(){
        bodyView.removeFromSuperview()
        bodyView = UIScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - headView.frame.origin.y - headView.frame.height))
        bodyView.delegate = self
        bodyView.backgroundColor = UIColor.backgroundGray()
        bodyView.addRefreshView()
        
        createDataView()
        createInfoView()
        createArrayView()
        
        switch selectTag {
        case TagController.MatchTags.infoButton:
            bodyView.addSubview(infoView)
            bodyView.contentSize = infoView.frame.size
        case TagController.MatchTags.arrayButton:
            bodyView.addSubview(arrayView)
            bodyView.contentSize = arrayView.frame.size
        default:
            bodyView.addSubview(dataView)
            bodyView.contentSize = dataView.frame.size
        }
        if(bodyView.frame.height >= bodyView.contentSize.height){
            bodyView.contentSize.height = bodyView.frame.height + 1
        }
        self.view.addSubview(bodyView)
    }
    
    func createDataView(){
        dataView = UIView()
        dataView.frame.origin = CGPoint.zero
        
        let historyView = MatchHistoryView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0), historyList: teamToTeamList, match: match)
        dataView.addSubview(historyView)
        dataView.frame.size = CGSize(width: screenWidth, height: historyView.frame.height)
        
        let dataInfoView = UIView(frame: CGRect(x: 0, y: dataView.frame.height, width: screenWidth, height: 0))
        dataInfoView.backgroundColor = UIColor.white
        
        let infoLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 72 * screenScale, height: 20 * screenScale))
        infoLabel.text = "近期战绩"
        infoLabel.textColor = UIColor.fontBlack()
        infoLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        infoLabel.textAlignment = NSTextAlignment.center
        let infoBackground = UIImageView(frame: CGRect(x: 0, y: infoLabel.frame.height/5*3, width: infoLabel.frame.width, height: 8 * screenScale))
        infoBackground.image = UIImage(named: "common_selected")
        infoLabel.addSubview(infoBackground)
        dataInfoView.addSubview(infoLabel)
        
        let homeHistoryView = MatchFixtrueView(frame: CGRect(x: 0, y: infoLabel.frame.origin.y + infoLabel.frame.height, width: screenWidth, height: 0), matchList: homeMatchList, hometeam: match.hometeam, hometeamName: match.hometeamName, hometeamIconUrl: match.hometeamIconUrl)
        dataInfoView.frame.size = CGSize(width: dataInfoView.frame.width, height: homeHistoryView.frame.origin.y + homeHistoryView.frame.height)
        dataInfoView.addSubview(homeHistoryView)
        
        let awayHistoryView = MatchFixtrueView(frame: CGRect(x: 0, y: homeHistoryView.frame.origin.y + homeHistoryView.frame.height, width: screenWidth, height: 0), matchList: awayMatchList, hometeam: match.awayteam, hometeamName: match.awayteamName, hometeamIconUrl: match.awayteamIconUrl)
        dataInfoView.frame.size = CGSize(width: dataInfoView.frame.width, height: awayHistoryView.frame.origin.y + awayHistoryView.frame.height + 15 * screenScale)
        dataInfoView.addSubview(awayHistoryView)
        
        dataView.frame.size = CGSize(width: dataView.frame.width, height: dataInfoView.frame.origin.y + dataInfoView.frame.height + 15 * screenScale)
        dataView.addSubview(dataInfoView)
        
        createAboutView(parent: dataView)
    }
    
    func createInfoView(){
        infoView = UIView()
        infoView.frame.origin = CGPoint.zero
        
        if(match.goals.count == 0 && match.cards.count == 0 && match.substitutes.count == 0 && match.statistics.count == 0 && match.status != "living"){
            let nodataView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 280 * screenScale))
            nodataView.backgroundColor = UIColor.white
            
            let nodataImage = UIImageView(frame: CGRect(x: screenWidth/3, y: 50 * screenScale, width: screenWidth/3, height: screenWidth/3))
            nodataImage.image = UIImage(named: "match_nodata")
            nodataView.addSubview(nodataImage)
            
            let nodataLabel = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 20 * screenScale, width: screenWidth, height: 20 * screenScale))
            nodataLabel.text = "数据和球员都走丢了，先看会儿新闻等等吧~"
            nodataLabel.textColor = UIColor.fontDarkGray()
            nodataLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            nodataLabel.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataLabel)
            
            infoView.frame.size = nodataView.frame.size
            infoView.addSubview(nodataView)
        }else{
            let infoMainView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
            infoMainView.backgroundColor = UIColor.white
            
            let infoDataLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 15 * screenScale, width: 72 * screenScale, height: 20 * screenScale))
            infoDataLabel.text = "比赛数据"
            infoDataLabel.textColor = UIColor.fontBlack()
            infoDataLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
            infoDataLabel.textAlignment = NSTextAlignment.center
            let titleBackground = UIImageView(frame: CGRect(x: 0, y: infoDataLabel.frame.height/5*3, width: infoDataLabel.frame.width, height: 8 * screenScale))
            titleBackground.image = UIImage(named: "common_selected")
            infoDataLabel.addSubview(titleBackground)
            infoMainView.addSubview(infoDataLabel)
            
            let infoIconView = UIView(frame: CGRect(x: 0, y: infoDataLabel.frame.origin.y + infoDataLabel.frame.height, width: infoMainView.frame.width, height: 40 * screenScale))
            
            let yellowcardIcon = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
            yellowcardIcon.image = UIImage(named: "match_yellowcard")
            infoIconView.addSubview(yellowcardIcon)
            let yellowcardLabel = UILabel(frame: CGRect(x: yellowcardIcon.frame.origin.x + yellowcardIcon.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: 32 * screenScale, height: yellowcardIcon.frame.height))
            yellowcardLabel.text = "黄牌"
            yellowcardLabel.textColor = UIColor.fontBlack()
            yellowcardLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            infoIconView.addSubview(yellowcardLabel)
            
            let redcardIcon = UIImageView(frame: CGRect(x: yellowcardLabel.frame.origin.x + yellowcardLabel.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            redcardIcon.image = UIImage(named: "match_redcard")
            infoIconView.addSubview(redcardIcon)
            let redcardLabel = UILabel(frame: CGRect(x: redcardIcon.frame.origin.x + redcardIcon.frame.width + 5 * screenScale, y: yellowcardLabel.frame.origin.y, width: yellowcardLabel.frame.width, height: yellowcardLabel.frame.height))
            redcardLabel.text = "红牌"
            redcardLabel.textColor = yellowcardLabel.textColor
            redcardLabel.font = yellowcardLabel.font
            infoIconView.addSubview(redcardLabel)
            
            let goalIcon = UIImageView(frame: CGRect(x: redcardLabel.frame.origin.x + redcardLabel.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            goalIcon.image = UIImage(named: "match_goal")
            infoIconView.addSubview(goalIcon)
            let goalLabel = UILabel(frame: CGRect(x: goalIcon.frame.origin.x + goalIcon.frame.width + 5 * screenScale, y: yellowcardLabel.frame.origin.y, width: yellowcardLabel.frame.width, height: yellowcardLabel.frame.height))
            goalLabel.text = "进球"
            goalLabel.textColor = yellowcardLabel.textColor
            goalLabel.font = yellowcardLabel.font
            infoIconView.addSubview(goalLabel)
            infoMainView.addSubview(infoIconView)
            
            let inHomeIcon = UIImageView(frame: CGRect(x: goalLabel.frame.origin.x + goalLabel.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            inHomeIcon.image = UIImage(named: "match_in_home")
            infoIconView.addSubview(inHomeIcon)
            let inAwayIcon = UIImageView(frame: CGRect(x: inHomeIcon.frame.origin.x + inHomeIcon.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            inAwayIcon.image = UIImage(named: "match_in_away")
            infoIconView.addSubview(inAwayIcon)
            let inLabel = UILabel(frame: CGRect(x: inAwayIcon.frame.origin.x + inAwayIcon.frame.width + 5 * screenScale, y: yellowcardLabel.frame.origin.y, width: yellowcardLabel.frame.width, height: yellowcardLabel.frame.height))
            inLabel.text = "上场"
            inLabel.textColor = yellowcardLabel.textColor
            inLabel.font = yellowcardLabel.font
            infoIconView.addSubview(inLabel)
            infoMainView.addSubview(infoIconView)
            
            let outHomeIcon = UIImageView(frame: CGRect(x: inLabel.frame.origin.x + inLabel.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            outHomeIcon.image = UIImage(named: "match_out_home")
            infoIconView.addSubview(outHomeIcon)
            let outAwayIcon = UIImageView(frame: CGRect(x: outHomeIcon.frame.origin.x + outHomeIcon.frame.width + 5 * screenScale, y: yellowcardIcon.frame.origin.y, width: yellowcardIcon.frame.width, height: yellowcardIcon.frame.height))
            outAwayIcon.image = UIImage(named: "match_out_away")
            infoIconView.addSubview(outAwayIcon)
            let outLabel = UILabel(frame: CGRect(x: outAwayIcon.frame.origin.x + outAwayIcon.frame.width + 5 * screenScale, y: yellowcardLabel.frame.origin.y, width: yellowcardLabel.frame.width, height: yellowcardLabel.frame.height))
            outLabel.text = "下场"
            outLabel.textColor = yellowcardLabel.textColor
            outLabel.font = yellowcardLabel.font
            infoIconView.addSubview(outLabel)
            
            let iconViewEndLine = CALayer()
            iconViewEndLine.frame = CGRect(x: 10 * screenScale, y: infoIconView.frame.height - 1 * screenScale, width: infoIconView.frame.width - 20 * screenScale, height: 1 * screenScale)
            iconViewEndLine.backgroundColor = UIColor.backgroundGray().cgColor
            infoIconView.layer.addSublayer(iconViewEndLine)
            infoMainView.addSubview(infoIconView)
            
            let infoTimelineView = UIView(frame: CGRect(x: 0, y: infoIconView.frame.origin.y + infoIconView.frame.height + 10 * screenScale, width: infoMainView.frame.width, height: 0))
            let startIcon = UIImageView(frame: CGRect(x: (infoTimelineView.frame.width - 20 * screenScale)/2, y: 0, width: 20 * screenScale, height: 20 * screenScale))
            startIcon.image = UIImage(named: "match_whistle")
            infoTimelineView.addSubview(startIcon)
            let startLine = CALayer()
            startLine.frame = CGRect(x: infoTimelineView.frame.width/2, y: startIcon.frame.origin.y + startIcon.frame.height + 10 * screenScale, width: 1 * screenScale, height: 25 * screenScale)
            startLine.backgroundColor = UIColor.black.withAlphaComponent(0.1).cgColor
            infoTimelineView.layer.addSublayer(startLine)
            infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: startLine.frame.origin.y + startLine.frame.height)
            
            for i in 0 ..< timelineList.count{
                let timeline = timelineList[i]
                let timeLineView = MatchTimelineView(frame: CGRect(x: 0, y: infoTimelineView.frame.height, width: infoTimelineView.frame.width, height: 70 * screenScale), timeline: timeline, isFinal: i == timelineList.count - 1)
                infoTimelineView.addSubview(timeLineView)
                infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: timeLineView.frame.origin.y + timeLineView.frame.height)
            }
            
            if(match.status == "finished"){
                let endIcon = UIImageView(frame: CGRect(x: (infoTimelineView.frame.width - startIcon.frame.width)/2, y: infoTimelineView.frame.height + 10 * screenScale, width: startIcon.frame.width, height: startIcon.frame.height))
                endIcon.image = startIcon.image
                infoTimelineView.addSubview(endIcon)
                infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: endIcon.frame.origin.y + endIcon.frame.height)
            }
            
            infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: infoTimelineView.frame.height + 20 * screenScale)
            infoMainView.addSubview(infoTimelineView)
            infoMainView.frame.size = CGSize(width: infoMainView.frame.width, height: infoTimelineView.frame.origin.y + infoTimelineView.frame.height)
            
            if(match.statistics.count > 0){
                let infoStatView = UIView(frame: CGRect(x: 0, y: infoMainView.frame.height, width: infoMainView.frame.width, height: 0))
                let infoStatLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 72 * screenScale, height: 20 * screenScale))
                infoStatLabel.text = "技术统计"
                infoStatLabel.textColor = UIColor.fontBlack()
                infoStatLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
                infoStatLabel.textAlignment = NSTextAlignment.center
                let statTitleBackground = UIImageView(frame: CGRect(x: 0, y: infoStatLabel.frame.height/5*3, width: infoStatLabel.frame.width, height: 8 * screenScale))
                statTitleBackground.image = UIImage(named: "common_selected")
                infoStatLabel.addSubview(statTitleBackground)
                infoStatView.addSubview(infoStatLabel)
                infoStatView.frame.size = CGSize(width: infoStatView.frame.width, height: infoStatLabel.frame.origin.y + infoStatLabel.frame.height)
                
                for stat in match.statistics{
                    let statView = MatchStatView(frame: CGRect(x: 0, y: infoStatView.frame.height, width: infoStatView.frame.width, height: 45 * screenScale), stat: stat)
                    infoStatView.addSubview(statView)
                    infoStatView.frame.size = CGSize(width: infoStatView.frame.width, height: statView.frame.origin.y + statView.frame.height)
                }
                
                infoMainView.addSubview(infoStatView)
                infoMainView.frame.size = CGSize(width: infoMainView.frame.width, height: infoStatView.frame.origin.y + infoStatView.frame.height + 20 * screenScale)
            }
            
            infoView.addSubview(infoMainView)
            infoView.frame.size = CGSize(width: screenWidth, height: infoMainView.frame.origin.y + infoMainView.frame.height + 10 * screenScale)
        }
        createAboutView(parent: infoView)
    }
    
    func createArrayView(){
        arrayView = UIView()
        arrayView.frame.origin = CGPoint.zero
        
        if(match.homestarting.count == 0 && match.awaystarting.count == 0){
            let nodataView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 280 * screenScale))
            nodataView.backgroundColor = UIColor.white
            
            let nodataImage = UIImageView(frame: CGRect(x: screenWidth/3, y: 50 * screenScale, width: screenWidth/3, height: screenWidth/3))
            nodataImage.image = UIImage(named: "match_nodata")
            nodataView.addSubview(nodataImage)
            
            let nodataLabel = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 20 * screenScale, width: screenWidth, height: 20 * screenScale))
            nodataLabel.text = "数据和球员都走丢了，先看会儿新闻等等吧~"
            nodataLabel.textColor = UIColor.fontDarkGray()
            nodataLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            nodataLabel.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataLabel)
            
            arrayView.frame.size = nodataView.frame.size
            arrayView.addSubview(nodataView)
        }else{
            let arrayMainView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
            arrayMainView.backgroundColor = UIColor.white
            
            let homeIconView = UIImageView(frame: CGRect(x: 40 * screenScale, y: 10 * screenScale, width: 22 * screenScale, height: 22 * screenScale))
            SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                if(SDImage != nil){
                    homeIconView.image = SDImage
                }else{
                    homeIconView.image = UIImage(named: "common_team_default")
                }
            }
            arrayMainView.addSubview(homeIconView)
            
            let homeLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x + homeIconView.frame.width + 10 * screenScale, y: homeIconView.frame.origin.y, width: screenWidth/2 - homeIconView.frame.origin.x - homeIconView.frame.width, height: homeIconView.frame.height))
            homeLabel.text = match.hometeamName
            homeLabel.textColor = UIColor.fontBlack()
            homeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            arrayMainView.addSubview(homeLabel)
            
            let awayIconView = UIImageView(frame: CGRect(x: screenWidth/2 + 40 * screenScale, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
            SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                if(SDImage != nil){
                    awayIconView.image = SDImage
                }else{
                    awayIconView.image = UIImage(named: "common_team_default")
                }
            }
            arrayMainView.addSubview(awayIconView)
            
            let awayLabel = UILabel(frame: CGRect(x: awayIconView.frame.origin.x + awayIconView.frame.width + 10 * screenScale, y: homeLabel.frame.origin.y, width: homeLabel.frame.width, height: homeLabel.frame.height))
            awayLabel.text = match.awayteamName
            awayLabel.textColor = homeLabel.textColor
            awayLabel.font = homeLabel.font
            arrayMainView.addSubview(awayLabel)
            
            let titleLabel = UILabel(frame: CGRect(x: (screenWidth - 40 * screenScale)/2, y: homeIconView.frame.origin.y + homeIconView.frame.height + 15 * screenScale, width: 40 * screenScale, height: 20 * screenScale))
            titleLabel.text = "首发"
            titleLabel.textColor = UIColor.black
            titleLabel.font = UIFont.boldFont(size: UIFont.bigSize() * screenScale)
            titleLabel.textAlignment = NSTextAlignment.center
            let titleBackground = UIImageView(frame: CGRect(x: 0, y: titleLabel.frame.height/5*3, width: titleLabel.frame.width, height: 8 * screenScale))
            titleBackground.image = UIImage(named: "common_selected")
            titleLabel.addSubview(titleBackground)
            arrayMainView.addSubview(titleLabel)
            
            let homeStartingView = UIView(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 5 * screenScale, width: screenWidth/2, height: 0))
            for player in match.homestarting{
                let playerView = MatchPlayerView(frame: CGRect(x: 0, y: homeStartingView.frame.height, width: homeStartingView.frame.width, height: 30 * screenScale), player: player)
                
                homeStartingView.addSubview(playerView)
                homeStartingView.frame.size = CGSize(width: homeStartingView.frame.width, height: playerView.frame.origin.y + playerView.frame.height + 10 * screenScale)
            }
            arrayMainView.addSubview(homeStartingView)
            
            let awayStaringView = UIView(frame: CGRect(x: screenWidth/2, y: homeStartingView.frame.origin.y, width: screenWidth/2, height: 0))
            for player in match.awaystarting{
                let playerView = MatchPlayerView(frame: CGRect(x: 0, y: awayStaringView.frame.height, width: awayStaringView.frame.width, height: 30 * screenScale), player: player)
                
                awayStaringView.addSubview(playerView)
                awayStaringView.frame.size = CGSize(width: awayStaringView.frame.width, height: playerView.frame.origin.y + playerView.frame.height + 10 * screenScale)
            }
            arrayMainView.addSubview(awayStaringView)
            
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: homeStartingView.frame.origin.y + (homeStartingView.frame.height > awayStaringView.frame.height ? homeStartingView.frame.height : awayStaringView.frame.height))
            let startingSpaceLine = CALayer()
            startingSpaceLine.frame = CGRect(x: screenWidth/2 - 1 * screenScale, y: homeStartingView.frame.origin.y, width: 1 * screenScale, height: arrayMainView.frame.height - homeStartingView.frame.origin.y)
            startingSpaceLine.backgroundColor = UIColor.lightGray.withAlphaComponent(0.2).cgColor
            arrayMainView.layer.addSublayer(startingSpaceLine)
            
            let subLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: arrayMainView.frame.height + 10 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height))
            subLabel.text = "替补"
            subLabel.textColor = titleLabel.textColor
            subLabel.font = titleLabel.font
            subLabel.textAlignment = titleLabel.textAlignment
            let subBackground = UIImageView(frame: CGRect(x: 0, y: subLabel.frame.height/5*3, width: subLabel.frame.width, height: 8 * screenScale))
            subBackground.image = UIImage(named: "common_selected")
            subLabel.addSubview(subBackground)
            arrayMainView.addSubview(subLabel)
            
            let homeSubView = UIView(frame: CGRect(x: 0, y: subLabel.frame.origin.y + subLabel.frame.height + 5 * screenScale, width: screenWidth/2, height: 0))
            for player in match.homesubstitutes{
                let playerView = MatchPlayerView(frame: CGRect(x: 0, y: homeSubView.frame.height, width: homeSubView.frame.width, height: 30 * screenScale), player: player)
                
                homeSubView.addSubview(playerView)
                homeSubView.frame.size = CGSize(width: homeSubView.frame.width, height: playerView.frame.origin.y + playerView.frame.height + 10 * screenScale)
            }
            arrayMainView.addSubview(homeSubView)
            
            let awaySubView = UIView(frame: CGRect(x: screenWidth/2, y: homeSubView.frame.origin.y, width: screenWidth/2, height: 0))
            for player in match.awaysubstitutes{
                let playerView = MatchPlayerView(frame: CGRect(x: 0, y: awaySubView.frame.height, width: awaySubView.frame.width, height: 30 * screenScale), player: player)
                
                awaySubView.addSubview(playerView)
                awaySubView.frame.size = CGSize(width: awaySubView.frame.width, height: playerView.frame.origin.y + playerView.frame.height + 10 * screenScale)
            }
            arrayMainView.addSubview(awaySubView)
            
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: homeSubView.frame.origin.y + (homeSubView.frame.height > awaySubView.frame.height ? homeSubView.frame.height : awaySubView.frame.height))
            let subSpaceLine = CALayer()
            subSpaceLine.frame = CGRect(x: screenWidth/2 - 1 * screenScale, y: homeSubView.frame.origin.y, width: 1 * screenScale, height: arrayMainView.frame.height - homeSubView.frame.origin.y)
            subSpaceLine.backgroundColor = startingSpaceLine.backgroundColor
            arrayMainView.layer.addSublayer(subSpaceLine)
            
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: arrayMainView.frame.height + 20 * screenScale)
            
            arrayView.addSubview(arrayMainView)
            arrayView.frame.size = CGSize(width: screenWidth, height: arrayMainView.frame.origin.y + arrayMainView.frame.height + 10 * screenScale)
        }
        createAboutView(parent: arrayView)
    }
    
    func createAboutView(parent: UIView){
        let aboutView = UIView(frame: CGRect(x: 0, y: parent.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        aboutView.backgroundColor = UIColor.white
        let aboutTitleLabel = UILabel(frame: CGRect(x: 0, y: 10 * screenScale, width: screenWidth, height: 30 * screenScale))
        aboutTitleLabel.text = "相关新闻"
        aboutTitleLabel.textColor = UIColor.fontBlack()
        aboutTitleLabel.font = UIFont.boldFont(size: UIFont.bigSize() * screenScale)
        aboutTitleLabel.textAlignment = NSTextAlignment.center
        let aboutLeftLine = CALayer()
        aboutLeftLine.frame = CGRect(x: screenWidth/4, y: (aboutTitleLabel.frame.height - 2 * screenScale)/2, width: screenWidth/8, height: 2 * screenScale)
        aboutLeftLine.backgroundColor = UIColor.mainRed().cgColor
        aboutTitleLabel.layer.addSublayer(aboutLeftLine)
        let aboutRightLine = CALayer()
        aboutRightLine.frame = CGRect(x: screenWidth/8 * 5, y: aboutLeftLine.frame.origin.y, width: aboutLeftLine.frame.width, height: aboutLeftLine.frame.height)
        aboutRightLine.backgroundColor = aboutLeftLine.backgroundColor
        aboutTitleLabel.layer.addSublayer(aboutRightLine)
        aboutView.addSubview(aboutTitleLabel)
        let aboutNewsView = UIView(frame: CGRect(x: 0, y: aboutTitleLabel.frame.origin.y +  aboutTitleLabel.frame.height, width: aboutView.frame.width, height: aboutCellHeight * CGFloat(aboutList.count)))
        for i in 0 ..< aboutList.count{
            let cellView = NewsCellView(frame: CGRect(x: 0, y: CGFloat(i) * aboutCellHeight, width: aboutNewsView.frame.width, height: aboutCellHeight), news: aboutList[i])
            aboutNewsView.addSubview(cellView)
            let buttonView = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            buttonView.tag = TagController.MatchTags.aboutButtonBase + i
            buttonView.addTarget(self, action: #selector(enterAbut(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(buttonView)
        }
        aboutView.addSubview(aboutNewsView)
        aboutView.frame.size = CGSize(width: screenWidth, height: aboutNewsView.frame.origin.y + (aboutNewsView.frame.height > 0 ? aboutNewsView.frame.height : 10 * screenScale))
        parent.addSubview(aboutView)
        parent.frame.size = CGSize(width: parent.frame.width, height: aboutView.frame.origin.y + aboutView.frame.height)
    }
    
    @objc func changeType(_ sender: MatchTypeButton){
        if(sender.tag == selectTag){
            return
        }else{
            selectTag = sender.tag
        }
        
        let dataButton = headView.viewWithTag(TagController.MatchTags.dataButton) as! MatchTypeButton
        let infoButton = headView.viewWithTag(TagController.MatchTags.infoButton) as! MatchTypeButton
        let arrayButton = headView.viewWithTag(TagController.MatchTags.arrayButton) as! MatchTypeButton
        
        dataButton.selectImage.isHidden = true
        infoButton.selectImage.isHidden = true
        arrayButton.selectImage.isHidden = true
        dataButton.isSelected = false
        infoButton.isSelected = false
        arrayButton.isSelected = false
        
        sender.selectImage.isHidden = false
        sender.isSelected = true
        
        dataView.removeFromSuperview()
        infoView.removeFromSuperview()
        arrayView.removeFromSuperview()
        switch selectTag {
        case TagController.MatchTags.infoButton:
            bodyView.addSubview(infoView)
            bodyView.contentSize = infoView.frame.size
        case TagController.MatchTags.arrayButton:
            bodyView.addSubview(arrayView)
            bodyView.contentSize = arrayView.frame.size
        default:
            bodyView.addSubview(dataView)
            bodyView.contentSize = dataView.frame.size
        }
        if(bodyView.frame.height >= bodyView.contentSize.height){
            bodyView.contentSize.height = bodyView.frame.height + 1
        }
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    
    @objc func enterAbut(_ sender: UIButton){
        let index = sender.tag - TagController.MatchTags.aboutButtonBase
        let data = aboutList[index]
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "NewsViewController") as! NewsViewController
        vc.uuid = data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func enterHometeam(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "TeamViewController") as! TeamViewController
        vc.uuid = match.hometeam
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func enterAwayteam(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "TeamViewController") as! TeamViewController
        vc.uuid = match.awayteam
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                bodyView.refreshToAble()
            }else{
                bodyView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == bodyView){
            if(bodyView.getRefreshView().status == UIScrollRefreshStatus.able){
                getMatch()
            }
        }
    }
}
