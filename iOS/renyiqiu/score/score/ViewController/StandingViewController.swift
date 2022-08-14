//
//  StandingViewController.swift
//  ryqiu
//
//  Created by worker on 2019/5/28.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class StandingViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    var headView : UIView!
    var typeView : UIView!
    var tableView: UITableView!
    
    var category: String = CategoryUuid.CSL
    var type = "standing"
    var scoredList: Array<CategoryTopscoreModel> = []
    var standingList: Array<Array<CategoryStandingModel>> = []
    
    let tableCellHeight: CGFloat = 45 * screenScale
    let numWidth: CGFloat = 40 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.fontBlack()], for: UIControl.State.selected)
        self.view.backgroundColor = UIColor.backgroundGray()
        
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: abs(navigationFrame.origin.y)))
        headView.backgroundColor = UIColor.white
        let categoryView = CategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.height - 40 * screenScale, width: screenWidth - 10 * screenScale, height: 40 * screenScale), dataList: categoryArray)
        categoryView.buttonDelegate = self
        headView.addSubview(categoryView)
        self.view.addSubview(headView)
        
        typeView = UIView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 5 * screenScale, width: screenWidth, height: 50 * screenScale))
        typeView.backgroundColor = UIColor.white
        let typeButtonView = UIView(frame: CGRect(x: typeView.frame.width/10 * 3, y: 10 * screenScale, width: typeView.frame.width/5 * 2, height: 30 * screenScale))
        typeButtonView.layer.borderColor = UIColor.mainRed().cgColor
        typeButtonView.layer.borderWidth = 2 * screenScale
        let standingButton = UIButton(frame: CGRect(x: 0, y: 0, width: typeButtonView.frame.width/2, height: typeButtonView.frame.height))
        standingButton.tag = TagController.StandingTags.typeStandingButton
        standingButton.isSelected = true
        standingButton.setTitle("积分榜", for: UIControl.State.normal)
        standingButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        standingButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControl.State.normal)
        standingButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainRed()), for: UIControl.State.selected)
        standingButton.setTitleColor(UIColor.mainRed(), for: UIControl.State.normal)
        standingButton.setTitleColor(UIColor.white, for: UIControl.State.selected)
        standingButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        typeButtonView.addSubview(standingButton)
        let scoredButton = UIButton(frame: CGRect(x: typeButtonView.frame.width/2, y: standingButton.frame.origin.y, width: typeButtonView.frame.width/2, height: standingButton.frame.height))
        scoredButton.tag = TagController.StandingTags.typeScoredButton
        scoredButton.setTitle("射手榜", for: UIControl.State.normal)
        scoredButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        scoredButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControl.State.normal)
        scoredButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainRed()), for: UIControl.State.selected)
        scoredButton.setTitleColor(UIColor.mainRed(), for: UIControl.State.normal)
        scoredButton.setTitleColor(UIColor.white, for: UIControl.State.selected)
        scoredButton.addTarget(self, action: #selector(changeType(_:)), for: UIControl.Event.touchUpInside)
        typeButtonView.addSubview(scoredButton)
        typeView.addSubview(typeButtonView)
        self.view.addSubview(typeView)
        
        tableView = UITableView(frame: CGRect(x: 0, y: typeView.frame.origin.y + typeView.frame.height + 5 * screenScale, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (typeView.frame.origin.y + typeView.frame.height + 5 * screenScale)))
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
        let standingButton = typeView.viewWithTag(TagController.StandingTags.typeStandingButton) as! UIButton
        let scoredButton = typeView.viewWithTag(TagController.StandingTags.typeScoredButton) as! UIButton
        if(sender.tag == TagController.StandingTags.typeStandingButton){
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
        
        let loadingView = HttpController.showLoading(viewController: self)
        if(type == "standing"){
            HttpController.get("front/category/standingList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.standingList = []
                    var standings: [[CategoryStandingModel]] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        let standing = CategoryStandingModel(data: data)
                        if(standing.place == 1){
                            var stList : [CategoryStandingModel] = []
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
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            HttpController.get("front/category/topscoreList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.scoredList = []
                    var topscores: [CategoryTopscoreModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        topscores.append(CategoryTopscoreModel(data: data))
                    }
                    self.scoredList = topscores
                    
                    self.tableView.reloadData()
                    if(self.scoredList.count > 0){
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
            return 30 * screenScale
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(type == "standing" && section == standingList.count){
            return nil
        }
        
        let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 30 * screenScale))
        view.backgroundColor = UIColor.backgroundGray()
        
        if(type == "standing"){
            if(standingList.count == 1){
                let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: view.frame.height))
                placeLabel.text = "排名"
                placeLabel.textColor = UIColor.fontGray()
                placeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
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
            }
        }else{
            let nameLabel = UILabel(frame: CGRect(x: numWidth, y: 0, width: (view.frame.width - 2 * numWidth)/2 - 5 * screenScale, height: view.frame.height))
            nameLabel.text = "球员"
            nameLabel.textColor = UIColor.fontGray()
            nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
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
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(splitLine)
        
        return view
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(type == "standing"){
            if(indexPath.section == standingList.count){
                return Utils.getStandingFootHeight(category: category)
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
                let footerView = StandingFooterView(category: category)
                cellView.addSubview(footerView)
            }else{
                let data = standingList[indexPath.section][indexPath.row]
                
                if(data.uuid == ""){
                    let roundLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
                    roundLabel.text = data.round
                    roundLabel.textColor = UIColor.fontBlack()
                    roundLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
                    roundLabel.textAlignment = NSTextAlignment.center
                    cellView.addSubview(roundLabel)
                }else{
                    let placeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 14 * screenScale, width: 17 * screenScale, height: 17 * screenScale))
                    placeLabel.backgroundColor = Utils.getStandingColor(category: data.category, place: data.place)
                    placeLabel.text = "\(data.place)"
                    placeLabel.textColor = placeLabel.backgroundColor == UIColor.white ? UIColor.fontBlack() : UIColor.white
                    placeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                    placeLabel.textAlignment = NSTextAlignment.center
                    placeLabel.layer.cornerRadius = 2 * screenScale
                    placeLabel.layer.masksToBounds = true
                    cellView.addSubview(placeLabel)
                    
                    let scoreLabel = UILabel(frame: CGRect(x: screenWidth - numWidth, y: 0, width: numWidth, height: cellView.frame.height))
                    scoreLabel.text = "\(data.point)"
                    scoreLabel.textColor = UIColor.fontBlack()
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
                    teamLabel.textColor = scoreLabel.textColor
                    teamLabel.font = scoreLabel.font
                    teamLabel.numberOfLines = 2
                    cellView.addSubview(teamLabel)
                    
                    let splitLine = CALayer()
                    splitLine.frame = CGRect(x: 0, y: cellView.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
                    splitLine.backgroundColor = UIColor.backgroundGray().cgColor
                    cellView.layer.addSublayer(splitLine)
                }
            }
        }else{
            let data = scoredList[indexPath.row]
            
            let placeLabel = UILabel(frame: CGRect(x: 0, y: 0, width: numWidth, height: cellView.frame.height))
            placeLabel.text = "\(data.place)"
            placeLabel.textColor = UIColor.fontDarkGray()
            placeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
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
            splitLine.backgroundColor = UIColor.backgroundGray().cgColor
            cellView.layer.addSublayer(splitLine)
            
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
