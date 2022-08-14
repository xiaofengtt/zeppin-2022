import UIKit
class SportsMainViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate{
    var headView : UIView!
    var tableView: UITableView!
    var category: String = ""
    var newsList: Array<SportsNewsModel> = []
    var matchList: Array<SportsMatchModel> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    let paddingLeft: CGFloat = 15 * screenScale
    let tableHeaderHeight: CGFloat = 45 * screenScale
    let matchScrollViewHeight: CGFloat = 120 * screenScale
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
        self.navigationController?.navigationBar.isUserInteractionEnabled = false
        self.view.backgroundColor = UIColor.colorBgGray()
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: abs(navigationFrame.origin.y) + 200 * screenScale))
        headView.backgroundColor = UIColor.white
        let headBgView = UIImageView(frame: CGRect(x: 0, y: 0, width: headView.frame.width, height: 170 * screenScale))
        headBgView.image = UIImage(named: "image_banner")
        headView.addSubview(headBgView)
        let topNewsView = SportsTopNewsView(frame: CGRect(x: 15 * screenScale, y: abs(navigationFrame.origin.y) + 105 * screenScale, width: headBgView.frame.width - 15 * 2 * screenScale, height: 80 * screenScale))
        topNewsView.tag = SportsNumberController.HomeNumbers.topNewsView
        topNewsView.layer.cornerRadius = 8 * screenScale
        topNewsView.layer.shadowPath = CGPath(rect: CGRect.init(x: 8 * screenScale, y: 8 * screenScale, width: topNewsView.frame.width - 16 * screenScale, height: topNewsView.frame.height - 16 * screenScale), transform: nil)
        topNewsView.layer.shadowColor = UIColor.black.cgColor
        topNewsView.layer.shadowOpacity = 0.5
        topNewsView.layer.shadowOffset = CGSize(width: 0, height: 4 * screenScale)
        topNewsView.layer.shadowRadius = 8 * screenScale
        let topNewsButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: topNewsView.frame.size))
        topNewsButton.addTarget(self, action: #selector(toTopNews(_:)), for: UIControl.Event.touchUpInside)
        topNewsView.addSubview(topNewsButton)
        headView.addSubview(topNewsView)
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
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
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
                self.updateTopNews()
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
    func updateTopNews(){
        let topNewsView = headView.viewWithTag(SportsNumberController.HomeNumbers.topNewsView) as! SportsTopNewsView
        if(newsList.count > 0){
            let news = newsList[0]
            SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + news.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                if(SDImage != nil){
                    topNewsView.imageView.image = SDImage
                }
            }
            topNewsView.label.text = news.title
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
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return tableHeaderHeight
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == 0){
            return matchScrollViewHeight
        }else if(indexPath.row == newsList.count){
            return 50 * screenScale
        }else{
            return tableCellHeight
        }
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableHeaderHeight))
        view.backgroundColor = UIColor.white
        var array = league.copy()
        array.insert("头条_", at: 0)
        let categoryView = SportsCategoryScrollView(frame: CGRect(x: 5 * screenScale, y: 0, width: screenWidth - 10 * screenScale, height: view.frame.height), dataList: array, selected: category)
        categoryView.buttonDelegate = self
        view.addSubview(categoryView)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: view.frame.height - 1 * screenScale, width: view.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        view.layer.addSublayer(splitLine)
        return view
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.white
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == 0){
            let matchScrollView = UIScrollView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: matchScrollViewHeight))
            matchScrollView.backgroundColor = UIColor.colorBgGray()
            matchScrollView.showsVerticalScrollIndicator = false
            matchScrollView.showsHorizontalScrollIndicator = false
            for i in 0 ..< matchList.count{
                let match = matchList[i]
                let matchButton = SportsMainMatchButton(frame: CGRect(x: paddingLeft + (paddingLeft + (matchScrollView.frame.height - 20 * screenScale)/5*9) * CGFloat(i), y: 10 * screenScale, width: (matchScrollView.frame.height - 20 * screenScale)/5*9, height: matchScrollView.frame.height - 20 * screenScale),match: match)
                matchButton.addTarget(self, action: #selector(toMatch(_:)), for: UIControl.Event.touchUpInside)
                matchScrollView.addSubview(matchButton)
            }
            matchScrollView.contentSize = CGSize(width: paddingLeft + (paddingLeft + (matchScrollView.frame.height - 20 * screenScale)/5*9) * CGFloat(matchList.count), height: matchScrollView.frame.height)
            matchScrollView.contentOffset = CGPoint(x: matchScrollView.contentSize.width/2 - (paddingLeft + (matchScrollView.frame.height - 20 * screenScale)/5*9), y: 0)
            cell.addSubview(matchScrollView)
        }else if(indexPath.row == newsList.count){
            let cellView = SportsNoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "没有更多新闻了", flagLine: true)
            cell.addSubview(cellView)
        }else{
            let data = newsList[indexPath.row]
            let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight), news: data)
            cell.addSubview(cellView)
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(indexPath.row > 0 && indexPath.row <= newsList.count - 1){
            let data = newsList[indexPath.row]
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsNewsViewController") as! SportsNewsViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
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
}
