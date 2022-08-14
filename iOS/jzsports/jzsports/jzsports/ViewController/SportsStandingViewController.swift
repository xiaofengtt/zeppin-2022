import Foundation
class SportsStandingViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    var headView: SportsNavigationBackground!
    var buttonView: UIView!
    var newsTableView : UITableView!
    var typeView : UIView!
    var tableView: UITableView!
    var category: String = ""
    var titleName: String = ""
    var type = "standing"
    var pageType = "news"
    var loadFlag: Bool = true
    var pageNum: Int = 1
    var nomoreFlag = false
    var newsList: Array<SportsNewsModel> = []
    var scoredList: Array<LeagueTopscoreModel> = []
    var standingList: Array<Array<SportsLeagueStandingModel>> = []
    let tableCellHeight: CGFloat = 45 * screenScale
    let newsTableCellHeight: CGFloat = 110 * screenScale
    let numWidth: CGFloat = 40 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = titleName
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        
        buttonView = UIView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 50 * screenScale))
        
        let newsButtonView = SportsLeagueTypeButtonView(frame: CGRect(x: 15 * screenScale, y: 20 * screenScale, width: 100 * screenScale, height: 20 * screenScale), title: "资讯")
        newsButtonView.tag = SportsTagController.StandingTags.newsButtonView
        newsButtonView.isSelected = true
        newsButtonView.button.addTarget(self, action: #selector(toNews(_:)), for: UIControl.Event.touchUpInside)
        buttonView.addSubview(newsButtonView)
        
        let standingButtonView = SportsLeagueTypeButtonView(frame: CGRect(x: newsButtonView.frame.origin.x + newsButtonView.frame.width + 20 * screenScale, y: newsButtonView.frame
            .origin.y, width: 100 * screenScale, height: 20 * screenScale), title: "资料库")
        standingButtonView.tag = SportsTagController.StandingTags.standingButtonView
        standingButtonView.isSelected = false
        standingButtonView.button.addTarget(self, action: #selector(toStanding(_:)), for: UIControl.Event.touchUpInside)
        buttonView.addSubview(standingButtonView)
        
        self.view.addSubview(buttonView)
        self.createNewsView()
        self.createStandingView()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func createNewsView(){
        newsTableView = UITableView(frame: CGRect(x: 0, y: buttonView.frame.origin.y + buttonView.frame.height + 10 * screenScale, width: screenWidth, height: self.view.frame.height - (buttonView.frame.origin.y + buttonView.frame.height + 10 * screenScale)))
        newsTableView.backgroundColor = UIColor.white
        newsTableView.delegate = self
        newsTableView.dataSource = self
        newsTableView.showsVerticalScrollIndicator = false
        newsTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        newsTableView.addRefreshView()
        self.view.addSubview(newsTableView)
        getNewsList()
    }
    func createStandingView(){
        typeView = UIView(frame: CGRect(x: 0, y: buttonView.frame.origin.y + buttonView.frame.height + 10 * screenScale , width: screenWidth, height: 50 * screenScale))
        typeView.isHidden = true
        typeView.backgroundColor = UIColor.white
        let typeButtonView = UIView(frame: CGRect(x: typeView.frame.width/10 * 3, y: 10 * screenScale, width: typeView.frame.width/5 * 2, height: 30 * screenScale))
        typeButtonView.layer.borderColor = UIColor.colorMainColor().cgColor
        typeButtonView.layer.borderWidth = 1 * screenScale
        typeButtonView.layer.cornerRadius = 2 * screenScale
        let standingButton = UIButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/2, height: typeButtonView.frame.height))
        standingButton.tag = SportsTagController.StandingTags.typeStandingButton
        standingButton.isSelected = true
        standingButton.setTitle("积分榜", for: UIControl.State.normal)
        standingButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        standingButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControl.State.normal)
        standingButton.setBackgroundImage(UIImage.imageWithColor(UIColor.colorMainColor()), for: UIControl.State.selected)
        standingButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        standingButton.setTitleColor(UIColor.white, for: UIControl.State.selected)
        standingButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        typeButtonView.addSubview(standingButton)
        let scoredButton = UIButton(frame: CGRect(x: typeButtonView.frame.width/2, y: standingButton.frame.origin.y, width: typeButtonView.frame.width/2, height: standingButton.frame.height))
        scoredButton.tag = SportsTagController.StandingTags.typeScoredButton
        scoredButton.setTitle("射手榜", for: UIControl.State.normal)
        scoredButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        scoredButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControl.State.normal)
        scoredButton.setBackgroundImage(UIImage.imageWithColor(UIColor.colorMainColor()), for: UIControl.State.selected)
        scoredButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        scoredButton.setTitleColor(UIColor.white, for: UIControl.State.selected)
        scoredButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        typeButtonView.addSubview(scoredButton)
        typeView.addSubview(typeButtonView)
        self.view.addSubview(typeView)
        tableView = UITableView(frame: CGRect(x: 0, y: typeView.frame.origin.y + typeView.frame.height + 10 * screenScale, width: screenWidth, height: self.view.frame.height - (typeView.frame.origin.y + typeView.frame.height + 10 * screenScale)))
        tableView.isHidden = true
        tableView.backgroundColor = UIColor.white
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
        self.view.addSubview(tableView)
        getList()
    }
    func changeCategory(_ uuid: String) {
        category = uuid
        getList()
    }
    @objc func toNews(_ sender: UIButton) {
        if (pageType != "news"){
            let newsButtonView = buttonView.viewWithTag(SportsTagController.StandingTags.newsButtonView) as! SportsLeagueTypeButtonView
            let standingButtonView = buttonView.viewWithTag(SportsTagController.StandingTags.standingButtonView) as! SportsLeagueTypeButtonView
            newsButtonView.isSelected = true
            standingButtonView.isSelected = false
            newsTableView.isHidden = false
            typeView.isHidden = true
            tableView.isHidden = true
            pageType = "news"
        }
    }
    @objc func toStanding(_ sender: UIButton) {
        if (pageType != "standing"){
            let newsButtonView = buttonView.viewWithTag(SportsTagController.StandingTags.newsButtonView) as! SportsLeagueTypeButtonView
            let standingButtonView = buttonView.viewWithTag(SportsTagController.StandingTags.standingButtonView) as! SportsLeagueTypeButtonView
            newsButtonView.isSelected = false
            standingButtonView.isSelected = true
            newsTableView.isHidden = true
            typeView.isHidden = false
            tableView.isHidden = false
            pageType = "standing"
        }
    }
    @objc func changeType(_ sender : UIButton){
        let standingButton = typeView.viewWithTag(SportsTagController.StandingTags.typeStandingButton) as! UIButton
        let scoredButton = typeView.viewWithTag(SportsTagController.StandingTags.typeScoredButton) as! UIButton
        if(sender.tag == SportsTagController.StandingTags.typeStandingButton){
            standingButton.isSelected = true
            scoredButton.isSelected = false
            type = "standing"
        }else{
            standingButton.isSelected = false
            scoredButton.isSelected = true
            type = "scored"
        }
        getList()
    }
    func getList(){
        if(category == ""){
            return
        }
        let loadingView = SportsHttpWorker.showLoading(viewController: self)
        if(type == "standing"){
            SportsHttpWorker.get("front/category/standingList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.standingList = []
                    var standings: [[SportsLeagueStandingModel]] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        let standing = SportsLeagueStandingModel(data: data)
                        if(standing.place == 1){
                            var stList : [SportsLeagueStandingModel] = []
                            stList.append(standing)
                            standings.append(stList)
                        }else{
                            standings[standings.count - 1].append(standing)
                        }
                    }
                    self.standingList = standings
                    self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    self.tableView.reloadData()
                    if(self.standingList.count > 0){
                        self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    }
                }else{
                    SportsHttpWorker.showTimeout(viewController: self)
                }
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }else{
            SportsHttpWorker.get("front/category/topscoreList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.scoredList = []
                    var topscores: [LeagueTopscoreModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        topscores.append(LeagueTopscoreModel(data: data))
                    }
                    self.scoredList = topscores
                    self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    self.tableView.reloadData()
                    if(self.scoredList.count > 0){
                        self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    }
                }else{
                    SportsHttpWorker.showTimeout(viewController: self)
                }
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }
    }
    func getNewsList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": pageNum])
        if(category != ""){
            newsDic.setValue(category, forKey: "category")
        }
        newsDic.setValue("checktime desc", forKey: "sort")
        SportsHttpWorker.get("front/news/list", params: newsDic, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.pageNum == 1){
                    self.newsList = []
                }
                 let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    self.loadFlag = true
                    var news: [SportsNewsModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        news.append(SportsNewsModel(data: data))
                    }
                    self.newsList.append(contentsOf: news)
                }
                self.newsTableView.reloadData()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        if(tableView == self.tableView){
            if(type == "standing"){
                return standingList.count + 1
            }else{
                return 1
            }
        }else{
            return 1
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == self.tableView){
            if(type == "standing"){
                if(standingList.count > section){
                    return standingList[section].count
                }else{
                    return 1
                }
            }else{
                return scoredList.count
            }
        }else if(tableView == self.newsTableView){
            return newsList.count + 1
        }else{
            return 0
        }
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(tableView == self.tableView){
            if(type == "standing" && standingList.count == section){
                return 0
            }else{
                return 40 * screenScale
            }
        }else{
            return 0
        }
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(tableView == self.tableView){
            if(type == "standing" && section == standingList.count){
                return nil
            }
            let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 40 * screenScale))
            view.backgroundColor = UIColor.colorBgGray()
            if(type == "standing"){
                if(standingList.count == 1){
                    let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: view.frame.height))
                    placeLabel.text = "排名"
                    placeLabel.textColor = UIColor.colorFontGray()
                    placeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                    placeLabel.textAlignment = NSTextAlignment.right
                    view.addSubview(placeLabel)
                    let scoreLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: view.frame.height))
                    scoreLabel.text = "积分"
                    scoreLabel.textColor = placeLabel.textColor
                    scoreLabel.font = placeLabel.font
                    scoreLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(scoreLabel)
                    let goalLable = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: view.frame.height))
                    goalLable.text = "进/失"
                    goalLable.textColor = placeLabel.textColor
                    goalLable.font = placeLabel.font
                    goalLable.textAlignment = NSTextAlignment.center
                    view.addSubview(goalLable)
                    let wonLabel = UILabel(frame: CGRect(x: goalLable.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: view.frame.height))
                    wonLabel.text = "胜/平/负"
                    wonLabel.textColor = placeLabel.textColor
                    wonLabel.font = placeLabel.font
                    wonLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(wonLabel)
                    let playedLabel = UILabel(frame: CGRect(x: wonLabel.frame.origin.x - numWidth, y: 0, width: numWidth, height: view.frame.height))
                    playedLabel.text = "场"
                    playedLabel.textColor = placeLabel.textColor
                    playedLabel.font = placeLabel.font
                    playedLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(playedLabel)
                    let teamLabel = UILabel(frame: CGRect(x: placeLabel.frame.origin.x + placeLabel.frame.width, y: 0, width: playedLabel.frame.origin.x - (placeLabel.frame.origin.x + placeLabel.frame.width) - 5 * screenScale, height: view.frame.height))
                    teamLabel.text = "   球队"
                    teamLabel.textColor = placeLabel.textColor
                    teamLabel.font = placeLabel.font
                    view.addSubview(teamLabel)
                }else{
                    let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: view.frame.width * 0.4, height: view.frame.height))
                    if(standingList[section].count > 0){
                        placeLabel.text = "\(standingList[section][0].round)"
                    }
                    placeLabel.textColor = UIColor.colorFontGray()
                    placeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                    placeLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(placeLabel)
                    let scoreLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: view.frame.height))
                    scoreLabel.text = "积分"
                    scoreLabel.textColor = placeLabel.textColor
                    scoreLabel.font = placeLabel.font
                    scoreLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(scoreLabel)
                    let goalLable = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: view.frame.height))
                    goalLable.text = "进/失"
                    goalLable.textColor = placeLabel.textColor
                    goalLable.font = placeLabel.font
                    goalLable.textAlignment = NSTextAlignment.center
                    view.addSubview(goalLable)
                    let wonLabel = UILabel(frame: CGRect(x: goalLable.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: view.frame.height))
                    wonLabel.text = "胜/平/负"
                    wonLabel.textColor = placeLabel.textColor
                    wonLabel.font = placeLabel.font
                    wonLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(wonLabel)
                    let playedLabel = UILabel(frame: CGRect(x: wonLabel.frame.origin.x - numWidth, y: 0, width: numWidth, height: view.frame.height))
                    playedLabel.text = "场"
                    playedLabel.textColor = placeLabel.textColor
                    playedLabel.font = placeLabel.font
                    playedLabel.textAlignment = NSTextAlignment.center
                    view.addSubview(playedLabel)
                }
            }else{
                let nameLabel = UILabel(frame: CGRect(x: numWidth, y: 0, width: (view.frame.width - 2 * numWidth)/2 - 5 * screenScale, height: view.frame.height))
                nameLabel.text = "球员"
                nameLabel.textColor = UIColor.colorFontGray()
                nameLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                view.addSubview(nameLabel)
                let teamLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: nameLabel.frame.width, height: view.frame.height))
                teamLabel.text = "球队"
                teamLabel.textColor = nameLabel.textColor
                teamLabel.font = nameLabel.font
                view.addSubview(teamLabel)
                let dataLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: view.frame.height))
                dataLabel.text = "进球"
                dataLabel.textColor = nameLabel.textColor
                dataLabel.font = nameLabel.font
                dataLabel.textAlignment = NSTextAlignment.center
                view.addSubview(dataLabel)
            }
            let splitLine = CALayer()
            splitLine.frame = CGRect(x: 0, y: view.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
            splitLine.backgroundColor = UIColor.colorBgGray().cgColor
            view.layer.addSublayer(splitLine)
            return view
        }else{
            return nil
        }
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(tableView == self.tableView){
            if(type == "standing"){
                if(indexPath.section == standingList.count){
                    return SportsUtils.getStandingFootHeight(category: category)
                }else{
                    return tableCellHeight
                }
            }else{
                return tableCellHeight
            }
        }else if(tableView == self.newsTableView){
            if(indexPath.row == newsList.count){
                return 50 * screenScale
            }else{
                return newsTableCellHeight
            }
        }else{
            return 0
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(tableView == self.tableView){
            let cellView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
            if(type == "standing"){
                if(indexPath.section == standingList.count){
                    let footerView = SportsStandingFooterView(category: category)
                    cellView.addSubview(footerView)
                }else{
                    let data = standingList[indexPath.section][indexPath.row]
                    if(data.uuid == ""){
                        let roundLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
                        roundLabel.text = data.round
                        roundLabel.textColor = UIColor.colorFontBlack()
                        roundLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
                        roundLabel.textAlignment = NSTextAlignment.center
                        cellView.addSubview(roundLabel)
                    }else{
                        let placeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 14 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
                        placeLabel.backgroundColor = SportsUtils.getStandingColor(category: data.category, place: data.place)
                        placeLabel.text = "\(data.place)"
                        placeLabel.textColor = placeLabel.backgroundColor == UIColor.white ?  UIColor.colorFontBlack() : UIColor.white
                        placeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                        placeLabel.textAlignment = NSTextAlignment.center
                        placeLabel.layer.masksToBounds = true
                        placeLabel.layer.cornerRadius = 2 * screenScale
                        cellView.addSubview(placeLabel)
                        let scoreLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                        scoreLabel.text = "\(data.point)"
                        scoreLabel.textColor = UIColor.colorFontBlack()
                        scoreLabel.font = placeLabel.font
                        scoreLabel.textAlignment = NSTextAlignment.center
                        cellView.addSubview(scoreLabel)
                        let goalLable = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: cellView.frame.height))
                        goalLable.text = "\(data.scored)/\(data.against)"
                        goalLable.textColor = scoreLabel.textColor
                        goalLable.font = scoreLabel.font
                        goalLable.textAlignment = NSTextAlignment.center
                        cellView.addSubview(goalLable)
                        let wonLabel = UILabel(frame: CGRect(x: goalLable.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: cellView.frame.height))
                        wonLabel.text = "\(data.won)/\(data.drawn)/\(data.lost)"
                        wonLabel.textColor = scoreLabel.textColor
                        wonLabel.font = scoreLabel.font
                        wonLabel.textAlignment = NSTextAlignment.center
                        cellView.addSubview(wonLabel)
                        let playedLabel = UILabel(frame: CGRect(x: wonLabel.frame.origin.x - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                        playedLabel.text = "\(data.played)"
                        playedLabel.textColor = scoreLabel.textColor
                        playedLabel.font = scoreLabel.font
                        playedLabel.textAlignment = NSTextAlignment.center
                        cellView.addSubview(playedLabel)
                        let teamIcon = UIImageView(frame: CGRect(x: numWidth, y: cellView.frame.height/4, width: cellView.frame.height/2, height: cellView.frame.height/2))
                        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + data.teamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                            if(SDImage != nil){
                                teamIcon.image = SDImage
                            }else{
                                teamIcon.image = UIImage(named: "image_team_default")
                            }
                        }
                        cellView.addSubview(teamIcon)
                        let teamLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x + teamIcon.frame.width + 5 * screenScale, y: 0, width: playedLabel.frame.origin.x - (teamIcon.frame.origin.x + teamIcon.frame.width) - 8 * screenScale, height: cellView.frame.height))
                        teamLabel.text = "\(data.teamName)"
                        teamLabel.textColor = scoreLabel.textColor
                        teamLabel.font = scoreLabel.font
                        teamLabel.numberOfLines = 2
                        cellView.addSubview(teamLabel)
                        let splitLine = CALayer()
                        splitLine.frame = CGRect(x: 0, y: cellView.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
                        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
                        cellView.layer.addSublayer(splitLine)
                    }
                }
            }else{
                let data = scoredList[indexPath.row]
                let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: cellView.frame.height))
                placeLabel.text = "\(data.place)"
                placeLabel.textColor = UIColor.colorFontDarkGray()
                placeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                placeLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(placeLabel)
                let nameLabel = UILabel(frame: CGRect(x: placeLabel.frame.origin.x + placeLabel.frame.width, y: 0, width: (cellView.frame.width - 2 * numWidth)/2 - 5 * screenScale, height: cellView.frame.height))
                nameLabel.text = "\(data.playerName)"
                nameLabel.textColor = placeLabel.textColor
                nameLabel.font = placeLabel.font
                nameLabel.numberOfLines = 2
                cellView.addSubview(nameLabel)
                let teamLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: nameLabel.frame.width, height: cellView.frame.height))
                teamLabel.text = "\(data.teamName)"
                teamLabel.textColor = placeLabel.textColor
                teamLabel.font = placeLabel.font
                teamLabel.numberOfLines = 2
                cellView.addSubview(teamLabel)
                let dataLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                dataLabel.text = "\(data.goals)"
                dataLabel.textColor = placeLabel.textColor
                dataLabel.font = placeLabel.font
                dataLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(dataLabel)
                let splitLine = CALayer()
                splitLine.frame = CGRect(x: 0, y: cellView.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
                splitLine.backgroundColor = UIColor.colorBgGray().cgColor
                cellView.layer.addSublayer(splitLine)
                cell.addSubview(cellView)
                return cell
            }
            cell.addSubview(cellView)
        }else if(tableView == self.newsTableView){
            if(indexPath.row == newsList.count){
                let cellView = SportsNoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "没有更多消息了", flagLine: true)
                cell.addSubview(cellView)
            }else{
                let data = newsList[indexPath.row]
                let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: newsTableCellHeight), news: data)
                cell.addSubview(cellView)
            }
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(tableView == self.tableView){
            if(type == "standing"){
                if(indexPath.section < standingList.count){
                    let vc = SportsTeamViewController()
                    vc.uuid = standingList[indexPath.section][indexPath.row].team
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }else if(tableView == self.newsTableView){
            if(indexPath.row < newsList.count){
                let data = newsList[indexPath.row]
                let vc = SportsNewsViewController()
                vc.uuid = data.uuid
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                tableView.refreshViewToAble()
            }else{
                tableView.refreshViewToNormal()
            }
        }else if(scrollView == newsTableView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                newsTableView.refreshViewToAble()
            }else{
                newsTableView.refreshViewToNormal()
            }
            if(newsTableView.contentOffset.y >= newsTableView.contentSize.height - newsTableView.frame.height - 50 * screenScale){
                if(loadFlag && !nomoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getNewsList()
                }
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                getList()
            }
        }else if(scrollView == newsTableView){
            if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                pageNum = 1
                nomoreFlag = false
                getNewsList()
            }
        }
    }
}
