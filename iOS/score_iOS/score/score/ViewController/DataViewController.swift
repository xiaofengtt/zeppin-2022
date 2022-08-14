//
//  DataViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/28.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class DataViewController: UIViewController, CategoryScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {
    
    var headView : NavigationBackground!
    var categoryView: CategoryScrollView!
    var tableView: UITableView!
    
    var category: String = CategoryUuid.CSL
    var dataList: Array<CategoryTopscoreModel> = []
    
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
        HttpController.get("front/category/topscoreList", params: NSDictionary(dictionary: [ "category": category]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.dataList = []
                var topscores: [CategoryTopscoreModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    topscores.append(CategoryTopscoreModel(data: data))
                }
                self.dataList = topscores
                
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
        return dataList.count
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return tableCellHeight
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        view.backgroundColor = UIColor.white
        
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
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: view.frame.height - 1 * screenScale, width: screenWidth, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(splitLine)
        
        return view
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableCellHeight
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let data = dataList[indexPath.row]
        
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        let cellView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        
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
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
