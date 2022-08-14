//
//  LuckyStoryViewController.swift
//  lucky
//  晒单
//  Created by Farmer Zhu on 2020/8/12.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyStoryViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //列表
    private var staticTableView: UITableView!
    
    private var dataList: [LuckyFrontUserCommentModel] = []
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 20
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取数据
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.isHidden = true
        headView.titleLabel.text = NSLocalizedString("Story", comment: "")
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //取数据
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            loadingView.isHidden = true
        }
        LuckyHttpManager.getWithoutToken("front/comment/list", params: ["pageNum": pageNum, "pageSize": pageSize], success: { (data) in
            
            if(self.pageNum == 1){
                self.dataList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                var comments: [LuckyFrontUserCommentModel] = []
                
                for dataDic in dataArray{
                    comments.append(LuckyFrontUserCommentModel(data: dataDic))
                }
                self.dataList.append(contentsOf: comments)
            }else{
                self.noMoreFlag = true
            }
            self.staticTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList.count + 1
        }else{
            return 1
        }
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0){
            if(indexPath.row < dataList.count){
                return dataList[indexPath.row].cellHeight
            }else{
                return LuckyTableLoadingFooterView.height
            }
        }else{
            return tableView.frame.height
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(dataList.count > 0){
            //有数据
            if(indexPath.row < dataList.count){
                //数据行
                let data = dataList[indexPath.row]
                let cellView = LuckyCommentCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), comment: data)
                cellView.personalButton.addTarget(self, action: #selector(toPersonal(_:)), for: UIControl.Event.touchUpInside)
                data.cellHeight = cellView.frame.height
                cell.contentView.addSubview(cellView)
            }else{
                //更多行
                let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
                cell.contentView.addSubview(view)
            }
        }else{
            //无数据 无数据图文
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No srories yet", comment: ""), image: UIImage(named: "image_table_clear"))
            cell.contentView.addSubview(emptyView)
        }
        
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticTableView.refreshViewToAble()
            }else{
                staticTableView.refreshViewToNormal()
            }
            //加载更多
            if(staticTableView.contentOffset.y >= staticTableView.contentSize.height - staticTableView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                pageNum = 1
                noMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    //去个人中心
    @objc func toPersonal(_ sender: UIButton){
        if let commentCellView = sender.superview?.superview as? LuckyCommentCellView{
            let data = commentCellView.comment
            let vc = LuckyPersonalViewController()
            vc.userUuid = data!.frontUser
            vc.userName = data!.nickName
            vc.userShowId = "\(data!.frontUserShowId)"
            vc.userImageUrl = data!.imageUrl
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
