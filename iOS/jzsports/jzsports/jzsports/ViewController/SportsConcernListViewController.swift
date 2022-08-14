import Foundation
class SportsConcrenListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    var headView: SportsNavigationBackground!
    var tableView: UITableView!
    var teamList: Array<SportsTeamModel> = []
    let cellHeight: CGFloat = 55 * screenScale
    var flagManage: Bool = false
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "我的关注"
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitle("管理", for: UIControl.State.normal)
        headView.rightButton.setTitleColor(UIColor.colorFontBlack(), for: UIControl.State.normal)
        headView.rightButton.titleLabel?.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        headView.rightButton.addTarget(self, action: #selector(manageConcern), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        tableView = UITableView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - (headView.frame.origin.y + headView.frame.height)))
        tableView.backgroundColor = UIColor.white
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
        self.view.addSubview(tableView)
    }
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    override func viewWillDisappear(_ animated: Bool) {
        headView.rightButton.setTitle("管理", for: UIControl.State.normal)
        flagManage = false
        tableView.reloadData()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func getList(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary()
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
                    self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    self.tableView.reloadData()
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
    func setConcren(index: Int){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        if(!self.teamList[index].isConcren){
            SportsHttpWorker.getPrivate(data: { (token) in
                SportsHttpWorker.get("front/concren/add", params: NSDictionary(dictionary: ["team": self.teamList[index].uuid, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let concren = dataDictionary.object(forKey: "data") as! NSDictionary
                        self.teamList[index].concren = concren.object(forKey: "uuid") as! String
                        self.teamList[index].isConcren = true
                        self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                        self.tableView.reloadData()
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
                SportsHttpWorker.get("front/concren/cancel", params: NSDictionary(dictionary: ["userConcern": self.teamList[index].concren, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        self.teamList[index].isConcren = false
                        self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                        self.tableView.reloadData()
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
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(teamList.count > 0){
            return teamList.count + 1
        }else{
            return 2
        }
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(teamList.count > 0 ){
            if(indexPath.row == teamList.count){
                return 100 * screenScale
            }else{
                return cellHeight
            }
        }else{
            if(indexPath.row == 0){
                return 300 * screenScale
            }else{
                return 100 * screenScale
            }
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(teamList.count > 0 ){
            if(indexPath.row == teamList.count){
                let addMoreView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 100 * screenScale))
                let addMoreButton = UIButton(frame: CGRect(x: 30 * screenScale, y: 55 * screenScale, width: addMoreView.frame.width - 60 * screenScale, height: 45 * screenScale))
                addMoreButton.layer.masksToBounds = true
                addMoreButton.layer.cornerRadius = addMoreButton.frame.height / 2
                addMoreButton.layer.borderWidth = 1 * screenScale
                addMoreButton.layer.borderColor = UIColor.colorMainColor().cgColor
                addMoreButton.setTitle("＋ 添加更多关注", for: UIControl.State.normal)
                addMoreButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
                addMoreButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                addMoreButton.addTarget(self, action: #selector(enterConcern(_:)), for: UIControl.Event.touchUpInside)
                addMoreView.addSubview(addMoreButton)
                cell.addSubview(addMoreButton)
            }else{
                let team = teamList[indexPath.row]
                let concernCellView = SportsConcernCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: cellHeight), team: team)
                if(!flagManage){
                    concernCellView.selectedImageView.isHidden = true
                }
                cell.addSubview(concernCellView)
            }
        }else{
            if(indexPath.row == 0){
                let nodataView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 300 * screenScale))
                let nodataImageView = UIImageView(frame: CGRect(x: nodataView.frame.width/4, y: 100 * screenScale, width: nodataView.frame.width/2, height: nodataView.frame.width/3))
                nodataImageView.image = UIImage(named: "image_nodata_concern")
                nodataView.addSubview(nodataImageView)
                let nodataLabel = UILabel(frame: CGRect(x: 0, y: nodataImageView.frame.origin.y + nodataImageView.frame.height + 20 * screenScale, width: nodataView.frame.width, height: 20 * screenScale))
                nodataLabel.text = "暂无关注"
                nodataLabel.textColor = UIColor.colorFontGray()
                nodataLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
                nodataLabel.textAlignment = NSTextAlignment.center
                nodataView.addSubview(nodataLabel)
                cell.addSubview(nodataView)
            }else{
                let addMoreView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 100 * screenScale))
                let addMoreButton = UIButton(frame: CGRect(x: 30 * screenScale, y: 55 * screenScale, width: addMoreView.frame.width - 60 * screenScale, height: 45 * screenScale))
                addMoreButton.layer.masksToBounds = true
                addMoreButton.layer.cornerRadius = addMoreButton.frame.height / 2
                addMoreButton.layer.borderWidth = 1 * screenScale
                addMoreButton.layer.borderColor = UIColor.colorMainColor().cgColor
                addMoreButton.setTitle("＋ 添加更多关注", for: UIControl.State.normal)
                addMoreButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
                addMoreButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                addMoreButton.addTarget(self, action: #selector(enterConcern(_:)), for: UIControl.Event.touchUpInside)
                addMoreView.addSubview(addMoreButton)
                cell.addSubview(addMoreButton)
            }
        }
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(flagManage){
            if(teamList.count > 0 && indexPath.row < teamList.count){
                setConcren(index: indexPath.row)
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
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView()?.status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
    @objc func manageConcern(){
        if(!flagManage){
            headView.rightButton.setTitle("完成", for: UIControl.State.normal)
            tableView.reloadData()
        }else{
            headView.rightButton.setTitle("管理", for: UIControl.State.normal)
            getList()
        }
        flagManage = !flagManage
    }
    @objc func enterConcern(_ sender: UIButton){
        let vc = SportsConcrenViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
