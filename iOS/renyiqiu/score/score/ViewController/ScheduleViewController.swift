//
//  ScheduleViewController.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class ScheduleViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {
    
    var headView: UIView!
    var tableView: UITableView!
    var todayView: UIView!
    
    var category: String = CategoryUuid.CSL
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
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.fontBlack()], for: UIControl.State.selected)
        
        self.view.backgroundColor = UIColor.backgroundGray()
        
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: abs(navigationFrame.origin.y)))
        headView.backgroundColor = UIColor.white
        var array = categoryArray.copy()
        array.insert("关注_", at: 0)
        let categoryView = CategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.height - 40 * screenScale, width: screenWidth - 10 * screenScale, height: 40 * screenScale), dataList: array)
        categoryView.buttonDelegate = self
        headView.addSubview(categoryView)
        self.view.addSubview(headView)
        
        tableView = UITableView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 5 * screenScale, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height + 5 * screenScale)))
        tableView.backgroundColor = UIColor.backgroundGray()
        tableView.estimatedRowHeight = 0
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        self.view.addSubview(tableView)
        
        todayView = UIView(frame: CGRect(x: screenWidth - 70 * screenScale, y: self.tabBarController!.tabBar.frame.origin.y - 80 * screenScale, width: 50 * screenScale, height: 70 * screenScale))
        todayView.backgroundColor = UIColor.clear
        todayView.layer.zPosition = 1.5
        let todayButton = UIButton(frame: CGRect(x: 0, y: 0, width: 50 * screenScale, height: 50 * screenScale))
        todayButton.setImage(UIImage(named: "match_to_today"), for: UIControl.State.normal)
        todayButton.layer.cornerRadius = todayButton.frame.height/2
        todayButton.addTarget(self, action: #selector(toToday(_:)), for: UIControl.Event.touchUpInside)
        todayView.addSubview(todayButton)
        let todayLabel = UILabel(frame: CGRect(x: 0, y: todayButton.frame.origin.y + todayButton.frame.height, width: todayView.frame.width, height: todayView.frame.height - (todayButton.frame.origin.y + todayButton.frame.height)))
        todayLabel.text = "未开赛"
        todayLabel.textColor = UIColor.fontGray()
        todayLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        todayLabel.textAlignment = NSTextAlignment.center
        todayView.addSubview(todayLabel)
        self.view.addSubview(todayView)
        loadFlag = false
        getList(first: true)
    }
    
    func getList(first: Bool){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["starttime": Utils.timestampFormat(timestamp: timestamp, format: "yyyy-MM-dd HH:mm:ss"),"pageSize": 20, "pageNum": pageNum,"sort": "time"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        HttpController.get("front/match/list", params: dictionary, data: { (data) in
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
                    let match = MatchModel(data: data)
                    
                    let dateStr = Utils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
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
                        date.weekday = Utils.getWeekDay(timestamp: match.time)
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
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlagCancle()
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getHistoryList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["endtime": Utils.timestampFormat(timestamp: timestamp, format: "yyyy-MM-dd HH:mm:ss") ,"pageSize": 20, "pageNum": historyPageNum,"sort": "time desc"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        
        HttpController.get("front/match/list", params: dictionary, data: { (data) in
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
                    let match = MatchModel(data: data)
                    
                    let dateStr = Utils.timestampFormat(timestamp: match.time, format: "yyyy-MM-dd")
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
                        date.weekday = Utils.getWeekDay(timestamp: match.time)
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
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            self.loadFlagCancle()
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
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
        view.backgroundColor = UIColor.backgroundGray()
        
        let label = UILabel(frame: CGRect(origin: CGPoint(x: paddingLeft, y: 5 * screenScale), size: CGSize(width: view.frame.width - paddingLeft * 2, height: UIFont.middleSize() * screenScale)))
        label.text = "\(date.date) \(date.weekday)"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
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
        
        let cellView = MatchCellView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: tableCellHeight)), match: data)
        cell.addSubview(cellView)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let data = dateList[indexPath.section].matchList[indexPath.row]
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "MatchViewController") as! MatchViewController
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
