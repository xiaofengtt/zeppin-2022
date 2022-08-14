import Foundation
class SportsTeamViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    var headView: UIView!
    var tableView: UITableView!
    var uuid: String = ""
    var team: SportsTeamDetailModel = SportsTeamDetailModel()
    var newsList: Array<SportsNewsModel> = []
    var dateList: Array<PhoenixDateModel> = []
    var newsPageNum = 1
    var matchPageNum = 1
    var newsNomoreFlag = false
    var matchNomoreFlag = false
    var loadFlag: Bool = true
    var selectTag: Int = SportsTagController.TeamTags.newsButton
    let paddingLeft: CGFloat = 15 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.colorBgGray()
        headView = UIView()
        self.view.addSubview(headView)
        tableView = UITableView()
        self.view.addSubview(tableView)
        getTeam()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    func getTeam(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/team/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.team = SportsTeamDetailModel(data: data)
                self.createHeadView()
                self.createTableView()
                self.loadFlag = false
                self.getNewsList()
                self.getMatchList()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getNewsList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": newsPageNum])
        if(team.category != ""){
            let categorys = team.category.split(separator: ",")
            if(categorys.count > 0){
                newsDic.setValue(team.category[0], forKey: "category")
            }
        }
        newsDic.setValue("checktime desc", forKey: "sort")
        SportsHttpWorker.get("front/news/list", params: newsDic, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.newsPageNum == 1){
                    self.newsList = []
                }
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    var news: [SportsNewsModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        news.append(SportsNewsModel(data: data))
                    }
                    self.newsList.append(contentsOf: news)
                }else{
                    self.newsNomoreFlag = true
                }
                self.loadFlag = true
                self.tableView.reloadData()
            }else{
                self.loadFlag = true
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlag = true
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getMatchList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/match/list", params: ["team": uuid, "pageSize": 10, "pageNum": matchPageNum,"sort": "time desc"], data: { (data) in
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
                    let match = SportsMatchModel(data: data)
                    let dateStr = SportsUtils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
                    var dateFlag = true
                    for date in self.dateList{
                        if(date.date == dateStr){
                            dateFlag = false
                            date.matchList.append(match)
                            break
                        }
                    }
                    if(dateFlag){
                        let date = PhoenixDateModel()
                        date.date = dateStr
                        date.weekday = SportsUtils.getWeekDay(timestamp: match.time)
                        date.matchList.append(match)
                        self.dateList.append(date)
                    }
                }
                self.loadFlag = true
                self.tableView.reloadData()
            }else{
                self.loadFlag = true
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlag = true
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getConcern(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.getPrivate(data: { (token) in
            SportsHttpWorker.get("front/concren/check", params: NSDictionary(dictionary: ["token": token, "team": self.uuid]), data: { (data) in
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
                    self.updateConcern()
                }else{
                    SportsHttpWorker.showTimeout(viewController: self)
                }
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func setConcern(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        if(!team.isConcren){
            SportsHttpWorker.getPrivate(data: { (token) in
                SportsHttpWorker.get("front/concren/add", params: NSDictionary(dictionary: ["team": self.team.uuid, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let concren = dataDictionary.object(forKey: "data") as! NSDictionary
                        self.team.concren = concren.object(forKey: "uuid") as! String
                        self.team.isConcren = true
                        self.updateConcern()
                    }else{
                        SportsHttpWorker.showTimeout(viewController: self)
                    }
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                }) { (error) in
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                    SportsHttpWorker.showTimeout(viewController: self)
                }
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }else{
            SportsHttpWorker.getPrivate(data: { (token) in
                SportsHttpWorker.get("front/concren/cancel", params: NSDictionary(dictionary: ["userConcern": self.team.concren, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        self.team.isConcren = false
                        self.team.concren = ""
                        self.updateConcern()
                    }else{
                        SportsHttpWorker.showTimeout(viewController: self)
                    }
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                }) { (error) in
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                    SportsHttpWorker.showTimeout(viewController: self)
                }
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }
    }
    func createHeadView(){
        headView.removeFromSuperview()
        let navigationFrame = self.navigationController!.navigationBar.frame
               headView.removeFromSuperview()
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: (navigationFrame.height + statusBarHeight) + 140 * screenScale))
        let backgroundImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundImageView.image = UIImage(named: "image_team_header")
        headView.addSubview(backgroundImageView)
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
        backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_white")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        
        let titleLabel = UILabel(frame: CGRect(x: (headView.frame.width - 100 * screenScale)/2, y: backButton.frame.origin.y, width: 100 * screenScale, height: backButton.frame.height))
        titleLabel.text = "球队详情"
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        headView.addSubview(titleLabel)
        
        let concernButton = SportsTeamConcernButton(frame: CGRect(x: screenWidth - 80 * screenScale, y: backButton.frame.origin.y, width: 65 * screenScale, height: backButton.frame.height))
        concernButton.tag = SportsTagController.TeamTags.concernButton
        concernButton.addTarget(self, action: #selector(concern(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(concernButton)
        
        let teamIcon = UIImageView(frame: CGRect(x: 40 * screenScale, y: navigationFrame.height + statusBarHeight + 10 * screenScale, width: 70 * screenScale, height: 70 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                teamIcon.image = SDImage
            }else{
                teamIcon.image = UIImage(named: "image_team_default")
            }
        }
        headView.addSubview(teamIcon)
        let teamNameLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x + teamIcon.frame.width + 20 * screenScale, y: teamIcon.frame.origin.y + (teamIcon.frame.height - 20 * screenScale) / 2 , width: headView.frame.width - (teamIcon.frame.origin.x + teamIcon.frame.width + 20 * screenScale), height: 20 * screenScale))
        teamNameLabel.text = team.name
        teamNameLabel.textColor = UIColor.white
        teamNameLabel.font = UIFont.fontNormal(size: (UIFont.FontSizeBiggest() + 2) * screenScale)
        headView.addSubview(teamNameLabel)
        
        let typeView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: headView.frame.height - 40 * screenScale), size: CGSize(width: screenWidth, height: 40 * screenScale)))
        typeView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        let typeButtonView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: typeView.frame.height))
        let dataButton = SportsMatchTypeButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/3, height: typeButtonView.frame.height), title: "新闻")
        dataButton.tag = SportsTagController.TeamTags.newsButton
        dataButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == dataButton.tag){
            dataButton.selectImage.isHidden = false
            dataButton.isSelected = true
        }
        typeButtonView.addSubview(dataButton)
        let infoButton = SportsMatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "阵容")
        infoButton.tag = SportsTagController.TeamTags.arrayButton
        infoButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == infoButton.tag){
            infoButton.selectImage.isHidden = false
            infoButton.isSelected = true
        }
        typeButtonView.addSubview(infoButton)
        let arrayButton = SportsMatchTypeButton(frame: CGRect(x: typeButtonView.frame.width/3 * 2, y: 0, width: dataButton.frame.width, height: dataButton.frame.height), title: "赛程")
        arrayButton.tag = SportsTagController.TeamTags.scheduleButton
        arrayButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        if(selectTag == arrayButton.tag){
            arrayButton.selectImage.isHidden = false
            arrayButton.isSelected = true
        }
        typeButtonView.addSubview(arrayButton)
        typeView.addSubview(typeButtonView)
        headView.addSubview(typeView)
        self.view.addSubview(headView)
    }
    func createTableView(){
        tableView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 1 * screenScale, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height - 1 * screenScale)
        tableView.backgroundColor = UIColor.white
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
    }
    override func viewWillAppear(_ animated: Bool) {
        if(user != nil){
            self.getConcern()
        }
    }
    func updateConcern(){
        let concernButton = headView.viewWithTag(SportsTagController.TeamTags.concernButton)
        if(concernButton != nil){
            let button = concernButton as! SportsTeamConcernButton
            button.isSelected = team.isConcren
        }
    }
    @objc func changeType(_ sender: SportsMatchTypeButton){
        if(sender.tag == selectTag){
            return
        }else{
            selectTag = sender.tag
        }
        let newsButton = headView.viewWithTag(SportsTagController.TeamTags.newsButton) as! SportsMatchTypeButton
        let arrayButton = headView.viewWithTag(SportsTagController.TeamTags.arrayButton) as! SportsMatchTypeButton
        let scheduleButton = headView.viewWithTag(SportsTagController.TeamTags.scheduleButton) as! SportsMatchTypeButton
        newsButton.selectImage.isHidden = true
        arrayButton.selectImage.isHidden = true
        scheduleButton.selectImage.isHidden = true
        newsButton.isSelected = false
        arrayButton.isSelected = false
        scheduleButton.isSelected = false
        sender.selectImage.isHidden = false
        sender.isSelected = true
        tableView.reloadData()
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    @objc func concern(_ sender: UIButton){
        if(user != nil){
            setConcern()
        }else{
            let vc = SportsLoginViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0){
                return 2
            }else{
                return 1
            }
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            return dateList.count
        }else{
            return 1
        }
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            return 40 * screenScale
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            return 30 * screenScale
        }else{
            return 0
        }
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 40 * screenScale)))
            view.backgroundColor = UIColor.colorBgGray()
            let nameLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: 0, width: view.frame.width/2, height: view.frame.height))
            if(team.coachesList.count > 0){
                if(section == 0){
                    nameLabel.text = "教练"
                }else{
                    nameLabel.text = "球员"
                }
            }else{
                nameLabel.text = "球员"
            }
            nameLabel.textColor = UIColor.colorFontBlack()
            nameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
            view.addSubview(nameLabel)
            if((team.coachesList.count > 0 && section > 0) || team.coachesList.count == 0){
                let redcardLabel = UILabel(frame: CGRect(x: view.frame.width - 50 * screenScale, y: 0, width: 50 * screenScale, height: view.frame.height))
                redcardLabel.text = "进球数"
                redcardLabel.textColor = nameLabel.textColor
                redcardLabel.font = nameLabel.font
                redcardLabel.textAlignment = NSTextAlignment.center
                view.addSubview(redcardLabel)
                let yellowcardLabel = UILabel(frame: CGRect(x: redcardLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: view.frame.height))
                yellowcardLabel.text = "出场数"
                yellowcardLabel.textColor = nameLabel.textColor
                yellowcardLabel.font = nameLabel.font
                yellowcardLabel.textAlignment = NSTextAlignment.center
                view.addSubview(yellowcardLabel)
                let goalLabel = UILabel(frame: CGRect(x: yellowcardLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: view.frame.height))
                goalLabel.text = "年龄"
                goalLabel.textColor = nameLabel.textColor
                goalLabel.font = nameLabel.font
                goalLabel.textAlignment = NSTextAlignment.center
                view.addSubview(goalLabel)
                let playedLabel = UILabel(frame: CGRect(x: goalLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: view.frame.height))
                playedLabel.text = "位置"
                playedLabel.textColor = nameLabel.textColor
                playedLabel.font = nameLabel.font
                playedLabel.textAlignment = NSTextAlignment.center
                view.addSubview(playedLabel)
                let numberLabel = UILabel(frame: CGRect(x: playedLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: view.frame.height))
                numberLabel.text = "号码"
                numberLabel.textColor = nameLabel.textColor
                numberLabel.font = nameLabel.font
                numberLabel.textAlignment = NSTextAlignment.center
                view.addSubview(numberLabel)
            }
            return view
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            let date = dateList[section]
            let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 30 * screenScale)))
            view.backgroundColor = UIColor.colorBgGray()
            let label = UILabel(frame: CGRect(origin: CGPoint(x: paddingLeft, y: 0), size: CGSize(width: view.frame.width - paddingLeft * 2, height: view.frame.height)))
            label.text = "\(date.date) \(date.weekday)"
            label.textColor = UIColor.colorFontBlack()
            label.font = UIFont.fontBold(size: UIFont.FontSizeMiddle() * screenScale)
            label.textAlignment = NSTextAlignment.left
            view.addSubview(label)
            return view
        }else{
            return nil
        }
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            return 50 * screenScale
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            return 100 * screenScale
        }else{
            return 110 * screenScale
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0){
                if(section == 0){
                    return team.coachesList.count
                }else{
                    return team.arrayList.count
                }
            }else{
                return team.arrayList.count
            }
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            return dateList[section].matchList.count
        }else{
            return newsList.count
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(selectTag == SportsTagController.TeamTags.arrayButton){
            if(team.coachesList.count > 0 && indexPath.section == 0){
                let data = team.coachesList[indexPath.row]
                let coacheView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
                coacheView.backgroundColor = UIColor.white
                let iconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
                iconView.image = UIImage(named: "image_player")
                coacheView.addSubview(iconView)
                let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: coacheView.frame.width/2, height: iconView.frame.height))
                nameLabel.text = String.valueOf(any: data.value(forKey: "coach_name"))
                nameLabel.textColor = UIColor.colorFontBlack()
                nameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                coacheView.addSubview(nameLabel)
                let bottomLine = CALayer()
                bottomLine.frame = CGRect(x: 0, y: coacheView.frame.height - 1 * screenScale, width: coacheView.frame.width, height: 1 * screenScale)
                bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
                coacheView.layer.addSublayer(bottomLine)
                cell.addSubview(coacheView)
            }else{
                let data: NSDictionary!
                if(team.coachesList.count > 0){
                    data = team.arrayList[indexPath.row]
                }else{
                    data = team.arrayList[indexPath.row]
                }
                let playerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
                playerView.backgroundColor = UIColor.white
                let iconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
                iconView.image = UIImage(named: "image_player")
                playerView.addSubview(iconView)
                let redcardLabel = UILabel(frame: CGRect(x: playerView.frame.width - 50 * screenScale, y: 0, width: 50 * screenScale, height: playerView.frame.height))
                redcardLabel.text = String.valueOf(any: data.value(forKey: "goals"))
                redcardLabel.textColor = UIColor.colorFontBlack()
                redcardLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                redcardLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(redcardLabel)
                let yellowcardLabel = UILabel(frame: CGRect(x: redcardLabel.frame.origin.x - 50 * screenScale, y: 0, width: 50 * screenScale, height: playerView.frame.height))
                yellowcardLabel.text = String.valueOf(any: data.value(forKey: "matchplayed"))
                yellowcardLabel.textColor = redcardLabel.textColor
                yellowcardLabel.font = redcardLabel.font
                yellowcardLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(yellowcardLabel)
                let goalLabel = UILabel(frame: CGRect(x: yellowcardLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: playerView.frame.height))
                goalLabel.text = String.valueOf(any: data.value(forKey: "age"))
                goalLabel.textColor = redcardLabel.textColor
                goalLabel.font = redcardLabel.font
                goalLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(goalLabel)
                let playedLabel = UILabel(frame: CGRect(x: goalLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: playerView.frame.height))
                if(String.valueOf(any: data.value(forKey: "type")) == "Goalkeepers"){
                    playedLabel.text = "门将"
                }else if(String.valueOf(any: data.value(forKey: "type")) == "Defenders"){
                    playedLabel.text = "后卫"
                }else if(String.valueOf(any: data.value(forKey: "type")) == "Midfielders"){
                    playedLabel.text = "中场"
                }else if(String.valueOf(any: data.value(forKey: "type")) == "Forwards"){
                    playedLabel.text = "前锋"
                }
                playedLabel.textColor = redcardLabel.textColor
                playedLabel.font = redcardLabel.font
                playedLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(playedLabel)
                let numberLabel = UILabel(frame: CGRect(x: playedLabel.frame.origin.x - 40 * screenScale, y: 0, width: 40 * screenScale, height: playerView.frame.height))
                let number = String.valueOf(any: data.value(forKey: "number"))
                numberLabel.text = number.count < 2 ? "0" + number : number
                numberLabel.textColor = redcardLabel.textColor
                numberLabel.font = redcardLabel.font
                numberLabel.textAlignment = NSTextAlignment.center
                playerView.addSubview(numberLabel)
                let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: numberLabel.frame.origin.x - (iconView.frame.origin.x + iconView.frame.width + 10 * screenScale), height: iconView.frame.height))
                nameLabel.text = String.valueOf(any: data.value(forKey: "cnname"))
                nameLabel.textColor = UIColor.colorFontBlack()
                nameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                playerView.addSubview(nameLabel)
                let middleLine = CALayer()
                middleLine.frame = CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: 1 * screenScale, height: playerView.frame.height)
                middleLine.backgroundColor = UIColor.colorBgGray().cgColor
                playerView.layer.addSublayer(middleLine)
                let bottomLine = CALayer()
                bottomLine.frame = CGRect(x: 0, y: playerView.frame.height - 1 * screenScale, width: playerView.frame.width, height: 1 * screenScale)
                bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
                playerView.layer.addSublayer(bottomLine)
                cell.addSubview(playerView)
            }
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            let data = dateList[indexPath.section].matchList[indexPath.row]
            let cellView = SportsMatchCellView(frame: CGRect(origin: CGPoint(x: 10 * screenScale, y: 0), size: CGSize(width: tableView.frame.width - 10 * screenScale * 2, height: 100 * screenScale)), match: data)
            cell.addSubview(cellView)
        }else{
            let data = newsList[indexPath.row]
            let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 110 * screenScale), news: data)
            cell.addSubview(cellView)
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(selectTag == SportsTagController.TeamTags.newsButton){
            let data = newsList[indexPath.row]
            let vc = SportsNewsViewController()
            vc.uuid = data.uuid
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            let data = dateList[indexPath.section].matchList[indexPath.row]
            let vc = SportsMatchViewController()
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(selectTag == SportsTagController.TeamTags.newsButton){
            if(scrollView == tableView){
                if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                    tableView.refreshViewToAble()
                }else{
                    tableView.refreshViewToNormal()
                }
            }
            if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
                if(loadFlag && !newsNomoreFlag){
                    loadFlag = false
                    newsPageNum = newsPageNum + 1
                    getNewsList()
                }
            }
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            if(scrollView == tableView){
                if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                    tableView.refreshViewToAble()
                }else{
                    tableView.refreshViewToNormal()
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
        if(selectTag == SportsTagController.TeamTags.newsButton){
            if(scrollView == tableView){
                if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                    newsPageNum = 1
                    newsNomoreFlag = false
                    getNewsList()
                }
            }
        }else if(selectTag == SportsTagController.TeamTags.scheduleButton){
            if(scrollView == tableView){
                if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                    matchPageNum = 1
                    matchNomoreFlag = false
                    getMatchList()
                }
            }
        }
    }
}
