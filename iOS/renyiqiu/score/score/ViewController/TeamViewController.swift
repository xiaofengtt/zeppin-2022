//
//  TeamViewController.swift
//  ryqiu
//
//  Created by worker on 2019/6/28.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation


class TeamViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    var headView: UIView!
    var tableView: UITableView!
    
    var uuid: String = ""
    var team: TeamDetailModel = TeamDetailModel()
    var newsList: Array<NewsModel> = []
    var dateList: Array<DateModel> = []
    var newsPageNum = 1
    var matchPageNum = 1
    var newsNomoreFlag = false
    var matchNomoreFlag = false
    var loadFlag: Bool = true
    var selectTag: Int = TagController.TeamTags.newsButton
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.backgroundGray()
        
        let leftItem = UIBarButtonItem(image: UIImage(named: "common_back_white")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        leftItem.setWhiteStyle()
        self.navigationItem.leftBarButtonItem = leftItem
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        headView = UIView()
        self.view.addSubview(headView)
        tableView = UITableView()
        self.view.addSubview(tableView)
        getTeam()
    }
    
    func getTeam(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/team/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.team = TeamDetailModel(data: data)
                
                self.createHeadView()
                self.createTableView()
                self.loadFlag = false
                self.getNewsList()
                self.getMatchList()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getNewsList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": newsPageNum])
        if(team.category != ""){
            let categorys = team.category.split(separator: ",")
            if(categorys.count > 0){
                newsDic.setValue(team.category[0], forKey: "category")
            }
        }
        newsDic.setValue("checktime desc", forKey: "sort")
        
        HttpController.get("front/news/list", params: newsDic, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.newsPageNum == 1){
                    self.newsList = []
                }
                
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    var news: [NewsModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        news.append(NewsModel(data: data))
                    }
                    self.newsList.append(contentsOf: news)
                }else{
                    self.newsNomoreFlag = true
                }
                self.loadFlag = true
                self.tableView.reloadData()
            }else{
                self.loadFlag = true
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlag = true
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getMatchList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        
        HttpController.get("front/match/list", params: ["team": uuid, "pageSize": 10, "pageNum": matchPageNum,"sort": "time desc"], data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.matchPageNum == 1){
                    self.dateList = []
                }
                
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count == 0){
                    self.matchNomoreFlag = true
                }
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    let match = MatchModel(data: data)
                    
                    let dateStr = Utils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
                    var dateFlag = true
                    for date in self.dateList{
                        if(date.date == dateStr){
                            dateFlag = false
                            date.matchList.append(match)
                            break
                        }
                    }
                    if(dateFlag){
                        let date = DateModel()
                        date.date = dateStr
                        date.weekday = Utils.getWeekDay(timestamp: match.time)
                        date.matchList.append(match)
                        self.dateList.append(date)
                    }
                }
                self.loadFlag = true
                self.tableView.reloadData()
            }else{
                self.loadFlag = true
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlag = true
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getConcren(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("front/concren/check", params: NSDictionary(dictionary: ["token": token, "team": self.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    let concren = UserConcrenModel(data: data)
                    
                    if(concren.uuid != ""){
                        self.team.isConcren = true
                        self.team.concren = concren.uuid
                    }else{
                        self.team.isConcren = false
                    }
                    self.updateConcren()
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func setConcren(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        if(!team.isConcren){
            HttpController.getToken(data: { (token) in
                HttpController.get("front/concren/add", params: NSDictionary(dictionary: ["team": self.team.uuid, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let concren = dataDictionary.object(forKey: "data") as! NSDictionary
                        self.team.concren = concren.object(forKey: "uuid") as! String
                        self.team.isConcren = true
                        self.updateConcren()
                    }else{
                        HttpController.showTimeout(viewController: self)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }) { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                }
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            HttpController.getToken(data: { (token) in
                HttpController.get("front/concren/cancel", params: NSDictionary(dictionary: ["userConcern": self.team.concren, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        self.team.isConcren = false
                        self.team.concren = ""
                        self.updateConcren()
                    }else{
                        HttpController.showTimeout(viewController: self)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }) { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                }
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }
    }
    
    func createHeadView(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.removeFromSuperview()
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: navigationFrame.height + 180 * screenScale))
        
        let backgroundImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundImageView.image = UIImage(named: "team_header")
        headView.addSubview(backgroundImageView)
        
        let titleView = NavigationBackground(navigationController: self.navigationController!)
        titleView.titleLabel.text = "球队详情"
        titleView.titleLabel.textColor = UIColor.white
        headView.addSubview(titleView)
        
        let iconView = UIImageView(frame: CGRect(x: 30 * screenScale, y: titleView.frame.origin.y + titleView.frame.height + 10 * screenScale, width: 70 * screenScale, height: 70 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                iconView.image = SDImage
            }else{
                iconView.image = UIImage(named: "common_team_default")
            }
        }
        headView.addSubview(iconView)
        
        let concrenButton = UIButton(frame: CGRect(x: screenWidth - 75 * screenScale, y: iconView.frame.origin.y + 21 * screenScale, width: 60 * screenScale, height: 28 * screenScale))
        concrenButton.tag = TagController.TeamTags.concrenButton
        concrenButton.setTitle("＋ 关注", for: UIControl.State.normal)
        concrenButton.setTitle("已关注", for: UIControl.State.selected)
        concrenButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        concrenButton.titleLabel?.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        concrenButton.layer.borderColor = UIColor.white.cgColor
        concrenButton.layer.cornerRadius = 4 * screenScale
        concrenButton.layer.masksToBounds = true
        concrenButton.backgroundColor = UIColor.mainRed()
        concrenButton.layer.borderWidth = 0
        concrenButton.addTarget(self, action: #selector(concren(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(concrenButton)
        
        let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: concrenButton.frame.origin.x - (iconView.frame.origin.x + iconView.frame.width + 20 * screenScale), height: iconView.frame.height))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.white
        nameLabel.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
        headView.addSubview(nameLabel)
        
        let typeButtonView = UIView(frame: CGRect(x: 0, y: headView.frame.height - 40 * screenScale, width: screenWidth, height: 40 * screenScale))
        typeButtonView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        headView.addSubview(typeButtonView)
        
        let dataButton = MatchTypeButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/3, height: typeButtonView.frame.height), title: "新闻")
        dataButton.tag = TagController.TeamTags.newsButton
        dataButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == dataButton.tag){
            dataButton.selectImage.isHidden = false
            dataButton.isSelected = true
        }
        typeButtonView.addSubview(dataButton)
        
        let infoButton = MatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "阵容")
        infoButton.tag = TagController.TeamTags.arrayButton
        infoButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == infoButton.tag){
            infoButton.selectImage.isHidden = false
            infoButton.isSelected = true
        }
        typeButtonView.addSubview(infoButton)
        
        let arrayButton = MatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3*2, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "赛程")
        arrayButton.tag = TagController.TeamTags.scheduleButton
        arrayButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == arrayButton.tag){
            arrayButton.selectImage.isHidden = false
            arrayButton.isSelected = true
        }
        typeButtonView.addSubview(arrayButton)
        self.view.addSubview(headView)
    }
    
    func createTableView(){
        tableView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height)
        tableView.backgroundColor = UIColor.backgroundGray()
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.white
        let navigationController = self.navigationController as! BaseNavigationController
        navigationController.statusBarStyle = UIStatusBarStyle.lightContent
        navigationController.setNeedsStatusBarAppearanceUpdate()
        
        if(user != nil){
            self.getConcren()
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        let navigationController = self.navigationController as! BaseNavigationController
        navigationController.statusBarStyle = UIStatusBarStyle.default
        navigationController.setNeedsStatusBarAppearanceUpdate()
    }
    
    func updateConcren(){
        let concrenButton = headView.viewWithTag(TagController.TeamTags.concrenButton)
        if(concrenButton != nil){
            let button = concrenButton as! UIButton
            if(!team.isConcren){
                button.isSelected = false
                button.backgroundColor = UIColor.mainRed()
                button.layer.borderWidth = 0
            }else{
                button.isSelected = true
                button.backgroundColor = UIColor.clear
                button.layer.borderWidth = 1 * screenScale
            }
        }
    }
    
    @objc func changeType(_ sender: MatchTypeButton){
        if(sender.tag == selectTag){
            return
        }else{
            selectTag = sender.tag
        }
        
        let newsButton = headView.viewWithTag(TagController.TeamTags.newsButton) as! MatchTypeButton
        let arrayButton = headView.viewWithTag(TagController.TeamTags.arrayButton) as! MatchTypeButton
        let scheduleButton = headView.viewWithTag(TagController.TeamTags.scheduleButton) as! MatchTypeButton
        
        newsButton.selectImage.isHidden = true
        arrayButton.selectImage.isHidden = true
        scheduleButton.selectImage.isHidden = true
        newsButton.isSelected = false
        arrayButton.isSelected = false
        scheduleButton.isSelected = false
        
        sender.selectImage.isHidden = false
        sender.isSelected = true
        
        if(selectTag == TagController.TeamTags.scheduleButton){
            tableView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 10 * screenScale, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height - 10 * screenScale)
        }else{
            tableView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height)
        }
        
        tableView.reloadData()
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    
    @objc func concren(_ sender: UIButton){
        if(user != nil){
            setConcren()
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if(selectTag == TagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0){
                return 5
            }else{
                return 4
            }
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            return dateList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(selectTag == TagController.TeamTags.arrayButton){
            return 40 * screenScale
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            return 30 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(selectTag == TagController.TeamTags.arrayButton){
            let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 40 * screenScale)))
            view.backgroundColor = UIColor.backgroundGray()
            
            let nameLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: 0, width: view.frame.width/2, height: view.frame.height))
            if(team.coachesList.count > 0){
                if(section == 0){
                    nameLabel.text = "教练"
                }else if(section == 1){
                    nameLabel.text = "前锋"
                }else if(section == 2){
                    nameLabel.text = "中场"
                }else if(section == 3){
                    nameLabel.text = "后卫"
                }else if(section == 4){
                    nameLabel.text = "守门员"
                }
            }else{
                if(section == 0){
                    nameLabel.text = "前锋"
                }else if(section == 1){
                    nameLabel.text = "中场"
                }else if(section == 2){
                    nameLabel.text = "后卫"
                }else if(section == 3){
                    nameLabel.text = "守门员"
                }
            }
            nameLabel.textColor = UIColor.fontGray()
            nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            view.addSubview(nameLabel)
            
            if((team.coachesList.count > 0 && section > 0) || team.coachesList.count == 0){
                let redcardLabel = UILabel(frame: CGRect(x: view.frame.width - 50 * screenScale, y: 0, width: 40 * screenScale, height: view.frame.height))
                redcardLabel.text = "红牌"
                redcardLabel.textColor = nameLabel.textColor
                redcardLabel.font = nameLabel.font
                redcardLabel.textAlignment = NSTextAlignment.center
                view.addSubview(redcardLabel)
                
                let yellowcardLabel = UILabel(frame: CGRect(x: redcardLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: view.frame.height))
                yellowcardLabel.text = "黄牌"
                yellowcardLabel.textColor = nameLabel.textColor
                yellowcardLabel.font = nameLabel.font
                yellowcardLabel.textAlignment = NSTextAlignment.center
                view.addSubview(yellowcardLabel)
                
                let goalLabel = UILabel(frame: CGRect(x: yellowcardLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: view.frame.height))
                goalLabel.text = "进球数"
                goalLabel.textColor = nameLabel.textColor
                goalLabel.font = nameLabel.font
                goalLabel.textAlignment = NSTextAlignment.center
                view.addSubview(goalLabel)
                
                let playedLabel = UILabel(frame: CGRect(x: goalLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: view.frame.height))
                playedLabel.text = "出场数"
                playedLabel.textColor = nameLabel.textColor
                playedLabel.font = nameLabel.font
                playedLabel.textAlignment = NSTextAlignment.center
                view.addSubview(playedLabel)
            }
            
            return view
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            let date = dateList[section]
            
            let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 30 * screenScale)))
            view.backgroundColor = UIColor.backgroundGray()
            
            let label = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: view.frame.width, height: UIFont.middleSize() * screenScale)))
            label.text = "\(date.date) \(date.weekday)"
            label.textColor = UIColor.fontBlack()
            label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            label.textAlignment = NSTextAlignment.center
            view.addSubview(label)
            
            return view
        }else{
            return nil
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(selectTag == TagController.TeamTags.arrayButton){
            return 50 * screenScale
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            return 115 * screenScale
        }else{
            return 110 * screenScale
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(selectTag == TagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0){
                if(section == 0){
                    return team.coachesList.count
                }else{
                    return team.arrayList[section - 1].count
                }
            }else{
                return team.arrayList[section].count
            }
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            return dateList[section].matchList.count
        }else{
            return newsList.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(selectTag == TagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0 && indexPath.section == 0){
                let data = team.coachesList[indexPath.row]
                
                let coacheView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
                coacheView.backgroundColor = UIColor.white
                
                let iconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
                iconView.image = UIImage(named: "team_player")
                coacheView.addSubview(iconView)
                
                let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: coacheView.frame.width/2, height: 18 * screenScale))
                nameLabel.text = String.valueOf(any: data.value(forKey: "coach_name"))
                nameLabel.textColor = UIColor.fontBlack()
                nameLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
                coacheView.addSubview(nameLabel)
                
                let infoLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height, width: nameLabel.frame.width, height: 12 * screenScale))
                infoLabel.text = "教练 / \(String.valueOf(any: data.value(forKey: "coach_age")) == "" ? "?" : String.valueOf(any: data.value(forKey: "coach_age")))岁"
                infoLabel.textColor = UIColor.fontGray()
                infoLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
                coacheView.addSubview(infoLabel)
                
                let bottomLine = CALayer()
                bottomLine.frame = CGRect(x: 0, y: coacheView.frame.height - 1 * screenScale, width: coacheView.frame.width, height: 1 * screenScale)
                bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
                coacheView.layer.addSublayer(bottomLine)
                
                cell.addSubview(coacheView)
            }else{
                let data: NSDictionary!
                if(team.coachesList.count > 0){
                    data = team.arrayList[indexPath.section - 1][indexPath.row]
                }else{
                    data = team.arrayList[indexPath.section][indexPath.row]
                }
                
                let playerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
                playerView.backgroundColor = UIColor.white
                let iconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
                iconView.image = UIImage(named: "team_player")
                playerView.addSubview(iconView)
                
                let redcardLabel = UILabel(frame: CGRect(x: playerView.frame.width - 50 * screenScale, y: 0, width: 40 * screenScale, height: playerView.frame.height))
                redcardLabel.text = String.valueOf(any: data.value(forKey: "redcards"))
                redcardLabel.textColor = UIColor.fontBlack()
                redcardLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                redcardLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(redcardLabel)
                
                let yellowcardLabel = UILabel(frame: CGRect(x: redcardLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: playerView.frame.height))
                yellowcardLabel.text = String.valueOf(any: data.value(forKey: "yellowcards"))
                yellowcardLabel.textColor = redcardLabel.textColor
                yellowcardLabel.font = redcardLabel.font
                yellowcardLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(yellowcardLabel)
                
                let goalLabel = UILabel(frame: CGRect(x: yellowcardLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: playerView.frame.height))
                goalLabel.text = String.valueOf(any: data.value(forKey: "goals"))
                goalLabel.textColor = redcardLabel.textColor
                goalLabel.font = redcardLabel.font
                goalLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(goalLabel)
                
                let playedLabel = UILabel(frame: CGRect(x: goalLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: playerView.frame.height))
                playedLabel.text = String.valueOf(any: data.value(forKey: "matchplayed"))
                playedLabel.textColor = redcardLabel.textColor
                playedLabel.font = redcardLabel.font
                playedLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(playedLabel)
                
                let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: playedLabel.frame.origin.x - (iconView.frame.origin.x + iconView.frame.width + 10 * screenScale), height: 18 * screenScale))
                nameLabel.text = String.valueOf(any: data.value(forKey: "cnname"))
                nameLabel.textColor = UIColor.fontBlack()
                nameLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
                playerView.addSubview(nameLabel)
                
                let infoLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height, width: nameLabel.frame.width, height: 12 * screenScale))
                infoLabel.text = "\(String.valueOf(any: data.value(forKey: "number")))号 / \(String.valueOf(any: data.value(forKey: "age")) == "" ? "?" : String.valueOf(any: data.value(forKey: "age")))岁"
                infoLabel.textColor = UIColor.fontGray()
                infoLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
                playerView.addSubview(infoLabel)
                
                let bottomLine = CALayer()
                bottomLine.frame = CGRect(x: 0, y: playerView.frame.height - 1 * screenScale, width: playerView.frame.width, height: 1 * screenScale)
                bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
                playerView.layer.addSublayer(bottomLine)
                
                cell.addSubview(playerView)
            }
            
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            let data = dateList[indexPath.section].matchList[indexPath.row]
            let cellView = MatchCellView(frame: CGRect(origin: CGPoint(x: 10 * screenScale, y: 0), size: CGSize(width: tableView.frame.width - 10 * screenScale * 2, height: 115 * screenScale - 15 * screenScale)), match: data)
            cell.addSubview(cellView)
        }else{
            let data = newsList[indexPath.row]
            let cellView = NewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 110 * screenScale), news: data)
            cell.addSubview(cellView)
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(selectTag == TagController.TeamTags.newsButton){
            let data = newsList[indexPath.row]
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "NewsViewController") as! NewsViewController
            vc.uuid = data.uuid
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            let data = dateList[indexPath.section].matchList[indexPath.row]
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "MatchViewController") as! MatchViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(selectTag == TagController.TeamTags.newsButton){
            if(scrollView == tableView){
                if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                    tableView.refreshToAble()
                }else{
                    tableView.refreshToNormal()
                }
            }
            if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
                if(loadFlag && !newsNomoreFlag){
                    loadFlag = false
                    newsPageNum = newsPageNum + 1
                    getNewsList()
                }
            }
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            if(scrollView == tableView){
                if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                    tableView.refreshToAble()
                }else{
                    tableView.refreshToNormal()
                }
            }
            if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
                if(loadFlag && !matchNomoreFlag){
                    loadFlag = false
                    matchPageNum = matchPageNum + 1
                    getMatchList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(selectTag == TagController.TeamTags.newsButton){
            if(scrollView == tableView){
                if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                    newsPageNum = 1
                    newsNomoreFlag = false
                    getNewsList()
                }
            }
        }else if(selectTag == TagController.TeamTags.scheduleButton){
            if(scrollView == tableView){
                if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                    matchPageNum = 1
                    matchNomoreFlag = false
                    getMatchList()
                }
            }
        }
    }
}
