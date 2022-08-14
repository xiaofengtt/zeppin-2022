//
//  StandingViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/28.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class StandingViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    var headView : NavigationBackground!
    var categoryView: CategoryScrollView!
    var tableView: UITableView!
    
    var category: String = CategoryUuid.CSL
    var dataList: Array<CategoryStandingModel> = []
    
    let tableCellHeight: CGFloat = 45 * screenScale
    let numWidth: CGFloat = 40 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.fontBlack()], for: UIControl.State.selected)
        
        self.view.backgroundColor = UIColor.backgroundGray()
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(headView)
        
        categoryView = CategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.origin.y + headView.frame.height, width: screenWidth - 10 * screenScale, height: 35 * screenScale), dataList: categoryArray)
        categoryView.buttonDelegate = self
        self.view.addSubview(categoryView)
        
        tableView = UITableView(frame: CGRect(x: 0, y: categoryView.frame.origin.y + categoryView.frame.height + 5 * screenScale, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (categoryView.frame.origin.y + categoryView.frame.height + 5 * screenScale)))
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
    
    func getList(){
        if(category == ""){
            return
        }
        
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/category/standingList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.dataList = []
                var standings: [CategoryStandingModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    let standing = CategoryStandingModel(data: data)
                    if(standing.category == CategoryUuid.AFCCL || standing.category == CategoryUuid.UCL){
                        if(standing.place == 1){
                            let group = CategoryStandingModel()
                            group.round = standing.round
                            standings.append(group)
                        }
                    }
                    standings.append(standing)
                }
                self.dataList = standings
                
                self.tableView.reloadData()
                if(self.dataList.count > 0){
                    self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                }
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList.count + 1
        }
        return dataList.count
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return tableCellHeight
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        view.backgroundColor = UIColor.white
        
        let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: view.frame.height))
        placeLabel.text = "排名"
        placeLabel.textColor = UIColor.fontGray()
        placeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
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
        
        let teamLabel = UILabel(frame: CGRect(x: placeLabel.frame.origin.x + placeLabel.frame.width, y: 0, width: playedLabel.frame.origin.x - (placeLabel.frame.origin.x + placeLabel.frame.width) - 5 * screenScale, height: view.frame.height))
        teamLabel.text = "   球队"
        teamLabel.textColor = placeLabel.textColor
        teamLabel.font = placeLabel.font
        view.addSubview(teamLabel)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: view.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(splitLine)
        
        return view
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == dataList.count){
            return Utils.getStandingFootHeight(category: category)
        }else{
            return tableCellHeight
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        let cellView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        if(indexPath.row == dataList.count){
            let footerView = StandingFooterView(category: category)
            cellView.addSubview(footerView)
        }else{
            let data = dataList[indexPath.row]
            
            if(data.uuid == ""){
                let roundLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
                roundLabel.text = data.round
                roundLabel.textColor = UIColor.fontBlack()
                roundLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
                roundLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(roundLabel)
            }else{
                let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: cellView.frame.height))
                placeLabel.text = "\(data.place)"
                placeLabel.textColor = Utils.getStandingColor(category: data.category, place: data.place)
                placeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                placeLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(placeLabel)
                
                let scoreLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                scoreLabel.text = "\(data.point)"
                scoreLabel.textColor = placeLabel.textColor
                scoreLabel.font = placeLabel.font
                scoreLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(scoreLabel)
                
                let goalLable = UILabel(frame: CGRect(x: scoreLabel.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: cellView.frame.height))
                goalLable.text = "\(data.scored)/\(data.against)"
                goalLable.textColor = placeLabel.textColor
                goalLable.font = placeLabel.font
                goalLable.textAlignment = NSTextAlignment.center
                cellView.addSubview(goalLable)
                
                let wonLabel = UILabel(frame: CGRect(x: goalLable.frame.origin.x - numWidth * 1.5, y: 0, width: numWidth * 1.5, height: cellView.frame.height))
                wonLabel.text = "\(data.won)/\(data.drawn)/\(data.lost)"
                wonLabel.textColor = placeLabel.textColor
                wonLabel.font = placeLabel.font
                wonLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(wonLabel)
                
                let playedLabel = UILabel(frame: CGRect(x: wonLabel.frame.origin.x - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                playedLabel.text = "\(data.played)"
                playedLabel.textColor = placeLabel.textColor
                playedLabel.font = placeLabel.font
                playedLabel.textAlignment = NSTextAlignment.center
                cellView.addSubview(playedLabel)
                
                let teamIcon = UIImageView(frame: CGRect(x: placeLabel.frame.origin.x + placeLabel.frame.width, y: cellView.frame.height/4, width: cellView.frame.height/2, height: cellView.frame.height/2))
                SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + data.teamIconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
                    if(SDImage != nil){
                        teamIcon.image = SDImage
                    }else{
                        teamIcon.image = UIImage(named: "common_team_default")
                    }
                }
                cellView.addSubview(teamIcon)
                
                let teamLabel = UILabel(frame: CGRect(x: teamIcon.frame.origin.x + teamIcon.frame.width + 5 * screenScale, y: 0, width: playedLabel.frame.origin.x - (teamIcon.frame.origin.x + teamIcon.frame.width) - 8 * screenScale, height: cellView.frame.height))
                teamLabel.text = "\(data.teamName)"
                teamLabel.textColor = placeLabel.textColor
                teamLabel.font = placeLabel.font
                teamLabel.numberOfLines = 2
                cellView.addSubview(teamLabel)
                
                let splitLine = CALayer()
                splitLine.frame = CGRect(x: 0, y: cellView.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
                splitLine.backgroundColor = UIColor.backgroundGray().cgColor
                cellView.layer.addSublayer(splitLine)
            }
        }
        cell.addSubview(cellView)
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                tableView.refreshToAble()
            }else{
                tableView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
