import UIKit
class SportsMainViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate{
    var headView : UIView!
    var tableView: UITableView!
    var category: String = CategoryUuid.ZHONGCHAO
    var newsList: Array<SportsNewsModel> = []
    var matchList: Array<SportsMatchModel> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    let paddingLeft: CGFloat = 15 * screenScale
    let headCellHeight: CGFloat = 410 * screenScale
    let tableCellHeight: CGFloat = 110 * screenScale
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
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: (navigationFrame.height + UIApplication.shared.statusBarFrame.height)))
        headView.backgroundColor = UIColor.white
        let array = league.copy()
        let categoryView = SportsCategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.height - 40 * screenScale, width: screenWidth - 10 * screenScale, height: 40 * screenScale), dataList: array, selected: "")
        categoryView.buttonDelegate = self
        headView.addSubview(categoryView)
        self.view.addSubview(headView)
        tableView = UITableView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 10 * screenScale, width: screenWidth, height:  self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height + 10 * screenScale)))
        tableView.backgroundColor = UIColor.colorBgGray()
        tableView.estimatedRowHeight = 0
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
        pageNum = 1
        getList()
    }
    func getList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["starttime": SportsUtils.timestampFormat(timestamp: Date().timestamp - 1000 * 60 * 60 * 3, format: "yyyy-MM-dd HH:mm:ss"),"pageSize": 5, "pageNum": pageNum,"sort": "time"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        SportsHttpWorker.get("front/match/list", params: dictionary, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                self.matchList = []
                var matches: [SportsMatchModel] = []
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matches.append(SportsMatchModel(data: data))
                }
                self.matchList = matches
                if(datas.count < 2){
                    dictionary.setValue("endtime", forKey: dictionary.value(forKey: "starttime") as! String)
                    dictionary.removeObject(forKey: "starttime")
                    dictionary.setValue("time desc", forKey: "sort")
                    SportsHttpWorker.get("front/match/list", params: dictionary, data: { (data) in
                        let dataDic = data as! NSDictionary
                        let stat = dataDic.object(forKey: "status") as! String
                        if stat == "SUCCESS" {
                            let datas1 = dataDic.object(forKey: "data") as! NSArray
                            for i in 0 ..< datas1.count{
                                let data = datas1[i] as! NSDictionary
                                self.matchList.append(SportsMatchModel(data: data))
                            }
                            self.getNewsList()
                            if(self.matchList.count > 0){
                                self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                            }
                            SportsHttpWorker.hideLoading(loadingView: loadingView)
                        }else{
                            SportsHttpWorker.showTimeout(viewController: self)
                        }
                    }) { (error) in
                        SportsHttpWorker.hideLoading(loadingView: loadingView)
                        SportsHttpWorker.showTimeout(viewController: self)
                    }
                }else{
                    self.getNewsList()
                    self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                }
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
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
                self.tableView.reloadData()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    @objc func toMatch(_ sender: SportsMainMatchButton){
        let data = sender.match!
        if(data.uuid != ""){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsMatchViewController") as! SportsMatchViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func toTopNews(_ sender: UIButton){
        if(newsList.count > 0){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsNewsViewController") as! SportsNewsViewController
            vc.uuid = newsList[0].uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return newsList.count + 1
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == 0){
            return headCellHeight
        }else if(indexPath.row == newsList.count){
            return 50 * screenScale
        }else{
            return tableCellHeight
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.white
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == 0){
            let headCellView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: headCellHeight))
            headCellView.backgroundColor = UIColor.colorBgGray()
            let bannerImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: headCellView.frame.width, height: 150 * screenScale))
            bannerImageView.image = UIImage(named: "phoenix_banner_1")
            headCellView.addSubview(bannerImageView)
            let enterView = UIView(frame: CGRect(x: 0, y: bannerImageView.frame.origin.y + bannerImageView.frame.height, width: headCellView.frame.width, height: 75 * screenScale))
            enterView.backgroundColor = UIColor.white
            let leftEnterView = SportsHomeEnterView(frame: CGRect(x: 0, y: 0, width: enterView.frame.width/2, height: enterView.frame.height))
            leftEnterView.titleLabel.text = "热门专题"
            leftEnterView.contentLabel.text = "精彩内容集中呈现"
            leftEnterView.iconImageView.image = UIImage(named: "phoenix_enter_special")
            leftEnterView.button.addTarget(self, action: #selector(enterSpecial(_:)), for: UIControl.Event.touchUpInside)
            enterView.addSubview(leftEnterView)
            let splitLine = CALayer()
            splitLine.frame = CGRect(x: enterView.frame.width/2 - 1 * screenScale, y: 20 * screenScale, width: 1 * screenScale, height: enterView.frame.height - 40 * screenScale)
            splitLine.backgroundColor = UIColor.colorBgGray().cgColor
            enterView.layer.addSublayer(splitLine)
            let rightEnterView = SportsHomeEnterView(frame: CGRect(x: enterView.frame.width/2, y: 0, width: enterView.frame.width/2, height: enterView.frame.height))
            rightEnterView.titleLabel.text = "数据统计"
            rightEnterView.contentLabel.text = "积分排名实时查看"
            rightEnterView.iconImageView.image = UIImage(named: "phoenix_enter_data")
            rightEnterView.button.addTarget(self, action: #selector(enterData(_:)), for: UIControl.Event.touchUpInside)
            enterView.addSubview(rightEnterView)
            headCellView.addSubview(enterView)
            let specialView = UIView(frame: CGRect(x: 0, y: enterView.frame.origin.y + enterView.frame.height + 10 * screenScale, width: headCellView.frame.width, height: 130 * screenScale))
            specialView.backgroundColor = UIColor.white
            let specialTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: specialView.frame.width, height: 30 * screenScale))
            specialTitleLabel.text = "专题"
            specialTitleLabel.textColor = UIColor.colorFontBlack()
            specialTitleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
            specialView.addSubview(specialTitleLabel)
            let specialChildrenWidth: CGFloat = (specialView.frame.width - paddingLeft * 4)/3
            let leftSpecialView = SportsHomeSpecialView(frame: CGRect(x: paddingLeft, y: specialTitleLabel.frame.origin.y + specialTitleLabel.frame.height + 10 * screenScale, width: specialChildrenWidth, height: 65 * screenScale))
            leftSpecialView.titleLabel.text = "足坛新鲜事"
            leftSpecialView.imageView.image = UIImage(named: "phoenix_special_bg_4")
            leftSpecialView.button.tag = 1
            leftSpecialView.button.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            specialView.addSubview(leftSpecialView)
            let middleSpecialView = SportsHomeSpecialView(frame: CGRect(x: paddingLeft * 2 + specialChildrenWidth, y: leftSpecialView.frame.origin.y, width: leftSpecialView.frame.width, height: leftSpecialView.frame.height))
            middleSpecialView.titleLabel.text = "欧冠集锦"
            middleSpecialView.imageView.image = UIImage(named: "phoenix_special_bg_6")
            middleSpecialView.button.tag = 2
            middleSpecialView.button.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            specialView.addSubview(middleSpecialView)
            let rightSpecialView = SportsHomeSpecialView(frame: CGRect(x: paddingLeft * 3 + specialChildrenWidth * 2, y: leftSpecialView.frame.origin.y, width: leftSpecialView.frame.width, height: leftSpecialView.frame.height))
            rightSpecialView.titleLabel.text = "绿茵英雄"
            rightSpecialView.imageView.image = UIImage(named: "phoenix_special_bg_0")
            rightSpecialView.button.tag = 3
            rightSpecialView.button.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            specialView.addSubview(rightSpecialView)
            headCellView.addSubview(specialView)
            let newsTitleView = UIView(frame: CGRect(x: 0, y: specialView.frame.origin.y + specialView.frame.height + 10 * screenScale, width: screenWidth, height: 40 * screenScale))
            newsTitleView.backgroundColor = UIColor.white
            let newsTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: newsTitleView.frame.width - paddingLeft * 2, height: 30 * screenScale))
            newsTitleLabel.text = "热点新闻"
            newsTitleLabel.textColor = specialTitleLabel.textColor
            newsTitleLabel.font = specialTitleLabel.font
            newsTitleView.addSubview(newsTitleLabel)
            headCellView.addSubview(newsTitleView)
            cell.addSubview(headCellView)
        }else if(indexPath.row == newsList.count){
            let cellView = SportsNoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "没有更多新闻了", flagLine: true)
            cell.addSubview(cellView)
        }else{
            if(newsList.count >= indexPath.row){
                let data = newsList[indexPath.row - 1]
                let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight), news: data)
                cell.addSubview(cellView)
            }
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(indexPath.row > 0 && indexPath.row <= newsList.count - 1){
            if(newsList.count >= indexPath.row){
                let data = newsList[indexPath.row - 1]
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "SportsNewsViewController") as! SportsNewsViewController
                vc.uuid = data.uuid
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                tableView.refreshViewToAble()
            }else{
                tableView.refreshViewToNormal()
            }
        }
        if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
            if(loadFlag){
                loadFlag = false
                pageNum = pageNum + 1
                getNewsList()
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView().status == UIScrollRefreshStatus.able){
                pageNum = 1
                getList()
            }
        }
    }
    @objc func enterSpecial(_ sender: UIButton){
        self.tabBarController?.selectedIndex = 2
    }
    @objc func enterData(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsStandingViewController") as! SportsStandingViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func enterSpecialDetial(_ sender: UIButton){
        var uuid : String!
        var title: String!
        var content: String!
        var bgName: String!
        if(sender.tag == 1){
            uuid = "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e"
            title = "足球新鲜事"
            content = "中国足坛新鲜趣闻"
            bgName = "phoenix_special_bg_4"
        }else if(sender.tag == 2){
            uuid = "5f61cb0b-8d40-4449-9d25-cbcddde89a57"
            title = "欧冠情报"
            content = "最高水平赛事"
            bgName = "phoenix_special_bg_6"
        }else{
            uuid = "5c3a7159-70e5-490e-b242-328c2f5c3cc1"
            title = "绿茵英雄"
            content = "向历史传奇致敬"
            bgName = "phoenix_special_bg_0"
        }
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsSpecialViewController") as! SportsSpecialViewController
        vc.dataUuid = uuid
        vc.dataTitle = title
        vc.dataContent = content
        vc.bgName = bgName
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
