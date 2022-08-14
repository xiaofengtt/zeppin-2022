import Foundation
class SportsSpecialViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    var headView: SportsNavigationBackground!
    var topView: UIView!
    var tableView: UITableView!
    var dataUuid: String = ""
    var dataTitle: String = ""
    var dataContent: String = ""
    var bgName: String = ""
    var newsList: Array<SportsNewsModel> = []
    var loadFlag: Bool = true
    var pageNum: Int = 1
    var nomoreFlag = false
    let tableCellHeight: CGFloat = 110 * screenScale
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "专题详情"
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        createTopView()
        createTableView()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func createTopView(){
        topView = UIView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 180 * screenScale))
        topView.layer.masksToBounds = true
        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: topView.frame.size))
        bgImageView.image = UIImage(named: bgName)
        topView.addSubview(bgImageView)
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 50 * screenScale, width: topView.frame.width, height: 30 * screenScale))
        titleLabel.text = dataTitle
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 2) * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(titleLabel)
        let contentLabel = UILabel(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 5 * screenScale, width: topView.frame.width, height: 30 * screenScale))
        contentLabel.text = dataContent
        contentLabel.textColor = titleLabel.textColor
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(contentLabel)
        let circleView = UIView(frame: CGRect(x: 0, y: topView.frame.height - 10 * screenScale, width: topView.frame.width, height: 20 * screenScale))
        circleView.backgroundColor = UIColor.white
        circleView.layer.masksToBounds = true
        circleView.layer.cornerRadius = circleView.frame.height/2
        topView.addSubview(circleView)
        self.view.addSubview(topView)
    }
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height, width: screenWidth, height: screenHeight - (topView.frame.origin.y + topView.frame.height)))
        tableView.estimatedRowHeight = 0
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
        self.view.addSubview(tableView)
        getNewsList()
    }
    func getNewsList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": pageNum])
        if(dataUuid != ""){
            newsDic.setValue(dataUuid, forKey: "category")
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
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == newsList.count){
            return 50 * screenScale
        }else{
            return tableCellHeight
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return newsList.count + 1
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.white
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == newsList.count){
            let cellView = SportsNoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "没有更多消息了", flagLine: true)
            cell.addSubview(cellView)
        }else{
            let data = newsList[indexPath.row]
            let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight), news: data)
            cell.addSubview(cellView)
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(indexPath.row < newsList.count){
            let data = newsList[indexPath.row]
            let vc = SportsNewsViewController()
            vc.uuid = data.uuid
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
            if(loadFlag && !nomoreFlag){
                loadFlag = false
                pageNum = pageNum + 1
                getNewsList()
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                pageNum = 1
                nomoreFlag = false
                getNewsList()
            }
        }
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
