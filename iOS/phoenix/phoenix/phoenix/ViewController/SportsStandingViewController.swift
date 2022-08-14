import Foundation
class SportsStandingViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    var headView : SportsNavigationBackground!
    var typeView : UIView!
    var tableView: UITableView!
    var category: String = CategoryUuid.ZHONGCHAO
    var type = "standing"
    var scoredList: Array<LeagueTopscoreModel> = []
    var standingList: Array<Array<SportsLeagueStandingModel>> = []
    let tableCellHeight: CGFloat = 45 * screenScale
    let numWidth: CGFloat = 40 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.colorBgGray()
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "数据统计"
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        typeView = UIView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 5 * screenScale, width: screenWidth, height: 90 * screenScale))
        typeView.backgroundColor = UIColor.white
        let categoryView = SportsCategoryScrollView(frame: CGRect(x: 5 * screenScale, y: 0 , width: screenWidth - 10 * screenScale, height: 40 * screenScale), dataList: league, selected: "")
        categoryView.buttonDelegate = self
        typeView.addSubview(categoryView)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: categoryView.frame.origin.y + categoryView.frame.height, width: typeView.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        typeView.layer.addSublayer(splitLine)
        let typeButtonView = UIView(frame: CGRect(x: typeView.frame.width/10 * 3, y: 50 * screenScale, width: typeView.frame.width/5 * 2, height: 30 * screenScale))
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
        tableView = UITableView(frame: CGRect(x: 0, y: typeView.frame.origin.y + typeView.frame.height, width: screenWidth, height: screenHeight - (typeView.frame.origin.y + typeView.frame.height)))
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
    func numberOfSections(in tableView: UITableView) -> Int {
        if(type == "standing"){
            return standingList.count + 1
        }else{
            return 1
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(type == "standing"){
            if(standingList.count > section){
                return standingList[section].count
            }else{
                return 1
            }
        }else{
            return scoredList.count
        }
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(type == "standing" && standingList.count == section){
            return 0
        }else{
            return 40 * screenScale
        }
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
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
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(type == "standing"){
            if(indexPath.section == standingList.count){
                return SportsUtils.getStandingFootHeight(category: category)
            }else{
                return tableCellHeight
            }
        }else{
            return tableCellHeight
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
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
                    cellView.backgroundColor = SportsUtils.getStandingColor(category: data.category, place: data.place)
                    let placeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 14 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
                    placeLabel.text = "\(data.place)"
                    placeLabel.textColor = UIColor.colorFontBlack()
                    placeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                    placeLabel.textAlignment = NSTextAlignment.center
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
                            teamIcon.image = UIImage(named: "phoenix_team_default")
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
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(type == "standing"){
            if(indexPath.section < standingList.count){
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "SportsTeamViewController") as! SportsTeamViewController
                vc.uuid = standingList[indexPath.section][indexPath.row].team
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
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
