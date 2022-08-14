//
//  LuckyShareViewController.swift
//  lucky
//  我的晒单页
//  Created by Farmer Zhu on 2020/8/26.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyShareViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //晒单列表
    private var staticTableView: UITableView!
    
    //晒单数据
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
        //创建功能区
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取列表
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Share", comment: "")
        headView.rightButton.setTitle("Review", for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(toReview), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.backgroundGray()
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //取列表
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            //加载更多 静默
            loadingView.isHidden = true
        }
        LuckyHttpManager.getWithToken("front/userAccount/commentList", params: ["pageNum": pageNum, "pageSize": pageSize], success: { (data) in
            
            if(self.pageNum == 1){
                //取首页 清空原数据
                self.dataList = []
            }
            
            let dataList = data as! NSArray
            if(dataList.count > 0){
                if(dataList.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                var comments: [LuckyFrontUserCommentModel] = []
                
                for data in dataList{
                    let dataDic = data as! NSDictionary
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
    
    //晒单列表
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
                let cellView = LuckyShareCommentCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), comment: data)
                data.cellHeight = cellView.frame.height + 10 * screenScale
                cellView.goodsButton.addTarget(self, action: #selector(toDetail(_:)), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(cellView)
            }else{
                //底
                let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
                cell.contentView.addSubview(view)
            }
        }else{
            //无数据
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No reviews yet", comment: ""), image: UIImage(named: "image_table_clear"))
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
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去晒单 获奖订单页
    @objc func toReview(){
        let vc = LuckyOrderViewController()
        vc.selectedType = "winning"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去商品详情页
    @objc func toDetail(_ sender: UIButton){
        if let view = sender.superview?.superview as? LuckyShareCommentCellView{
            let vc = LuckyDetailViewController()
            vc.uuid = view.comment.goodsIssue
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
