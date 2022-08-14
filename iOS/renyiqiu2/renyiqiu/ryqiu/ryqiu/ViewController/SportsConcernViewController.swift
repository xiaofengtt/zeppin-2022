import Foundation
class SportsConcrenViewController: UIViewController, UIScrollViewDelegate, LeftCategoryScrollViewDelegate{
    var headView: SportsNavigationBackground!
    var leftScrollView: SportsLeftCategoryScrollView!
    var rightScrollView: UIScrollView!
    var category: String = ""
    var teamList: Array<SportsTeamModel> = []
    let cellHeight: CGFloat = 60 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "关注"
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        var array = league.copy()
        array.insert("已关注_", at: 0)
        leftScrollView = SportsLeftCategoryScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: 80 * screenScale, height: self.view.frame.height - (headView.frame.origin.y + headView.frame.height)), dataList: array)
        leftScrollView.buttonDelegate = self
        self.view.addSubview(leftScrollView)
        rightScrollView = UIScrollView(frame: CGRect(x: leftScrollView.frame.width, y: leftScrollView.frame.origin.y, width: screenWidth - leftScrollView.frame.width, height: leftScrollView.frame.height))
        rightScrollView.backgroundColor = UIColor.white
        self.view.addSubview(rightScrollView)
        getList()
    }
    func getList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        if(category != ""){
            SportsHttpWorker.get("front/category/teamList", params: NSDictionary(dictionary: ["category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.teamList = []
                    var teams: [SportsTeamModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        teams.append(SportsTeamModel(data: data))
                    }
                    self.teamList = teams
                    self.rightScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: self.rightScrollView.frame.size), animated: false)
                    self.getUserConcrenList()
                }else{
                    SportsHttpWorker.showTimeout(viewController: self)
                }
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }) { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            }
        }else{
            getUserConcrenList()
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }
    }
    func getUserConcrenList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary()
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        SportsHttpWorker.getPrivate(data: { (token) in
            dictionary.setValue(token, forKey: "token")
            SportsHttpWorker.get("front/concren/list", params: dictionary, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    var concrenList: [UserConcrenModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        concrenList.append(UserConcrenModel(data: data))
                    }
                    if(self.category != ""){
                        for team in self.teamList{
                            for concren in concrenList {
                                if(team.uuid == concren.team){
                                    team.isConcren = true
                                    team.concren = concren.uuid
                                }
                            }
                        }
                    }else{
                        self.teamList = []
                        var teams: Array<SportsTeamModel> = []
                        for concren in concrenList{
                            let team = SportsTeamModel()
                            team.uuid = concren.team
                            team.name = concren.teamName
                            team.iconUrl = concren.teamIconUrl
                            team.isConcren = true
                            team.concren = concren.uuid
                            teams.append(team)
                        }
                        self.teamList = teams
                    }
                    self.updateRightScrollView()
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
    func updateRightScrollView(){
        rightScrollView.removeFromSuperview()
        rightScrollView = UIScrollView(frame: rightScrollView.frame)
        rightScrollView.delegate = self
        rightScrollView.backgroundColor = UIColor.white
        rightScrollView.showsVerticalScrollIndicator = false
        rightScrollView.showsHorizontalScrollIndicator = false
        rightScrollView.addRefreshView()
        if(teamList.count > 0){
            for index in 0 ..< teamList.count{
                let cellButton = SportsTeamCellButton(frame: CGRect(x: 0, y: CGFloat(index) * cellHeight, width: rightScrollView.frame.width, height: cellHeight), team: teamList[index])
                cellButton.addTarget(self, action: #selector(setConcren(_:)), for: UIControl.Event.touchUpInside)
                rightScrollView.addSubview(cellButton)
            }
            if(CGFloat(teamList.count) * cellHeight > rightScrollView.frame.height){
                rightScrollView.contentSize = CGSize(width: rightScrollView.frame.width, height: CGFloat(teamList.count) * cellHeight)
            }else{
                rightScrollView.contentSize = CGSize(width: rightScrollView.frame.width, height: rightScrollView.frame.height + 1)
            }
        }else{
            let emptyIcon = UIImageView(frame: CGRect(x: rightScrollView.frame.width/4, y: rightScrollView.frame.height * 0.3, width: rightScrollView.frame.width/2, height: rightScrollView.frame.width/2 * 0.65))
            emptyIcon.image = UIImage(named: "image_nodata_match")
            rightScrollView.addSubview(emptyIcon)
            let emptyLabel = UILabel(frame: CGRect(x: 0, y: emptyIcon.frame.origin.y + emptyIcon.frame.height + 20 * screenScale, width: rightScrollView.frame.width, height: 20 * screenScale))
            emptyLabel.text = "暂无关注的球队"
            emptyLabel.textColor = UIColor.colorFontGray()
            emptyLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
            emptyLabel.textAlignment = NSTextAlignment.center
            rightScrollView.addSubview(emptyLabel)
        }
        self.view.addSubview(rightScrollView)
    }
    @objc func setConcren(_ sender: SportsTeamCellButton){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        if(!sender.team.isConcren){
            SportsHttpWorker.getPrivate(data: { (token) in
                SportsHttpWorker.get("front/concren/add", params: NSDictionary(dictionary: ["team": sender.team.uuid, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let concren = dataDictionary.object(forKey: "data") as! NSDictionary
                        sender.setConcren(concren: concren.object(forKey: "uuid") as! String)
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
                SportsHttpWorker.get("front/concren/cancel", params: NSDictionary(dictionary: ["userConcern": sender.team.concren, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        sender.cancelConcren()
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
    func changeCategory(_ uuid: String) {
        category = uuid
        getList()
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == rightScrollView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                rightScrollView.refreshViewToAble()
            }else{
                rightScrollView.refreshViewToNormal()
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == rightScrollView){
            if(rightScrollView.refreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
