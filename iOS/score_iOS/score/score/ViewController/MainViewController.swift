//
//  ViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/22.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import UIKit

class MainViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate{
    
    var headView : NavigationBackground!
    var categoryView: CategoryScrollView!
    var tableView: UITableView!
    
    var category: String = ""
    var newsList: Array<NewsModel> = []
    var matchList: Array<MatchModel> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    
    let paddingLeft: CGFloat = 10 * screenScale
    let tableHeaderHeight: CGFloat = 280 * screenScale
    let tableCellHeight: CGFloat = 110 * screenScale
    
    override func viewDidLoad() {
        showAdView()
        super.viewDidLoad()
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.fontBlack()], for: UIControl.State.selected)
        
        self.view.backgroundColor = UIColor.backgroundGray()
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(headView)
        
        var array = categoryArray.copy()
        array.insert("头条_", at: 0)
        categoryView = CategoryScrollView(frame: CGRect(x: 5 * screenScale, y: headView.frame.origin.y + headView.frame.height, width: screenWidth - 10 * screenScale, height: 35 * screenScale), dataList: array)
        categoryView.buttonDelegate = self
        self.view.addSubview(categoryView)
        
        tableView = UITableView(frame: CGRect(x: 0, y: categoryView.frame.origin.y + categoryView.frame.height + 5 * screenScale, width: screenWidth, height:  self.tabBarController!.tabBar.frame.origin.y - (categoryView.frame.origin.y + categoryView.frame.height + 5 * screenScale)))
        tableView.backgroundColor = UIColor.backgroundGray()
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
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary(dictionary: ["starttime": Utils.timestampFormat(timestamp: Date().timestamp - 1000 * 60 * 60 * 3, format: "yyyy-MM-dd HH:mm:ss"),"pageSize": 2, "pageNum": pageNum,"sort": "time"])
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        
        HttpController.get("front/match/list", params: dictionary, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                
                self.matchList = []
                var matches: [MatchModel] = []
                
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    matches.append(MatchModel(data: data))
                }
                self.matchList = matches
                
                if(datas.count < 2){
                    dictionary.setValue("endtime", forKey: dictionary.value(forKey: "starttime") as! String)
                    dictionary.removeObject(forKey: "starttime")
                    dictionary.setValue("time desc", forKey: "sort")
                    HttpController.get("front/match/list", params: dictionary, data: { (data) in
                        let dataDic = data as! NSDictionary
                        let stat = dataDic.object(forKey: "status") as! String
                        if stat == "SUCCESS" {
                            let datas1 = dataDic.object(forKey: "data") as! NSArray
                            for i in 0 ..< datas1.count{
                                let data = datas1[i] as! NSDictionary
                                self.matchList.append(MatchModel(data: data))
                            }
                            self.getNewsList()
                            if(self.matchList.count > 0){
                                self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                            }
                            HttpController.hideLoading(loadingView: loadingView)
                        }else{
                            HttpController.showTimeout(viewController: self)
                        }
                    }) { (error) in
                        HttpController.hideLoading(loadingView: loadingView)
                        HttpController.showTimeout(viewController: self)
                    }
                }else{
                    self.getNewsList()
                    self.tableView.scrollToRow(at: IndexPath(item: 0, section: 0), at: UITableView.ScrollPosition.top, animated: false)
                    HttpController.hideLoading(loadingView: loadingView)
                }
            }else{
                HttpController.showTimeout(viewController: self)
                HttpController.hideLoading(loadingView: loadingView)
            }
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getNewsList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let newsDic: NSMutableDictionary = NSMutableDictionary(dictionary: ["pageSize": 10, "pageNum": pageNum])
        if(category != ""){
            newsDic.setValue(category, forKey: "category")
        }
        newsDic.setValue("checktime desc", forKey: "sort")
        
        HttpController.get("front/news/list", params: newsDic, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.pageNum == 1){
                    self.newsList = []
                }
                
                 let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    self.loadFlag = true
                    var news: [NewsModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        news.append(NewsModel(data: data))
                    }
                    self.newsList.append(contentsOf: news)
                }
                self.tableView.reloadData()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    @objc func toMatch(_ sender: MainMatchButton){
        let data = sender.match!
        
        if(data.uuid != ""){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "MatchViewController") as! MatchViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return newsList.count + 2
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == 0){
            return tableHeaderHeight
        }else if(indexPath.row == newsList.count + 1){
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
            let headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableHeaderHeight))
            headerView.backgroundColor = UIColor.backgroundGray()
            
            let headImageView = UIImageView(frame: CGRect(x: paddingLeft, y: 0, width: screenWidth - paddingLeft * 2, height: 160 * screenScale))
            headImageView.image = UIImage(named: "main_header_banner")
            headerView.addSubview(headImageView)
            
            let leftMatchButton = MainMatchButton(frame: CGRect(x: paddingLeft, y: headImageView.frame.origin.y + headImageView.frame.height + 15 * screenScale, width: (screenWidth - paddingLeft * 3) / 2, height: headerView.frame.height - (headImageView.frame.origin.y + headImageView.frame.height + 15 * screenScale * 2)),match: matchList.count > 0 ? matchList[0] : MatchModel())
            leftMatchButton.addTarget(self, action: #selector(toMatch(_:)), for: UIControl.Event.touchUpInside)
            headerView.addSubview(leftMatchButton)
            
            let rightMatchButton = MainMatchButton(frame: CGRect(x: leftMatchButton.frame.origin.x + leftMatchButton.frame.width + paddingLeft, y: leftMatchButton.frame.origin.y, width: leftMatchButton.frame.width, height: leftMatchButton.frame.height),match: matchList.count > 1 ? matchList[1] : MatchModel())
            rightMatchButton.addTarget(self, action: #selector(toMatch(_:)), for: UIControl.Event.touchUpInside)
            headerView.addSubview(rightMatchButton)
            cell.addSubview(headerView)
        }else if(indexPath.row == newsList.count + 1){
            let cellView = NoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "好像已经到底了", flagLine: true)
            cell.addSubview(cellView)
        }else{
            let data = newsList[indexPath.row - 1]
            let cellView = NewsCellView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight), news: data)
            cell.addSubview(cellView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(indexPath.row > 0 && indexPath.row <= newsList.count){
            let data = newsList[indexPath.row - 1]
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "NewsViewController") as! NewsViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                tableView.refreshToAble()
            }else{
                tableView.refreshToNormal()
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
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                pageNum = 1
                getList()
            }
        }
    }
    
    func showAdView(){
        let adView = AdView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        adView.show()
    }
}

