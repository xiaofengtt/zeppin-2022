import UIKit
class SportsMainViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UIScrollViewDelegate, SportsHomeSpecialDelegate{
    var headView : UIView!
    var tableView: UITableView!
    var category: String = CategoryUuid.YINGCHAO
    var newsList: Array<SportsNewsModel> = []
    var specialNewsList: Array<Array<SportsNewsModel>> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    let paddingLeft: CGFloat = 15 * screenScale
    let headCellHeight: CGFloat = 820 * screenScale
    let tableCellHeight: CGFloat = 110 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.white
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.height + statusBarHeight)))
        headView.backgroundColor = UIColor.white
        let mainHeaderView = SportsMainHeaderView(frame: CGRect(x: 0, y: (navigationFrame.height + statusBarHeight) - 40 * screenScale, width: screenWidth, height: 40 * screenScale), title: "首页")
        headView.addSubview(mainHeaderView)
        self.view.addSubview(headView)
        tableView = UITableView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 20 * screenScale, width: screenWidth, height:  self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height + 20 * screenScale)))
        tableView.backgroundColor = UIColor.colorBgGray()
        tableView.estimatedRowHeight = 0
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
        self.view.addSubview(tableView)
        getSpecialNewsList()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func changeCategory(_ uuid: String) {
        category = uuid
        pageNum = 1
        getNewsList()
    }
    func getSpecialNewsList(){
        let leagueArray = [CategoryUuid.YINGCHAO,CategoryUuid.XIJIA,CategoryUuid.OUGUAN]
        for _ in leagueArray{
            specialNewsList.append([])
        }
        for index in 0 ..< leagueArray.count {
            let uuid = leagueArray[index]
            let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
            let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": pageNum])
            newsDic.setValue(uuid, forKey: "category")
            newsDic.setValue("checktime desc", forKey: "sort")
            SportsHttpWorker.get("front/news/list", params: newsDic, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    if(datas.count > 0 ){
                        self.loadFlag = true
                        var news: [SportsNewsModel] = []
                        for i in 0 ..< datas.count{
                            let data = datas[i] as! NSDictionary
                            news.append(SportsNewsModel(data: data))
                        }
                        self.specialNewsList[index].append(contentsOf: news)
                    }
                    if(index == leagueArray.count - 1){
                        self.getNewsList()
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
            let vc = SportsMatchViewController()
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func toTopNews(_ sender: UIButton){
        if(newsList.count > 0){
            let vc = SportsNewsViewController()
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
            headCellView.backgroundColor = UIColor.white
            let bannerImageView = UIView(frame: CGRect(x: paddingLeft, y: 0, width: headCellView.frame.width - paddingLeft * 2, height: 140 * screenScale))
            let bannerImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: bannerImageView.frame.size))
            bannerImage.image = UIImage(named: "image_home_banner")
            bannerImage.layer.cornerRadius = 10 * screenScale
            bannerImage.layer.masksToBounds = true
            bannerImageView.addSubview(bannerImage)
            bannerImageView.layer.cornerRadius = 10 * screenScale
            bannerImageView.addBaseShadow()
            headCellView.addSubview(bannerImageView)
            
            let leagueTitleView = SportsMatchTitleView(frame: CGRect(x: 0, y: bannerImageView.frame.origin.y + bannerImageView.frame.height + 10 * screenScale, width: tableView.frame.width, height: 50 * screenScale), title: "热门赛事")
            headCellView.addSubview(leagueTitleView)
            let leagueView = UIView(frame: CGRect(x: 0, y: leagueTitleView.frame.origin.y + leagueTitleView.frame.height, width: tableView.frame.width, height: 180 * screenScale))
            for i in 0 ..< 6 {
                let params = league[i].components(separatedBy: "_")
                let name = params[0]
                let uuid = params[1]
                let rowNum = i / 3
                let columnNum = i % 3
                let leagueButtonView = SportsHomeLeagueButtonView(frame: CGRect(x: CGFloat(columnNum) * leagueView.frame.width/3, y: CGFloat(rowNum) * leagueView.frame.height/2, width: leagueView.frame.width/3, height: leagueView.frame.height/2))
                leagueButtonView.imageView.image = UIImage(named: "image_league_\(uuid)")
                leagueButtonView.titleLabel.text = name
                leagueButtonView.button.addTarget(self, action: #selector(enterLeague(_:)), for: UIControl.Event.touchUpInside)
                leagueButtonView.button.tag = i
                leagueView.addSubview(leagueButtonView)
            }
            headCellView.addSubview(leagueView)
            let specialTitleView = SportsMatchTitleView(frame: CGRect(x: 0, y: leagueView.frame.origin.y + leagueView.frame.height, width: tableView.frame.width, height: 40 * screenScale), title: "精选专题")
            headCellView.addSubview(specialTitleView)
            let specialScrollView = UIScrollView(frame: CGRect(x: 0, y: specialTitleView.frame.origin.y + specialTitleView.frame.height + 10 * screenScale, width: screenWidth, height: 340 * screenScale))
            specialScrollView.tag = SportsTagController.HomeTags.specialScrollView
            specialScrollView.isPagingEnabled = true
            specialScrollView.showsVerticalScrollIndicator = false
            specialScrollView.showsHorizontalScrollIndicator = false
            specialScrollView.contentSize = CGSize(width: specialScrollView.frame.width * 3, height: specialScrollView.frame.height)
            specialScrollView.delegate = self
            
            let firstView = SportsHomeSpecialView(frame: CGRect(x: paddingLeft, y: 0, width: specialScrollView.frame.width - paddingLeft * 2, height: specialScrollView.frame.height - 10 * screenScale), newsList: specialNewsList[0])
            firstView.buttonDelegate = self
            firstView.imageView.image = UIImage(named: "image_main_special_1")
            firstView.titleLabel.text = "#英超联赛"
            firstView.contentLabel.text = "风起云涌，群雄逐鹿"
            firstView.moreButton.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            firstView.moreButton.tag = 1
            firstView.addBaseShadow()
            specialScrollView.addSubview(firstView)
            
            let secondView = SportsHomeSpecialView(frame: CGRect(x: specialScrollView.frame.width + paddingLeft, y: firstView.frame.origin.y, width: firstView.frame.width, height: firstView.frame.height), newsList: specialNewsList[1])
            secondView.buttonDelegate = self
            secondView.imageView.image = UIImage(named: "image_main_special_2")
            secondView.titleLabel.text = "#西甲联赛"
            secondView.contentLabel.text = "领略巨星风采，彰显团队魅力"
            secondView.moreButton.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            secondView.moreButton.tag = 2
            secondView.addBaseShadow()
            specialScrollView.addSubview(secondView)
            
            let thirdView = SportsHomeSpecialView(frame: CGRect(x: specialScrollView.frame.width * 2 + paddingLeft, y: firstView.frame.origin.y, width: firstView.frame.width, height: firstView.frame.height), newsList: specialNewsList[2])
            thirdView.buttonDelegate = self
            thirdView.imageView.image = UIImage(named: "image_main_special_3")
            thirdView.titleLabel.text = "#欧冠赛场"
            thirdView.contentLabel.text = "各地诸侯并起，决战欧洲之巅"
            thirdView.moreButton.addTarget(self, action: #selector(enterSpecialDetial(_:)), for: UIControl.Event.touchUpInside)
            thirdView.moreButton.tag = 3
            thirdView.addBaseShadow()
            specialScrollView.addSubview(thirdView)
            headCellView.addSubview(specialScrollView)
            let specialPageControl = UIPageControl(frame: CGRect(x: (tableView.frame.width - 50 * screenScale)/2, y: specialScrollView.frame.origin.y + specialScrollView.frame.height, width: 50 * screenScale, height: 20 * screenScale))
            specialPageControl.tag = SportsTagController.HomeTags.specialPageControl
            specialPageControl.setValue(UIImage(named: "image_page_normal"), forKey: "_pageImage")
            specialPageControl.setValue(UIImage(named: "image_page_selected"), forKey: "_currentPageImage")
            specialPageControl.numberOfPages = 3
            specialPageControl.currentPage = 0
            headCellView.addSubview(specialPageControl)
            
            let newsTitleView = SportsMatchTitleView(frame: CGRect(x: 0, y: headCellView.frame.height - 20 * screenScale, width: tableView.frame.width, height: 20 * screenScale), title: "热点新闻")
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
                let vc = SportsNewsViewController()
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
            
            if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
                if(loadFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getNewsList()
                }
            }
        }
        if(scrollView.tag == SportsTagController.HomeTags.specialScrollView){
            let page = Int(scrollView.contentOffset.x/scrollView.frame.width)
            let pageControl = tableView.viewWithTag(SportsTagController.HomeTags.specialPageControl) as? UIPageControl
            pageControl?.currentPage = page
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                pageNum = 1
                getNewsList()
            }
        }
    }
    @objc func enterData(_ sender: UIButton){
        let vc = SportsStandingViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func enterSpecialDetial(_ sender: UIButton){
        var uuid : String!
        var title: String!
        var content: String!
        var bgName: String!
        if(sender.tag == 1){
            uuid = CategoryUuid.YINGCHAO
            title = "英超联赛"
            content = "风起云涌，群雄逐鹿"
            bgName = "image_main_special_1"
        }else if(sender.tag == 2){
            uuid = CategoryUuid.XIJIA
            title = "西甲联赛"
            content = "领略巨星风采，彰显团队魅力"
            bgName = "image_main_special_2"
        }else{
            uuid = CategoryUuid.OUGUAN
            title = "欧冠赛场"
            content = "各地诸侯并起，决战欧洲之巅"
            bgName = "image_main_special_3"
        }
        let vc = SportsSpecialViewController()
        vc.dataUuid = uuid
        vc.dataTitle = title
        vc.dataContent = content
        vc.bgName = bgName
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func enterLeague(_ sender: UIButton){
        var uuid: String!
        var titleName: String!
        if(sender.tag == 0){
            uuid = CategoryUuid.YINGCHAO
            titleName = "英超"
        }else if(sender.tag == 1){
            uuid = CategoryUuid.ZHONGCHAO
            titleName = "中超"
        }else if(sender.tag == 2){
            uuid = CategoryUuid.XIJIA
            titleName = "西甲"
        }else if(sender.tag == 3){
            uuid = CategoryUuid.YIJIA
            titleName = "意甲"
        }else if(sender.tag == 4){
            uuid = CategoryUuid.DEJIA
            titleName = "德甲"
        }else{
            uuid = CategoryUuid.OUGUAN
            titleName = "欧冠"
        }
        let vc = SportsStandingViewController()
        vc.category = uuid
        vc.titleName = titleName
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    func enterNews(_ uuid: String) {
        let vc = SportsNewsViewController()
        vc.uuid = uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
