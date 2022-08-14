import Foundation
class SportsScheduleViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {
    var headView: UIView!
    var tableView: UITableView!
    var todayView: UIView!
    var category: String = ""
    var dateList: Array<DateModel> = []
    var todayUuid = ""
    let timestamp = Date().timestamp - 1000 * 60 * 60 * 3
    var pageNum = 1
    var historyPageNum = 1
    var loadFlag: Bool = true
    var nomoreFlag: Bool = false
    let paddingLeft: CGFloat = 10 * screenScale
    let sectionHeight: CGFloat = 30 * screenScale
    let tableCellHeight: CGFloat = 90 * screenScale
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
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: abs(navigationFrame.origin.y)))
        headView.backgroundColor = UIColor.white
        var array = league.copy()
        array.insert("关注_", at: 0)
        let categoryView = SportsCategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.height - 40 * screenScale, width: screenWidth - 10 * screenScale, height: 40 * screenScale), dataList: array, selected: "")
        categoryView.buttonDelegate = self
        headView.addSubview(categoryView)
        self.view.addSubview(headView)
        tableView = UITableView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height)))
        tableView.backgroundColor = UIColor.colorBgGray()
        tableView.estimatedRowHeight = 0
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        self.view.addSubview(tableView)
        todayView = UIView(frame: CGRect(x: screenWidth - 70 * screenScale, y: self.tabBarController!.tabBar.frame.origin.y - 80 * screenScale, width: 50 * screenScale, height: 50 * screenScale))
        todayView.backgroundColor = UIColor.clear
        todayView.layer.zPosition = 1.5
        let todayButton = UIButton(frame: CGRect(x: 0, y: 0, width: 50 * screenScale, height: 50 * screenScale))
        todayButton.setImage(UIImage(named: "image_today"), for: UIControl.State.normal)
        todayButton.layer.cornerRadius = todayButton.frame.height/2
        todayButton.addTarget(self, action: #selector(toToday(_:)), for: UIControl.Event.touchUpInside)
        todayView.addSubview(todayButton)
        self.view.addSubview(todayView)
        loadFlag = false
        getList(first: true)
    }
    func getList(first: Bool){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["starttime": SportsUtils.timestampFormat(timestamp: timestamp, format: "yyyy-MM-dd HH:mm:ss"),"pageSize": 20, "pageNum": pageNum,"sort": "time"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        SportsHttpWorker.get("front/match/list", params: dictionary, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.pageNum == 1){
                    self.dateList = []
                }
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count == 0){
                    self.nomoreFlag = true
                }
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    let match = SportsMatchModel(data: data)
                    let dateStr = SportsUtils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
                    var dateFlag = true
                    for date in self.dateList{
                        if(date.date == dateStr){
                            for index in 0 ..< date.matchList.count{
                                if date.matchList[index].time > match.time{
                                    dateFlag = false
                                    date.matchList.insert(match, at: index)
                                    break
                                }
                            }
                            if(dateFlag){
                                dateFlag = false
                                date.matchList.append(match)
                            }
                            break
                        }
                    }
                    if(dateFlag){
                        let date = DateModel()
                        date.date = dateStr
                        date.weekday = SportsUtils.getWeekDay(timestamp: match.time)
                        date.matchList.append(match)
                        var dateFlag = true
                        for index in 0 ..< self.dateList.count {
                            if(self.dateList[index].date > date.date){
                                dateFlag = false
                                self.dateList.insert(date, at: index)
                            }
                            break
                        }
                        if(dateFlag){
                            self.dateList.append(date)
                        }
                    }
                }
                self.tableView.reloadData()
                if(first){
                    self.todayUuid = self.dateList.count > 0 ? self.dateList[0].matchList[0].uuid : ""
                    self.getHistoryList()
                }else{
                    self.loadFlagCancle()
                }
            }else{
                self.loadFlagCancle()
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlagCancle()
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getHistoryList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["endtime": SportsUtils.timestampFormat(timestamp: timestamp, format: "yyyy-MM-dd HH:mm:ss") ,"pageSize": 20, "pageNum": historyPageNum,"sort": "time desc"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        SportsHttpWorker.get("front/match/list", params: dictionary, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count == 0){
                    self.nomoreFlag = true
                }
                let thisUuid = self.dateList.count > 0 ? self.dateList[0].matchList[0].uuid : ""
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    let match = SportsMatchModel(data: data)
                    let dateStr = SportsUtils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
                    var dateFlag = true
                    for date in self.dateList{
                        if(date.date == dateStr){
                            for index in 0 ..< date.matchList.count{
                                if date.matchList[index].time > match.time{
                                    dateFlag = false
                                    date.matchList.insert(match, at: index)
                                    break
                                }
                            }
                            if(dateFlag){
                                dateFlag = false
                                date.matchList.append(match)
                            }
                            break
                        }
                    }
                    if(dateFlag){
                        let date = DateModel()
                        date.date = dateStr
                        date.weekday = SportsUtils.getWeekDay(timestamp: match.time)
                        date.matchList.append(match)
                        var dateFlag = true
                        for index in 0 ..< self.dateList.count {
                            if(self.dateList[index].date > date.date){
                                dateFlag = false
                                self.dateList.insert(date, at: index)
                            }
                            break
                        }
                        if(dateFlag){
                            self.dateList.append(date)
                        }
                    }
                }
                self.tableView.reloadData()
                if(thisUuid == ""){
                    self.tableView.scrollToRow(at: IndexPath(row: self.dateList[self.dateList.count - 1].matchList.count - 1, section: self.dateList.count - 1), at: UITableView.ScrollPosition.bottom, animated: false)
                }else{
                    for sec in 0 ..< self.dateList.count{
                        let date = self.dateList[sec]
                        for row in 0 ..< date.matchList.count{
                            if(thisUuid == date.matchList[row].uuid){
                                self.tableView.scrollToRow(at: IndexPath(row: row, section: sec), at: UITableView.ScrollPosition.top, animated: false)
                                break;
                            }
                        }
                    }
                }
                if(self.todayUuid == ""){
                    self.todayUuid = self.dateList.count > 0 ? self.dateList[self.dateList.count - 1].matchList[self.dateList[self.dateList.count - 1].matchList.count - 1].uuid : ""
                }
                self.loadFlagCancle()
            }else{
                self.loadFlagCancle()
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlagCancle()
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    @objc func toToday(_ sender: UIButton){
        for sec in 0 ..< self.dateList.count{
            let date = self.dateList[sec]
            for row in 0 ..< date.matchList.count{
                if(todayUuid == date.matchList[row].uuid){
                    self.tableView.scrollToRow(at: IndexPath(row: row, section: sec), at: UITableView.ScrollPosition.top, animated: true)
                    return;
                }
            }
        }
    }
    func changeCategory(_ uuid: String) {
        category = uuid
        pageNum = 1
        historyPageNum = 1
        nomoreFlag = false
        loadFlag = false
        getList(first: true)
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return dateList.count
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return sectionHeight
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let date = dateList[section]
        let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: sectionHeight)))
        view.backgroundColor = UIColor.colorBgGray()
        let label = UILabel(frame: CGRect(origin: CGPoint(x: paddingLeft, y: 5 * screenScale), size: CGSize(width: view.frame.width - paddingLeft * 2, height: 20 * screenScale)))
        label.text = "\(date.date) \(date.weekday)"
        label.textColor = UIColor.colorFontBlack()
        label.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        label.textAlignment = NSTextAlignment.center
        view.addSubview(label)
        return view
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableCellHeight
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dateList[section].matchList.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let data = dateList[indexPath.section].matchList[indexPath.row]
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        let cellView = SportsMatchCellView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: tableCellHeight)), match: data)
        cell.addSubview(cellView)
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let data = dateList[indexPath.section].matchList[indexPath.row]
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsMatchViewController") as! SportsMatchViewController
        vc.uuid = data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < -20 * screenScale){
                if(loadFlag){
                    loadFlag = false
                    historyPageNum = historyPageNum + 1
                    getHistoryList()
                }
            }
            if(scrollView.contentSize.height > scrollView.frame.height && scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !nomoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getList(first: false)
                }
            }
        }
    }
    func loadFlagCancle(){
        Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
            self.loadFlag = true
        }
    }
}
