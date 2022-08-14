//
//  ConcrenViewController.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class ConcrenViewController: UIViewController, UIScrollViewDelegate, LeftCategoryScrollViewDelegate{
    
    var headView: NavigationBackground!
    var leftScrollView: LeftCategoryScrollView!
    var rightScrollView: UIScrollView!
    
    var category: String = ""
    var teamList: Array<TeamModel> = []
    
    let cellHeight: CGFloat = 50 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "我的关注"
        self.view.addSubview(headView)
        
        var array = categoryArray.copy()
        array.insert("已关注_", at: 0)
        leftScrollView = LeftCategoryScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: 80 * screenScale, height: self.view.frame.height - (headView.frame.origin.y + headView.frame.height)), dataList: array)
        leftScrollView.buttonDelegate = self
        self.view.addSubview(leftScrollView)
        
        rightScrollView = UIScrollView(frame: CGRect(x: leftScrollView.frame.width, y: leftScrollView.frame.origin.y, width: screenWidth - leftScrollView.frame.width, height: leftScrollView.frame.height))
        rightScrollView.backgroundColor = UIColor.white
        self.view.addSubview(rightScrollView)
        
        getList()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
    
    func getList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        if(category != ""){
            HttpController.get("front/category/teamList", params: NSDictionary(dictionary: ["category": category]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.teamList = []
                    var teams: [TeamModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        teams.append(TeamModel(data: data))
                    }
                    self.teamList = teams
                    
                    self.rightScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: self.rightScrollView.frame.size), animated: false)
                    self.getUserConcrenList()
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            getUserConcrenList()
            HttpController.hideLoading(loadingView: loadingView)
        }
    }
    
    func getUserConcrenList(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        let dictionary: NSMutableDictionary = NSMutableDictionary()
        if(category != ""){
            dictionary.setValue(category, forKey: "category")
        }
        HttpController.getToken(data: { (token) in
            dictionary.setValue(token, forKey: "token")
            HttpController.get("front/concren/list", params: dictionary, data: { (data) in
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
                        var teams: Array<TeamModel> = []
                        for concren in concrenList{
                            let team = TeamModel()
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
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func updateRightScrollView(){
        let paddingLeft = 5 * screenScale
        let paddingTop = 10 * screenScale
        let paddingRight = 10 * screenScale
        
        let cellWidth: CGFloat = (rightScrollView.frame.width - paddingLeft * 3 - paddingRight) / 3
        
        rightScrollView.removeFromSuperview()
        
        rightScrollView = UIScrollView(frame: rightScrollView.frame)
        rightScrollView.delegate = self
        rightScrollView.backgroundColor = UIColor.white
        rightScrollView.showsVerticalScrollIndicator = false
        rightScrollView.showsHorizontalScrollIndicator = false
        rightScrollView.addRefreshView()
        
        for index in 0 ..< teamList.count{
            let team = teamList[index]
            let rowNum = index / 3
            let columnNum = (index + 3) % 3
            let cellButton = TeamCellButton(frame: CGRect(x:  paddingLeft + (cellWidth + paddingLeft) * CGFloat(columnNum), y: paddingTop + (cellWidth + paddingTop) * CGFloat(rowNum), width: cellWidth, height: cellWidth), team: team)
            cellButton.addTarget(self, action: #selector(setConcren(_:)), for: UIControl.Event.touchUpInside)
            rightScrollView.addSubview(cellButton)
        }
        
        let rowCount = (teamList.count + 2) / 3
        if(paddingTop + (cellWidth + paddingTop) * CGFloat(rowCount) > rightScrollView.frame.height){
            rightScrollView.contentSize = CGSize(width: rightScrollView.frame.width, height: paddingTop + (cellWidth + paddingTop) * CGFloat(rowCount))
        }else{
            rightScrollView.contentSize = CGSize(width: rightScrollView.frame.width, height: rightScrollView.frame.height + 1)
        }
        self.view.addSubview(rightScrollView)
    }
    
    @objc func setConcren(_ sender: TeamCellButton){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        if(!sender.team.isConcren){
            HttpController.getToken(data: { (token) in
                HttpController.get("front/concren/add", params: NSDictionary(dictionary: ["team": sender.team.uuid, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let concren = dataDictionary.object(forKey: "data") as! NSDictionary
                        sender.setConcren(concren: concren.object(forKey: "uuid") as! String)
                    }else{
                        HttpController.showTimeout(viewController: self)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }) { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                }
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            HttpController.getToken(data: { (token) in
                HttpController.get("front/concren/cancel", params: NSDictionary(dictionary: ["userConcern": sender.team.concren, "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        sender.cancelConcren()
                    }else{
                        HttpController.showTimeout(viewController: self)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }) { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                }
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }
    }
    
    func changeCategory(_ uuid: String) {
        category = uuid
        getList()
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == rightScrollView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                rightScrollView.refreshToAble()
            }else{
                rightScrollView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == rightScrollView){
            if(rightScrollView.getRefreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
