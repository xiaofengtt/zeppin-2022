import Foundation
class SportsMatchViewController: UIViewController, UIScrollViewDelegate{
    var headView: UIView!
    var bodyView: UIScrollView!
    var dataView: UIView!
    var infoView: UIView!
    var arrayView: UIView!
    var uuid: String = ""
    var match: SportsMatchDetailModel = SportsMatchDetailModel()
    var selectTag: Int = SportsTagController.MatchTags.dataButton
    var aboutList: Array<SportsNewsModel> = []
    var timelineList: Array<SportsTimelineModel> = []
    var teamToTeamList: Array<SportsMatchModel> = []
    var homeMatchList: Array<SportsMatchModel> = []
    var awayMatchList: Array<SportsMatchModel> = []
    let aboutCellHeight: CGFloat = 110 * screenScale
    let paddingLeft: CGFloat = 20 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = UIView()
        self.view.addSubview(headView)
        bodyView = UIScrollView()
        self.view.addSubview(bodyView)
        getMatch()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    func getMatch(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/match/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.match = SportsMatchDetailModel(data: data)
                var timelines: Array<SportsTimelineModel> = []
                for goal in self.match.goals{
                    let timeline: SportsTimelineModel = SportsTimelineModel()
                    if(String.valueOf(any: goal.value(forKey: "home_scorer")) != ""){
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: goal.value(forKey: "home_scorer"))
                        timeline.iconUrl = self.match.hometeamIconUrl
                        timeline.teamName = self.match.hometeamName
                    }else if(String.valueOf(any: goal.value(forKey: "away_scorer")) != ""){
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: goal.value(forKey: "away_scorer"))
                        timeline.iconUrl = self.match.awayteamIconUrl
                        timeline.teamName = self.match.awayteamName
                    }else{
                        continue
                    }
                    timeline.time = String.valueOf(any: goal.value(forKey: "time"))
                    timeline.type = "goal"
                    timeline.score = String.valueOf(any: goal.value(forKey: "score"))
                    timelines.append(timeline)
                }
                for card in self.match.cards{
                    let timeline: SportsTimelineModel = SportsTimelineModel()
                    if(String.valueOf(any: card.value(forKey: "home_fault")) != ""){
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: card.value(forKey: "home_fault"))
                        timeline.iconUrl = self.match.hometeamIconUrl
                        timeline.teamName = self.match.hometeamName
                    }else if(String.valueOf(any: card.value(forKey: "away_fault")) != ""){
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: card.value(forKey: "away_fault"))
                        timeline.iconUrl = self.match.awayteamIconUrl
                        timeline.teamName = self.match.awayteamName
                    }else{
                        continue
                    }
                    timeline.time = String.valueOf(any: card.value(forKey: "time"))
                    timeline.type = String.valueOf(any: card.value(forKey: "card"))
                    timelines.append(timeline)
                }
                for subs in self.match.substitutes{
                    let timeline: SportsTimelineModel = SportsTimelineModel()
                    if(subs.value(forKey: "home_scorer") != nil && subs.value(forKey: "home_scorer").debugDescription.contains("out")){
                        let dic = subs.value(forKey: "home_scorer") as! NSDictionary
                        timeline.side = "home"
                        timeline.playerIn = String.valueOf(any: dic.value(forKey: "in"))
                        timeline.playerOut = String.valueOf(any: dic.value(forKey: "out"))
                        timeline.iconUrl = self.match.hometeamIconUrl
                        timeline.teamName = self.match.hometeamName
                    }
                    if(subs.value(forKey: "away_scorer") != nil && subs.value(forKey: "away_scorer").debugDescription.contains("out")){
                        let dic = subs.value(forKey: "away_scorer") as! NSDictionary
                        timeline.side = "away"
                        timeline.playerIn = String.valueOf(any: dic.value(forKey: "in"))
                        timeline.playerOut = String.valueOf(any: dic.value(forKey: "out"))
                        timeline.iconUrl = self.match.awayteamIconUrl
                        timeline.teamName = self.match.awayteamName
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
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getHistory(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "teamToTeam":  "\(match.hometeam),\(match.awayteam)", "sort": "time desc", "status": "finished" ]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.teamToTeamList = []
                var teamToTeams: [SportsMatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    teamToTeams.append(SportsMatchModel(data: data))
                }
                self.teamToTeamList = teamToTeams
                self.getHomeMatchs()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getHomeMatchs(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "team": match.hometeam, "sort": "time desc", "status": "finished"]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.homeMatchList = []
                var matchs: [SportsMatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matchs.append(SportsMatchModel(data: data))
                }
                self.homeMatchList = matchs
                self.getAwayMatchs()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getAwayMatchs(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/match/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "team": match.awayteam, "sort": "time desc", "status": "finished"]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.awayMatchList = []
                var matchs: [SportsMatchModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matchs.append(SportsMatchModel(data: data))
                }
                self.awayMatchList = matchs
                self.createBodyView()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getAbout(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "category":  match.category]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.aboutList = []
                var abouts: [SportsNewsModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    abouts.append(SportsNewsModel(data: data))
                }
                self.aboutList = abouts
                
                
                self.getHistory()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func createHeadView(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.removeFromSuperview()
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: (navigationFrame.height + statusBarHeight) + 150 * screenScale))
        
        let backgroundImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundImageView.image = UIImage(named: "image_match_header")
        headView.addSubview(backgroundImageView)
        
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
        backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_white")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        
        let timeLabel = UILabel(frame: CGRect(x: 0, y: statusBarHeight + 5 * screenScale, width: headView.frame.width, height: 20 * screenScale))
        timeLabel.text = "\(SportsUtils.timestampFormat(timestamp: match.time, format: "MM-dd HH:mm"))"
        timeLabel.textColor = UIColor.white
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        timeLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(timeLabel)
        let titleLabel = UILabel(frame: CGRect(x: 0, y: timeLabel.frame.origin.y + timeLabel.frame.height, width: headView.frame.width, height: 20 * screenScale))
        titleLabel.text = "\(match.categoryName)\(match.roundName)"
        titleLabel.textColor = timeLabel.textColor
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(titleLabel)
        
        let homeIconView = UIImageView(frame: CGRect(x: screenWidth/6, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale, width: screenWidth/8, height: screenWidth/8))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.hometeamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                homeIconView.image = SDImage
            }else{
                homeIconView.image = UIImage(named: "image_team_default")
            }
        }
        headView.addSubview(homeIconView)
        
        let homeNameLabel = UILabel(frame: CGRect(x: homeIconView.frame.origin.x - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height + 8 * screenScale, width: homeIconView.frame.width + 100 * screenScale, height: 22 * screenScale))
        homeNameLabel.text = match.hometeamName
        homeNameLabel.textColor = UIColor.white
        homeNameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        homeNameLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(homeNameLabel)
        
        let hometeamButton = UIButton(frame: CGRect(x: 0, y: homeIconView.frame.origin.y, width: screenWidth/2, height: homeNameLabel.frame.origin.y + homeNameLabel.frame.height - homeIconView.frame.origin.y))
        hometeamButton.addTarget(self, action: #selector(enterHometeam(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(hometeamButton)
        
        let awayIconView = UIImageView(frame: CGRect(x: screenWidth - homeIconView.frame.origin.x - homeIconView.frame.width, y: homeIconView.frame.origin.y, width: homeIconView.frame.width, height: homeIconView.frame.height))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + match.awayteamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                awayIconView.image = SDImage
            }else{
                awayIconView.image = UIImage(named: "image_team_default")
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
            let scoreLabel = UILabel(frame: CGRect(x: screenWidth/2 - 50 * screenScale, y: homeIconView.frame.origin.y + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
            if(match.status == "finished" || match.status == "living"){
                scoreLabel.text = match.finalresult.replacingOccurrences(of: "-", with: ":")
            }else{
                scoreLabel.text = "VS"
            }
            scoreLabel.textColor = UIColor.white
            scoreLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 10 ) * screenScale)
            scoreLabel.textAlignment = NSTextAlignment.center
            headView.addSubview(scoreLabel)
        }else{
            let finalStr = match.finalresult.substring(to: match.finalresult.firstIndex(of: "(")!)
            let halfStr = match.finalresult.replacingOccurrences(of: finalStr, with: "")
            
            let finalLabel = UILabel(frame: CGRect(x: screenWidth/2 - 50 * screenScale, y: homeIconView.frame.origin.y + homeIconView.frame.height/10, width: 100 * screenScale, height: 20 * screenScale))
            finalLabel.text = finalStr
            finalLabel.textColor = UIColor.white
            finalLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 2) * screenScale)
            finalLabel.textAlignment = NSTextAlignment.center
            headView.addSubview(finalLabel)
            
            let halfLabel = UILabel(frame: CGRect(x: 0, y: finalLabel.frame.origin.y + finalLabel.frame.height, width: screenWidth, height: 20 * screenScale))
            halfLabel.text = halfStr
            halfLabel.textColor = finalLabel.textColor
            halfLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
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
        statusLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(statusLabel)
        
        let typeButtonView = UIView(frame: CGRect(x: 0, y: headView.frame.height - 45 * screenScale, width: screenWidth, height: 45 * screenScale))
        typeButtonView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        headView.addSubview(typeButtonView)
        
        let dataButton = SportsMatchTypeButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/3, height: typeButtonView.frame.height), title: "数据")
        dataButton.tag = SportsTagController.MatchTags.dataButton
        dataButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == dataButton.tag){
            dataButton.selectImage.isHidden = false
            dataButton.isSelected = true
        }
        typeButtonView.addSubview(dataButton)
        
        let infoButton = SportsMatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "赛况")
        infoButton.tag = SportsTagController.MatchTags.infoButton
        infoButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == infoButton.tag){
            infoButton.selectImage.isHidden = false
            infoButton.isSelected = true
        }
        typeButtonView.addSubview(infoButton)
        
        let arrayButton = SportsMatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3*2, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "阵容")
        arrayButton.tag = SportsTagController.MatchTags.arrayButton
        arrayButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == arrayButton.tag){
            arrayButton.selectImage.isHidden = false
            arrayButton.isSelected = true
        }
        typeButtonView.addSubview(arrayButton)
        self.view.addSubview(headView)
        getAbout()
    }
    func createBodyView(){
        bodyView.removeFromSuperview()
        bodyView = UIScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 2 * screenScale, width: screenWidth, height: self.view.frame.height - headView.frame.origin.y - headView.frame.height))
        bodyView.delegate = self
        bodyView.backgroundColor = UIColor.colorBgGray()
        bodyView.showsVerticalScrollIndicator = false
        bodyView.showsHorizontalScrollIndicator = false
        bodyView.addRefreshView()
        createDataView()
        createInfoView()
        createArrayView()
        switch selectTag {
        case SportsTagController.MatchTags.infoButton:
            bodyView.addSubview(infoView)
            bodyView.contentSize = infoView.frame.size
        case SportsTagController.MatchTags.arrayButton:
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
        let historyView = SportsMatchHistoryView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0), historyList: teamToTeamList, match: match)
        dataView.addSubview(historyView)
        dataView.frame.size = CGSize(width: screenWidth, height: historyView.frame.height)
        let dataInfoView = UIView(frame: CGRect(x: 0, y: dataView.frame.height, width: screenWidth, height: 0))
        dataInfoView.backgroundColor = UIColor.white
        let infoLabelView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: dataInfoView.frame.width, height: 60 * screenScale), title: "近期战绩")
        dataInfoView.addSubview(infoLabelView)
        let homeHistoryView = SportsMatchFixtrueView(frame: CGRect(x: 0, y: infoLabelView.frame.origin.y + infoLabelView.frame.height, width: screenWidth, height: 0), matchList: homeMatchList, hometeam: match.hometeam, hometeamName: match.hometeamName, hometeamIconUrl: match.hometeamIconUrl)
        dataInfoView.frame.size = CGSize(width: dataInfoView.frame.width, height: homeHistoryView.frame.origin.y + homeHistoryView.frame.height)
        dataInfoView.addSubview(homeHistoryView)
        let awayHistoryView = SportsMatchFixtrueView(frame: CGRect(x: 0, y: homeHistoryView.frame.origin.y + homeHistoryView.frame.height, width: screenWidth, height: 0), matchList: awayMatchList, hometeam: match.awayteam, hometeamName: match.awayteamName, hometeamIconUrl: match.awayteamIconUrl)
        dataInfoView.frame.size = CGSize(width: dataInfoView.frame.width, height: awayHistoryView.frame.origin.y + awayHistoryView.frame.height + 15 * screenScale)
        dataInfoView.addSubview(awayHistoryView)
        dataView.frame.size = CGSize(width: dataView.frame.width, height: dataInfoView.frame.origin.y + dataInfoView.frame.height)
        dataView.addSubview(dataInfoView)
        if(aboutList.count > 0){
            createAboutView(parent: dataView)
        }
    }
    func createInfoView(){
        infoView = UIView()
        infoView.frame.origin = CGPoint.zero
        if(match.goals.count == 0 && match.cards.count == 0 && match.substitutes.count == 0 && match.statistics.count == 0 && match.status != "living"){
            let nodataView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 280 * screenScale))
            nodataView.backgroundColor = UIColor.white
            let nodataImage = UIImageView(frame: CGRect(x: screenWidth/4, y: 60 * screenScale, width: screenWidth/2, height: screenWidth/4))
            nodataImage.image = UIImage(named: "image_nodata_info")
            nodataView.addSubview(nodataImage)
            let nodataLabel = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 20 * screenScale, width: screenWidth, height: 20 * screenScale))
            nodataLabel.text = "暂无内容"
            nodataLabel.textColor = UIColor.colorFontDarkGray()
            nodataLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            nodataLabel.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataLabel)
            infoView.frame.size = nodataView.frame.size
            infoView.addSubview(nodataView)
        }else{
            let infoMainView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
            infoMainView.backgroundColor = UIColor.white
            let infoLabelView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: infoMainView.frame.width, height: 60 * screenScale), title: "比赛事件")
            infoMainView.addSubview(infoLabelView)
            let infoTimelineView = UIView(frame: CGRect(x: 0, y: infoLabelView.frame.origin.y + infoLabelView.frame.height + 10 * screenScale, width: infoMainView.frame.width, height: 0))
            let startView = UIView(frame: CGRect(x: paddingLeft, y: 5 * screenScale, width: 10 * screenScale, height: 10 * screenScale))
            startView.backgroundColor = UIColor.colorMainColor()
            infoTimelineView.addSubview(startView)
            let startLabel = UILabel(frame: CGRect(x: startView.frame.origin.x + startView.frame.width + paddingLeft, y: 0, width: infoTimelineView.frame.width - (startView.frame.origin.x + startView.frame.width + paddingLeft), height: 20 * screenScale))
            startLabel.text = "比赛开始"
            startLabel.textColor = UIColor.colorFontBlack()
            startLabel.font = UIFont.fontMedium(size: UIFont.FontSizeMiddle() * screenScale)
            infoTimelineView.addSubview(startLabel)
            let startLine = SportsDottedLine(frame: CGRect(x: paddingLeft + 4 * screenScale , y: startView.frame.origin.y + startView.frame.height + 5 * screenScale, width: 2 * screenScale, height: 25 * screenScale), color: UIColor.colorFontGray(), direction: SportsDottedLineDirection.vertical)
            infoTimelineView.addSubview(startLine)
            infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: startLine.frame.origin.y + startLine.frame.height + 5 * screenScale)
            for i in 0 ..< timelineList.count{
                let timeline = timelineList[i]
                let timeLineView = SportsMatchTimelineView(frame: CGRect(x: 0, y: infoTimelineView.frame.height, width: infoTimelineView.frame.width, height: 90 * screenScale), timeline: timeline, isFinal: i == timelineList.count - 1)
                infoTimelineView.addSubview(timeLineView)
                infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: timeLineView.frame.origin.y + timeLineView.frame.height)
            }
            if(match.status == "finished"){
                let endView = UIView(frame: CGRect(x: startView.frame.origin.x, y: infoTimelineView.frame.height + 5 * screenScale, width: startView.frame.width, height: startView.frame.height))
                endView.backgroundColor = startView.backgroundColor
                infoTimelineView.addSubview(endView)
                let endLabel = UILabel(frame: CGRect(x: startLabel.frame.origin.x, y: endView.frame.origin.y - 5 * screenScale, width: startLabel.frame.width, height: startLabel.frame.height))
                endLabel.text = "比赛结束"
                endLabel.textColor = startLabel.textColor
                endLabel.font = startLabel.font
                endLabel.textAlignment = startLabel.textAlignment
                infoTimelineView.addSubview(endLabel)
                infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: endLabel.frame.origin.y + endLabel.frame.height)
            }
            infoTimelineView.frame.size = CGSize(width: infoTimelineView.frame.width, height: infoTimelineView.frame.height + 20 * screenScale)
            infoMainView.addSubview(infoTimelineView)
            infoMainView.frame.size = CGSize(width: infoMainView.frame.width, height: infoTimelineView.frame.origin.y + infoTimelineView.frame.height)
            if(match.statistics.count > 0){
                let infoSpaceView = UIView(frame: CGRect(x: 0, y: infoMainView.frame.height, width: infoMainView.frame.width, height: 10 * screenScale))
                infoSpaceView.backgroundColor = UIColor.colorBgGray()
                infoMainView.addSubview(infoSpaceView)
                let infoStatView = UIView(frame: CGRect(x: 0, y: infoSpaceView.frame.origin.y + infoSpaceView.frame.height, width: infoMainView.frame.width, height: 0))
                let infoStatLabelView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: infoMainView.frame.width, height: 60 * screenScale), title: "技术统计")
                infoStatView.addSubview(infoStatLabelView)
                infoStatView.frame.size = CGSize(width: infoStatView.frame.width, height: infoStatLabelView.frame.origin.y + infoStatLabelView.frame.height)
                for stat in match.statistics{
                    let statView = SportsMatchStatView(frame: CGRect(x: 0, y: infoStatView.frame.height, width: infoStatView.frame.width, height: 35 * screenScale), stat: stat)
                    infoStatView.addSubview(statView)
                    infoStatView.frame.size = CGSize(width: infoStatView.frame.width, height: statView.frame.origin.y + statView.frame.height)
                }
                infoMainView.addSubview(infoStatView)
                infoMainView.frame.size = CGSize(width: infoMainView.frame.width, height: infoStatView.frame.origin.y + infoStatView.frame.height + 20 * screenScale)
            }
            infoView.addSubview(infoMainView)
            infoView.frame.size = CGSize(width: screenWidth, height: infoMainView.frame.origin.y + infoMainView.frame.height)
        }
        if(aboutList.count > 0){
            createAboutView(parent: infoView)
        }
    }
    func createArrayView(){
        arrayView = UIView()
        arrayView.frame.origin = CGPoint.zero
        if(match.homestarting.count == 0 && match.awaystarting.count == 0){
            let nodataView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 280 * screenScale))
            nodataView.backgroundColor = UIColor.white
            let nodataImage = UIImageView(frame: CGRect(x: screenWidth/4, y: 60 * screenScale, width: screenWidth/2, height: screenWidth/4))
            nodataImage.image = UIImage(named: "image_nodata_info")
            nodataView.addSubview(nodataImage)
            let nodataLabel = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 20 * screenScale, width: screenWidth, height: 20 * screenScale))
            nodataLabel.text = "暂无内容"
            nodataLabel.textColor = UIColor.colorFontDarkGray()
            nodataLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            nodataLabel.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataLabel)
            arrayView.frame.size = nodataView.frame.size
            arrayView.addSubview(nodataView)
        }else{
            let arrayMainView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
            arrayMainView.backgroundColor = UIColor.white
            let titleLabelView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: arrayMainView.frame.width, height: 60 * screenScale), title: "首发")
            arrayMainView.addSubview(titleLabelView)
            let startingBottomLine = CALayer()
            startingBottomLine.frame = CGRect(x: 0, y: titleLabelView.frame.height - 1 * screenScale, width: titleLabelView.frame.width, height: 1 * screenScale)
            startingBottomLine.backgroundColor = UIColor.colorBgGray().cgColor
            titleLabelView.layer.addSublayer(startingBottomLine)
            let homeStartingView = UIView(frame: CGRect(x: 0, y: titleLabelView.frame.origin.y + titleLabelView.frame.height, width: screenWidth/2, height: 0))
            for player in match.homestarting{
                let playerView = SportsMatchPlayerView(frame: CGRect(x: 0, y: homeStartingView.frame.height, width: homeStartingView.frame.width, height: 50 * screenScale), player: player, color: UIColor(red: 255/255, green: 58/255, blue: 2/255, alpha: 1))
                homeStartingView.addSubview(playerView)
                homeStartingView.frame.size = CGSize(width: homeStartingView.frame.width, height: playerView.frame.origin.y + playerView.frame.height)
            }
            arrayMainView.addSubview(homeStartingView)
            let awayStaringView = UIView(frame: CGRect(x: screenWidth/2, y: homeStartingView.frame.origin.y, width: screenWidth/2, height: 0))
            for player in match.awaystarting{
                let playerView = SportsMatchPlayerView(frame: CGRect(x: 0, y: awayStaringView.frame.height, width: awayStaringView.frame.width, height: 50 * screenScale), player: player, color: UIColor(red: 0/255, green: 153/255, blue: 255/255, alpha: 1))
                awayStaringView.addSubview(playerView)
                awayStaringView.frame.size = CGSize(width: awayStaringView.frame.width, height: playerView.frame.origin.y + playerView.frame.height)
            }
            arrayMainView.addSubview(awayStaringView)
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: homeStartingView.frame.origin.y + (homeStartingView.frame.height > awayStaringView.frame.height ? homeStartingView.frame.height : awayStaringView.frame.height))
            let startingSplitLine = CALayer()
            startingSplitLine.frame = CGRect(x: homeStartingView.frame.width - 1 * screenScale, y: 0, width: 1 * screenScale, height: homeStartingView.frame.height)
            startingSplitLine.backgroundColor = UIColor.colorBgGray().cgColor
            homeStartingView.layer.addSublayer(startingSplitLine)
            let subLabelView = SportsMatchTitleView(frame: CGRect(x: 0, y: arrayMainView.frame.height, width: arrayMainView.frame.width, height: 60 * screenScale), title: "替补")
            arrayMainView.addSubview(subLabelView)
            let subBottomLine = CALayer()
            subBottomLine.frame = CGRect(x: 0, y: subLabelView.frame.height - 1 * screenScale, width: subLabelView.frame.width, height: 1 * screenScale)
            subBottomLine.backgroundColor = startingBottomLine.backgroundColor
            subLabelView.layer.addSublayer(subBottomLine)
            let homeSubView = UIView(frame: CGRect(x: 0, y: subLabelView.frame.origin.y + subLabelView.frame.height, width: screenWidth/2, height: 0))
            for player in match.homesubstitutes{
                let playerView = SportsMatchPlayerView(frame: CGRect(x: 0, y: homeSubView.frame.height, width: homeSubView.frame.width, height: 50 * screenScale), player: player, color: UIColor(red: 255/255, green: 178/255, blue: 178/255, alpha: 1))
                homeSubView.addSubview(playerView)
                homeSubView.frame.size = CGSize(width: homeSubView.frame.width, height: playerView.frame.origin.y + playerView.frame.height)
            }
            arrayMainView.addSubview(homeSubView)
            let awaySubView = UIView(frame: CGRect(x: screenWidth/2, y: homeSubView.frame.origin.y, width: screenWidth/2, height: 0))
            for player in match.awaysubstitutes{
                let playerView = SportsMatchPlayerView(frame: CGRect(x: 0, y: awaySubView.frame.height, width: awaySubView.frame.width, height: 50 * screenScale), player: player, color: UIColor(red: 177/255, green: 224/255, blue: 255/255, alpha: 1))
                awaySubView.addSubview(playerView)
                awaySubView.frame.size = CGSize(width: awaySubView.frame.width, height: playerView.frame.origin.y + playerView.frame.height)
            }
            arrayMainView.addSubview(awaySubView)
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: homeSubView.frame.origin.y + (homeSubView.frame.height > awaySubView.frame.height ? homeSubView.frame.height : awaySubView.frame.height))
            let subSplitLine = CALayer()
            subSplitLine.frame = CGRect(x: homeSubView.frame.width - 1 * screenScale, y: 0, width: 1 * screenScale, height: homeSubView.frame.height)
            subSplitLine.backgroundColor = UIColor.colorBgGray().cgColor
            homeSubView.layer.addSublayer(subSplitLine)
            arrayMainView.frame.size = CGSize(width: arrayMainView.frame.width, height: arrayMainView.frame.height)
            arrayView.addSubview(arrayMainView)
            arrayView.frame.size = CGSize(width: screenWidth, height: arrayMainView.frame.origin.y + arrayMainView.frame.height)
        }
        if(aboutList.count > 0){
            createAboutView(parent: arrayView)
        }
    }
    func createAboutView(parent: UIView){
        let aboutView = UIView(frame: CGRect(x: 0, y: parent.frame.height + 10 * screenScale, width: screenWidth, height: 0))
        aboutView.backgroundColor = UIColor.white
        let aboutTitleView = SportsMatchTitleView(frame: CGRect(x: 0, y: 0, width: parent.frame.width, height: 60 * screenScale), title: "相关新闻")
        aboutView.addSubview(aboutTitleView)
        let aboutNewsView = UIView(frame: CGRect(x: 0, y: aboutTitleView.frame.origin.y +  aboutTitleView.frame.height, width: aboutView.frame.width, height: aboutCellHeight * CGFloat(aboutList.count)))
        for i in 0 ..< aboutList.count{
            let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: CGFloat(i) * aboutCellHeight, width: aboutNewsView.frame.width, height: aboutCellHeight), news: aboutList[i])
            aboutNewsView.addSubview(cellView)
            let buttonView = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            buttonView.tag = SportsTagController.MatchTags.aboutButtonBase + i
            buttonView.addTarget(self, action: #selector(enterAbut(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(buttonView)
        }
        aboutView.addSubview(aboutNewsView)
        aboutView.frame.size = CGSize(width: screenWidth, height: aboutNewsView.frame.origin.y + (aboutNewsView.frame.height > 0 ? aboutNewsView.frame.height : 10 * screenScale))
        parent.addSubview(aboutView)
        parent.frame.size = CGSize(width: parent.frame.width, height: aboutView.frame.origin.y + aboutView.frame.height)
    }
    @objc func changeType(_ sender: SportsMatchTypeButton){
        if(sender.tag == selectTag){
            return
        }else{
            selectTag = sender.tag
        }
        let dataButton = headView.viewWithTag(SportsTagController.MatchTags.dataButton) as! SportsMatchTypeButton
        let infoButton = headView.viewWithTag(SportsTagController.MatchTags.infoButton) as! SportsMatchTypeButton
        let arrayButton = headView.viewWithTag(SportsTagController.MatchTags.arrayButton) as! SportsMatchTypeButton
         dataButton.isSelected = false
        infoButton.isSelected = false
        arrayButton.isSelected = false
        sender.isSelected = true
        dataView.removeFromSuperview()
        infoView.removeFromSuperview()
        arrayView.removeFromSuperview()
        switch selectTag {
        case SportsTagController.MatchTags.infoButton:
            bodyView.addSubview(infoView)
            bodyView.contentSize = infoView.frame.size
        case SportsTagController.MatchTags.arrayButton:
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
        let index = sender.tag - SportsTagController.MatchTags.aboutButtonBase
        let data = aboutList[index]
        let vc = SportsNewsViewController()
        vc.uuid = data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func enterHometeam(_ sender: UIButton){
        let vc = SportsTeamViewController()
        vc.uuid = match.hometeam
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func enterAwayteam(_ sender: UIButton){
       let vc = SportsTeamViewController()
        vc.uuid = match.awayteam
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                bodyView.refreshViewToAble()
            }else{
                bodyView.refreshViewToNormal()
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == bodyView){
            if(bodyView.refreshView()?.status == UIScrollRefreshStatus.able){
                getMatch()
            }
        }
    }
}
